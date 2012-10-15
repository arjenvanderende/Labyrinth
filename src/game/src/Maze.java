import java.awt.*;
import java.applet.*;
import java.io.*;
import java.util.*;

import javax.vecmath.*;
import javax.media.j3d.*;

import com.sun.j3d.utils.geometry.*;

import Block.*;

/**
 * <p>Title: The aMAZEing Labyrinth</p>
 * <p>Description: Graphics opdracht HI3</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Lunar Studios</p>
 * @author Arjen van der Ende
 * @version 1.0
 */
public class Maze
{
  private static final boolean debug = false;  // For debug purposes only

  protected int       stepCount;           // The steps set in the maze
  protected boolean   endReached;          // Has the end been reached

  protected Dimension size;                // Maze dimensions
  protected Point     player;              // Player position inside the maze
  private   Point     offset;              // Upperleft block position in the file
  private   Point     end;                 // Ending block inside the maze

  protected BranchGroup        bgMaze;     // Parent for all entities
  protected TransformGroup[][] tgBlocks;   // All blocks of the maze
  protected TransformGroup     tgCamera;   // The camera
  protected TransformGroup     tgPlayer;   // The player
  protected TransformGroup     tgArrow;    // Arrow figure
  protected TransformGroup     tgLight;    // Arrow figure
  protected MovementThread     move;       // Pawn-movement thread
  protected TimeThread         timer;      // Time-control
  protected Texture[]          txTextures; // Block texture array
  protected Text2D             t2dSteps;   // Step Count

  /**
   * Integer that represents upward movement
   */
  protected static final int MOVE_UP    = 0;

  /**
   * Integer that represents movement to the right
   */
  protected static final int MOVE_RIGHT = 1;

  /**
   * Integer that represents downward movement
   */
  protected static final int MOVE_DOWN  = 2;

  /**
   * Integer that represents movement to the left
   */
  protected static final int MOVE_LEFT  = 3;

  /**
   * Construct a new maze object
   */
  public Maze()
  {
    this.bgMaze     = null;
    this.size       = new Dimension( -1, -1 );
    this.player     = new Point( -1, -1 );
    this.end        = new Point( -1, -1 );
    this.offset     = new Point( 0, 0 );
    this.tgBlocks   = null;
    this.tgPlayer   = null;
    this.move       = null;
    this.timer      = null;
    this.t2dSteps   = null;
    this.txTextures = new Texture[6];
  }

  /**
   * Load a maze-level file (*.mze)
   * @param filename String The filename of the level
   * @return Is the level succesfully loaded?
   */
  public boolean Load( String filename )
  {
    try
    {
      stepCount = 0;
      endReached = false;

      bgMaze = new BranchGroup();
      bgMaze.setCapability( BranchGroup.ALLOW_CHILDREN_EXTEND );
      bgMaze.setCapability( BranchGroup.ALLOW_DETACH );

      FileInputStream fin = new FileInputStream( "levels\\" + filename );
      Properties propMaze = new Properties();
      propMaze.load( fin );

      System.out.println("Maze loading - Textures - Begin");
      LoadTextures();
      System.out.println("Maze loading - Textures - Done");

      String[] maze_props = ReadProperty( propMaze, "SIZE" );
      size.width  = Integer.parseInt( maze_props[0] );
      size.height = Integer.parseInt( maze_props[1] );
      tgBlocks    = new TransformGroup[size.width][size.height];
      String[] maze_offsets = ReadProperty( propMaze, "OFFSET" );
      offset.x = Integer.parseInt( maze_offsets[0] );
      offset.y = Integer.parseInt( maze_offsets[1] );

      String[] maze_start = ReadProperty( propMaze, "START" );
      bgMaze.addChild( CreateBlock( maze_start ) );
      player.x = Integer.parseInt( maze_start[0] ) - offset.x;
      player.y = Integer.parseInt( maze_start[1] ) - offset.y;
      String[] maze_end = ReadProperty( propMaze, "END" );
      bgMaze.addChild( CreateBlock( maze_end ) );
      bgMaze.addChild( CreateBeacon( maze_end ) );
      end.x = Integer.parseInt( maze_end[0] ) - offset.x;
      end.y = Integer.parseInt( maze_end[1] ) - offset.y;

      System.out.println("Maze loading - Header - Done");

      int blocknr = 0;
      while ( true )
      {
        String block_name = "BLOCK" + blocknr++;
        String[] block_properties = ReadProperty( propMaze, block_name );
        if ( block_properties == null )
          break;
        bgMaze.addChild( CreateBlock( block_properties ) );
        System.out.println("Maze loading - BLOCK" + (blocknr -1) + " - Done");
      }
    }
    catch ( Exception ex )
    {
      bgMaze = null;
      if ( debug )
        System.out.println( ex.toString() );
      return false;
    }
    return true;
  }

  /**
   * Load the block textures into the texture array
   */
  private void LoadTextures()
  {
    // Load all textures
    Component observer = new Canvas();
    txTextures[0] = ImageLoader.LoadTexture( ".\\skins\\block_straight.jpg", observer );
    System.out.println("Maze loading - Texture 1 - Done");
    txTextures[1] = ImageLoader.LoadTexture( ".\\skins\\block_corner.jpg", observer );
    System.out.println("Maze loading - Texture 2 - Done");
    txTextures[2] = ImageLoader.LoadTexture( ".\\skins\\block_crossing.jpg", observer );
    System.out.println("Maze loading - Texture 3 - Done");
    txTextures[3] = ImageLoader.LoadTexture( ".\\skins\\block_split.jpg", observer );
    System.out.println("Maze loading - Texture 4 - Done");
    txTextures[4] = ImageLoader.LoadTexture( ".\\skins\\block_deadend.jpg", observer );
    System.out.println("Maze loading - Texture 5 - Done");
    txTextures[5] = ImageLoader.LoadTexture( ".\\skins\\block_empty.jpg", observer );
    System.out.println("Maze loading - Texture 6 - Done");
  }

  /**
   * Get the specified property from the properties-file
   * @param properties Properties The properties-file
   * @param property String The property to be retrieved
   * @return The read property from the file
   */
  private String[] ReadProperty ( Properties properties, String property )
  {
    String property_read = properties.getProperty( property );
    if ( property_read != null )
    {
      String[] properties_read = property_read.split( " " );
      return properties_read;
    }
    return null;
  }

  /**
   * Create a block with the specified parameters
   * @param properties String[] The block creation parameters.
   * @throws Exception Invalid parameters specified
   * @return The newly created block
   */
  private TransformGroup CreateBlock ( String[] properties ) throws Exception
  {
    // Retrieve current block properties
    String block_type = properties[2];
    int block_x = Integer.parseInt( properties[0] ) - offset.x;
    int block_y = Integer.parseInt( properties[1] ) - offset.y;
    int block_angle = Integer.parseInt( properties[3] );

    // Create the block according to the set properties
    Block block;
    if ( block_type.compareTo( "STRAIGHT" ) == 0 ) {
      block = new BlockStraight( txTextures[0] );
    }
    else if ( block_type.compareTo( "CORNER" ) == 0 ) {
      block = new BlockCorner( txTextures[1] );
    }
    else if ( block_type.compareTo( "CROSSING" ) == 0 ) {
      block = new BlockCrossing( txTextures[2] );
    }
    else if ( block_type.compareTo( "T-SPLIT" ) == 0 ) {
      block = new BlockSplit( txTextures[3] );
    }
    else if ( block_type.compareTo( "DEADEND" ) == 0 ) {
      block = new BlockDeadEnd( txTextures[4] );
    }
    else if ( block_type.compareTo( "EMPTY" ) == 0 ) {
      block = new BlockEmpty( txTextures[5] );
    }
    else {
      throw new Exception( "Invalid block type!" );
    }

    // rotate access bits
    for ( int i = 0; i < block_angle / 90; i++ )
      block.rotateAccess();

    // Calculate block translation matrix
    Transform3D t3dTranslate = new Transform3D();
    t3dTranslate.setTranslation( new Vector3f( block_x - ( ( size.width - 1 ) / 2f ),
      0.0f, block_y - ( ( size.height - 1 ) / 2f )));
    Transform3D t3dRotate = new Transform3D();
    t3dRotate.rotY( ( block_angle / 180f ) * ( float ) Math.PI );
    t3dTranslate.mul( t3dRotate );

    tgBlocks[block_x][block_y] = new TransformGroup( t3dTranslate );
    tgBlocks[block_x][block_y].addChild( block );
    tgBlocks[block_x][block_y].setCapability( TransformGroup.ALLOW_CHILDREN_READ );
    tgBlocks[block_x][block_y].setCapability( TransformGroup.ALLOW_TRANSFORM_READ );
    return tgBlocks[block_x][block_y];
  }

  /**
   * Create a block with the specified parameters
   * @param properties String[] The block creation parameters.
   * @throws Exception Invalid parameters specified
   * @return The newly created block
   */
  private TransformGroup CreateBeacon ( String[] properties ) throws Exception
  {
    // Retrieve current block properties
    String block_type = properties[2];
    int block_x = Integer.parseInt( properties[0] ) - offset.x;
    int block_y = Integer.parseInt( properties[1] ) - offset.y;

    // Calculate block translation matrix
    Transform3D t3dTranslate = new Transform3D();
    t3dTranslate.setTranslation( new Vector3f(
      block_x - ( ( size.width - 1 ) / 2f ),
      5.0f,
      block_y - ( ( size.height - 1 ) / 2f ))
    );

    Appearance apBeacon = new Appearance();
    ColoringAttributes caBeacon = new ColoringAttributes( 1, 1, 1, ColoringAttributes.NICEST );
    apBeacon.setColoringAttributes( caBeacon );
    TransparencyAttributes taBeacon = new TransparencyAttributes( TransparencyAttributes.NICEST, .4f );
    apBeacon.setTransparencyAttributes( taBeacon );

    Appearance apBeacon2 = new Appearance();
    ColoringAttributes caBeacon2 = new ColoringAttributes( 1, .8f, .4f, ColoringAttributes.NICEST );
    apBeacon2.setColoringAttributes( caBeacon2 );
    TransparencyAttributes taBeacon2 = new TransparencyAttributes( TransparencyAttributes.NICEST, .4f );
    apBeacon2.setTransparencyAttributes( taBeacon2 );

    PointLight dirMaze = new PointLight();
    dirMaze.setColor( new Color3f( 1.0f, 1.0f, 1.0f ) );
    dirMaze.setInfluencingBounds( new BoundingSphere( new Point3d(), 1000.0f ) );

    TransformGroup tgLight = Transform.createRotator( Transform.AXIS_Y, 4000, false );
    tgLight.addChild( dirMaze );

    TransformGroup tgBeacon = new TransformGroup( t3dTranslate );
//    tgBeacon.addChild( new Cylinder( 0.20f, 10, apBeacon ) );
//    tgBeacon.addChild( new Cylinder( 0.25f, 10, apBeacon2 ) );
    tgBeacon.addChild( tgLight );
    return tgBeacon;
  }


  /**
   * Move the player pawn in the specified direction, while the end is not
   * reached.
   * @param direction int The direction to move the pawn
   */
  public void MovePawn( int direction )
  {
    if ( !endReached )
    {
      // Animate pawn movement
      if ( move == null || move.isAlive() == false )
      {
        switch ( direction )
        {
          case MOVE_UP:
            player.y -= 1;
            break;
          case MOVE_RIGHT:
            player.x += 1;
            break;
          case MOVE_DOWN:
            player.y += 1;
            break;
          case MOVE_LEFT:
            player.x -= 1;
            break;
        }
        move = new MovementThread( this, tgPlayer, tgCamera, direction );
        move.start();
        // Update step count
        String count = ++stepCount + "";
        while ( count.length() < 3 )
          count = "0" + count;
        //t2dSteps.setString( count );
        if ( stepCount == 999 )
          endReached = true;
        // Play a sound
        AudioManager manager = AudioManager.getInstance();
        manager.PlaySound( "sound2.wav" );
      }
    }
  }

  /**
   * Check if movement to a certain direction is possible
   * @param direction int The direction that needs to be checked
   * @return Is movement valid in thet specified direction?
   */
  public boolean IsMoveValid( int direction )
  {
    Block block_current;
    Block block_next;

    try {
      block_current = ( Block ) tgBlocks[player.x][player.y].getChild( 0 );
      switch ( direction ) {
        // Check movement up
        case MOVE_UP:
          if ( ( block_current.getAccess() & Block.UP ) != 0 ) {
            block_next = ( Block ) tgBlocks[player.x][player.y - 1].getChild( 0 );
            if ( ( block_next.getAccess() & Block.DOWN ) != 0 )
              return true;
          }
          break;
        // Check movement to the right
        case MOVE_RIGHT:
          if ( ( block_current.getAccess() & Block.RIGHT ) != 0 ) {
            block_next = ( Block ) tgBlocks[player.x + 1][player.y].getChild( 0 );
            if ( ( block_next.getAccess() & Block.LEFT ) != 0 )
              return true;
          }
          break;
        // Check movement down
        case MOVE_DOWN:
          if ( ( block_current.getAccess() & Block.DOWN ) != 0 ) {
            block_next = ( Block ) tgBlocks[player.x][player.y + 1].getChild( 0 );
            if ( ( block_next.getAccess() & Block.UP ) != 0 )
              return true;
          }
          break;
        // Check movement to the left
        case MOVE_LEFT:
          if ( ( block_current.getAccess() & Block.LEFT ) != 0 ) {
            block_next = ( Block ) tgBlocks[player.x - 1][player.y].getChild( 0 );
            if ( ( block_next.getAccess() & Block.RIGHT ) != 0 )
              return true;
          }
          break;
      }
    }
    catch ( Exception ex ) {
      if ( debug )
        System.out.println( "Array out of bounds exception" );
    }
    return false;
  }

  /**
   * Set the player pawn geometry
   * @param pawn TransformGroup The TransformGroup that contains the pawn geometry
   */
  public void SetPawnTransformGroup( TransformGroup pawn )
  {
    if ( tgPlayer != null )
      bgMaze.removeChild( tgPlayer );

    Transform3D t3dPlayer = new Transform3D();
    t3dPlayer.setTranslation(
      new Vector3d(
        player.x - ( size.width - 1 ) / 2.0,
        Block.floor_height,
        player.y - ( size.height - 1) / 2.0
      )
    );

    tgPlayer = pawn;
    tgPlayer.setTransform( t3dPlayer );
    tgPlayer.setCapability( TransformGroup.ALLOW_TRANSFORM_READ );
    tgPlayer.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );

    tgArrow = new TransformGroup();
    tgArrow.addChild( new Arrow( false ) );
    tgArrow.addChild( new Arrow( true ) );
    tgArrow.setCapability( TransformGroup.ALLOW_TRANSFORM_READ );
    tgArrow.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
    TransformGroup tgArrowHolder = Transform.Translate( tgArrow, new Vector3f( 0, 1.5f, 0) );
    //tgPlayer.addChild( tgArrowHolder );

    BranchGroup bgPawn = new BranchGroup();
    bgPawn.addChild( tgPlayer );
    bgMaze.addChild( bgPawn );

