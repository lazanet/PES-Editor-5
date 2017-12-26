package editor;

import com.sixlegs.png.PngImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FlagPanel
  extends JPanel
  implements MouseListener
{
  private JButton[] flagButton;
  private boolean trans = true;
  private OptionFile of;
  private JFileChooser chooser = new JFileChooser();
  private JFileChooser chooserPNG = new JFileChooser();
  private GIFPNGFilter filter128 = new GIFPNGFilter();
  private PNGFilter pngFilter = new PNGFilter();
  private EmblemImportDialog flagImpDia;
  private TeamPanel teamPanel;
  private JPanel flagPanel;
  private JButton addButton;
  private JButton add2Button;
  private JLabel free16Label;
  private JLabel free128Label;
  private PngImage pngImage;
  private JLabel largeFlag;
  
  public FlagPanel(OptionFile paramOptionFile, EmblemImportDialog paramEmblemImportDialog, TeamPanel paramTeamPanel)
  {
    this.of = paramOptionFile;
    this.flagImpDia = paramEmblemImportDialog;
    this.teamPanel = paramTeamPanel;
    this.chooser.addChoosableFileFilter(this.filter128);
    this.chooser.setAcceptAllFileFilterUsed(false);
    this.chooser.setDialogTitle("Import Emblem");
    this.chooserPNG.addChoosableFileFilter(this.pngFilter);
    this.chooserPNG.setAcceptAllFileFilterUsed(false);
    this.chooserPNG.setDialogTitle("Export Emblem");
    this.pngImage = new PngImage();
    this.flagButton = new JButton[100];
    this.flagPanel = new JPanel(new GridLayout(10, 10));
    PES4Utils.javaUI();
    for (int i = 0; i < 100; i++)
    {
      this.flagButton[i] = new JButton();
      this.flagButton[i].setBackground(new Color(204, 204, 204));
      this.flagButton[i].setMargin(new Insets(0, 0, 0, 0));
      this.flagButton[i].setActionCommand(new Integer(i).toString());
      this.flagButton[i].addMouseListener(this);
      this.flagButton[i].addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent paramAnonymousActionEvent)
        {
          int i = new Integer(((JButton)paramAnonymousActionEvent.getSource()).getActionCommand()).intValue();
          ImageIcon localImageIcon = null;
          boolean bool = false;
          if (i >= Flags.count16(FlagPanel.this.of))
          {
            bool = true;
            i = 99 - i;
            localImageIcon = new ImageIcon(Flags.get128(FlagPanel.this.of, i, !FlagPanel.this.trans, false));
          }
          else
          {
            localImageIcon = new ImageIcon(Flags.get16(FlagPanel.this.of, i, !FlagPanel.this.trans, false));
          }
          Object[] arrayOfObject2 = { "Delete", "Import PNG / GIF", "Export as PNG", "Import (OF2)", "Cancel" };
          Object[] arrayOfObject3 = { "Delete", "Import PNG / GIF", "Export as PNG", "Cancel" };
          Object[] arrayOfObject1;
          if (FlagPanel.this.flagImpDia.of2Open) {
            arrayOfObject1 = arrayOfObject2;
          } else {
            arrayOfObject1 = arrayOfObject3;
          }
          int j = JOptionPane.showOptionDialog(null, "Options:", "Emblem", 1, 3, localImageIcon, arrayOfObject1, arrayOfObject1[0]);
          if (j == 0)
          {
            if (bool) {
              Flags.delete128(FlagPanel.this.of, i);
            } else {
              Flags.delete16(FlagPanel.this.of, i);
            }
            FlagPanel.this.teamPanel.refresh();
            FlagPanel.this.refresh();
          }
          int k;
          if (j == 1)
          {
            k = FlagPanel.this.chooser.showOpenDialog(null);
            if (k == 0)
            {
              File localFile = FlagPanel.this.chooser.getSelectedFile();
              try
              {
                String str = PES4Utils.getExtension(localFile);
                BufferedImage localBufferedImage;
                if ((str != null) && (str.equals("png"))) {
                  localBufferedImage = FlagPanel.this.pngImage.read(localFile);
                } else {
                  localBufferedImage = ImageIO.read(localFile);
                }
                int m = FlagPanel.this.checkImage(localBufferedImage);
                if (m != 0)
                {
                  if (bool)
                  {
                    if (m < 128) {
                      if (m > 15) {
                        Flags.set128(FlagPanel.this.of, i, localBufferedImage);
                      } else {
                        FlagPanel.this.wasteMsg();
                      }
                    }
                  }
                  else if (m < 16) {
                    Flags.set16(FlagPanel.this.of, i, localBufferedImage);
                  } else {
                    FlagPanel.this.col16Msg();
                  }
                  FlagPanel.this.teamPanel.refresh();
                  FlagPanel.this.refresh();
                }
              }
              catch (Exception localException)
              {
                JOptionPane.showMessageDialog(null, "Could not open file", "Error", 0);
              }
            }
          }
          if (j == 2) {
            FlagPanel.this.savePNG(bool, i);
          }
          if ((FlagPanel.this.flagImpDia.of2Open) && (j == 3))
          {
            k = -1;
            if (bool)
            {
              k = FlagPanel.this.flagImpDia.getFlag("Import Emblem", 2);
              if (k != -1) {
                Flags.import128(FlagPanel.this.of, i, FlagPanel.this.flagImpDia.of, k);
              }
            }
            else
            {
              k = FlagPanel.this.flagImpDia.getFlag("Import Emblem", 1);
              if (k != -1)
              {
                k -= 50;
                Flags.import16(FlagPanel.this.of, i, FlagPanel.this.flagImpDia.of, k);
              }
            }
            FlagPanel.this.teamPanel.refresh();
            FlagPanel.this.refresh();
          }
        }
      });
      this.flagPanel.add(this.flagButton[i]);
    }
    PES4Utils.systemUI();
    JButton localJButton = new JButton("Transparency");
    localJButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        FlagPanel.this.trans = (!FlagPanel.this.trans);
        FlagPanel.this.refresh();
      }
    });
    this.free16Label = new JLabel();
    this.free128Label = new JLabel();
    this.addButton = new JButton("Add Emblem");
    this.addButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if ((Flags.getFree128(FlagPanel.this.of) > 0) || (Flags.getFree16(FlagPanel.this.of) > 0))
        {
          int i = FlagPanel.this.chooser.showOpenDialog(null);
          if (i == 0)
          {
            File localFile = FlagPanel.this.chooser.getSelectedFile();
            try
            {
              String str = PES4Utils.getExtension(localFile);
              BufferedImage localBufferedImage;
              if ((str != null) && (str.equals("png"))) {
                localBufferedImage = FlagPanel.this.pngImage.read(localFile);
              } else {
                localBufferedImage = ImageIO.read(localFile);
              }
              int j = FlagPanel.this.checkImage(localBufferedImage);
              if (j != -1)
              {
                if (j < 16) {
                  Flags.set16(FlagPanel.this.of, Flags.count16(FlagPanel.this.of), localBufferedImage);
                } else if (j < 128) {
                  Flags.set128(FlagPanel.this.of, Flags.count128(FlagPanel.this.of), localBufferedImage);
                }
                FlagPanel.this.teamPanel.refresh();
                FlagPanel.this.refresh();
              }
            }
            catch (Exception localException)
            {
              JOptionPane.showMessageDialog(null, "Could not open file", "Error", 0);
            }
          }
        }
      }
    });
    this.add2Button = new JButton("Add Emblem (OF2)");
    this.add2Button.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        int i = -1;
        if (Flags.getFree128(FlagPanel.this.of) > 0) {
          i = FlagPanel.this.flagImpDia.getFlag("Import Emblem", 0);
        } else if (Flags.getFree16(FlagPanel.this.of) > 0) {
          i = FlagPanel.this.flagImpDia.getFlag("Import Emblem", 1);
        }
        if (i != -1)
        {
          if (i > 49)
          {
            i -= 50;
            Flags.import16(FlagPanel.this.of, Flags.count16(FlagPanel.this.of), FlagPanel.this.flagImpDia.of, i);
          }
          else
          {
            Flags.import128(FlagPanel.this.of, Flags.count128(FlagPanel.this.of), FlagPanel.this.flagImpDia.of, i);
          }
          FlagPanel.this.teamPanel.refresh();
          FlagPanel.this.refresh();
        }
      }
    });
    JPanel localJPanel1 = new JPanel();
    JPanel localJPanel2 = new JPanel(new GridLayout(0, 1));
    localJPanel2.add(this.free16Label);
    localJPanel2.add(this.free128Label);
    localJPanel2.add(this.addButton);
    localJPanel2.add(this.add2Button);
    localJPanel1.add(localJPanel2);
    this.largeFlag = new JLabel();
    this.largeFlag.setIcon(new ImageIcon(Flags.get16(this.of, -1, false, false)));
    JPanel localJPanel3 = new JPanel(new BorderLayout());
    localJPanel3.setBorder(BorderFactory.createLineBorder(Color.gray));
    localJPanel3.add(new JLabel("16 Colour Format"), "North");
    localJPanel3.add(this.flagPanel, "Center");
    localJPanel3.add(new JLabel("128 Colour Format", 4), "South");
    JPanel localJPanel4 = new JPanel(new BorderLayout());
    localJPanel4.add(localJPanel3, "Center");
    localJPanel4.add(localJButton, "South");
    add(localJPanel4);
    add(this.largeFlag);
    add(localJPanel1);
    refresh();
  }
  
  private void savePNG(boolean paramBoolean, int paramInt)
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
      if (writeFile(localFile, paramBoolean, paramInt)) {
        JOptionPane.showMessageDialog(null, localFile.getName() + "\nSaved in:\n" + localFile.getParent(), "File Successfully Saved", 1);
      } else {
        i = 1;
      }
      if (i != 0) {
        JOptionPane.showMessageDialog(null, "Could not access file", "Error", 0);
      }
    }
  }
  
  private boolean writeFile(File paramFile, boolean paramBoolean, int paramInt)
  {
    boolean bool = false;
    BufferedImage localBufferedImage;
    if (paramBoolean) {
      localBufferedImage = (BufferedImage)Flags.get128(this.of, paramInt, false, false);
    } else {
      localBufferedImage = (BufferedImage)Flags.get16(this.of, paramInt, false, false);
    }
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
    for (int i = 0; i < Flags.count16(this.of); i++)
    {
      this.flagButton[i].setIcon(new ImageIcon(Flags.get16(this.of, i, !this.trans, true)));
      this.flagButton[i].setVisible(true);
    }
    for (int i = 0; i < Flags.count128(this.of); i++)
    {
      this.flagButton[(99 - i)].setIcon(new ImageIcon(Flags.get128(this.of, i, !this.trans, true)));
      this.flagButton[(99 - i)].setVisible(true);
    }
    for (int i = Flags.count16(this.of); i < 100 - Flags.count128(this.of); i++) {
      this.flagButton[i].setVisible(false);
    }
    this.free16Label.setText("16-colour, can stock: " + Flags.getFree16(this.of));
    this.free128Label.setText("128-colour, can stock: " + Flags.getFree128(this.of));
    if (this.flagImpDia.of2Open) {
      this.add2Button.setVisible(true);
    } else {
      this.add2Button.setVisible(false);
    }
    if (Flags.getFree16(this.of) > 0)
    {
      this.addButton.setEnabled(true);
      this.add2Button.setEnabled(true);
    }
    else
    {
      this.addButton.setEnabled(false);
      this.add2Button.setEnabled(false);
    }
  }
  
  public void mousePressed(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent)
  {
    JButton localJButton = (JButton)paramMouseEvent.getSource();
    int i = new Integer(localJButton.getActionCommand()).intValue();
    if (i >= Flags.count16(this.of))
    {
      i = 99 - i;
      this.largeFlag.setIcon(new ImageIcon(Flags.get128(this.of, i, !this.trans, false)));
    }
    else
    {
      this.largeFlag.setIcon(new ImageIcon(Flags.get16(this.of, i, !this.trans, false)));
    }
  }
  
  public void mouseExited(MouseEvent paramMouseEvent)
  {
    this.largeFlag.setIcon(new ImageIcon(Flags.get16(this.of, -1, false, false)));
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
  private int checkImage(BufferedImage paramBufferedImage)
  {
    int i = -1;
    if ((paramBufferedImage.getWidth() == 64) && (paramBufferedImage.getHeight() == 64))
    {
      ColorModel localColorModel = paramBufferedImage.getColorModel();
      if ((localColorModel instanceof IndexColorModel))
      {
        int[] arrayOfInt = new int[Flags.raster];
        Raster localRaster = paramBufferedImage.getData();
        localRaster.getPixels(0, 0, 64, 64, arrayOfInt);
        for (int j = 0; j < Flags.raster; j++) {
          if (arrayOfInt[j] > i) {
            i = arrayOfInt[j];
          }
        }
        if (i > 127)
        {
          colourMsg();
          i = -1;
        }
        else if ((i > 15) && (Flags.getFree128(this.of) == 0))
        {
          noSpaceMsg();
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
  
  private void noSpaceMsg()
  {
    JOptionPane.showMessageDialog(null, "Not enough space for a 128-colour emblem", "Error", 0);
  }
  
  private void colourMsg()
  {
    JOptionPane.showMessageDialog(null, "Too many colours, maximum is 128", "Error", 0);
  }
  
  private void sizeMsg()
  {
    JOptionPane.showMessageDialog(null, "Size must be 64x64 pixels", "Error", 0);
  }
  
  private void col16Msg()
  {
    JOptionPane.showMessageDialog(null, "Too many colours for a 16-colour slot", "Error", 0);
  }
  
  private void wasteMsg()
  {
    JOptionPane.showMessageDialog(null, "A 16 colour image in a 128-colour slot would waste space!", "Error", 0);
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/FlagPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
