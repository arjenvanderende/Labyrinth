package Block;

import javax.vecmath.*;
import javax.media.j3d.*;

public abstract class Block extends Shape3D {

  protected int access = 0;

  public static final int UP    = ( 1 << 1 );
  public static final int RIGHT = ( 1 << 2 );
  public static final int DOWN  = ( 1 << 3 );
  public static final int LEFT  = ( 1 << 4 );

  protected static final int AXIS_XY = 1;
  protected static final int AXIS_XZ = 2;
  protected static final int AXIS_YZ = 3;

  public static final float wall_height  = 0.9375f;
  public static final float floor_height = 0.0625f;
  public static final float wall_width   = 1.0f;
  public static final float thickness    = 0.1875f;

  public void rotateAccess()
  {
    int new_access = 0;
    if ( (access & UP) != 0)
      new_access |= LEFT;

    if ( (access & RIGHT) != 0)
      new_access |= UP;

    if ( (access & DOWN) != 0)
      new_access |= RIGHT;

    if ( (access & LEFT) != 0)
      new_access |= DOWN;

    access = new_access;
  }

  public int getAccess() { return access; }

  protected Appearance createAppearance( Texture texture )
  {
    // Create texture attributes for this appearance
    TextureAttributes taAttributes = new TextureAttributes();
    taAttributes.setTextureMode( TextureAttributes.MODULATE );

    // Create a new material so lighting will affect this appearance
    Material matMaterial = new Material();
    matMaterial.setDiffuseColor(  new Color3f( 1.0f, 1.0f, 1.0f ) );
    matMaterial.setSpecularColor( new Color3f( 0.2f, 0.2f, 0.2f) );

    // Create an appearance with the specified texture
    Appearance template = new Appearance();
    template.setTexture( texture );
    template.setTextureAttributes( taAttributes );
    template.setMaterial( matMaterial );
    return template;
  }

  protected void createQuad( Point3f[]    vertices,
                           Vector3f[]   normals,
                           TexCoord2f[] textures,
                           int          index,
                           float        pos_x,
                           float        pos_y,
                           float        pos_z,
                           float        width,
                           float        height,
                           int          direction,
                           boolean      rotate,
                           float        tex_pos_x,
                           float        tex_pos_y,
                           float        tex_width,
                           float        tex_height
                           )
  {
    Vector3f normal = null;

    // Generate vertices
    if ( direction == AXIS_XY ) {
      vertices[index + 0] = new Point3f( pos_x        , pos_y + height, pos_z );
      vertices[index + 1] = new Point3f( pos_x        , pos_y         , pos_z );
      vertices[index + 2] = new Point3f( pos_x + width, pos_y         , pos_z );
      vertices[index + 3] = new Point3f( pos_x + width, pos_y + height, pos_z );
      normal = new Vector3f( 0.0f, 0.0f, width * height > 0 ? 1.0f : -1.0f );
    }
    else if ( direction == AXIS_YZ ) {
      vertices[index + 0] = new Point3f( pos_x, pos_y + height, pos_z );
      vertices[index + 1] = new Point3f( pos_x, pos_y         , pos_z );
      vertices[index + 2] = new Point3f( pos_x, pos_y         , pos_z - width );
      vertices[index + 3] = new Point3f( pos_x, pos_y + height, pos_z - width );
      normal = new Vector3f( width * height > 0 ? 1.0f : -1.0f, 0.0f, 0.0f );
    }
    else if ( direction == AXIS_XZ ) {
      vertices[index + 0] = new Point3f( pos_x        , pos_y, pos_z - height );
      vertices[index + 1] = new Point3f( pos_x        , pos_y, pos_z );
      vertices[index + 2] = new Point3f( pos_x + width, pos_y, pos_z );
      vertices[index + 3] = new Point3f( pos_x + width, pos_y, pos_z - height );
      normal = new Vector3f( 0.0f, width * height > 0 ? 1.0f : -1.0f, 0.0f );
    }

    // Generate normals
    for (int i = index; i < index + 4; i++)
      normals[i] = new Vector3f( normal );

    // Generate texture coordinates
    if ( rotate ) {
      textures[index + 0] = new TexCoord2f( tex_pos_x + tex_width, tex_pos_y + tex_height );
      textures[index + 1] = new TexCoord2f( tex_pos_x            , tex_pos_y + tex_height);
      textures[index + 2] = new TexCoord2f( tex_pos_x            , tex_pos_y );
      textures[index + 3] = new TexCoord2f( tex_pos_x + tex_width, tex_pos_y );
    }
    else {
      textures[index + 0] = new TexCoord2f( tex_pos_x, tex_pos_y + tex_height );
      textures[index + 1] = new TexCoord2f( tex_pos_x, tex_pos_y );
      textures[index + 2] = new TexCoord2f( tex_pos_x + tex_width, tex_pos_y );
      textures[index + 3] = new TexCoord2f( tex_pos_x + tex_width, tex_pos_y + tex_height );
    }
  }


}
