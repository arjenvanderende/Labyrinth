package Block;

import javax.media.j3d.*;
import javax.vecmath.*;

public class BlockCorner extends Block {

  public BlockCorner ( Texture texture ) {
    access = RIGHT | DOWN;

    addGeometry( createFloorSideGeometry() );
    addGeometry( createTopGeometry() );
    addGeometry( createBigWallGeometry() );
    addGeometry( createPillarGeometry() );
    setAppearance( createAppearance( texture ) );
  }

  private Geometry createFloorSideGeometry () {
    int   iVertexCount = 4 * 4;
    float width_offset = wall_width / 2;

    // Specify vertex coordinates
    Point3f p3fVertexCoordinates[] = {
        // front
        new Point3f( -width_offset, 0 + floor_height, width_offset ),
        new Point3f( -width_offset, 0               , width_offset ),
        new Point3f(  width_offset, 0               , width_offset ),
        new Point3f(  width_offset, 0 + floor_height, width_offset ),
        // right
        new Point3f( width_offset, 0 + floor_height,  width_offset ),
        new Point3f( width_offset, 0               ,  width_offset ),
        new Point3f( width_offset, 0               , -width_offset ),
        new Point3f( width_offset, 0 + floor_height, -width_offset ),
        // back
        new Point3f(  width_offset, 0 + floor_height, -width_offset ),
        new Point3f(  width_offset, 0               , -width_offset ),
        new Point3f( -width_offset, 0               , -width_offset ),
        new Point3f( -width_offset, 0 + floor_height, -width_offset ),
        // left
        new Point3f( -width_offset, 0 + floor_height, -width_offset ),
        new Point3f( -width_offset, 0               , -width_offset ),
        new Point3f( -width_offset, 0               ,  width_offset ),
        new Point3f( -width_offset, 0 + floor_height,  width_offset ),
    };

    // Specify normal vectors
    Vector3f v3fPolygonNormals[] = {
      // front
      new Vector3f( 0.0f, 0.0f, 1.0f ), new Vector3f( 0.0f, 0.0f, 1.0f ),
      new Vector3f( 0.0f, 0.0f, 1.0f ), new Vector3f( 0.0f, 0.0f, 1.0f ),
      // right
      new Vector3f( 1.0f, 0.0f, 0.0f ), new Vector3f( 1.0f, 0.0f, 0.0f ),
      new Vector3f( 1.0f, 0.0f, 0.0f ), new Vector3f( 1.0f, 0.0f, 0.0f ),
      // back
      new Vector3f( 0.0f, 0.0f, -1.0f ), new Vector3f( 0.0f, 0.0f, -1.0f ),
      new Vector3f( 0.0f, 0.0f, -1.0f ), new Vector3f( 0.0f, 0.0f, -1.0f ),
      // left
      new Vector3f( -1.0f, 0.0f, 0.0f ), new Vector3f( -1.0f, 0.0f, 0.0f ),
      new Vector3f( -1.0f, 0.0f, 0.0f ), new Vector3f( -1.0f, 0.0f, 0.0f ),
    };

    float offset = 1 / 3f;

    // Specify texture coordinates
    TexCoord2f tc2fTextureCoordinates[] = {
      new TexCoord2f( 0.0f  , floor_height ),
      new TexCoord2f( 0.0f  , 0.0f ),
      new TexCoord2f( offset, 0.0f ),
      new TexCoord2f( offset, floor_height ),

      new TexCoord2f( offset    , floor_height ),
      new TexCoord2f( offset    , 0.0f ),
      new TexCoord2f( offset * 2, 0.0f ),
      new TexCoord2f( offset * 2, floor_height ),

      new TexCoord2f( 0.0f  , floor_height ),
      new TexCoord2f( 0.0f  ,  0.0f ),
      new TexCoord2f( offset, 0.0f ),
      new TexCoord2f( offset, floor_height ),

      new TexCoord2f( offset    , floor_height ),
      new TexCoord2f( offset    , 0.0f ),
      new TexCoord2f( offset * 2, 0.0f ),
      new TexCoord2f( offset * 2, floor_height ),
    };

    QuadArray quadList = new QuadArray( iVertexCount, QuadArray.COORDINATES |
      QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS);
    quadList.setCoordinates( 0, p3fVertexCoordinates );
    quadList.setNormals( 0, v3fPolygonNormals );
    quadList.setTextureCoordinates( 0, 0, tc2fTextureCoordinates );

    return quadList;
  }

  private Geometry createTopGeometry () {
    int   iVertexCount = 4 * 4;
    float width_offset = wall_width / 2;

    Point3f p3fVertexCoordinates[] = {
      // wall top
      new Point3f( -width_offset            , wall_height, -width_offset ),
      new Point3f( -width_offset            , wall_height,  width_offset ),
      new Point3f( -width_offset + thickness, wall_height,  width_offset ),
      new Point3f( -width_offset + thickness, wall_height, -width_offset + thickness),

      new Point3f( -width_offset            , wall_height, -width_offset),
      new Point3f( -width_offset + thickness, wall_height, -width_offset + thickness),
      new Point3f(  width_offset            , wall_height, -width_offset + thickness),
      new Point3f(  width_offset            , wall_height, -width_offset),

      // floor
      new Point3f( -width_offset, floor_height, -width_offset ),
      new Point3f( -width_offset, floor_height,  width_offset ),
      new Point3f(  width_offset, floor_height,  width_offset ),
      new Point3f(  width_offset, floor_height, -width_offset ),
      // pillar
      new Point3f( width_offset - thickness, wall_height, width_offset - thickness),
      new Point3f( width_offset - thickness, wall_height, width_offset ),
      new Point3f( width_offset            , wall_height, width_offset ),
      new Point3f( width_offset            , wall_height, width_offset - thickness ),
    };

    Vector3f v3fPolygonNormals[] = {
      new Vector3f( 0.0f, 1.0f, 0.0f), new Vector3f( 0.0f, 1.0f, 0.0f),
      new Vector3f( 0.0f, 1.0f, 0.0f), new Vector3f( 0.0f, 1.0f, 0.0f),
      new Vector3f( 0.0f, 1.0f, 0.0f), new Vector3f( 0.0f, 1.0f, 0.0f),
      new Vector3f( 0.0f, 1.0f, 0.0f), new Vector3f( 0.0f, 1.0f, 0.0f),

      new Vector3f( 0.0f, 1.0f, 0.0f), new Vector3f( 0.0f, 1.0f, 0.0f),
      new Vector3f( 0.0f, 1.0f, 0.0f), new Vector3f( 0.0f, 1.0f, 0.0f),

      new Vector3f( 0.0f, 1.0f, 0.0f), new Vector3f( 0.0f, 1.0f, 0.0f),
      new Vector3f( 0.0f, 1.0f, 0.0f), new Vector3f( 0.0f, 1.0f, 0.0f),
    };

    float offset = 2 / 3f;
    float offset_x = 0.0625f;
    float offset_y = 0.1875f;

    TexCoord2f tc2fTextureCoordinates[] = {
        new TexCoord2f( offset            , 1.0f ),
        new TexCoord2f( offset            , 0.0f ),
        new TexCoord2f( offset + offset_x , 0.0f ),
        new TexCoord2f( offset + offset_x , 1.0f - offset_y ),

        new TexCoord2f( offset            , 1.0f ),
        new TexCoord2f( offset + offset_x , 1.0f - offset_y),
        new TexCoord2f( 1.0f              , 1.0f - offset_y),
        new TexCoord2f( 1.0f              , 1.0f ),

        new TexCoord2f( offset, 1.0f ),
        new TexCoord2f( offset, 0.0f ),
        new TexCoord2f( 1.0f , 0.0f ),
        new TexCoord2f( 1.0f , 1.0f ),

        new TexCoord2f( 1.0f - offset_x, offset_y ),
        new TexCoord2f( 1.0f - offset_x, 0.0f ),
        new TexCoord2f( 1.0f           , 0.0f ),
        new TexCoord2f( 1.0f           , offset_y ),
    };

    QuadArray quadList = new QuadArray( iVertexCount, QuadArray.COORDINATES |
      QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS);
    quadList.setCoordinates( 0, p3fVertexCoordinates );
    quadList.setNormals( 0, v3fPolygonNormals );
    quadList.setTextureCoordinates( 0, 0, tc2fTextureCoordinates );

    return quadList;
  }

