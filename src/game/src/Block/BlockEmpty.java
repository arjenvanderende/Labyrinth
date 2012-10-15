package Block;

import javax.media.j3d.*;
import javax.vecmath.*;

public class BlockEmpty extends Block {

  public BlockEmpty ( Texture texture ) {
    access = 0;

    addGeometry( createGeometry() );
    setAppearance( createAppearance( texture ) );
  }

  private Geometry createGeometry() {
    final int   vertex_count = 20;
    final float width_offset = wall_width / 2;

    final float ground = 32 / 640f;

    Point3f    p3fVertices[]  = new Point3f[vertex_count];
    Vector3f   v3fPNormals[]  = new Vector3f[vertex_count];
    TexCoord2f tc2fTextures[] = new TexCoord2f[vertex_count];

    // floor-front
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 0, -width_offset            , 0.0f        ,  width_offset                            ,  wall_width                   , floor_height, AXIS_XY, true, 1.0f - ground, 0.0f, ground, 1.0f);
    // floor-right
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 4,  width_offset            , 0.0f        ,  width_offset                            ,  wall_width                  , floor_height, AXIS_YZ, true, 1.0f - (2 * ground), 0.0f, ground, 1.0f);
    // floor-back
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 8,  width_offset            , 0.0f        ,  -wall_width + width_offset              , -wall_width                  , floor_height, AXIS_XY, true, 1.0f - (3 * ground), 0.0f, ground, 1.0f);
    // floor-left
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 12, -width_offset            , 0.0f        ,  -wall_width + width_offset              , -wall_width                   , floor_height, AXIS_YZ, true, 1.0f - (4 * ground), 0.0f, ground, 1.0f);
    // floor-top
    createQuad( p3fVertices, v3fPNormals, tc2fTextures, 16, -width_offset            , floor_height,  width_offset                            ,  wall_width                   , wall_width , AXIS_XZ, false, 0.0f, 0.0f, 1.0f - (4 * ground), 1.0f);

    QuadArray quadList = new QuadArray( vertex_count, QuadArray.COORDINATES |
      QuadArray.TEXTURE_COORDINATE_2 | QuadArray.NORMALS);
    quadList.setCoordinates( 0, p3fVertices );
    quadList.setNormals( 0, v3fPNormals );
    quadList.setTextureCoordinates( 0, 0, tc2fTextures );

    return quadList;
  }

}
