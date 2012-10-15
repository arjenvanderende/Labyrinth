import javax.media.j3d.*;
import javax.vecmath.*;

public class Transform {

  static int AXIS_X = 1;
  static int AXIS_Y = 2;
  static int AXIS_Z = 3;

  public static TransformGroup Translate ( Node child, Vector3f translation ) {
    // Create a Transform3D object to move the child
    Transform3D t3dTranslation = new Transform3D();
    t3dTranslation.setTranslation( translation );
    // Add child to transformgroup and return
    TransformGroup tgTranslate = new TransformGroup( t3dTranslation );
    tgTranslate.addChild( child );
    return tgTranslate;
  }

  public static TransformGroup Rotate ( Node child, int axis, float angle ) {
    // Create Transform3D rotation
    Transform3D t3dRotate = new Transform3D();
    if ( axis == AXIS_X ) {
      t3dRotate.rotX( angle );
    }
    else if ( axis == AXIS_Y ) {
      t3dRotate.rotY( angle );
    }
    else if ( axis == AXIS_Z ) {
      t3dRotate.rotZ( angle );
    }
    // Add child to transformgroup and return
    TransformGroup tgRotate = new TransformGroup( t3dRotate );
    tgRotate.addChild( child );
    return tgRotate;
  }

  public static TransformGroup createRotator ( int axis, int speed, boolean inverse ) {
    // Creeer een transformeer-groep voor het draaien
    TransformGroup rotatorTG = new TransformGroup();

    // Zorg ervoor dat de TransformGroup tijdens runtime verandert kan worden
    rotatorTG.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );

    // Creeer een Alpha-object dat zorgt voor de tijd-functie
    Alpha rotatorAlpha = new Alpha( -1, speed );

    // Creeer een as waarom de draaiing plaatsvindt
    Transform3D xAs = new Transform3D();
    if ( axis == AXIS_X ) {
      xAs.rotZ( Math.PI / 2.0f );
    }
    else if ( axis == AXIS_Y ) {
      xAs.rotY( Math.PI / 2.0f );
    }
    else if ( axis == AXIS_Z ) {
      xAs.rotX( Math.PI / 2.0f );

      // Creeer een nieuw gedrag (Behavior) voor het draaien
    }
    RotationInterpolator rotator = new RotationInterpolator(
        rotatorAlpha, rotatorTG, xAs, 0.0f, ( float ) Math.PI * 2.0f * ( inverse == true ? -1 : 1 )
        );

    // Creeer een gebied waarbinnen het gedrag actief is
    BoundingSphere rotatorBounds = new BoundingSphere();
    rotator.setSchedulingBounds( rotatorBounds );

    // Hang het gedrag aan de transformatie-groep voor het draaien
    rotatorTG.addChild( rotator );

    return rotatorTG;
  }
}