  private Geometry createBigWallGeometry () {
    int   iVertexCount = 6 * 4;
    float width_offset = wall_width / 2;

    Point3f p3fVertexCoordinates[] = {
      // outer back side
      new Point3f(  width_offset,  wall_height, -width_offset ),
      new Point3f(  width_offset, floor_height, -width_offset ),
      new Point3f( -width_offset, floor_height, -width_offset ),
      new Point3f( -width_offset,  wall_height, -width_offset ),
      // outer left side
      new Point3f( -width_offset,  wall_height, -width_offset ),
      new Point3f( -width_offset, floor_height, -width_offset ),
      new Point3f( -width_offset, floor_height,  width_offset ),
      new Point3f( -width_offset,  wall_height,  width_offset ),
      // outer front side
      new Point3f( -width_offset,              wall_height,  width_offset ),
      new Point3f( -width_offset,             floor_height,  width_offset ),
      new Point3f( -width_offset + thickness, floor_height,  width_offset ),
      new Point3f( -width_offset + thickness,  wall_height,  width_offset ),
      // inner left side
      new Point3f( -width_offset + thickness,  wall_height,  width_offset ),
      new Point3f( -width_offset + thickness, floor_height,  width_offset ),
      new Point3f( -width_offset + thickness, floor_height, -width_offset + thickness),
      new Point3f( -width_offset + thickness,  wall_height, -width_offset + thickness),
      // inner back side
      new Point3f( -width_offset + thickness,  wall_height, -width_offset + thickness),
      new Point3f( -width_offset + thickness, floor_height, -width_offset + thickness),
      new Point3f(  width_offset            , floor_height, -width_offset + thickness),
      new Point3f(  width_offset            ,  wall_height, -width_offset + thickness),
      // outer right side
      new Point3f(  width_offset,  wall_height, -width_offset + thickness),
      new Point3f(  width_offset, floor_height, -width_offset + thickness),
      new Point3f(  width_offset, floor_height, -width_offset),
      new Point3f(  width_offset,  wall_height, -width_offset),
    };

    Vector3f v3fPolygonNormals[] = {
      new Vector3f( 0.0f, 0.0f, -1.0f ), new Vector3f( 0.0f, 0.0f, -1.0f ),
      new Vector3f( 0.0f, 0.0f, -1.0f ), new Vector3f( 0.0f, 0.0f, -1.0f ),

      new Vector3f( -1.0f, 0.0f, 0.0f ), new Vector3f( -1.0f, 0.0f, 0.0f ),
      new Vector3f( -1.0f, 0.0f, 0.0f ), new Vector3f( -1.0f, 0.0f, 0.0f ),

      new Vector3f( 0.0f, 0.0f, 1.0f ), new Vector3f( 0.0f, 0.0f, 1.0f ),
      new Vector3f( 0.0f, 0.0f, 1.0f ), new Vector3f( 0.0f, 0.0f, 1.0f ),

      new Vector3f( 1.0f, 0.0f, 0.0f ), new Vector3f( 1.0f, 0.0f, 0.0f ),
      new Vector3f( 1.0f, 0.0f, 0.0f ), new Vector3f( 1.0f, 0.0f, 0.0f ),

      new Vector3f( 0.0f, 0.0f, 1.0f ), new Vector3f( 0.0f, 0.0f, 1.0f ),
      new Vector3f( 0.0f, 0.0f, 1.0f ), new Vector3f( 0.0f, 0.0f, 1.0f ),

      new Vector3f( 1.0f, 0.0f, 0.0f ), new Vector3f( 1.0f, 0.0f, 0.0f ),
      new Vector3f( 1.0f, 0.0f, 0.0f ), new Vector3f( 1.0f, 0.0f, 0.0f ),
    };

    // Specify texture coordinates
    float offset   = 1 / 3f;
    float offset_x = 0.0625f;
    float offset_y = 0.0625f;

    TexCoord2f tc2fTextureCoordinates[] = {
      new TexCoord2f( 0.0f  , 1.0f ),
      new TexCoord2f( 0.0f  , offset_y ),
      new TexCoord2f( offset, offset_y ),
      new TexCoord2f( offset, 1.0f ),

      new TexCoord2f( 0.0f  , 1.0f ),
      new TexCoord2f( 0.0f  , offset_y ),
      new TexCoord2f( offset, offset_y ),
      new TexCoord2f( offset, 1.0f ),

      new TexCoord2f( offset           , 1.0f ),
      new TexCoord2f( offset           , offset_y ),
      new TexCoord2f( offset + offset_x, offset_y ),
      new TexCoord2f( offset + offset_x, 1.0f ),

      new TexCoord2f( offset + offset_x, 1.0f ),
      new TexCoord2f( offset + offset_x, offset_y ),
      new TexCoord2f( 2 * offset       , offset_y ),
      new TexCoord2f( 2 * offset       , 1.0f ),

      new TexCoord2f( offset + offset_x, 1.0f ),
      new TexCoord2f( offset + offset_x, offset_y ),
      new TexCoord2f( 2 * offset       , offset_y ),
      new TexCoord2f( 2 * offset       , 1.0f ),

      new TexCoord2f( offset           , 1.0f ),
      new TexCoord2f( offset           , offset_y ),
      new TexCoord2f( offset + offset_x, offset_y ),
      new TexCoord2f( offset + offset_x, 1.0f ),
    };

    //
    QuadArray quadList = new QuadArray( iVertexCount, QuadArray.COORDINATES |
      QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS );
    quadList.setCoordinates( 0, p3fVertexCoordinates );
    quadList.setNormals( 0, v3fPolygonNormals );
    quadList.setTextureCoordinates( 0, 0, tc2fTextureCoordinates );

    return quadList;
  }

  private Geometry createPillarGeometry () {
    int   iVertexCount = 4 * 4;
    float width_offset = wall_width / 2;

    // Specify polygon vertices
    Point3f p3fVertexCoordinates[] = {
      // front
      new Point3f( width_offset - thickness,  wall_height, width_offset ),
      new Point3f( width_offset - thickness, floor_height, width_offset ),
      new Point3f( width_offset            , floor_height, width_offset ),
      new Point3f( width_offset            ,  wall_height, width_offset ),
      // right
      new Point3f( width_offset,  wall_height, width_offset ),
      new Point3f( width_offset, floor_height, width_offset ),
      new Point3f( width_offset, floor_height, width_offset - thickness),
      new Point3f( width_offset,  wall_height, width_offset - thickness),
      // back
      new Point3f( width_offset            ,   wall_height, width_offset - thickness),
      new Point3f( width_offset            ,  floor_height, width_offset - thickness),
      new Point3f( width_offset - thickness,  floor_height, width_offset - thickness),
      new Point3f( width_offset - thickness,   wall_height, width_offset - thickness),
      // left
      new Point3f( width_offset - thickness,   wall_height, width_offset - thickness),
      new Point3f( width_offset - thickness,  floor_height, width_offset - thickness),
      new Point3f( width_offset - thickness,  floor_height, width_offset),
      new Point3f( width_offset - thickness,   wall_height, width_offset),
    };

    // Specify polygon normals
    Vector3f v3fPolygonNormals[] = {
      // outer front
      new Vector3f( 0.0f, 0.0f, 1.0f ), new Vector3f( 0.0f, 0.0f, 1.0f ),
      new Vector3f( 0.0f, 0.0f, 1.0f ), new Vector3f( 0.0f, 0.0f, 1.0f ),
      // inner front
      new Vector3f( 1.0f, 0.0f, 0.0f ), new Vector3f( 1.0f, 0.0f, 0.0f ),
      new Vector3f( 1.0f, 0.0f, 0.0f ), new Vector3f( 1.0f, 0.0f, 0.0f ),
      // inner back
      new Vector3f( 0.0f, 0.0f, -1.0f ), new Vector3f( 0.0f, 0.0f, -1.0f ),
      new Vector3f( 0.0f, 0.0f, -1.0f ), new Vector3f( 0.0f, 0.0f, -1.0f ),
      // outer back
      new Vector3f( -1.0f, 0.0f, 0.0f ), new Vector3f( -1.0f, 0.0f, 0.0f ),
      new Vector3f( -1.0f, 0.0f, 0.0f ), new Vector3f( -1.0f, 0.0f, 0.0f ),
    };

    // Specify texture coordinates
    float offset   = 1 / 3f;
    float offset_x = 0.0625f;
    float offset_y = 0.0625f;

    TexCoord2f tc2fTextureCoordinates[] = {
      // outer front
      new TexCoord2f( offset           , 1.0f ),
      new TexCoord2f( offset           , offset_y ),
      new TexCoord2f( offset + offset_x, offset_y ),
      new TexCoord2f( offset + offset_x, 1.0f ),
      // inner front
      new TexCoord2f( offset           , 1.0f ),
      new TexCoord2f( offset           , offset_y ),
      new TexCoord2f( offset + offset_x, offset_y ),
      new TexCoord2f( offset + offset_x, 1.0f ),
      // inner back
      new TexCoord2f( offset           , 1.0f ),
      new TexCoord2f( offset           , offset_y ),
      new TexCoord2f( offset + offset_x, offset_y ),
      new TexCoord2f( offset + offset_x, 1.0f ),
      // outer back
      new TexCoord2f( offset           , 1.0f ),
      new TexCoord2f( offset           , offset_y ),
      new TexCoord2f( offset + offset_x, offset_y ),
      new TexCoord2f( offset + offset_x, 1.0f ),
    };

    // Create quad and return
    QuadArray quadList = new QuadArray( iVertexCount, QuadArray.COORDINATES |
      QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS );
    quadList.setCoordinates( 0, p3fVertexCoordinates );
    quadList.setNormals( 0, v3fPolygonNormals );
    quadList.setTextureCoordinates( 0, 0, tc2fTextureCoordinates );
    return quadList;
  }

}
