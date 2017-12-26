package editor;

import java.awt.Container;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class BackChooserDialog
  extends JDialog
  implements ActionListener
{
  JButton[] flagButton = new JButton[12];
  byte slot;
  Raster[] raster = new Raster[12];
  
  public BackChooserDialog(Frame paramFrame)
  {
    super(paramFrame, "Choose Background", true);
    JPanel localJPanel = new JPanel(new GridLayout(3, 4));
    for (int i = 0; i < 12; i++)
    {
      this.flagButton[i] = new JButton();
      URL backUrl = Editor.class.getResource("data/backflag" + String.valueOf(i) + ".png");
      BufferedImage img = null;
      if (backUrl != null) {
        try
        {
          img = ImageIO.read((URL)backUrl);
          this.raster[i] = ((BufferedImage)img).getData();
        }
        catch (IOException localIOException) {}
      }
      this.flagButton[i].setMargin(new Insets(0, 0, 0, 0));
      this.flagButton[i].setActionCommand(new Integer(i).toString());
      this.flagButton[i].addActionListener(this);
      localJPanel.add(this.flagButton[i]);
    }
    byte[] arrayOfByte = { 0, -1 };
    byte[] obj1 = { 0, -1 };
    byte[] obj2 = { 0, -1 };
    refresh(null, arrayOfByte, obj1, obj2);
    CancelButton localCancelButton = new CancelButton(this);
    getContentPane().add(localCancelButton, "South");
    getContentPane().add(localJPanel, "Center");
    this.slot = 99;
    pack();
    setResizable(false);
  }
  
  public byte getBack(Image paramImage, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
  {
    this.slot = 99;
    refresh(paramImage, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3);
    setVisible(true);
    return this.slot;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    this.slot = new Byte(((JButton)paramActionEvent.getSource()).getActionCommand()).byteValue();
    setVisible(false);
  }
  
  private void refresh(Image paramImage, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
  {
    for (byte b = 0; b < 12; b = (byte)(b + 1)) {
      this.flagButton[b].setIcon(getFlagBG(paramImage, b, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3));
    }
  }
  
  public ImageIcon getFlagBG(Image paramImage, byte paramByte, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
  {
    IndexColorModel localIndexColorModel = new IndexColorModel(1, 2, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3);
    BufferedImage localBufferedImage1 = new BufferedImage(localIndexColorModel, (WritableRaster)this.raster[paramByte], false, null);
    BufferedImage localBufferedImage2 = new BufferedImage(85, 64, 6);
    Graphics2D localGraphics2D = (Graphics2D)localBufferedImage2.getGraphics();
    localGraphics2D.drawImage(localBufferedImage1, 0, 0, null);
    if (paramImage != null) {
      localGraphics2D.drawImage(paramImage, 11, 0, null);
    }
    ImageIcon localImageIcon = new ImageIcon(localBufferedImage2);
    return localImageIcon;
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/BackChooserDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
