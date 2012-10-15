import java.util.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import java.awt.*;
import java.awt.event.*;

import com.sun.j3d.utils.behaviors.mouse.*;

/**
 * <p>Title: The aMAZEing Labyrinth</p>
 * <p>Description: Graphics opdracht HI3</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Lunar Studios</p>
 * @author Arjen van der Ende
 * @version 1.0
 */
public class IsometricCamera extends MouseBehavior
{
  Maze   maze;
  double x_angle, y_angle, tot_angle;
  double x_factor = .005;
  double y_factor = .03;

  /**
   * Creates a default isometric rotate behavior.
   **/
  public IsometricCamera ( Maze maze )
  {
    super( 0 );
    this.maze = maze;
  }

  /**
   * Initializes the isometric rotate behaviour.
   */
  public void initialize ()
  {
    super.initialize();
    x_angle = 0;
    y_angle = -0.5f;
    if ( ( flags & INVERT_INPUT ) == INVERT_INPUT )
    {
      invert = true;
      x_factor *= -1;
      y_factor *= -1;
    }
  }

  /**
   * Processes the 'pulse' that is sent to this behaviour when an AWT-event occurs
   * @param criteria Enumeration The criteria that has been met when this method was called
   */
  public void processStimulus ( Enumeration criteria )
  {
    WakeupCriterion wakeup;
    AWTEvent[] events;
    MouseEvent evt;

    while ( criteria.hasMoreElements() )
    {
      wakeup = ( WakeupCriterion ) criteria.nextElement();
      if ( wakeup instanceof WakeupOnAWTEvent )
      {
        events = ( ( WakeupOnAWTEvent ) wakeup ).getAWTEvent();
        if ( events.length > 0 )
        {
          evt = ( MouseEvent ) events[ events.length - 1 ];

          doProcess( evt );
        }
      }

      else if ( wakeup instanceof WakeupOnBehaviorPost )
      {
        while ( true )
        {
          // access to the queue must be synchronized
          synchronized ( mouseq )
          {
            if ( mouseq.isEmpty() )break;
            evt = ( MouseEvent ) mouseq.remove( 0 );
            // consolidate MOUSE_DRAG events

            while ( ( evt.getID() == MouseEvent.MOUSE_DRAGGED ) && !mouseq.isEmpty() &&
                    ( ( ( MouseEvent ) mouseq.get( 0 ) ).getID() ==
                      MouseEvent.MOUSE_DRAGGED ) )
            {
              evt = ( MouseEvent ) mouseq.remove( 0 );
            }
          }
          doProcess( evt );
        }
      }

    }
    wakeupOn( mouseCriterion );
  }

  /**
   * Process the AWT-event that has influence on this behaviour
   * @param evt MouseEvent The mouse-event that has taken place
   */
  void doProcess ( MouseEvent evt )
  {
    int id;
    int dx, dy;

    processMouseEvent( evt );
    if ( ( ( buttonPress ) && ( ( flags & MANUAL_WAKEUP ) == 0 ) ) ||
         ( ( wakeUp ) && ( ( flags & MANUAL_WAKEUP ) != 0 ) ) )
    {
      id = evt.getID();

      if ( ( id == MouseEvent.MOUSE_DRAGGED ) && !evt.isMetaDown() && !evt.isAltDown() )
      {
        x = evt.getX();
        dx = x_last - x;

        if ( !reset )
        {
          x_angle = dx * x_factor;
          tot_angle += x_angle;

          transformY.rotY( x_angle );
          transformGroup.getTransform( currXform );

          // Translate camera back to make rotation around pawn possible
          Vector3d currentpos = new Vector3d();
          currXform.get( currentpos );
          Vector3d player = maze.getPlayer();
          currentpos.sub( player );
          currXform.setTranslation( currentpos );

          if ( invert )
          {
            currXform.mul( currXform, transformX );
            currXform.mul( currXform, transformY );
          }
          else
          {
            currXform.mul( transformX, currXform );
            currXform.mul( transformY, currXform );
          }

          // Translate back to original camera-pawn distance
          currentpos = new Vector3d();
          currXform.get( currentpos );
          currentpos.add( player );
          currXform.setTranslation( currentpos );

          transformGroup.setTransform( currXform );
        }
        else
        {
          reset = false;
        }

        x_last = x;
        y_last = y;
      }
      else if ( id == MouseEvent.MOUSE_PRESSED )
      {
        x_last = evt.getX();
        y_last = evt.getY();
      }
    }
  }

  /**
   * Get the current camera angle in degrees in relation to the pawn. Ranges between 0 and 360
   * @return The current camera rotational angle
   */
  public double getCameraAngle()
  {
    tot_angle %= 2 * Math.PI;
    if (tot_angle < 0)
      tot_angle += 2 * Math.PI;
    return tot_angle * 180 / Math.PI;
  }

}
