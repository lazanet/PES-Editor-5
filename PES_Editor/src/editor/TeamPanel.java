package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.UnsupportedEncodingException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TeamPanel
  extends JPanel
  implements ActionListener, ListSelectionListener, MouseListener
{
  OptionFile of;
  OptionFile of2;
  JList list;
  JTextField editor;
  JTextField abvEditor;
  TransferPanel tran;
  String[] team = new String[''];
  String[] abv = new String[''];
  JButton badgeButton;
  JButton backButton;
  JComboBox stadiumBox;
  JPanel panel3;
  EmblemChooserDialog flagChooser;
  LogoChooserDialog logoChooser;
  ImagePanel imagePan;
  private int firstFlag;
  FlagPanel flagPan;
  private boolean ok = false;
  private GlobalPanel globalPanel;
  private BackChooserDialog backChooser;
  private JButton colour1But;
  private JButton colour2But;
  private KitImportDialog kitImpDia;
  private DefaultIcon defaultIcon = new DefaultIcon();
  
  public TeamPanel(OptionFile paramOptionFile1, TransferPanel paramTransferPanel, EmblemChooserDialog paramEmblemChooserDialog, OptionFile paramOptionFile2, ImagePanel paramImagePanel, GlobalPanel paramGlobalPanel, KitImportDialog paramKitImportDialog, LogoChooserDialog paramLogoChooserDialog)
  {
    super(new BorderLayout());
    this.of = paramOptionFile1;
    this.of2 = paramOptionFile2;
    this.tran = paramTransferPanel;
    this.flagChooser = paramEmblemChooserDialog;
    this.logoChooser = paramLogoChooserDialog;
    this.imagePan = paramImagePanel;
    this.kitImpDia = paramKitImportDialog;
    this.globalPanel = paramGlobalPanel;
    this.backChooser = new BackChooserDialog(null);
    PES4Utils.javaUI();
    this.backButton = new JButton(new ImageIcon(Flags.get16(this.of, -1, false, false)));
    this.backButton.setBackground(new Color(204, 204, 204));
    this.backButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        int i = TeamPanel.this.list.getSelectedIndex();
        if (i != -1)
        {
          byte[] arrayOfByte1 = new byte[2];
          byte[] arrayOfByte2 = new byte[2];
          byte[] arrayOfByte3 = new byte[2];
          int j = 803732 + i * 140;
          arrayOfByte1[0] = TeamPanel.this.of.data[j];
          arrayOfByte1[1] = TeamPanel.this.of.data[(j + 4)];
          arrayOfByte3[0] = TeamPanel.this.of.data[(j + 1)];
          arrayOfByte3[1] = TeamPanel.this.of.data[(j + 5)];
          arrayOfByte2[0] = TeamPanel.this.of.data[(j + 2)];
          arrayOfByte2[1] = TeamPanel.this.of.data[(j + 6)];
          int k = TeamPanel.this.backChooser.getBack(TeamPanel.this.getEmblemImage(), arrayOfByte1, arrayOfByte3, arrayOfByte2);
          if (k != 99)
          {
            j = 803730 + i * 140;
            TeamPanel.this.of.data[j] = (byte)k;
            TeamPanel.this.backButton.setIcon(TeamPanel.this.backChooser.flagButton[k].getIcon());
          }
        }
      }
    });
    this.colour1But = new JButton();
    this.colour1But.setPreferredSize(new Dimension(20, 20));
    this.colour1But.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        int i = TeamPanel.this.list.getSelectedIndex();
        if (i != -1)
        {
          int j = 803732 + i * 140;
          int k = TeamPanel.this.of.toInt(TeamPanel.this.of.data[j]);
          int m = TeamPanel.this.of.toInt(TeamPanel.this.of.data[(j + 1)]);
          int n = TeamPanel.this.of.toInt(TeamPanel.this.of.data[(j + 2)]);
          Color localColor1 = new Color(k, m, n);
          Color localColor2 = JColorChooser.showDialog(null, "BG Colour 1", localColor1);
          if (localColor2 != null)
          {
            int i1 = (byte)localColor2.getRed();
            int i2 = (byte)localColor2.getGreen();
            int i3 = (byte)localColor2.getBlue();
            TeamPanel.this.of.data[j] = (byte)i1;
            TeamPanel.this.of.data[(j + 1)] = (byte)i2;
            TeamPanel.this.of.data[(j + 2)] = (byte)i3;
            TeamPanel.this.colour1But.setBackground(localColor2);
            TeamPanel.this.updateBackBut();
          }
        }
      }
    });
    this.colour2But = new JButton();
    this.colour2But.setPreferredSize(new Dimension(20, 20));
    this.colour2But.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        int i = TeamPanel.this.list.getSelectedIndex();
        if (i != -1)
        {
          int j = 803736 + i * 140;
          int k = TeamPanel.this.of.toInt(TeamPanel.this.of.data[j]);
          int m = TeamPanel.this.of.toInt(TeamPanel.this.of.data[(j + 1)]);
          int n = TeamPanel.this.of.toInt(TeamPanel.this.of.data[(j + 2)]);
          Color localColor1 = new Color(k, m, n);
          Color localColor2 = JColorChooser.showDialog(null, "BG Colour 2", localColor1);
          if (localColor2 != null)
          {
            int i1 = (byte)localColor2.getRed();
            int i2 = (byte)localColor2.getGreen();
            int i3 = (byte)localColor2.getBlue();
            TeamPanel.this.of.data[j] = (byte)i1;
            TeamPanel.this.of.data[(j + 1)] = (byte)i2;
            TeamPanel.this.of.data[(j + 2)] = (byte)i3;
            TeamPanel.this.colour2But.setBackground(localColor2);
            TeamPanel.this.updateBackBut();
          }
        }
      }
    });
    this.badgeButton = new JButton(new ImageIcon(Flags.get16(this.of, -1, false, false)));
    this.badgeButton.setBackground(new Color(204, 204, 204));
    this.badgeButton.addMouseListener(this);
    this.badgeButton.setToolTipText("Left click to change, right click to default");
    this.badgeButton.setAlignmentX(0.5F);
    this.badgeButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        int i = TeamPanel.this.list.getSelectedIndex();
        if (i != -1)
        {
          int j = TeamPanel.this.flagChooser.getFlag("Choose Emblem", 0);
          if (j != -1)
          {
            if (j < 50) {
              TeamPanel.this.badgeButton.setIcon(new ImageIcon(Flags.get128(TeamPanel.this.of, j, false, false)));
            } else {
              TeamPanel.this.badgeButton.setIcon(new ImageIcon(Flags.get16(TeamPanel.this.of, j - 50, false, false)));
            }
            int k = 803720 + i * 140;
            int m = 803728 + i * 140;
            System.arraycopy(Flags.getIndex(TeamPanel.this.of, j), 0, TeamPanel.this.of.data, k, 2);
            System.arraycopy(Flags.getIndex(TeamPanel.this.of, j), 0, TeamPanel.this.of.data, k + 4, 2);
            TeamPanel.this.of.data[m] = 1;
            TeamPanel.this.of.data[(m + 1)] = 1;
            TeamPanel.this.updateBackBut();
          }
        }
      }
    });
    PES4Utils.systemUI();
    JButton localJButton1 = new JButton(new CopySwapIcon(false));
    localJButton1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        int i = TeamPanel.this.list.getSelectedIndex();
        if (i != -1)
        {
          int j = 803732 + i * 140;
          System.arraycopy(TeamPanel.this.of.data, j, TeamPanel.this.of.data, j + 4, 3);
          TeamPanel.this.colour2But.setBackground(TeamPanel.this.colour1But.getBackground());
          TeamPanel.this.updateBackBut();
        }
      }
    });
    JButton localJButton2 = new JButton(new CopySwapIcon(true));
    localJButton2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        int i = TeamPanel.this.list.getSelectedIndex();
        if (i != -1)
        {
          int j = 803732 + i * 140;
          byte[] arrayOfByte = new byte[3];
          System.arraycopy(TeamPanel.this.of.data, j, arrayOfByte, 0, 3);
          System.arraycopy(TeamPanel.this.of.data, j + 4, TeamPanel.this.of.data, j, 3);
          System.arraycopy(arrayOfByte, 0, TeamPanel.this.of.data, j + 4, 3);
          Color localColor = TeamPanel.this.colour1But.getBackground();
          TeamPanel.this.colour1But.setBackground(TeamPanel.this.colour2But.getBackground());
          TeamPanel.this.colour2But.setBackground(localColor);
          TeamPanel.this.updateBackBut();
        }
      }
    });
    this.stadiumBox = new JComboBox();
    this.stadiumBox.setAlignmentX(0.5F);
    this.stadiumBox.setPreferredSize(new Dimension(375, 25));
    this.stadiumBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        int i = TeamPanel.this.stadiumBox.getSelectedIndex();
        int j = TeamPanel.this.list.getSelectedIndex();
        if ((paramAnonymousActionEvent.getActionCommand() == "y") && (i != -1) && (j != -1))
        {
          int k = 803741 + j * 140;
          TeamPanel.this.of.data[k] = TeamPanel.this.of.toByte(i);
          int m = 803743 + j * 140;
          TeamPanel.this.of.data[m] = 1;
        }
      }
    });
    this.list = new JList();
    this.list.setSelectionMode(0);
    this.list.setLayoutOrientation(0);
    this.list.setVisibleRowCount(11);
    this.list.addListSelectionListener(this);
    this.list.addMouseListener(this);
    this.editor = new JTextField(14);
    this.editor.setToolTipText("Enter new name and press return");
    this.abvEditor = new JTextField(4);
    this.abvEditor.setToolTipText("Enter new short name and press return");
    this.editor.addActionListener(this);
    this.abvEditor.addActionListener(this);
    JPanel localJPanel1 = new JPanel();
    JPanel localJPanel2 = new JPanel();
    JPanel localJPanel3 = new JPanel();
    JPanel localJPanel4 = new JPanel();
    this.panel3 = new JPanel();
    this.panel3.setLayout(new BoxLayout(this.panel3, 1));
    localJPanel2.add(this.stadiumBox);
    localJPanel3.add(this.editor);
    localJPanel4.add(this.abvEditor);
    this.panel3.add(localJPanel3);
    this.panel3.add(localJPanel4);
    JLabel localJLabel1 = new JLabel("Emblem");
    localJLabel1.setAlignmentX(0.5F);
    this.panel3.add(localJLabel1);
    this.panel3.add(this.badgeButton);
    JLabel localJLabel2 = new JLabel("Flag");
    localJLabel2.setAlignmentX(0.5F);
    JPanel localJPanel5 = new JPanel(new BorderLayout());
    JPanel localJPanel6 = new JPanel(new GridLayout(0, 1));
    localJPanel6.add(this.colour1But);
    localJPanel6.add(this.colour2But);
    localJPanel5.add(localJButton1, "West");
    localJPanel5.add(localJPanel6, "Center");
    localJPanel5.add(localJButton2, "East");
    JPanel localJPanel7 = new JPanel(new BorderLayout());
    localJPanel7.add(localJPanel5, "North");
    localJPanel7.add(this.backButton, "South");
    localJPanel1.add(localJPanel7);
    this.panel3.add(Box.createRigidArea(new Dimension(0, 10)));
    this.panel3.add(localJLabel2);
    this.panel3.add(localJPanel1);
    this.panel3.add(Box.createRigidArea(new Dimension(0, 30)));
    JLabel localJLabel3 = new JLabel("Stadium");
    localJLabel3.setAlignmentX(0.5F);
    this.panel3.add(localJLabel3);
    this.panel3.add(localJPanel2);
    JPanel localJPanel8 = new JPanel();
    this.panel3.add(localJPanel8);
    JScrollPane localJScrollPane = new JScrollPane(20, 31);
    localJScrollPane.setViewportView(this.list);
    add(localJScrollPane, "West");
    add(this.panel3, "Center");
  }
  
  public void refresh()
  {
    String[] arrayOfString = new String['Ê'];
    this.firstFlag = 284;
    this.stadiumBox.setActionCommand("n");
    this.stadiumBox.removeAllItems();
    for (int i = 0; i < Stadia.total; i++) {
      this.stadiumBox.addItem(Stadia.get(this.of, i));
    }
    this.stadiumBox.setSelectedIndex(-1);
    this.stadiumBox.setActionCommand("y");
    this.backButton.setIcon(new ImageIcon(Flags.get16(this.of, -1, false, false)));
    this.badgeButton.setIcon(new ImageIcon(Flags.get16(this.of, -1, false, false)));
    this.team = Clubs.getNames(this.of);
    for (int j = 0; j < 138; j++)
    {
      int i = 803657 + j * 140;
      if (this.of.data[i] != 0)
      {
        this.abv[j] = new String(this.of.data, i + 24, 3);
        arrayOfString[j] = (this.abv[j] + "     " + this.team[j]);
      }
      else
      {
        arrayOfString[j] = ("<" + String.valueOf(j) + ">");
      }
    }
    this.globalPanel.updateTeamBox(this.team);
    for (int j = 0; j < 57; j++) {
      arrayOfString[(j + 138)] = Stat.nation[j];
    }
    for (int j = 0; j < 7; j++) {
      arrayOfString[(j + 138 + 57)] = PES4Utils.extraSquad[j];
    }
    this.ok = false;
    this.list.setListData(arrayOfString);
    this.panel3.setVisible(false);
    this.ok = true;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    String str;
    int i;
    int j;
    int k;
    byte[] arrayOfByte2;
    if (paramActionEvent.getSource() == this.editor)
    {
      str = this.editor.getText();
      if ((str.length() <= 48) && (str.length() > 0))
      {
        i = this.list.getSelectedIndex();
        j = 803608 + i * 140;
        k = 803718 + i * 140;
        byte[] arrayOfByte1 = new byte[49];
        try
        {
          arrayOfByte2 = str.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          arrayOfByte2 = new byte[48];
        }
        if (arrayOfByte2.length <= 48) {
          System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, arrayOfByte2.length);
        } else {
          System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, 48);
        }
        System.arraycopy(arrayOfByte1, 0, this.of.data, j, 49);
        this.of.data[k] = 1;
        refresh();
        this.tran.refresh();
        if (i < this.list.getModel().getSize() - 1)
        {
          this.list.setSelectedIndex(i + 1);
          this.list.ensureIndexIsVisible(this.list.getSelectedIndex());
          this.editor.requestFocusInWindow();
          this.editor.selectAll();
        }
      }
    }
    else
    {
      str = this.abvEditor.getText();
      if (str.length() == 3)
      {
        str = str.toUpperCase();
        i = this.list.getSelectedIndex();
        j = 803681 + i * 140;
        k = 716932 + i * 628;
        int m = 803718 + i * 140;
        arrayOfByte2 = new byte[3];
        byte[] arrayOfByte3 = str.getBytes();
        System.arraycopy(arrayOfByte3, 0, arrayOfByte2, 0, 3);
        System.arraycopy(arrayOfByte2, 0, this.of.data, j, 3);
        System.arraycopy(arrayOfByte2, 0, this.of.data, k, 3);
        this.of.data[m] = 1;
        refresh();
        this.tran.refresh();
        if (i < this.list.getModel().getSize() - 1)
        {
          this.list.setSelectedIndex(i + 1);
          this.list.ensureIndexIsVisible(this.list.getSelectedIndex());
          this.abvEditor.requestFocusInWindow();
          this.abvEditor.selectAll();
        }
      }
    }
  }
  
  public void valueChanged(ListSelectionEvent paramListSelectionEvent)
  {
    if ((this.ok) && (!paramListSelectionEvent.getValueIsAdjusting()))
    {
      int i = this.list.getSelectedIndex();
      if ((i >= 0) && (i < 138))
      {
        if (!this.panel3.isVisible()) {
          this.panel3.setVisible(true);
        }
        int j = Clubs.getEmblem(this.of, i);
        if ((j >= this.firstFlag) && (j < this.firstFlag + 150))
        {
          j -= this.firstFlag;
          this.badgeButton.setIcon(new ImageIcon(Flags.getImage(this.of, j)));
        }
        else if (j == i + 111)
        {
          this.badgeButton.setIcon(this.defaultIcon);
        }
        else
        {
          this.badgeButton.setIcon(new ImageIcon(Flags.get16(this.of, -1, false, false)));
        }
        int k = 803732 + i * 140;
        int m = this.of.toInt(this.of.data[k]);
        int n = this.of.toInt(this.of.data[(k + 1)]);
        int i1 = this.of.toInt(this.of.data[(k + 2)]);
        Color localColor = new Color(m, n, i1);
        this.colour1But.setBackground(localColor);
        m = this.of.toInt(this.of.data[(k + 4)]);
        n = this.of.toInt(this.of.data[(k + 5)]);
        i1 = this.of.toInt(this.of.data[(k + 6)]);
        localColor = new Color(m, n, i1);
        this.colour2But.setBackground(localColor);
        updateBackBut();
        int i2 = this.of.toInt(this.of.data[(803741 + i * 140)]);
        this.stadiumBox.setActionCommand("n");
        this.stadiumBox.setSelectedIndex(i2);
        this.stadiumBox.setActionCommand("y");
        this.editor.setText(this.team[i]);
        this.abvEditor.setText(this.abv[i]);
      }
      else
      {
        this.editor.setText("");
        this.abvEditor.setText("");
        this.stadiumBox.setActionCommand("n");
        this.stadiumBox.setSelectedIndex(-1);
        this.stadiumBox.setActionCommand("y");
        this.badgeButton.setIcon(new ImageIcon(Flags.get16(this.of, -1, false, false)));
        this.panel3.setVisible(false);
      }
    }
  }
  
  public int getInt(byte[] paramArrayOfByte)
  {
    int i = 0;
    if (paramArrayOfByte.length == 2) {
      i = this.of.toInt(paramArrayOfByte[1]) << 8 | this.of.toInt(paramArrayOfByte[0]) & 0xFF;
    }
    return i;
  }
  
  public void mousePressed(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mouseClicked(MouseEvent paramMouseEvent)
  {
    int i = paramMouseEvent.getClickCount();
    int j = this.list.getSelectedIndex();
    if ((paramMouseEvent.getSource() == this.list) && (paramMouseEvent.getButton() == 1) && (i == 2) && (this.of2.fileName != null) && (j != -1))
    {
      int k = this.kitImpDia.show(j);
      if (k != -1) {
        importKit(j, k);
      }
    }
    if ((paramMouseEvent.getButton() == 3) && (i == 1) && (paramMouseEvent.getSource() == this.badgeButton) && (j != -1) && (j < 138))
    {
      Clubs.setEmblem(this.of, j, null);
      this.badgeButton.setIcon(this.defaultIcon);
      updateBackBut();
    }
  }
  
  private void updateBackBut()
  {
    int i = this.list.getSelectedIndex();
    int j = 803732 + i * 140;
    byte[] arrayOfByte1 = new byte[2];
    byte[] arrayOfByte2 = new byte[2];
    byte[] arrayOfByte3 = new byte[2];
    arrayOfByte1[0] = this.of.data[j];
    arrayOfByte1[1] = this.of.data[(j + 4)];
    arrayOfByte3[0] = this.of.data[(j + 1)];
    arrayOfByte3[1] = this.of.data[(j + 5)];
    arrayOfByte2[0] = this.of.data[(j + 2)];
    arrayOfByte2[1] = this.of.data[(j + 6)];
    j = 803730 + i * 140;
    this.backButton.setIcon(this.backChooser.getFlagBG(getEmblemImage(), this.of.data[j], arrayOfByte1, arrayOfByte3, arrayOfByte2));
  }
  
  private Image getEmblemImage()
  {
    Image localImage = null;
    int i = this.list.getSelectedIndex();
    int j = Clubs.getEmblem(this.of, i);
    if ((j >= this.firstFlag) && (j < this.firstFlag + 150))
    {
      ImageIcon localImageIcon = (ImageIcon)this.badgeButton.getIcon();
      localImage = localImageIcon.getImage();
    }
    return localImage;
  }
  
  private void importKit(int paramInt1, int paramInt2)
  {
    int i = 0;
    if (paramInt1 < 138)
    {
      i = Clubs.getEmblem(this.of, paramInt1) - this.firstFlag;
      if ((i >= 0) && (i < 150)) {
        Flags.deleteImage(this.of, i);
      }
    }
    byte[] arrayOfByte1 = new byte[4];
    boolean[] arrayOfBoolean = new boolean[4];
    for (int j = 0; j < 4; j++)
    {
      arrayOfBoolean[j] = true;
      if (Kits.logoUsed(this.of, paramInt1, j)) {
        arrayOfByte1[j] = Kits.getLogo(this.of, paramInt1, j);
      } else {
        arrayOfByte1[j] = 88;
      }
    }
    int n;
    for (int j = 0; j < 202; j++) {
      for (int k = 0; (j != paramInt1) && (k < 4); k++) {
        if (arrayOfByte1[k] != 88) {
          for (n = 0; n < 4; n++) {
            if (Kits.getLogo(this.of, j, n) == arrayOfByte1[k]) {
              if (Kits.logoUsed(this.of, j, n)) {
                arrayOfBoolean[k] = false;
              } else {
                Kits.setLogoUnused(this.of, j, n);
              }
            }
          }
        }
      }
    }
    for (int j = 0; j < 4; j++) {
      if ((arrayOfBoolean[j]) && (arrayOfByte1[j] >= 0) && (arrayOfByte1[j] < 80)) {
        Logos.delete(this.of, arrayOfByte1[j]);
      }
    }
    if (paramInt1 < 138)
    {
      int j = Clubs.getEmblem(this.of2, paramInt2) - this.firstFlag;
      byte[] arrayOfByte2 = Flags.getIndex(this.of2, j);
      if ((j >= 0) && (j < 150)) {
        if (j < 50)
        {
          if (Flags.getFree128(this.of) > 0)
          {
            Flags.import128(this.of, Flags.count128(this.of), this.of2, Flags.getLoc(this.of2, j));
            arrayOfByte2 = Flags.getIndex(this.of, Flags.count128(this.of) - 1);
          }
          else
          {
            n = this.flagChooser.getFlag("Replace Emblem", 2);
            if (n != -1)
            {
              Flags.import128(this.of, n, this.of2, Flags.getLoc(this.of2, j));
              arrayOfByte2 = Flags.getIndex(this.of, n);
            }
            else
            {
              arrayOfByte2 = (byte[])null;
            }
          }
        }
        else if (Flags.getFree16(this.of) > 0)
        {
          Flags.import16(this.of, Flags.count16(this.of), this.of2, Flags.getLoc(this.of2, j) - 50);
          arrayOfByte2 = Flags.getIndex(this.of, Flags.count16(this.of) + 49);
        }
        else
        {
          n = this.flagChooser.getFlag("Replace Emblem", 1);
          if (n != -1)
          {
            Flags.import16(this.of, n - 50, this.of2, Flags.getLoc(this.of2, j) - 50);
            arrayOfByte2 = Flags.getIndex(this.of, n);
          }
          else
          {
            arrayOfByte2 = (byte[])null;
          }
        }
      }
      Clubs.importClub(this.of, paramInt1, this.of2, paramInt2);
      if ((j >= 0) && (j < 150)) {
        Clubs.setEmblem(this.of, paramInt1, arrayOfByte2);
      }
    }
    Kits.importKit(this.of, paramInt1, this.of2, paramInt2);
    for (int j = 0; j < 4; j++) {
      if (Kits.logoUsed(this.of2, paramInt2, j))
      {
        int m = 0;
        for (n = 0; (m == 0) && (n < j); n++) {
          if (Kits.getLogo(this.of2, paramInt2, j) == Kits.getLogo(this.of2, paramInt2, n)) {
            m = 1;
          }
        }
        if (m == 0)
        {
          n = this.logoChooser.getFlag("Choose logo to replace", Logos.get(this.of2, Kits.getLogo(this.of2, paramInt2, j), false));
          int i1;
          if (n != 88)
          {
            Logos.importLogo(this.of2, Kits.getLogo(this.of2, paramInt2, j), this.of, n);
            for (i1 = j; i1 < 4; i1++) {
              if (Kits.getLogo(this.of2, paramInt2, j) == Kits.getLogo(this.of2, paramInt2, i1)) {
                Kits.setLogo(this.of, paramInt1, i1, (byte)n);
              }
            }
          }
          else
          {
            for (i1 = j; i1 < 4; i1++) {
              if (Kits.getLogo(this.of2, paramInt2, j) == Kits.getLogo(this.of2, paramInt2, i1)) {
                Kits.setLogoUnused(this.of, paramInt1, i1);
              }
            }
          }
        }
      }
    }
    this.flagPan.refresh();
    this.imagePan.refresh();
    this.tran.refresh();
    refresh();
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/TeamPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
