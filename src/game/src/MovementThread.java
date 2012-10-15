import javax.media.j3d.*;

import javax.vecmath.*;

public class MovementThread extends Thread
{
  protected static final int MOVE_UP    = 0;
  protected static final int MOVE_RIGHT = 1;
  protected static final int MOVE_DOWN  = 2;
  protected static final int MOVE_LEFT  = 3;

  private static final int NR_OF_STEPS = 10;
  private static final double STEP_OFFSET = 1.0 / NR_OF_STEPS;

  private Maze           maze;
  private TransformGroup tgMover;
  private TransformGroup tgCamera;
  private int            direction;

  public MovementThread( Maze maze, TransformGroup tgMover, TransformGroup tgCamera, int direction )
  {
    this.maze    = maze;
    this.tgMover = tgMover;
    this.tgCamera = tgCamera;
    this.direction = direction;
  }

  public void run()
  {
    Vector3d movement = null;

    //
    switch ( direction )
    {
      case MOVE_UP:
        movement = new Vector3d( 0.0, 0.0, -STEP_OFFSET );
        break;
      case MOVE_RIGHT:
        movement = new Vector3d( STEP_OFFSET, 0.0, 0.0 );
        break;
      case MOVE_DOWN:
        movement = new Vector3d( 0.0, 0.0, STEP_OFFSET );
        break;
      case MOVE_LEFT:
        movement = new Vector3d( -STEP_OFFSET, 0.0, 0.0 );
        break;
    }

    //
    for ( int i = 0; i < NR_OF_STEPS; i++ )
    {
      // Move the pawn
      Vector3d v3dCurrent = new Vector3d();
      Transform3D t3dCurrent = new Transform3D();
      tgMover.getTransform( t3dCurrent );
      t3dCurrent.get( v3dCurrent );
      v3dCurrent.add( movement );
      t3dCurrent.setTranslation( v3dCurrent );
      tgMover.setTransform( t3dCurrent );

      // Move the camera
      tgCamera.getTransform( t3dCurrent );
      t3dCurrent.get( v3dCurrent );
      v3dCurrent.add( movement );
      t3dCurrent.setTranslation( v3dCurrent );
      tgCamera.setTransform( t3dCurrent );

      maze.UpdateArrow();

      try {
        sleep( 25 );
      }
      catch ( Exception ex ) {}
    }
    maze.checkDone();
  }

}
