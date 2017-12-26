package editor;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class GIFPNGFilter
  extends FileFilter
{
  public boolean accept(File paramFile)
  {
    if (paramFile.isDirectory()) {
      return true;
    }
    String str = PES4Utils.getExtension(paramFile);
    if (str != null) {
      return (str.equals("png")) || (str.equals("gif"));
    }
    return false;
  }
  
  public String getDescription()
  {
    return "PNG / GIF";
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/GIFPNGFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */