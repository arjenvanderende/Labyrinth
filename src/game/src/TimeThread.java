import javax.media.j3d.*;

import com.sun.j3d.utils.geometry.*;

public class TimeThread extends Thread
{
  private volatile boolean shouldRun;
  private Text2D t2dText;

  public TimeThread( Text2D t2dText )
  {
    this.t2dText = t2dText;
    this.t2dText.setCapability( Text2D.ALLOW_APPEARANCE_READ );
    this.t2dText.getAppearance().setCapability( Appearance.ALLOW_TEXTURE_READ );
    this.t2dText.getAppearance().getTexture().setCapability( Texture.ALLOW_IMAGE_WRITE );
  }

  public void stopThread()
  {
    shouldRun = false;
  }

  public void run()
  {
    long currentTime, startTime = System.currentTimeMillis();
    String szTime;
    shouldRun = true;
    while ( shouldRun )
    {
      currentTime = ( System.currentTimeMillis() - startTime ) / 1000;
      int carry = (int)(currentTime % 60);
      szTime = currentTime / 60 + ":" + ( carry < 10 ? "0" : "" ) + carry;
      t2dText.setString( szTime );

      try
      {
        sleep( 1000 );
      }
      catch ( Exception ex ) { }
    }
  }
}
