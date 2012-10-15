import java.awt.*;
import java.awt.event.*;
import javax.media.j3d.*;
import java.util.*;

public class KeyboardBehavior extends Behavior {

  private Maze maze;
  private IsometricCamera icCamera;

  public KeyboardBehavior( Maze maze, IsometricCamera icCamera )
  {
    this.maze = maze;
    this.icCamera = icCamera;
  }

  public void initialize () {
    this.wakeupOn( new WakeupOnAWTEvent( KeyEvent.KEY_PRESSED ) );
  }

  public void processStimulus ( Enumeration criteria ) {
    while ( criteria.hasMoreElements() ) {
      WakeupCriterion genericEvt = ( WakeupCriterion ) criteria.nextElement();
      if ( genericEvt instanceof WakeupOnAWTEvent ) {
        WakeupOnAWTEvent ev = ( WakeupOnAWTEvent ) genericEvt;
        AWTEvent[] events = ev.getAWTEvent();
        processAWTEvent( events );
      }
    }

    this.wakeupOn( new WakeupOnAWTEvent( KeyEvent.KEY_PRESSED ) );
  }

  /**
   *  Process a keyboard event
   */
  private void processAWTEvent ( AWTEvent[] events ) {
    for ( int loop = 0; loop < events.length; loop++ ) {
      if ( events[loop] instanceof KeyEvent ) {
        KeyEvent eventKey = ( KeyEvent ) events[loop];
        //  change the transformation; for example to zoom
        if ( eventKey.getID() == KeyEvent.KEY_PRESSED ) {
          //System.out.println("Keyboard is hit! " + eventKey);
          processKeyEvent( eventKey );
        }
      }
    }
  }

  public void processKeyEvent ( KeyEvent keyEvent ) {
    int keyCode = keyEvent.getKeyCode();

    int direction = -1;
    switch ( keyCode ) {
      case KeyEvent.VK_ESCAPE:
        System.exit(0);
      case KeyEvent.VK_UP:
        direction = Maze.MOVE_UP;
        break;
      case KeyEvent.VK_RIGHT:
        direction = Maze.MOVE_RIGHT;
        break;
      case KeyEvent.VK_DOWN:
        direction = Maze.MOVE_DOWN;
        break;
      case KeyEvent.VK_LEFT:
        direction = Maze.MOVE_LEFT;
        break;
    }

    if ( direction != -1 )
    {
      // Change direction according to the position of the camera in relation to the pawn
      double angle = icCamera.getCameraAngle();
      if (angle >= 45 && angle <= 135)
        direction += 3;
      else if (angle >= 135 && angle <= 225 )
        direction += 2;
      else if (angle >= 225 && angle <= 315 )
        direction += 1;
      direction %= 4;

      // Check movement validity
      if ( maze.IsMoveValid( direction ) )
        maze.MovePawn( direction );
    }
  }
}
