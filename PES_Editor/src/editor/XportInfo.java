package editor;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class XportInfo
{
  public String gameName = new String("");
  public String saveName = new String("");
  public String notes = new String("");
  
  public boolean getInfo(File paramFile)
  {
    if (paramFile.isFile()) {
      try
      {
        RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramFile, "r");
        this.gameName = "";
        this.saveName = "";
        this.notes = "";
        localRandomAccessFile.seek(21L);
        int i = PES4Utils.swabInt(localRandomAccessFile.readInt());
        byte[] arrayOfByte = new byte[i];
        localRandomAccessFile.read(arrayOfByte);
        this.gameName = new String(arrayOfByte);
        i = PES4Utils.swabInt(localRandomAccessFile.readInt());
        arrayOfByte = new byte[i];
        localRandomAccessFile.read(arrayOfByte);
        this.saveName = new String(arrayOfByte);
        i = PES4Utils.swabInt(localRandomAccessFile.readInt());
        arrayOfByte = new byte[i];
        localRandomAccessFile.read(arrayOfByte);
        this.notes = new String(arrayOfByte);
        localRandomAccessFile.close();
        return true;
      }
      catch (IOException localIOException)
      {
        return false;
      }
    }
    return false;
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/XportInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */