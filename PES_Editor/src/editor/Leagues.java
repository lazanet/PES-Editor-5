package editor;

import java.io.UnsupportedEncodingException;

public class Leagues
{
  static byte total = 28;
  static int nameAdr = 11699;
  static byte maxLen = 61;
  static byte fieldLen = 84;
  static String[] def = { "ISS", "England", "French", "German", "Italian", "Netherlands", "Spanish", "International", "European", "African", "American", "Asia-Oceanian", "Konami", "D2", "D1", "WEFA Masters", "WEFA Championships", "International League" };
  
  static String get(OptionFile paramOptionFile, int paramInt)
  {
    int j = nameAdr + paramInt * fieldLen;
    int i = 0;
    String str;
    if (paramOptionFile.data[j] != 0)
    {
      for (int k = 0; (i == 0) && (k < maxLen + 1); k = (byte)(k + 1)) {
        if ((i == 0) && (paramOptionFile.data[(j + k)] == 0)) {
          i = k;
        }
      }
      try
      {
        str = new String(paramOptionFile.data, j, i, "UTF-8");
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        str = "<" + String.valueOf(paramInt) + ">";
      }
    }
    else if (paramInt > 10)
    {
      str = def[(paramInt - 11)];
      if (paramInt < 27) {
        str = str + " Cup";
      }
    }
    else
    {
      str = "<" + String.valueOf(paramInt) + ">";
    }
    return str;
  }
  
  static String[] get(OptionFile paramOptionFile)
  {
    String[] arrayOfString = new String[total];
    for (int i = 0; i < total; i = (byte)(i + 1)) {
      arrayOfString[i] = get(paramOptionFile, i);
    }
    return arrayOfString;
  }
  
  static void set(OptionFile paramOptionFile, int paramInt, String paramString)
  {
    if ((paramString.length() <= maxLen) && (paramString.length() > 0))
    {
      int i = nameAdr + paramInt * fieldLen;
      byte[] arrayOfByte1 = new byte[maxLen + 2];
      byte[] arrayOfByte2;
      try
      {
        arrayOfByte2 = paramString.getBytes("UTF-8");
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        arrayOfByte2 = new byte[maxLen];
      }
      if (arrayOfByte2.length <= maxLen) {
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, arrayOfByte2.length);
      } else {
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, maxLen);
      }
      arrayOfByte1[(maxLen + 1)] = 1;
      System.arraycopy(arrayOfByte1, 0, paramOptionFile.data, i, maxLen + 2);
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/Leagues.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */