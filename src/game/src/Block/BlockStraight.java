package Block;

import javax.media.j3d.*;
import javax.vecmath.*;

public class BlockStraight extends Block {

  public BlockStraight ( Texture texture ) {
    access = RIGHT | LEFT;

    addGeometry( createFloorSideGeometry() );
    addGeometry( createFloorTopGeometry() );
    addGeometry( createWallSideGeometry() );
    addGeometry( createWallFrontGeometry() );
    addGeometry( createWallTopGeometry() );
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

    // Specify texture coordinates
    TexCoord2f tc2fTextureCoordinates[] = {
        new TexCoord2f( 0.0f, floor_height ),
        new TexCoord2f( 0.0f, 0.0f ),
        new TexCoord2f( 0.25f, 0.0f ),
        new TexCoord2f( 0.25f, floor_height ),
        new TexCoord2f( 0.25f, floor_height ),
        new TexCoord2f( 0.25f, 0.0f ),
        new TexCoord2f( 0.5f, 0.0f ),
        new TexCoord2f( 0.5f, floor_height ),
        new TexCoord2f( 0.0f, floor_height ),
        new TexCoord2f( 0.0f, 0.0f ),
        new TexCoord2f( 0.25f, 0.0f ),
        new TexCoord2f( 0.25f, floor_height ),
        new TexCoord2f( 0.25f, floor_height ),
        new TexCoord2f( 0.25f, 0.0f ),
        new TexCoord2f( 0.5f, 0.0f ),
        new TexCoord2f( 0.5f, floor_height ),
    };

    QuadArray quadList = new QuadArray( iVertexCount, QuadArray.COORDINATES |
      QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS);
    quadList.setCoordinates( 0, p3fVertexCoordinates );
    quadList.setNormals( 0, v3fPolygonNormals );
    quadList.setTextureCoordinates( 0, 0, tc2fTextureCoordinates );

    return quadList;
  }

  private Geometry createFloorTopGeometry () {
    int   iVertexCount = 1 * 4;
    float width_offset = wall_width / 2;

    Point3f p3fVertexCoordinates[] = {
      new Point3f( -width_offset, 0 + floor_height, -width_offset ),
      new Point3f( -width_offset, 0 + floor_height,  width_offset ),
      new Point3f(  width_offset, 0 + floor_height,  width_offset ),
      new Point3f(  width_offset, 0 + floor_height, -width_offset ),
    };

    Vector3f v3fPolygonNormals[] = {
      new Vector3f( 0.0f, 1.0f, 0.0f), new Vector3f( 0.0f, 1.0f, 0.0f),
      new Vector3f( 0.0f, 1.0f, 0.0f), new Vector3f( 0.0f, 1.0f, 0.0f),
    };

    TexCoord2f tc2fTextureCoordinates[] = {
        new TexCoord2f( 1.0f , 1.0f ),
        new TexCoord2f( 0.5f , 1.0f ),
        new TexCoord2f( 0.5f , 0.0f ),
        new TexCoord2f( 1.0f , 0.0f ),
    };

    QuadArray quadList = new QuadArray( iVertexCount, QuadArray.COORDINATES |
      QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS);
    quadList.setCoordinates( 0, p3fVertexCoordinates );
    quadList.setNormals( 0, v3fPolygonNormals );
    quadList.setTextureCoordinates( 0, 0, tc2fTextureCoordinates );

    return quadList;
  }

  private Geometry createWallSideGeometry () {
    int iVertexCount = 4 * 4;

    float width_offset = wall_width / 2;

    Point3f p3fVertexCoordinates[] = {
        // right side - left
        new Point3f( width_offset,  wall_height, width_offset ),
        new Point3f( width_offset, floor_height, width_offset ),
        new Point3f( width_offset, floor_height, width_offset - thickness ),
        new Point3f( width_offset,  wall_height, width_offset - thickness ),
        // right side - right
        new Point3f( width_offset,  wall_height, -width_offset + thickness ),
        new Point3f( width_offset, floor_height, -width_offset + thickness ),
        new Point3f( width_offset, floor_height, -width_offset ),
        new Point3f( width_offset,  wall_height, -width_offset ),
        // left side - left
        new Point3f( -width_offset,  wall_height, width_offset - thickness ),
        new Point3f( -width_offset, floor_height, width_offset - thickness ),
        new Point3f( -width_offset, floor_height, width_offset ),
        new Point3f( -width_offset,  wall_height, width_offset ),
        // left side - right
        new Point3f( -width_offset,  wall_height, -width_offset ),
        new Point3f( -width_offset, floor_height, -width_offset ),
        new Point3f( -width_offset, floor_height, -width_offset + thickness ),
        new Point3f( -width_offset,  wall_height, -width_offset + thickness ),
    };

    Vector3f v3fPolygonNormals[] = {
      new Vector3f( 1.0f, 0.0f, 0.0f ), new Vector3f( 1.0f, 0.0f, 0.0f ),
      new Vector3f( 1.0f, 0.0f, 0.0f ), new Vector3f( 1.0f, 0.0f, 0.0f ),

      new Vector3f( 1.0f, 0.0f, 0.0f ), new Vector3f( 1.0f, 0.0f, 0.0f ),
      new Vector3f( 1.0f, 0.0f, 0.0f ), new Vector3f( 1.0f, 0.0f, 0.0f ),

      new Vector3f( -1.0f, 0.0f, 0.0f ), new Vector3f( -1.0f, 0.0f, 0.0f ),
      new Vector3f( -1.0f, 0.0f, 0.0f ), new Vector3f( -1.0f, 0.0f, 0.0f ),

      new Vector3f( -1.0f, 0.0f, 0.0f ), new Vector3f( -1.0f, 0.0f, 0.0f ),
      new Vector3f( -1.0f, 0.0f, 0.0f ), new Vector3f( -1.0f, 0.0f, 0.0f ),
    };

    //
    float thick = thickness / 2;
    TexCoord2f tc2fTextureCoordinates[] = {
      new TexCoord2f( 0.5f        , 1.0f ),
      new TexCoord2f( 0.5f        , floor_height ),
      new TexCoord2f( 0.5f + thick, floor_height ),
      new TexCoord2f( 0.5f + thick, 1.0f ),
      new TexCoord2f( 0.5f        , 1.0f ),
      new TexCoord2f( 0.5f        , floor_height ),
      new TexCoord2f( 0.5f + thick, floor_height ),
      new TexCoord2f( 0.5f + thick, 1.0f ),
      new TexCoord2f( 0.5f        , 1.0f ),
      new TexCoord2f( 0.5f        , floor_height ),
      new TexCoord2f( 0.5f + thick, floor_height ),
      new TexCoord2f( 0.5f + thick, 1.0f ),
      new TexCoord2f( 0.5f        , 1.0f ),
      new TexCoord2f( 0.5f        , floor_height ),
      new TexCoord2f( 0.5f + thick, floor_height ),
      new TexCoord2f( 0.5f + thick, 1.0f ),
    };

    //
    QuadArray quadList = new QuadArray( iVertexCount, QuadArray.COORDINATES |
      QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS );
    quadList.setCoordinates( 0, p3fVertexCoordinates );
    quadList.setNormals( 0, v3fPolygonNormals );
    quadList.setTextureCoordinates( 0, 0, tc2fTextureCoordinates );

    return quadList;
  }

  private Geometry createWallFrontGeometry () {
    int   iVertexCount = 4 * 4;
    float width_offset = wall_width / 2;

    // Specify polygon vertices
    Point3f p3fVertexCoordinates[] = {
        // outer front
        new Point3f( -width_offset,  wall_height, width_offset ),
        new Point3f( -width_offset, floor_height, width_offset ),
        new Point3f(  width_offset, floor_height, width_offset ),
        new Point3f(  width_offset,  wall_height, width_offset ),
        // inner front
        new Point3f(  width_offset,  wall_height, width_offset - thickness ),
        new Point3f(  width_offset, floor_height, width_offset - thickness ),
        new Point3f( -width_offset, floor_height, width_offset - thickness ),
        new Point3f( -width_offset,  wall_height, width_offset - thickness ),
        // inner back
        new Point3f( -width_offset,  wall_height, -width_offset + thickness ),
        new Point3f( -width_offset, floor_height, -width_offset + thickness ),
        new Point3f(  width_offset, floor_height, -width_offset + thickness ),
        new Point3f(  width_offset,  wall_height, -width_offset + thickness ),
        // outer back
        new Point3f(  width_offset,  wall_height, -width_offset ),
        new Point3f(  width_offset, floor_height, -width_offset ),
        new Point3f( -width_offset, floor_height, -width_offset ),
        new Point3f( -width_offset,  wall_height, -width_offset ),
    };

    // Specify polygon normals
    Vector3f v3fPolygonNormals[] = {
      // outer front
      new Vector3f( 0.0f, 0.0f,  1.0f ), new Vector3f( 0.0f, 0.0f,  1.0f ),
      new Vector3f( 0.0f, 0.0f,  1.0f ), new Vector3f( 0.0f, 0.0f,  1.0f ),
      // inner front
      new Vector3f( 0.0f, 0.0f, -1.0f ), new Vector3f( 0.0f, 0.0f, -1.0f ),
      new Vector3f( 0.0f, 0.0f, -1.0f ), new Vector3f( 0.0f, 0.0f, -1.0f ),
      // inner back
      new Vector3f( 0.0f, 0.0f,  1.0f ), new Vector3f( 0.0f, 0.0f,  1.0f ),
      new Vector3f( 0.0f, 0.0f,  1.0f ), new Vector3f( 0.0f, 0.0f,  1.0f ),
      // outer back
      new Vector3f( 0.0f, 0.0f, -1.0f ), new Vector3f( 0.0f, 0.0f, -1.0f ),
      new Vector3f( 0.0f, 0.0f, -1.0f ), new Vector3f( 0.0f, 0.0f, -1.0f ),
    };

    // Specify texture coordinates
    TexCoord2f tc2fTextureCoordinates[] = {
      // outer front
      new TexCoord2f( 0.0f, 1.0f ),
      new TexCoord2f( 0.0f, floor_height ),
      new TexCoord2f( 0.5f, floor_height ),
      new TexCoord2f( 0.5f, 1.0f ),
      // inner front
      new TexCoord2f( 0.0f, 1.0f ),
      new TexCoord2f( 0.0f, floor_height ),
      new TexCoord2f( 0.5f, floor_height ),
      new TexCoord2f( 0.5f, 1.0f ),
      // inner back
      new TexCoord2f( 0.0f, 1.0f ),
      new TexCoord2f( 0.0f, floor_height ),
      new TexCoord2f( 0.5f, floor_height ),
      new TexCoord2f( 0.5f, 1.0f ),
      // outer back
      new TexCoord2f( 0.0f, 1.0f ),
      new TexCoord2f( 0.0f, floor_height ),
      new TexCoord2f( 0.5f, floor_height ),
      new TexCoord2f( 0.5f, 1.0f ),
    };

    // Create quad and return
    QuadArray quadList = new QuadArray( iVertexCount, QuadArray.COORDINATES |
      QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS );
    quadList.setCoordinates( 0, p3fVertexCoordinates );
    quadList.setNormals( 0, v3fPolygonNormals );
    quadList.setTextureCoordinates( 0, 0, tc2fTextureCoordinates );
    return quadList;
  }

  private Geometry createWallTopGeometry () {
    int   iVertexCount = 2 * 4;
    float width_offset = wall_width / 2;

    Point3f p3fVertexCoordinates[] = {
        // top front
        new Point3f( -width_offset, wall_height, width_offset - thickness ),
        new Point3f( -width_offset, wall_height, width_offset ),
        new Point3f(  width_offset, wall_height, width_offset ),
        new Point3f(  width_offset, wall_height, width_offset - thickness ),
        // top back
        new Point3f(  width_offset, wall_height, -width_offset + thickness ),
        new Point3f(  width_offset, wall_height, -width_offset ),
        new Point3f( -width_offset, wall_height, -width_offset ),
        new Point3f( -width_offset, wall_height, -width_offset + thickness ),
    };

    Vector3f v3fPolygonNormals[] = {
      new Vector3f( 0.0f, 1.0f, 0.0f ), new Vector3f( 0.0f, 1.0f, 0.0f ),
      new Vector3f( 0.0f, 1.0f, 0.0f ), new Vector3f( 0.0f, 1.0f, 0.0f ),

      new Vector3f( 0.0f, 1.0f, 0.0f ), new Vector3f( 0.0f, 1.0f, 0.0f ),
      new Vector3f( 0.0f, 1.0f, 0.0f ), new Vector3f( 0.0f, 1.0f, 0.0f ),
    };

    float thick = thickness / 2;
    TexCoord2f tc2fTextureCoordinates[] = {
        new TexCoord2f( 1.0f        , 1.0f ),
        new TexCoord2f( 1.0f - thick, 1.0f ),
        new TexCoord2f( 1.0f - thick, 0.0f ),
        new TexCoord2f( 1.0f        , 0.0f ),
        new TexCoord2f( 1.0f        , 1.0f ),
        new TexCoord2f( 1.0f - thick, 1.0f ),
        new TexCoord2f( 1.0f - thick, 0.0f ),
        new TexCoord2f( 1.0f        , 0.0f ),
    };

    QuadArray quadList = new QuadArray( iVertexCount, QuadArray.COORDINATES |
      QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS );
    quadList.setCoordinates( 0, p3fVertexCoordinates );
    quadList.setNormals( 0, v3fPolygonNormals );
    quadList.setTextureCoordinates( 0, 0, tc2fTextureCoordinates );

    return quadList;
  }

}
