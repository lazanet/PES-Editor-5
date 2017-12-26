package editor;

import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

public class OptionPreview
  extends JPanel
  implements PropertyChangeListener
{
  private File file = null;
  private XportInfo xpInfo = new XportInfo();
  private JTextArea previewText = new JTextArea(20, 19);
  private FileFilter filter;
  
  public OptionPreview(JFileChooser paramJFileChooser)
  {
    this.filter = paramJFileChooser.getFileFilter();
    setBorder(BorderFactory.createTitledBorder("Details"));
    this.previewText.setFont(new Font("SansSerif", 0, 12));
    this.previewText.setEditable(false);
    this.previewText.setLineWrap(true);
    this.previewText.setWrapStyleWord(true);
    paramJFileChooser.addPropertyChangeListener(this);
    add(this.previewText);
  }
  
  public void loadImage()
  {
    if ((this.file == null) || (this.file.isDirectory()) || (!this.filter.accept(this.file)))
    {
      this.previewText.setText("");
      return;
    }
    String str = PES4Utils.getExtension(this.file);
    if (str != null)
    {
      if ((str.equals("xps")) && (this.xpInfo.getInfo(this.file))) {
        this.previewText.setText("PS2\n\nX-Port game name:\n" + this.xpInfo.gameName + "\n\nX-Port save name:\n" + this.xpInfo.saveName + "\n\nX-Port notes:\n" + this.xpInfo.notes);
      } else {
        this.previewText.setText("XBox");
      }
    }
    else {
      this.previewText.setText("PC");
    }
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent)
  {
    int i = 0;
    String str = paramPropertyChangeEvent.getPropertyName();
    if ("directoryChanged".equals(str))
    {
      this.file = null;
      i = 1;
    }
    else if ("SelectedFileChangedProperty".equals(str))
    {
      this.file = ((File)paramPropertyChangeEvent.getNewValue());
      i = 1;
    }
    if (i != 0)
    {
      this.previewText.setText("");
      if (isShowing())
      {
        loadImage();
        repaint();
      }
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/OptionPreview.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */