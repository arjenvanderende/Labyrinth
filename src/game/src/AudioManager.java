import java.applet.*;

/**
 * <p>Title: The aMAZEing Labyrinth</p>
 * <p>Description: Graphics opdracht HI3</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Lunar Studios</p>
 * @author Arjen van der Ende
 * @version 1.0
 */
public class AudioManager
{
  private Applet    observer;
  private AudioClip acMusic;
  private AudioClip acCache;
  private String    szCache = "";
  private static AudioManager instance = null;

  /**
   * Create a new AudioManager-object. The manager uses an applet to load and
   * play audio files. The last-played sound is cached to reduce disc-access.
   * @param observer The applet that will be used to load and play files
   */
  public AudioManager( Applet observer )
  {
    this.observer = observer;
    instance = this;
  }

  /**
   * Get a previous instance of the AudioManager
   * @return The requested AudioManager, or <CODE>null</CODE> if no AudioManager
   * has been made yet
   */
  public static AudioManager getInstance()
  {
    return instance;
  }

  /**
   * Play a looping background music file
   * @param filename The filename of the background music
   */
  public void PlayMusic( String filename )
  {
    if ( acMusic != null )
      acMusic.stop();
    acMusic = observer.getAudioClip( observer.getDocumentBase(), "music/" + filename );
    acMusic.loop();
  }

  /**
   * Stop the background music
   */
  public void StopMusic()
  {
    acMusic.stop();
  }

  /**
   * Play a sound one time
   * @param filename The filename of the sound
   */
  public void PlaySound( String filename )
  {
    if ( szCache != filename )
    {
      acCache = observer.getAudioClip( observer.getDocumentBase(), "sound/" + filename );
      szCache = filename;
    }
    acCache.play();
  }

}
