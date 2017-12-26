package editor;

import java.io.UnsupportedEncodingException;

public class Stadia
{
  static byte total = 35;
  static int nameAdr = 9508;
  static byte maxLen = 61;
  static int switchAdr = 11643;
  
  static String get(OptionFile paramOptionFile, int paramInt)
  {
    int j = nameAdr + paramInt * maxLen;
    int i = 0;
    for (int k = 0; (i == 0) && (k < maxLen); k = (byte)(k + 1)) {
      if ((i == 0) && (paramOptionFile.data[(j + k)] == 0)) {
        i = k;
      }
    }
    String str;
    try
    {
      str = new String(paramOptionFile.data, j, i, "UTF-8");
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      str = "";
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
    if ((paramString.length() < maxLen) && (paramString.length() > 0))
    {
      int i = nameAdr + paramInt * maxLen;
      int j = switchAdr + paramInt;
      byte[] arrayOfByte1 = new byte[maxLen];
      byte[] arrayOfByte2;
      try
      {
        arrayOfByte2 = paramString.getBytes("UTF-8");
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        arrayOfByte2 = new byte[maxLen - 1];
      }
      if (arrayOfByte2.length <= maxLen - 1) {
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, arrayOfByte2.length);
      } else {
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, maxLen - 1);
      }
      System.arraycopy(arrayOfByte1, 0, paramOptionFile.data, i, maxLen);
      paramOptionFile.data[j] = 1;
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/Stadia.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */