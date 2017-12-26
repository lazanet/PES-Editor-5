package editor;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class Player
  implements Comparable, Serializable
{
  public String name;
  public int index;
  public int adr;
  private OptionFile of;
  
  public Player(OptionFile paramOptionFile, int paramInt1, int paramInt2)
  {
    this.of = paramOptionFile;
    if (this.of == null) {
      throw new NullPointerException();
    }
    this.index = paramInt1;
    this.adr = paramInt2;
    if (paramInt1 == 0)
    {
      this.name = "<empty>";
    }
    else
    {
      int j = 36872;
      int k = paramInt1 * 124;
      if (paramInt1 > 4999)
      {
        j = 14044;
        k = (paramInt1 - 32768) * 124;
      }
      byte[] arrayOfByte = new byte[32];
      System.arraycopy(this.of.data, j + k, arrayOfByte, 0, 32);
      int i = 0;
      int m = 0;
      for (int n = 0; (i == 0) && (n < arrayOfByte.length - 1); n += 2) {
        if ((arrayOfByte[n] == 0) && (arrayOfByte[(n + 1)] == 0))
        {
          i = 1;
          m = n;
        }
      }
      try
      {
        this.name = new String(arrayOfByte, 0, m, "UTF-16LE");
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        this.name = ("<Error " + String.valueOf(this.index) + ">");
      }
      if ((this.name.equals("")) && (this.index > 4999)) {
        this.name = ("<Edited " + String.valueOf(this.index - 32768) + ">");
      } else if (this.name.equals("")) {
        if (this.index > 4895) {
          this.name = ("<Unused " + String.valueOf(this.index) + ">");
        } else {
          this.name = ("<L " + String.valueOf(this.index) + ">");
        }
      }
    }
  }
  
  public String toString()
  {
    return this.name;
  }
  
  public int compareTo(Object paramObject)
  {
    Player localPlayer = (Player)paramObject;
    int i = this.name.compareTo(localPlayer.name);
    if (i == 0)
    {
      Stat localStat = new Stat(this.of, 2, 62, 5, 31, "Age");
      i = new Integer(localStat.getValue(this.index)).compareTo(new Integer(localStat.getValue(localPlayer.index)));
    }
    return i;
  }
  
  public void setName(String paramString)
  {
    int i = paramString.length();
    if ((this.index != 0) && (i <= 15))
    {
      byte[] arrayOfByte1 = new byte[32];
      byte[] arrayOfByte2;
      try
      {
        arrayOfByte2 = paramString.getBytes("UTF-16LE");
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        arrayOfByte2 = new byte[30];
      }
      if (arrayOfByte2.length <= 30) {
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, arrayOfByte2.length);
      } else {
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, 30);
      }
      int j = 36872;
      int k = this.index * 124;
      if (this.index > 4999)
      {
        j = 14044;
        k = (this.index - 32768) * 124;
      }
      System.arraycopy(arrayOfByte1, 0, this.of.data, j + k, 32);
      this.of.data[(j + k + 48)] = -51;
      this.of.data[(j + k + 49)] = -51;
      Stat localStat1 = new Stat(this.of, 0, 3, 0, 1, "");
      Stat localStat2 = new Stat(this.of, 0, 3, 2, 1, "");
      localStat1.setValue(this.index, 1);
      localStat2.setValue(this.index, 1);
      this.name = paramString;
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/Player.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */