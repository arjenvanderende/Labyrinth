package Block;

import javax.media.j3d.*;
import javax.vecmath.*;

public class BlockDeadEnd extends Block {

  public BlockDeadEnd ( Texture texture ) {
    access = DOWN;

    addGeometry( createGeometry() );
    setAppearance( createAppearance( texture ) );
  }

  private Geometry createGeometry() {
    final int   vertex_count = 64;
    final float width_offset = wall_width / 2;

    final float x_offs = 1 / 3f;
    final float y_offs = 32 / 512f;
    final float ground = 32 / 1536f;
    final float side   = 96 / 1536f;
    final float side_y = 96 / 512f;

    Point3f    p3fVertices[]  = new Point3f[vertex_count];
    Vector3f   v3fPNormals[]  = new Vector3f[vertex_count];
    TexCoord2f tc2fTextures[] = new TexCoord2f[vertex_count];

    // left-inner
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 0 , -width_offset + thickness, floor_height,  width_offset                            ,  wall_width - thickness      , wall_height - floor_height, AXIS_YZ, false, x_offs + side , y_offs, x_offs - side, 1.0f - y_offs);
    // back-inner
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 4 , -width_offset + thickness, floor_height, (-wall_width + thickness) + width_offset ,  wall_width - (2 * thickness), wall_height - floor_height, AXIS_XY, false, x_offs + side , y_offs, x_offs - side, 1.0f - y_offs);
    // right-inner
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 8 ,  width_offset - thickness, floor_height, (-wall_width + thickness) + width_offset , -wall_width + thickness      , wall_height - floor_height, AXIS_YZ, false, x_offs + side , y_offs, x_offs - side, 1.0f - y_offs);
    // front-right
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 12,  width_offset - thickness, floor_height,  width_offset                            ,  thickness                   , wall_height - floor_height, AXIS_XY, false, x_offs, y_offs, side, 1.0f - y_offs);
    // right-outer
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 16,  width_offset            , floor_height,  width_offset                            ,  wall_width                  , wall_height - floor_height, AXIS_YZ, false, 0.0f, y_offs, x_offs, 1.0f - y_offs);
    // back-outer
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 20,  width_offset            , floor_height, -wall_width + width_offset               , -wall_width                  , wall_height - floor_height, AXIS_XY, false, 0.0f, y_offs, x_offs, 1.0f - y_offs);
    // left-outer
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 24, -width_offset            , floor_height, -wall_width + width_offset               , -wall_width                  , wall_height - floor_height, AXIS_YZ, false, 0.0f, y_offs, x_offs, 1.0f - y_offs);
    // front-left
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 28, -width_offset            , floor_height,  width_offset                            ,  thickness                    , wall_height - floor_height, AXIS_XY, false, x_offs, y_offs, side, 1.0f - y_offs);
    // floor-front
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 32, -width_offset            , 0.0f        ,  width_offset                            ,  wall_width                   , floor_height, AXIS_XY, false, 0.0f, 0.0f, x_offs, y_offs);
    // floor-right
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 36,  width_offset            , 0.0f        ,  width_offset                            ,  wall_width                  , floor_height, AXIS_YZ, false, 0.0f, 0.0f, x_offs, y_offs);
    // floor-back
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 40,  width_offset            , 0.0f        ,  -wall_width + width_offset              , -wall_width                  , floor_height, AXIS_XY, false, 0.0f, 0.0f, x_offs, y_offs);
    // floor-left
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 44, -width_offset            , 0.0f        ,  -wall_width + width_offset              , -wall_width                   , floor_height, AXIS_YZ, false, 0.0f, 0.0f, x_offs, y_offs);
    // floor-top
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 48, -width_offset            , floor_height,  width_offset                            ,  wall_width                   , wall_width , AXIS_XZ, false, 2 * x_offs, 0.0f, x_offs, 1.0f);
    // top-left
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 52, -width_offset            , wall_height,  width_offset              ,  thickness                    , wall_width, AXIS_XZ, false, 2 * x_offs, 0.0f, side, 1.0f);
    // top-back
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 56, -width_offset + thickness, wall_height,  (-wall_width + thickness) + width_offset, wall_width - (2 * thickness)  , thickness, AXIS_XZ, false, (1.0f - x_offs) + side, 1.0f - side_y, x_offs - (2 * side), side_y);
    // top-right
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 60,  width_offset -thickness , wall_height,  width_offset              ,  thickness                    , wall_width, AXIS_XZ, false, 1.0f - side, 0.0f, side, 1.0f);

    QuadArray quadList = new QuadArray( vertex_count, QuadArray.COORDINATES |
      QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS);
    quadList.setCoordinates( 0, p3fVertices );
    quadList.setNormals( 0, v3fPNormals );
    quadList.setTextureCoordinates( 0, 0, tc2fTextures );

    return quadList;
  }

}
