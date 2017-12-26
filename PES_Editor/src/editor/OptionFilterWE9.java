package editor;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.filechooser.FileFilter;

public class OptionFilterWE9
  extends FileFilter
{
  public boolean accept(File paramFile)
  {
    if (paramFile == null) {
      return false;
    }
    if (paramFile.isDirectory()) {
      return true;
    }
    String str = PES4Utils.getExtension(paramFile);
    if (str != null)
    {
      if ((str.equals("xps")) && (fileIsXPSOption(paramFile))) {
        return true;
      }
      if ((str.equals("xsv")) && (fileIsXBOXOption(paramFile))) {
        return true;
      }
      if ((str.equals("zip")) && (fileIsMaxOption(paramFile))) {
        return true;
      }
    }
    else if (fileIsPCOption(paramFile))
    {
      return true;
    }
    return false;
  }
  
  private boolean fileIsXPSOption(File paramFile)
  {
    boolean bool = false;
    try
    {
      if (paramFile.canRead())
      {
        String str1 = "BESLES-53545PES5OPT";
        String str2 = "BESLES-53544PES5OPT";
        String str3 = "BASLUS-21220WWE9OPT";
        byte[] arrayOfByte = new byte[19];
        String str4 = "";
        RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramFile, "r");
        localRandomAccessFile.seek(21L);
        int i = PES4Utils.swabInt(localRandomAccessFile.readInt());
        if (localRandomAccessFile.skipBytes(i) == i)
        {
          i = PES4Utils.swabInt(localRandomAccessFile.readInt());
          if (localRandomAccessFile.skipBytes(i) == i)
          {
            i = PES4Utils.swabInt(localRandomAccessFile.readInt()) + 6;
            if (localRandomAccessFile.skipBytes(i) == i)
            {
              localRandomAccessFile.read(arrayOfByte);
              str4 = new String(arrayOfByte);
            }
          }
        }
        localRandomAccessFile.close();
        if ((str4.equals(str2)) || (str4.equals(str1)) || (str4.equals(str3))) {
          bool = true;
        }
      }
    }
    catch (IOException localIOException)
    {
      bool = false;
    }
    return bool;
  }
  
  private boolean fileIsPCOption(File paramFile)
  {
    boolean bool = false;
    try
    {
      if ((paramFile.canRead()) && (paramFile.length() == 1250304L))
      {
        byte[] arrayOfByte = new byte[4];
        RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramFile, "r");
        localRandomAccessFile.seek(5132L);
        localRandomAccessFile.read(arrayOfByte);
        localRandomAccessFile.close();
        if ((arrayOfByte[0] == -25) && (arrayOfByte[1] == -80) && (arrayOfByte[2] == 5) && (arrayOfByte[3] == -6)) {
          bool = true;
        }
      }
    }
    catch (IOException localIOException)
    {
      bool = false;
    }
    return bool;
  }
  
  private boolean fileIsXBOXOption(File paramFile)
  {
    boolean bool = false;
    try
    {
      if ((paramFile.canRead()) && (paramFile.length() == 1261588L))
      {
        byte[] arrayOfByte = new byte[4];
        RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramFile, "r");
        localRandomAccessFile.seek(16396L);
        localRandomAccessFile.read(arrayOfByte);
        localRandomAccessFile.close();
        if ((arrayOfByte[0] == -48) && (arrayOfByte[1] == 4) && (arrayOfByte[2] == 0) && (arrayOfByte[3] == 0)) {
          bool = true;
        }
      }
    }
    catch (IOException localIOException)
    {
      bool = false;
    }
    return bool;
  }
  
  private boolean fileIsMaxOption(File paramFile)
  {
    boolean bool = false;
    try
    {
      String str1 = "UDATA/4b4e0030/5252897584A1/KONAMI-XBOX-PES5OPT.xsv";
      String str2 = "UDATA/4b4e002f/5252897584A1/KONAMI-XBOX-WE9UOPT.xsv";
      ZipFile localZipFile = new ZipFile(paramFile);
      Enumeration localEnumeration = localZipFile.entries();
      while ((!bool) && (localEnumeration.hasMoreElements()))
      {
        ZipEntry localZipEntry = (ZipEntry)localEnumeration.nextElement();
        String str3 = localZipEntry.getName();
        if ((str3.equals(str1)) || (str3.equals(str2))) {
          bool = true;
        }
      }
      localZipFile.close();
    }
    catch (Exception localException)
    {
      bool = false;
    }
    return bool;
  }
  
  public String getDescription()
  {
    return "PES5  Option File";
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/OptionFilterWE9.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */