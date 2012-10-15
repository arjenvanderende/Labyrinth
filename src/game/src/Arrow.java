import javax.media.j3d.*;
import javax.vecmath.*;

public class Arrow extends Shape3D
{

  public Arrow( boolean outline )
  {
    if ( outline )
    {
      addGeometry( CreateOutlineGeometry() );
    }
    else
    {
      addGeometry( CreateGeometry() );
      setAppearance( CreateAppearance() );
    }
  }

  public Geometry CreateGeometry()
  {
    int iVertexTotalCount = 25;
    int iVertexStripCount[] = { 5, 4, 16 };

    // Creeer een lijst en vul deze met coordinaten
    Point3f VertexCoordinates[] =
    {
      // Strip 01: Top
      new Point3f( -.2f,    0f, -.1f),
      new Point3f( -.1f,    0f, -.1f),
      new Point3f(   0f,    0f, -.3f),
      new Point3f(  .1f,    0f, -.1f),
      new Point3f(  .2f,    0f, -.1f),
      new Point3f( -.1f,    0f, -.1f),
      new Point3f( -.1f,    0f,  .3f),
      new Point3f(  .1f,    0f, -.1f),
      new Point3f(  .1f,    0f,  .3f),

      // Strip 02: Middle
      new Point3f(   0f,    0f, -.3f ),
      new Point3f(   0f, -.05f, -.3f ),
      new Point3f( -.2f,    0f, -.1f ),
      new Point3f( -.2f, -.05f, -.1f ),
      new Point3f( -.1f,    0f, -.1f ),
      new Point3f( -.1f, -.05f, -.1f ),
      new Point3f( -.1f,    0f,  .3f ),
      new Point3f( -.1f, -.05f,  .3f ),
      new Point3f(  .1f,    0f,  .3f ),
      new Point3f(  .1f, -.05f,  .3f ),
      new Point3f(  .1f,    0f, -.1f ),
      new Point3f(  .1f, -.05f, -.1f ),
      new Point3f(  .2f,    0f, -.1f ),
      new Point3f(  .2f, -.05f, -.1f ),
      new Point3f(   0f,    0f, -.3f ),
      new Point3f(   0f, -.05f, -.3f ),
    };

    // creeer een nieuw TriangleArray object
    TriangleStripArray triList = new TriangleStripArray(iVertexTotalCount,
        TriangleStripArray.COORDINATES, iVertexStripCount);

    // vul het TriangleArray object met de coordinaten
    triList.setCoordinates(0, VertexCoordinates);

    return triList;
  }

  public Geometry CreateOutlineGeometry()
  {
    int iVertexTotalCount = 14 + 16;
    int iVertexStripCount[] = { 8, 2, 2, 2, 2, 2, 2, 2, 8 };

    // Creeer een lijst en vul deze met coordinaten
    Point3f VertexCoordinates[] =
    {
      // Strip 01: Top
      new Point3f(   0f,    0f, -.3f),
      new Point3f( -.2f,    0f, -.1f),
      new Point3f( -.1f,    0f, -.1f),
      new Point3f( -.1f,    0f,  .3f),
      new Point3f(  .1f,    0f,  .3f),
      new Point3f(  .1f,    0f, -.1f),
      new Point3f(  .2f,    0f, -.1f),
      new Point3f(   0f,    0f, -.3f),

      // Strip 02: Middle
      new Point3f(   0f, -.05f, -.3f ),
      new Point3f(   0f, 0f, -.3f ),
      new Point3f( -.2f, -.05f, -.1f ),
      new Point3f( -.2f, 0f, -.1f ),
      new Point3f( -.1f, -.05f, -.1f ),
      new Point3f( -.1f, 0f, -.1f ),
      new Point3f( -.1f, -.05f, .3f ),
      new Point3f( -.1f, 0f, .3f ),
      new Point3f(  .1f, -.05f, .3f ),
      new Point3f(  .1f, 0f, .3f ),
      new Point3f(  .1f, -.05f, -.1f ),
      new Point3f(  .1f, 0f, -.1f ),
      new Point3f(  .2f, -.05f, -.1f ),
      new Point3f(  .2f, 0f, -.1f ),

      // Strip 03: Bottom
      new Point3f(   0f, -.05f, -.3f),
      new Point3f( -.2f, -.05f, -.1f),
      new Point3f( -.1f, -.05f, -.1f),
      new Point3f( -.1f, -.05f,  .3f),
      new Point3f(  .1f, -.05f,  .3f),
      new Point3f(  .1f, -.05f, -.1f),
      new Point3f(  .2f, -.05f, -.1f),
      new Point3f(   0f, -.05f, -.3f),
    };

    Color3f green = new Color3f ( 0.0f, 1.0f, 0.0f );
    Color3f lineColor[] = {
      green, green, green, green, green, green, green, green,
      green, green, green, green, green, green, green,
      green, green, green, green, green, green, green,
      green, green, green, green, green, green, green, green,
    };

    // creeer een nieuw TriangleArray object
    LineStripArray lineList = new LineStripArray(iVertexTotalCount,
        LineStripArray.COORDINATES | LineStripArray.COLOR_3 , iVertexStripCount);

    // vul het TriangleArray object met de coordinaten
    lineList.setCoordinates(0, VertexCoordinates);
    lineList.setColors(0, lineColor);

    return lineList;
  }

  public Appearance CreateAppearance( )
  {
    Appearance apTemplate = new Appearance();
    // Set coloring attributes
    ColoringAttributes caArrow = new ColoringAttributes( 0, .5f, 0,
      ColoringAttributes.NICEST );
    apTemplate.setColoringAttributes( caArrow );
    // Set transparancy attributes
    TransparencyAttributes taArrow = new TransparencyAttributes(
      TransparencyAttributes.NICEST, .4f );
    apTemplate.setTransparencyAttributes( taArrow );
    return apTemplate;
  }
}
