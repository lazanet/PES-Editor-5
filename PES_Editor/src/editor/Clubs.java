package editor;

import java.io.UnsupportedEncodingException;

public class Clubs
{
  static int total = 138;
  static int startAdr = 803608;
  static int size = 140;
  
  static int getEmblem(OptionFile paramOptionFile, int paramInt)
  {
    int i = startAdr + 112 + size * paramInt;
    byte[] arrayOfByte = new byte[2];
    System.arraycopy(paramOptionFile.data, i, arrayOfByte, 0, 2);
    return paramOptionFile.toInt(arrayOfByte[1]) << 8 | paramOptionFile.toInt(arrayOfByte[0]) & 0xFF;
  }
  
  static void importClub(OptionFile paramOptionFile1, int paramInt1, OptionFile paramOptionFile2, int paramInt2)
  {
    int i = startAdr + size * paramInt1;
    int j = startAdr + size * paramInt2;
    System.arraycopy(paramOptionFile2.data, j, paramOptionFile1.data, i, size);
  }
  
  static void setEmblem(OptionFile paramOptionFile, int paramInt, byte[] paramArrayOfByte)
  {
    int i = 1;
    if (paramArrayOfByte == null)
    {
      paramArrayOfByte = paramOptionFile.getBytes(paramInt + 111);
      i = 0;
    }
    int j = startAdr + 112 + size * paramInt;
    int k = startAdr + 120 + size * paramInt;
    System.arraycopy(paramArrayOfByte, 0, paramOptionFile.data, j, 2);
    System.arraycopy(paramArrayOfByte, 0, paramOptionFile.data, j + 4, 2);
    paramOptionFile.data[k] = (byte)i;
    paramOptionFile.data[(k + 1)] = (byte)i;
  }
  
  static void unAssEmblem(OptionFile paramOptionFile, int paramInt)
  {
    for (int i = 0; i < total; i++) {
      if (paramInt == getEmblem(paramOptionFile, i) - 284) {
        setEmblem(paramOptionFile, i, null);
      }
    }
  }
  
  static String[] getNames(OptionFile paramOptionFile)
  {
    String[] arrayOfString = new String[total];
    for (int i = 0; i < arrayOfString.length; i++) {
      arrayOfString[i] = getName(paramOptionFile, i);
    }
    return arrayOfString;
  }
  
  static String getName(OptionFile paramOptionFile, int paramInt)
  {
    String str = "";
    int i = 0;
    int j = startAdr + paramInt * size;
    if (paramOptionFile.data[j] != 0)
    {
      for (int k = 0; k < 49; k++) {
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
    else
    {
      str = "<" + String.valueOf(paramInt) + ">";
    }
    return str;
  }
  
  static void importNames(OptionFile paramOptionFile1, OptionFile paramOptionFile2)
  {
    for (int i = 0; i < total; i++) {
      System.arraycopy(paramOptionFile2.data, startAdr + i * size, paramOptionFile1.data, startAdr + i * size, 80);
    }
  }
  
  static void importData(OptionFile paramOptionFile1, OptionFile paramOptionFile2)
  {
    for (int i = 0; i < total; i++) {
      System.arraycopy(paramOptionFile2.data, startAdr + i * size + 80, paramOptionFile1.data, startAdr + i * size + 80, 60);
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/Clubs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
