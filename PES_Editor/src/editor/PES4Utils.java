package editor;

import java.io.File;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PES4Utils
{
  public static final String xps = "xps";
  public static final String xsv = "xsv";
  public static final String png = "png";
  public static final String gif = "gif";
  public static final String pfek = "pfek";
  public static final String csv = "csv";
  public static final String zip = "zip";
  public static final byte[] authKeyBytes = { -14, -55, -125, -28, 82, 115, 125, -54, 84, -61, 101, -62, -110, -117, -91, -80 };
  public static final byte[] authKeyBytesWE9u = { -79, -72, -17, -123, 66, -96, -127, 116, -100, 63, 122, 58, -19, 116, -22, -15 };
  public static final String[] extraSquad = { "Classic Argentina", "Classic Brazil", "Classic England", "Classic France", "Classic Germany", "Classic Italy", "Classic Netherlands", "<Japan 1>", "<Japan 2>", "<Edited> National 1", "<Edited> National 2", "<Edited> National 3", "<Edited> National 4", "<Edited> National 5", "<Edited> National 6", "<Edited> National 7", "<Edited>", "<ML Default>", "<Shop 1>", "<Shop 2>", "<Shop 3>", "<Shop 4>", "<ML Default 2>", "<ML Default 3>" };
  
  public static String getExtension(File paramFile)
  {
    String str1 = null;
    String str2 = paramFile.getName();
    int i = str2.lastIndexOf('.');
    if ((i > 0) && (i < str2.length() - 1)) {
      str1 = str2.substring(i + 1).toLowerCase();
    }
    return str1;
  }
  
  public static int swabInt(int paramInt)
  {
    return paramInt >>> 24 | paramInt << 24 | paramInt << 8 & 0xFF0000 | paramInt >> 8 & 0xFF00;
  }
  
  public static void javaUI()
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
    }
    catch (UnsupportedLookAndFeelException localUnsupportedLookAndFeelException)
    {
      localUnsupportedLookAndFeelException.printStackTrace();
    }
  }
  
  public static void systemUI()
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
    }
    catch (UnsupportedLookAndFeelException localUnsupportedLookAndFeelException)
    {
      localUnsupportedLookAndFeelException.printStackTrace();
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/PES4Utils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */