import java.awt.*;
import java.io.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.TextureLoader;

public class Menu extends Thread
{
  private SimpleUniverse simpleU;
  private Main           callback;
  private BranchGroup    menu;
  private Appearance     apLoad;
  private String         filename;

  public Menu( Main callback, SimpleUniverse simpleU )
  {
    this.callback = callback;
    this.simpleU = simpleU;
    // Create block appearance
    Component observer = new Canvas();
    Texture texture = ImageLoader.LoadTexture( ".\\skins\\loading.jpg", observer );
    TextureAttributes taAttributes = new TextureAttributes();
    taAttributes.setTextureMode( TextureAttributes.MODULATE );
    apLoad = new Appearance();
    apLoad.setTexture( texture );
    apLoad.setTextureAttributes( taAttributes );
  }

  public BranchGroup createSceneGraph ()
  {
    BoundingSphere behaveBounds = new BoundingSphere();

    // Creeer de wortel van de branch graph
    menu = new BranchGroup();
    menu.setCapability( BranchGroup.ALLOW_DETACH );
    menu.setCapability( BranchGroup.ALLOW_CHILDREN_EXTEND );
    menu.setCapability( BranchGroup.ALLOW_CHILDREN_WRITE );

    BranchGroup bgMainMenu = new BranchGroup();
    bgMainMenu.setCapability( BranchGroup.ALLOW_DETACH );
    addMenuItem(bgMainMenu, "LABYRINTH", "", new Vector3f( -13.0f, 10.0f, -50.0f), new Color3f(0, 0.7f, 0) );
    addMenuItem(bgMainMenu, "Start Game", "load", new Vector3f( -12.5f, 0.0f, -60.0f));
    addMenuItem(bgMainMenu, "Quit", "quit", new Vector3f(-5.0f, -6.0f, -60.0f));
    PickMouseBehavior pick = new PickMouseBehavior( this, menu, simpleU.getCanvas(), behaveBounds );

    Canvas observer = new Canvas();
    ImageComponent2D icBackground = new TextureLoader( ".\\skins\\background.jpg", observer ).getImage();
    Background bgBackground = new Background( icBackground );
    bgBackground.setApplicationBounds( new BoundingSphere() );

    menu.addChild(bgMainMenu);
    menu.addChild(bgBackground);
    menu.addChild(pick);
    menu.compile();

    return menu;
  }

  private void addMenuItem(BranchGroup main, String name, String command, Vector3f position )
  {
    addMenuItem( main, name, command, position, new Color3f(0, 0.4f, 0) );
  }

  private void addMenuItem(BranchGroup main, String name, String command, Vector3f position, Color3f color)
  {
    Transform3D trans = new Transform3D();
    trans.setTranslation( position );
    TransformGroup tg = new TransformGroup(trans);

    Appearance apText = new Appearance();
    ColoringAttributes caText = new ColoringAttributes();
    caText.setColor( color );
    apText.setColoringAttributes( caText );
    Font3D font = new Font3D(new Font("Verdana", Font.BOLD, 4), new FontExtrusion());
    Text3D text = new Text3D(font, name);
    Shape3D shape = new Shape3D(text, apText);

    tg.setUserData( command );
    tg.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    tg.addChild(shape);
    main.addChild(tg);
  }

  public void LoadLevel ( String filename ) {
     // Create a loading branchgroup
    BranchGroup bgLoading = new BranchGroup();
    bgLoading.setCapability( BranchGroup.ALLOW_DETACH );
    TransformGroup tgRotator = Transform.createRotator( Transform.AXIS_Y, 8000, true );
    tgRotator.addChild( new Sphere( 1, Sphere.GENERATE_TEXTURE_COORDS, apLoad ) );
    bgLoading.addChild( tgRotator );

    // Replace the menu with the loading block
    simpleU.getLocale().replaceBranchGraph( menu, bgLoading );
    simpleU.getViewingPlatform().setNominalViewingTransform();
    menu = bgLoading;

    // Set the file to be loaded
    this.filename = filename;

    // Load the maze level
    Thread loader = new Thread( this );
    loader.start();
  }

  public void SelectLevel()
  {
    BranchGroup bgLoadMenu = new BranchGroup();
    bgLoadMenu.setCapability( BranchGroup.ALLOW_DETACH );
    addMenuItem(bgLoadMenu, "SELECT MAZE", "", new Vector3f( -15.0f, 10.0f, -50.0f), new Color3f(0, 0.7f, 0) );

    String path = callback.getDocumentBase().getPath() + "levels\\";
    int filenr = 0;
    File currentDir = new File( path );
    String[] files = currentDir.list();
    for ( int i = 0; i < files.length; i++ )
    {
      if ( files[i].toLowerCase().endsWith( ".mze" ) )
      {
        String display_name = files[i].substring( 0, files[i].length() -4 );
        addMenuItem(bgLoadMenu, display_name, "L" + files[i] ,
                    new Vector3f( -7, 6 - (filenr * 3.25f ), -60.0f));
        filenr++;
      }
    }
    menu.removeChild( 0 );
    menu.addChild(bgLoadMenu);
  }

  public void run()
  {
    BranchGroup objRoot = callback.LoadMaze( filename );
    simpleU.getLocale().replaceBranchGraph( menu, objRoot );
  }



}
