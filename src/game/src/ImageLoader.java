import java.awt.Component;
import javax.media.j3d.Texture;
import com.sun.j3d.utils.image.TextureLoader;

public class ImageLoader {

  /**
   * Loads an image file and constructs a texture.
   * @param filename - The filename of the texture to be used.
   * @param observer - The observer for this image
   * @return Texture The loaded image
   */
  public static Texture LoadTexture ( String filename, Component observer ) {
    return new TextureLoader( filename, observer ).getTexture();
  }

}
