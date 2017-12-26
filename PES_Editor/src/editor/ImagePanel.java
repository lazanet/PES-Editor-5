package editor;

import com.sixlegs.png.PngImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImagePanel
  extends JPanel
{
  private JButton[] flagButton;
  private boolean trans = true;
  private OptionFile of;
  private JFileChooser chooser = new JFileChooser();
  private JFileChooser chooserPNG = new JFileChooser();
  private GIFPNGFilter filter = new GIFPNGFilter();
  private PNGFilter pngFilter = new PNGFilter();
  private FlagImportDialog imageImpDia;
  private PngImage pngImage;
  
  public ImagePanel(OptionFile paramOptionFile, FlagImportDialog paramFlagImportDialog)
  {
    this.of = paramOptionFile;
    this.imageImpDia = paramFlagImportDialog;
    this.chooser.addChoosableFileFilter(this.filter);
    this.chooser.setAcceptAllFileFilterUsed(false);
    this.chooser.setDialogTitle("Import Logo");
    this.chooserPNG.addChoosableFileFilter(this.pngFilter);
    this.chooserPNG.setAcceptAllFileFilterUsed(false);
    this.chooserPNG.setDialogTitle("Export Logo");
    this.pngImage = new PngImage();
    this.flagButton = new JButton[Logos.total];
    JPanel localJPanel1 = new JPanel(new GridLayout(8, 10));
    PES4Utils.javaUI();
    for (int i = 0; i < Logos.total; i++)
    {
      this.flagButton[i] = new JButton();
      this.flagButton[i].setBackground(new Color(204, 204, 204));
      this.flagButton[i].setMargin(new Insets(0, 0, 0, 0));
      this.flagButton[i].setActionCommand(new Integer(i).toString());
      this.flagButton[i].addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent paramAnonymousActionEvent)
        {
          int i = new Integer(((JButton)paramAnonymousActionEvent.getSource()).getActionCommand()).intValue();
          ImageIcon localImageIcon = null;
          localImageIcon = new ImageIcon(Logos.get(ImagePanel.this.of, i, !ImagePanel.this.trans));
          Object[] arrayOfObject2 = { "Import PNG / GIF", "Export as PNG", "Import (OF2)", "Cancel" };
          Object[] arrayOfObject3 = { "Import PNG / GIF", "Export as PNG", "Cancel" };
          Object[] arrayOfObject1;
          if (ImagePanel.this.imageImpDia.of2Open) {
            arrayOfObject1 = arrayOfObject2;
          } else {
            arrayOfObject1 = arrayOfObject3;
          }
          int j = JOptionPane.showOptionDialog(null, "Options:", "Logo", 1, 3, localImageIcon, arrayOfObject1, arrayOfObject1[0]);
          if (j == 0)
          {
            int k = ImagePanel.this.chooser.showOpenDialog(null);
            if (k == 0)
            {
              File localFile = ImagePanel.this.chooser.getSelectedFile();
              try
              {
                String str = PES4Utils.getExtension(localFile);
                BufferedImage localBufferedImage;
                if ((str != null) && (str.equals("png"))) {
                  localBufferedImage = ImagePanel.this.pngImage.read(localFile);
                } else {
                  localBufferedImage = ImageIO.read(localFile);
                }
                int m = ImagePanel.this.checkImage(localBufferedImage);
                if ((m != -1) && (m < 16))
                {
                  Logos.set(ImagePanel.this.of, i, localBufferedImage);
                  ImagePanel.this.flagButton[i].setIcon(new ImageIcon(Logos.get(ImagePanel.this.of, i, !ImagePanel.this.trans)));
                }
              }
              catch (Exception localException)
              {
                JOptionPane.showMessageDialog(null, "Could not access file", "Error", 0);
              }
            }
          }
          if ((j == 1) && (Logos.isUsed(ImagePanel.this.of, i))) {
            ImagePanel.this.savePNG(i);
          }
          if ((ImagePanel.this.imageImpDia.of2Open) && (j == 2))
          {
            ImagePanel.this.imageImpDia.show(i, "Import Logo");
            ImagePanel.this.flagButton[i].setIcon(new ImageIcon(Logos.get(ImagePanel.this.of, i, !ImagePanel.this.trans)));
          }
        }
      });
      localJPanel1.add(this.flagButton[i]);
    }
    PES4Utils.systemUI();
    JButton localJButton = new JButton("Transparency");
    localJButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        ImagePanel.this.trans = (!ImagePanel.this.trans);
        ImagePanel.this.refresh();
      }
    });
    JPanel localJPanel2 = new JPanel(new BorderLayout());
    localJPanel2.add(localJPanel1, "Center");
    localJPanel2.add(localJButton, "South");
    add(localJPanel2);
    refresh();
  }
  
  private void savePNG(int paramInt)
  {
    int i = 0;
    int j = this.chooserPNG.showSaveDialog(null);
    if (j == 0)
    {
      File localFile = this.chooserPNG.getSelectedFile();
      if ((PES4Utils.getExtension(localFile) == null) || (!PES4Utils.getExtension(localFile).equals("png"))) {
        localFile = new File(localFile.getParent() + File.separator + localFile.getName() + ".png");
      }
      if (localFile.exists())
      {
        int k = JOptionPane.showConfirmDialog(null, localFile.getName() + "\nAlready exists in:\n" + localFile.getParent() + "\nAre you sure you want to overwrite this file?", "Overwrite:  " + localFile.getName(), 0, 2, null);
        if (k == 0)
        {
          boolean bool = localFile.delete();
          if (!bool)
          {
            JOptionPane.showMessageDialog(null, "Could not access file", "Error", 0);
            return;
          }
        }
        else
        {
          return;
        }
      }
      if (writeFile(localFile, paramInt)) {
        JOptionPane.showMessageDialog(null, localFile.getName() + "\nSaved in:\n" + localFile.getParent(), "File Successfully Saved", 1);
      } else {
        i = 1;
      }
      if (i != 0) {
        JOptionPane.showMessageDialog(null, "Could not access file", "Error", 0);
      }
    }
  }
  
  private boolean writeFile(File paramFile, int paramInt)
  {
    boolean bool = false;
    BufferedImage localBufferedImage = Logos.get(this.of, paramInt, false);
    try
    {
      ImageIO.write(localBufferedImage, "png", paramFile);
      bool = true;
    }
    catch (IOException localIOException)
    {
      bool = false;
    }
    return bool;
  }
  
  public void refresh()
  {
    for (int i = 0; i < Logos.total; i++) {
      this.flagButton[i].setIcon(new ImageIcon(Logos.get(this.of, i, !this.trans)));
    }
  }
  
  private int checkImage(BufferedImage paramBufferedImage)
  {
    int i = -1;
    if ((paramBufferedImage.getWidth() == 32) && (paramBufferedImage.getHeight() == 32))
    {
      ColorModel localColorModel = paramBufferedImage.getColorModel();
      if ((localColorModel instanceof IndexColorModel))
      {
        int[] arrayOfInt = new int['Ѐ'];
        Raster localRaster = paramBufferedImage.getData();
        localRaster.getPixels(0, 0, 32, 32, arrayOfInt);
        for (int j = 0; j < 1024; j++) {
          if (arrayOfInt[j] > i) {
            i = arrayOfInt[j];
          }
        }
        if (i > 15)
        {
          colourMsg();
          i = -1;
        }
      }
      else
      {
        notIndexMsg();
      }
    }
    else
    {
      sizeMsg();
    }
    return i;
  }
  
  private void notIndexMsg()
  {
    JOptionPane.showMessageDialog(null, "PNG files must be INDEXED format", "Error", 0);
  }
  
  private void colourMsg()
  {
    JOptionPane.showMessageDialog(null, "Too many colours, maximum is 16", "Error", 0);
  }
  
  private void sizeMsg()
  {
    JOptionPane.showMessageDialog(null, "Size must be 32x32 pixels", "Error", 0);
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/ImagePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */