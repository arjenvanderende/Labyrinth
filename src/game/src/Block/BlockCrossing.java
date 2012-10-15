package Block;

import javax.media.j3d.*;
import javax.vecmath.*;

public class BlockCrossing extends Block {

  public BlockCrossing ( Texture texture ) {
    access = UP | RIGHT | DOWN | LEFT;

    addGeometry( createFloorGeometry() );
    addGeometry( createPillarGeometry( -wall_width / 2            , -wall_width / 2             , 0.5f    , 0.8125f ) );
    addGeometry( createPillarGeometry(  wall_width / 2 - thickness,  wall_width / 2 - thickness , 0.90625f, 0.0f ) );
    addGeometry( createPillarGeometry( -wall_width / 2            ,  wall_width / 2 - thickness , 0.5f    , 0.0f ) );
    addGeometry( createPillarGeometry(  wall_width / 2 - thickness, -wall_width / 2             , 0.90625f, 0.8125f ) );
    setAppearance( createAppearance( texture ) );
  }

  private Geometry createFloorGeometry () {
    int   iVertexCount = 5 * 4;
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
      // top
      new Point3f( -width_offset, 0 + floor_height, -width_offset ),
      new Point3f( -width_offset, 0 + floor_height,  width_offset ),
      new Point3f(  width_offset, 0 + floor_height,  width_offset ),
      new Point3f(  width_offset, 0 + floor_height, -width_offset ),
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
      // top
      new Vector3f( 0.0f, 1.0f, 0.0f ), new Vector3f( 0.0f, 1.0f, 0.0f ),
      new Vector3f( 0.0f, 1.0f, 0.0f ), new Vector3f( 0.0f, 1.0f, 0.0f ),
    };

    float offset = 0.03125f;

    // Specify texture coordinates
    TexCoord2f tc2fTextureCoordinates[] = {
      new TexCoord2f( 0.5f - 0 * offset, 1.0f ),
      new TexCoord2f( 0.5f - 1 * offset, 1.0f ),
      new TexCoord2f( 0.5f - 1 * offset, 0.0f ),
      new TexCoord2f( 0.5f - 0 * offset, 0.0f ),

      new TexCoord2f( 0.5f - 1 * offset, 1.0f ),
      new TexCoord2f( 0.5f - 2 * offset, 1.0f ),
      new TexCoord2f( 0.5f - 2 * offset, 0.0f ),
      new TexCoord2f( 0.5f - 1 * offset, 0.0f ),

      new TexCoord2f( 0.5f - 2 * offset, 1.0f ),
      new TexCoord2f( 0.5f - 3 * offset, 1.0f ),
      new TexCoord2f( 0.5f - 3 * offset, 0.0f ),
      new TexCoord2f( 0.5f - 2 * offset, 0.0f ),

      new TexCoord2f( 0.5f - 3 * offset, 1.0f ),
      new TexCoord2f( 0.5f - 4 * offset, 1.0f ),
      new TexCoord2f( 0.5f - 4 * offset, 0.0f ),
      new TexCoord2f( 0.5f - 3 * offset, 0.0f ),

      new TexCoord2f( 0.5f, 1.0f ),
      new TexCoord2f( 0.5f, 0.0f ),
      new TexCoord2f( 1.0f, 0.0f ),
      new TexCoord2f( 1.0f, 1.0f ),
    };

    QuadArray quadList = new QuadArray( iVertexCount, QuadArray.COORDINATES |
      QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS);
    quadList.setCoordinates( 0, p3fVertexCoordinates );
    quadList.setNormals( 0, v3fPolygonNormals );
    quadList.setTextureCoordinates( 0, 0, tc2fTextureCoordinates );

    return quadList;
  }

  private Geometry createPillarGeometry ( float offset_x, float offset_y,
                                          float offtex_x, float offtex_y ) {
    int   iVertexCount = 5 * 4;

    // Specify polygon vertices
    Point3f p3fVertexCoordinates[] = {
      // Left Front Pillar
      //front
      new Point3f( offset_x            ,  wall_height, offset_y + thickness ),
      new Point3f( offset_x            , floor_height, offset_y + thickness ),
      new Point3f( offset_x + thickness, floor_height, offset_y + thickness ),
      new Point3f( offset_x + thickness,  wall_height, offset_y + thickness ),
      // right
      new Point3f( offset_x + thickness,  wall_height, offset_y + thickness ),
      new Point3f( offset_x + thickness, floor_height, offset_y + thickness ),
      new Point3f( offset_x + thickness, floor_height, offset_y ),
      new Point3f( offset_x + thickness,  wall_height, offset_y ),
      // back
      new Point3f( offset_x + thickness,   wall_height, offset_y ),
      new Point3f( offset_x + thickness,  floor_height, offset_y ),
      new Point3f( offset_x            ,  floor_height, offset_y ),
      new Point3f( offset_x            ,   wall_height, offset_y ),
      // left
      new Point3f( offset_x,   wall_height, offset_y ),
      new Point3f( offset_x,  floor_height, offset_y ),
      new Point3f( offset_x,  floor_height, offset_y + thickness ),
      new Point3f( offset_x,   wall_height, offset_y + thickness ),
      // top
      new Point3f( offset_x            , wall_height, offset_y ),
      new Point3f( offset_x            , wall_height, offset_y + thickness ),
      new Point3f( offset_x + thickness, wall_height, offset_y + thickness ),
      new Point3f( offset_x + thickness, wall_height, offset_y ),
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
      // top
      new Vector3f( 0.0f, 1.0f, 0.0f ), new Vector3f( 0.0f, 1.0f, 0.0f ),
      new Vector3f( 0.0f, 1.0f, 0.0f ), new Vector3f( 0.0f, 1.0f, 0.0f ),
    };

    float offset = 0.09375f;
    float tex_x = 96 / 1024f;
    float tex_y = 96 / 512f;

    // Specify texture coordinates
    TexCoord2f tc2fTextureCoordinates[] = {
      new TexCoord2f( 0 * offset, 1.0f ),
      new TexCoord2f( 0 * offset, floor_height ),
      new TexCoord2f( 1 * offset, floor_height ),
      new TexCoord2f( 1 * offset, 1.0f ),

      new TexCoord2f( 1 * offset, 1.0f ),
      new TexCoord2f( 1 * offset, floor_height ),
      new TexCoord2f( 2 * offset, floor_height ),
      new TexCoord2f( 2 * offset, 1.0f ),

      new TexCoord2f( 2 * offset, 1.0f ),
      new TexCoord2f( 2 * offset, floor_height ),
      new TexCoord2f( 3 * offset, floor_height ),
      new TexCoord2f( 3 * offset, 1.0f ),

      new TexCoord2f( 3 * offset, 1.0f ),
      new TexCoord2f( 3 * offset, floor_height ),
      new TexCoord2f( 4 * offset, floor_height ),
      new TexCoord2f( 4 * offset, 1.0f ),

      new TexCoord2f( offtex_x        , offtex_y + tex_y),
      new TexCoord2f( offtex_x        , offtex_y ),
      new TexCoord2f( offtex_x + tex_x, offtex_y ),
      new TexCoord2f( offtex_x + tex_x, offtex_y + tex_y),
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
