// De benodigde klassen
import java.awt.*;

import java.applet.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;

public class Main extends Applet {

  private final double CAMERA_DISTANCE  = 20.0;
  private final double CAMERA_ANGLE_DEG = 45;

  private SimpleUniverse simpleU;

  private BranchGroup menu;

  private Canvas3D canvas3d;
  private IsometricCamera icCamera;

  public BranchGroup LoadMaze( String filename )
  {
    // Create the branch graph root
    BranchGroup objRoot = new BranchGroup();

    // Create and load the maze
    Maze maze = new Maze();
    if ( maze.Load( filename ) == false )
    {
      System.out.println( "Error while loading \"" + filename + "\"!" );
      System.exit(1);
    }
    maze.SetPawnTransformGroup( CreatePlayerPawn() );

    // Setup camera and lighting
    SetupCamera( objRoot, simpleU, maze );
    System.out.println("Camera setup - Done");
    SetupLighting( objRoot );
    System.out.println("Lighting setup - Done");
    //SetupHUD( simpleU, maze );
    System.out.println("HUD setup - Done");

    // Add keyboard behaviour to root
    KeyboardBehavior keyboard = new KeyboardBehavior( maze, icCamera );
    keyboard.setSchedulingBounds( new BoundingSphere() );
    maze.GetBranchGroup().addChild( keyboard );
    maze.GetBranchGroup().compile();
    System.out.println("Keyboard setup - Done");

    Background bgBackground = new Background();
    bgBackground.setApplicationBounds( new BoundingSphere() );
    bgBackground.setColor( new Color3f( 0.05f, 0.05f, 0.2f ) );
    objRoot.addChild( bgBackground );
    System.out.println("Background setup - Done");

    // Compile the Root BranchGroup
    objRoot.addChild( maze.GetBranchGroup() );
    objRoot.compile();

    // Play new music
    int nr = ( int ) ( Math.random() * 5 ) + 1;
    AudioManager manager = AudioManager.getInstance();
    manager.PlayMusic( "music" + nr + ".mid" );
    //maze.StartTimer();
    System.out.println("Music setup - Done");

    return objRoot;
  }

  /**
   * Create the player pawn shape
   * @return The created pawn shape
   */
  public TransformGroup CreatePlayerPawn()
  {
    float pionRadius = 0.10f;
    float pionHeight = 0.75f;

    Appearance apPawn = new Appearance();
    Material mPawn = new Material();
    mPawn.setDiffuseColor( new Color3f( 1.0f, 0.0f, 0.0f ) );
    apPawn.setMaterial( mPawn );

    TransformGroup pawn = new TransformGroup();
    pawn.addChild( Transform.Translate( new Cone( pionRadius * 1.75f, pionHeight, apPawn ), new Vector3f( 0.0f, pionHeight / 2, 0.0f )));
    pawn.addChild( Transform.Translate( new Sphere( pionRadius * 1.2f, apPawn ), new Vector3f( 0.0f, pionHeight - pionRadius / 2, 0.0f )));

    Transform3D t3dFoot = new Transform3D();
    t3dFoot.setScale( new Vector3d( 1.0, 0.3, 1.0 ) );
    TransformGroup tgFoot = new TransformGroup( t3dFoot );
    tgFoot.addChild( new Sphere( pionRadius * 2.5f, apPawn ) );
    pawn.addChild( tgFoot );

    Transform3D t3dFootTranslate = new Transform3D();
    t3dFootTranslate.setTranslation( new Vector3d( 0.0, 0.75 * pionRadius * 2.5, 0.0 ) );
    t3dFoot.mul( t3dFootTranslate );
    TransformGroup tgFoot2 = new TransformGroup( t3dFoot );
    tgFoot2.addChild( new Sphere( pionRadius * 2.2f, apPawn ) );
    pawn.addChild( tgFoot2 );

    t3dFootTranslate = new Transform3D();
    t3dFootTranslate.setTranslation( new Vector3d( 0.0, pionHeight * 0.75, 0.0 ) );
    t3dFoot = new Transform3D();
    t3dFoot.setScale( new Vector3d( 1.0, 0.3, 1.0 ) );
    t3dFootTranslate.mul( t3dFoot );
    TransformGroup tgMiddle = new TransformGroup( t3dFootTranslate );
    tgMiddle.addChild( new Sphere( pionRadius * 0.9f, apPawn ) );
    pawn.addChild( tgMiddle );

    return pawn;
  }

  public void SetupCamera( BranchGroup root, SimpleUniverse suUniverse, Maze maze )
  {
    // Move the camera back under a 45 degrees angle
    TransformGroup tgCamera = suUniverse.getViewingPlatform().getViewPlatformTransform();

    Transform3D t3dTranslate = new Transform3D();
    Transform3D t3dRotate    = new Transform3D();
    t3dTranslate.setTranslation( new Vector3d( 0.0, 0.0, CAMERA_DISTANCE ) );
    t3dRotate.rotX( -CAMERA_ANGLE_DEG * ( Math.PI / 180 ) );
    t3dRotate.mul( t3dTranslate );
    tgCamera.setTransform( t3dRotate );

    // Create the camera
    icCamera = new IsometricCamera( maze );
    icCamera.setTransformGroup( tgCamera );
    icCamera.setSchedulingBounds( new BoundingSphere() );
    root.addChild( icCamera );

    // Offset camera to player position
    Transform3D t3dCamera = new Transform3D();
    tgCamera.getTransform( t3dCamera );
    Transform3D t3dTranslation = new Transform3D();
    Vector3d player = maze.getPlayer();
    t3dTranslation.setTranslation( player );
    t3dTranslation.mul( t3dCamera );
    tgCamera.setTransform( t3dTranslation );

    maze.setCameraTransformGroup( tgCamera );
  }

  public void SetupLighting( BranchGroup objRoot )
  {
    AmbientLight ambMaze = new AmbientLight();
    ambMaze.setColor( new Color3f( 0.2f, 0.2f, 0.9f ) );
    ambMaze.setInfluencingBounds( new BoundingSphere( new Point3d(), 1000.0f ) );
    objRoot.addChild( ambMaze );
  }

  public void SetupHUD( SimpleUniverse suUniverse, Maze maze )
  {
    // Create text
    TransformGroup tgCamera = suUniverse.getViewingPlatform().getViewPlatformTransform();
    BranchGroup bgHUD = new BranchGroup();
    Text2D t2dText = new Text2D( "Time: ", new Color3f( 0, 1, 0 ), "Verdana", 36, Font.BOLD );
    bgHUD.addChild( Transform.Translate( t2dText, new Vector3f(-2.3f, 1.5f, -6) ));
    Text2D t2dTime = new Text2D( "0:00 ", new Color3f( 0, 1, 0 ), "Verdana", 36, Font.BOLD );
    TransformGroup tgTime = Transform.Translate( t2dTime, new Vector3f(-1.8f, 1.5f, -6) );
    bgHUD.addChild( tgTime );

    Text2D t2dSteps = new Text2D( "Step Count: ", new Color3f( 0, 1, 0 ), "Verdana", 36, Font.BOLD );
    bgHUD.addChild( Transform.Translate( t2dSteps, new Vector3f(1.0f, 1.5f, -6) ));
    Text2D t2dStepCount = new Text2D( "000", new Color3f( 0, 1, 0 ), "Verdana", 36, Font.BOLD );
    bgHUD.addChild( Transform.Translate( t2dStepCount, new Vector3f(2.0f, 1.5f, -6) ));
    maze.setStepCounter( t2dStepCount );

    // Set timer
    TimeThread thTime = new TimeThread( t2dTime );
    maze.setTimer( thTime );

    Appearance apTemplate = new Appearance();
    // Set coloring attributes
    ColoringAttributes caArrow = new ColoringAttributes( 0, .5f, 0,
      ColoringAttributes.NICEST );
    apTemplate.setColoringAttributes( caArrow );
    // Set transparancy attributes
    TransparencyAttributes taArrow = new TransparencyAttributes(
      TransparencyAttributes.NICEST, .4f );
    apTemplate.setTransparencyAttributes( taArrow );
    bgHUD.addChild( Transform.Translate( new Box( 3f, 0.1f, 0.01f, apTemplate ), new Vector3f( 0, 1.58f, -6f ) ));

    tgCamera.addChild( bgHUD );
  }

  public Main() {
    setLayout( new BorderLayout() );
    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
    canvas3d = new Canvas3D( config );
    add( "Center", canvas3d );

    // De SimpleUniverse klasse bevat alle elementen die nodig
    // zijn voor de rendering van een virtuele wereld
    simpleU = new SimpleUniverse( canvas3d );

    // Creeer een scene gebaseerd op de createSceneGraph methode
    Menu menu = new Menu( this, simpleU );
    simpleU.addBranchGraph( menu.createSceneGraph() );

    // Creeer de audio manager
    AudioManager manager = new AudioManager( this );
  }

  /**
   * Play the menu music
   */
  public void PlayMusic()
  {
    int nr = ( int ) ( Math.random() * 2 ) + 6;
    AudioManager manager = AudioManager.getInstance();
    manager.PlayMusic( "music" + nr + ".mid" );
  }

  //  Het volgende laat dit programma zowel als een applicatie
  //  als een applet uitvoeren
  public static void main ( String[] args ) {
    Main main = new Main();
    new FullscreenFrame( main, 800, 600 );
    main.PlayMusic();
  }

}
