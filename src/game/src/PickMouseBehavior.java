import javax.media.j3d.*;
import com.sun.j3d.utils.picking.*;
import com.sun.j3d.utils.picking.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.media.j3d.*;

public class PickMouseBehavior extends Behavior
{
  protected PickCanvas pickCanvas;

  protected Menu callback;

  protected WakeupCriterion[] conditions;
  protected WakeupOr wakeupCondition;
  protected boolean buttonPress = false;
  protected boolean mouseDragged = false;

  protected MouseEvent mevent;

  public PickMouseBehavior(Menu callback, BranchGroup root, Canvas3D canvas, Bounds bounds) {
    super();
    pickCanvas = new PickCanvas(canvas, root);
    this.setSchedulingBounds(bounds);
    this.callback = callback;
  }

  public void initialize() {
    // Set the wake-up condition to be a mouse up event
    conditions = new WakeupCriterion[2];
    conditions[0] = new WakeupOnAWTEvent(Event.MOUSE_UP);
    conditions[1] = new WakeupOnAWTEvent(Event.MOUSE_DRAG);
    wakeupCondition = new WakeupOr(conditions);
    wakeupOn(wakeupCondition);
  }

  private void processMouseEvent(MouseEvent evt) {
    buttonPress = false;

    // Check if mouse-event is a mouse drag event
    if (evt.getID() == MouseEvent.MOUSE_DRAGGED) {
      mouseDragged = true;
      return;
    }
    // Check if mouse-event is a mouse release event
    else if (evt.getID() == MouseEvent.MOUSE_RELEASED) {
      buttonPress = true;
      return;
    }
  }

  public void processStimulus(Enumeration criteria) {
    WakeupCriterion wakeup;
    AWTEvent[] evt = null;
    int xpos = 0, ypos = 0;

    // Check if criteria contains an AWT-events
    while (criteria.hasMoreElements()) {
      wakeup = (WakeupCriterion) criteria.nextElement();
      if (wakeup instanceof WakeupOnAWTEvent)
        evt = ( (WakeupOnAWTEvent) wakeup).getAWTEvent();
    }

    // Check if the trigger event is a mouse event
    if (evt[0] instanceof MouseEvent) {
      mevent = (MouseEvent) evt[0];
      processMouseEvent( (MouseEvent) evt[0]);
      xpos = mevent.getPoint().x;
      ypos = mevent.getPoint().y;
    }

    // Check if the button was pressed
    if (buttonPress) {
      // If the mouse was dragged, don't update
      if (mouseDragged)
        mouseDragged = false;
      else
        updateScene(xpos, ypos);
    }
    wakeupOn(wakeupCondition);
  }

  public void updateScene(int xpos, int ypos) {
    TransformGroup tg = null;

    if ( (mevent.getID() & mevent.MOUSE_RELEASED) == mevent.MOUSE_RELEASED) {
      // Get picked transformgroup
      pickCanvas.setShapeLocation(xpos, ypos);
      PickResult pr = pickCanvas.pickClosest();

      // Check if the node has the necessary capabilities
      if ( (pr != null) &&
          ( (tg = (TransformGroup) pr.getNode(PickResult.TRANSFORM_GROUP)) != null) &&
          (tg.getCapability(TransformGroup.ALLOW_TRANSFORM_READ)) &&
          (tg.getCapability(TransformGroup.ALLOW_TRANSFORM_WRITE))) {
        // Show the user data
        String command = (String)tg.getUserData();
        if ( command.startsWith( "L" ) )
          callback.LoadLevel( command.substring( 1, command.length() ) );
        else if ( command.compareTo( "load" ) == 0 )
          callback.SelectLevel();
        else if ( command.compareTo( "quit" ) == 0 )
          System.exit( 0 );
      }
    }
  }
}
