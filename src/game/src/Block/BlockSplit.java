package Block;

import javax.media.j3d.*;
import javax.vecmath.*;

public class BlockSplit extends Block {

  public BlockSplit ( Texture texture ) {
    access = RIGHT | DOWN | LEFT;

    addGeometry( createGeometry() );
    setAppearance( createAppearance( texture ) );
  }

  private Geometry createGeometry() {
    final int   vertex_count = 80;
    final float width_offset = wall_width / 2;

    final float x_offs = 1 / 3f;
    final float y_offs = 32 / 512f;
    final float ground = 32 / 1536f;
    final float side   = 96 / 1536f;

    Point3f    p3fVertices[]  = new Point3f[vertex_count];
    Vector3f   v3fPNormals[]  = new Vector3f[vertex_count];
    TexCoord2f tc2fTextures[] = new TexCoord2f[vertex_count];

    // Floor
    createQuad( p3fVertices, v3fPNormals, tc2fTextures,  0, -width_offset, 0.0f,  width_offset,  wall_width, floor_height, AXIS_XY, true, 2 * x_offs - 1 * ground, 0.0f, ground, 1.0f );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures,  4,  width_offset, 0.0f,  width_offset,  wall_width, floor_height, AXIS_YZ, true, 2 * x_offs - 2 * ground, 0.0f, ground, 1.0f  );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures,  8,  width_offset, 0.0f, -width_offset, -wall_width, floor_height, AXIS_XY, true, 2 * x_offs - 3 * ground, 0.0f, ground, 1.0f  );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 12, -width_offset, 0.0f, -width_offset, -wall_width, floor_height, AXIS_YZ, true, 2 * x_offs - 4 * ground, 0.0f, ground, 1.0f  );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 16, -width_offset, floor_height, width_offset, wall_width, wall_width, AXIS_XZ, false, x_offs * 2, 0.0f, x_offs, 1.0f );

    // Left Pillar
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 20, -width_offset            , floor_height, width_offset            ,  thickness, wall_height - floor_height, AXIS_XY, false, x_offs + 0 * side, y_offs, side, 1.0f - y_offs );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 24, -width_offset + thickness, floor_height, width_offset            ,  thickness, wall_height - floor_height, AXIS_YZ, false, x_offs + 1 * side, y_offs, side, 1.0f - y_offs );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 28, -width_offset + thickness, floor_height, width_offset - thickness, -thickness, wall_height - floor_height, AXIS_XY, false, x_offs + 2 * side, y_offs, side, 1.0f - y_offs );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 32, -width_offset            , floor_height, width_offset - thickness, -thickness, wall_height - floor_height, AXIS_YZ, false, x_offs + 3 * side, y_offs, side, 1.0f - y_offs );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 36, -width_offset            ,  wall_height, width_offset            ,  thickness, thickness                 , AXIS_XZ, false, 2 * x_offs, 0.0f, 3 * ground, 3 * y_offs );

    // Right Pillar
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 40, width_offset - thickness, floor_height, width_offset            ,  thickness, wall_height - floor_height, AXIS_XY, false, x_offs + 0 * side, y_offs, side, 1.0f - y_offs );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 44, width_offset            , floor_height, width_offset            ,  thickness, wall_height - floor_height, AXIS_YZ, false, x_offs + 1 * side, y_offs, side, 1.0f - y_offs );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 48, width_offset            , floor_height, width_offset - thickness, -thickness, wall_height - floor_height, AXIS_XY, false, x_offs + 2 * side, y_offs, side, 1.0f - y_offs );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 52, width_offset - thickness, floor_height, width_offset - thickness, -thickness, wall_height - floor_height, AXIS_YZ, false, x_offs + 3 * side, y_offs, side, 1.0f - y_offs );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 56, width_offset - thickness,  wall_height, width_offset            ,  thickness, thickness                 , AXIS_XZ, false, 3 * x_offs - 3 * ground, 0.0f, 3 * ground, 3 * y_offs );

    // Back Wall
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 60, -width_offset, floor_height, -width_offset + thickness,  wall_width, wall_height - floor_height , AXIS_XY, false, 0.0f             , y_offs, x_offs, 1.0f - y_offs );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 64,  width_offset, floor_height, -width_offset + thickness,  thickness , wall_height - floor_height , AXIS_YZ, false, x_offs + 1 * side, y_offs,   side, 1.0f - y_offs );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 68,  width_offset, floor_height, -width_offset            , -wall_width, wall_height - floor_height , AXIS_XY, false, 0.0f             , y_offs, x_offs, 1.0f - y_offs );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 72, -width_offset, floor_height, -width_offset            , -thickness , wall_height - floor_height , AXIS_YZ, false, x_offs + 3 * side, y_offs,   side, 1.0f - y_offs );
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 76, -width_offset,  wall_height, -width_offset + thickness,  wall_width, thickness                  , AXIS_XZ, false, 2 * x_offs, 1.0f - 3 * y_offs, x_offs, 3 * y_offs );

    QuadArray quadList = new QuadArray( vertex_count, QuadArray.COORDINATES |
      QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS);
    quadList.setCoordinates( 0, p3fVertices );
    quadList.setNormals( 0, v3fPNormals );
    quadList.setTextureCoordinates( 0, 0, tc2fTextures );

    return quadList;
  }

}