    UpdateArrow();
  }

  /**
   * Update the rotational value of the arrow above the player. The arrow always
   * points to the end-position of the maze
   */
  public void UpdateArrow()
  {
    /*
    Transform3D t3dPlayer = new Transform3D();
    tgPlayer.getTransform( t3dPlayer );
    Vector3d v3fArrow = new Vector3d();
    t3dPlayer.get( v3fArrow );
    Transform3D t3dEnd   = new Transform3D();
    tgBlocks[end.x][end.y].getTransform( t3dEnd );
    Vector3d v3fEnd   = new Vector3d();
    t3dEnd.get( v3fEnd );

    double dx = v3fEnd.x - v3fArrow.x;
    double dy = v3fArrow.z - v3fEnd.z;

    Transform3D t3dArrow = new Transform3D();
    tgArrow.getTransform( t3dArrow );
    Transform3D t3dRotation = new Transform3D();
    if ( Math.round( dx * 10 ) == 0 )
    {
      if ( Math.round( dy * 10 ) == 0 )
      {
        t3dRotation.setTranslation( new Vector3d(100, 100, 100) );
      }
      else
        if ( dy > 0 )
          t3dRotation.rotY( 0 );
        else
          t3dRotation.rotY( Math.PI );
    }
    else
      if ( dx > 0 )
        t3dRotation.rotY( Math.atan( dy / dx ) - Math.PI / 2);
      else
        t3dRotation.rotY( Math.atan( dy / dx ) + Math.PI / 2);
    tgArrow.setTransform( t3dRotation );
    */
  }

  /**
   * Set the camera TransformGroup. The camera is offset, when the player pawn
   * moves so the distance between the two stays the same.
   * @param camera TransformGroup
   */
  public void setCameraTransformGroup( TransformGroup camera )
  {
    tgCamera = camera;
  }

  /**
   * Get the dimension of the maze
   * @return The dimensions of the maze
   */
  public Dimension GetDimension()
  {
    return size;
  }

  /**
   * Retrieve the maze branchgroup
   * @return The maze branchgroup
   */
  public BranchGroup GetBranchGroup()
  {
    return bgMaze;
  }

  /**
   * Get the absolute player position
   * @return The position of the player in the maze
   */
  public Vector3d getPlayer()
  {
    Transform3D t3dPlayer = new Transform3D();
    tgPlayer.getTransform( t3dPlayer );
    Vector3d v3dPosition = new Vector3d();
    t3dPlayer.get( v3dPosition );
    return v3dPosition;
  }

  /**
   * Check if the player has reached the end, and if so stop the timer and the
   * background music, and play the winner-song :)
   */
  public void checkDone()
  {
    if ( player.x == end.x && player.y == end.y )
    {
      StopTimer();
      endReached = true;
      AudioManager manager = AudioManager.getInstance();
      manager.StopMusic();
      manager.PlaySound( "soundend.mid" );
      System.out.println("End reached");
    }
  }

  /**
   * Set the timer that is going to measure the play-time
   * @param timer TimeThread The timer
   */
  public void setTimer( TimeThread timer )
  {
    this.timer = timer;
  }

  /**
   * Start the play-time timer
   */
  public void StartTimer()
  {
    timer.start();
  }

  /**
   * Stop the timer to determine the end-time
   */
  public void StopTimer()
  {
    timer.stopThread();
  }

  /**
   * Set the step counter text object that is displayed in the HUD. The
   * text object is updated at every step in the maze.
   * @param t2dSteps The text object that displays the step count.
   */
  public void setStepCounter( Text2D t2dSteps )
  {
    this.t2dSteps = t2dSteps;
    this.t2dSteps.setCapability( Text2D.ALLOW_APPEARANCE_READ );
    this.t2dSteps.getAppearance().setCapability( Appearance.ALLOW_TEXTURE_READ );
    this.t2dSteps.getAppearance().getTexture().setCapability( Texture.ALLOW_IMAGE_WRITE );
  }
}
