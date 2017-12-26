package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.PrintStream;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FormPanel
  extends JPanel
  implements ListSelectionListener, DropTargetListener, DragSourceListener, DragGestureListener
{
  int team;
  OptionFile of;
  SquadList squadList;
  PositionList posList;
  JobList sFK;
  JobList lFK;
  JobList rCR;
  JobList lCR;
  JobList pk;
  JobList cap;
  private byte[] formData = { 9, 9, 11, 11, 18, 26, 26, 34, 34, 43, 63, 41, 85, 19, 52, 75, 29, 64, 40, 52, 0, 7, 1, 9, 8, 12, 23, 22, 28, 24, 38, 9, 9, 11, 11, 18, 18, 26, 26, 34, 43, 63, 41, 85, 19, 61, 43, 75, 29, 52, 52, 0, 7, 1, 9, 8, 14, 10, 23, 22, 26, 38, 9, 9, 11, 11, 18, 26, 26, 34, 43, 43, 63, 41, 85, 19, 52, 75, 29, 52, 66, 38, 0, 7, 1, 9, 8, 12, 23, 22, 26, 40, 36, 9, 9, 11, 11, 18, 18, 30, 30, 43, 43, 63, 41, 85, 19, 61, 43, 70, 34, 66, 38, 0, 7, 1, 9, 8, 14, 10, 28, 24, 40, 36, 9, 9, 11, 11, 26, 26, 26, 26, 43, 43, 63, 41, 85, 19, 77, 61, 43, 27, 66, 38, 0, 7, 1, 9, 8, 20, 21, 17, 18, 40, 36, 9, 9, 11, 11, 18, 30, 30, 43, 43, 43, 63, 41, 85, 19, 52, 64, 40, 72, 32, 52, 0, 7, 1, 9, 8, 12, 28, 24, 30, 29, 38, 9, 9, 11, 11, 18, 18, 30, 43, 43, 43, 63, 41, 85, 19, 61, 43, 52, 72, 32, 52, 0, 7, 1, 9, 8, 14, 10, 26, 30, 29, 38, 9, 9, 11, 11, 26, 26, 26, 43, 43, 43, 63, 41, 85, 19, 77, 52, 27, 72, 32, 52, 0, 7, 1, 9, 8, 21, 19, 17, 30, 29, 38, 9, 9, 9, 18, 26, 26, 26, 34, 34, 43, 72, 52, 32, 52, 52, 77, 27, 64, 40, 52, 0, 7, 3, 1, 12, 19, 23, 22, 28, 24, 38, 9, 9, 9, 18, 18, 26, 26, 34, 34, 43, 72, 52, 32, 61, 43, 77, 27, 64, 40, 52, 0, 7, 3, 1, 14, 10, 23, 22, 28, 24, 38, 9, 9, 9, 18, 26, 26, 34, 34, 43, 43, 72, 52, 32, 52, 77, 27, 64, 40, 66, 38, 0, 7, 3, 1, 12, 23, 22, 28, 24, 40, 36, 9, 9, 9, 18, 18, 26, 26, 34, 43, 43, 72, 52, 32, 61, 43, 77, 27, 52, 66, 38, 0, 7, 3, 1, 14, 10, 23, 22, 26, 40, 36, 9, 9, 9, 18, 26, 26, 34, 43, 43, 43, 72, 52, 32, 52, 77, 27, 52, 72, 32, 52, 0, 7, 3, 1, 12, 23, 22, 26, 30, 29, 38, 9, 9, 9, 18, 18, 30, 30, 43, 43, 43, 72, 52, 32, 61, 43, 70, 34, 72, 32, 52, 0, 7, 3, 1, 14, 10, 28, 24, 30, 29, 38, 9, 9, 9, 26, 26, 26, 26, 43, 43, 43, 72, 52, 32, 77, 61, 43, 27, 72, 32, 52, 0, 7, 3, 1, 20, 21, 17, 18, 30, 29, 38, 9, 9, 9, 12, 12, 18, 26, 26, 34, 43, 72, 52, 32, 87, 17, 52, 75, 29, 52, 52, 0, 7, 3, 1, 9, 8, 12, 23, 22, 26, 38, 9, 9, 9, 12, 12, 18, 18, 30, 30, 43, 72, 52, 32, 87, 17, 61, 43, 70, 34, 52, 0, 7, 3, 1, 9, 8, 14, 10, 28, 24, 38, 9, 9, 9, 12, 12, 26, 26, 26, 26, 43, 72, 52, 32, 87, 17, 77, 61, 43, 27, 52, 0, 7, 3, 1, 9, 8, 20, 21, 17, 18, 38, 9, 9, 9, 12, 12, 18, 34, 34, 43, 43, 72, 52, 32, 87, 17, 52, 64, 40, 66, 38, 0, 7, 3, 1, 9, 8, 12, 28, 24, 40, 36, 9, 9, 9, 12, 12, 18, 18, 34, 43, 43, 72, 52, 32, 87, 17, 61, 43, 52, 66, 38, 0, 7, 3, 1, 9, 8, 14, 10, 26, 40, 36, 9, 9, 9, 12, 12, 26, 26, 26, 43, 43, 72, 52, 32, 87, 17, 77, 52, 27, 66, 38, 0, 7, 3, 1, 9, 8, 21, 19, 17, 40, 36 };
  private String[] formName = { "Formation", "4-5-1 A", "4-5-1 B", "4-4-2 A", "4-4-2 B", "4-4-2 C", "4-3-3 A", "4-3-3 B", "4-3-3 C", "3-6-1 A", "3-6-1 B", "3-5-2 A", "3-5-2 B", "3-4-3 A", "3-4-3 B", "3-4-3 C", "5-4-1 A", "5-4-1 B", "5-4-1 C", "5-3-2 A", "5-3-2 B", "5-3-2 C" };
  private JComboBox formBox;
  boolean ok = false;
  PitchPanel pitchPanel;
  static boolean fromPitch = false;
  private JButton snapButton;
  AttDefPanel adPanel;
  JComboBox roleBox;
  Role[] role;
  JComboBox altBox;
  SquadNumList numList;
  private JFileChooser chooserPNG = new JFileChooser();
  private PNGFilter pngFilter = new PNGFilter();
  int def = 0;
  int mid = 0;
  int att = 0;
  private TeamSetPanel teamSetPan;
  private StrategyPanel stratPan;
  private DataFlavor localPlayerFlavor;
  private int sourceIndex = -1;
  
  public FormPanel(OptionFile paramOptionFile)
  {
    this.of = paramOptionFile;
    String str = "application/x-java-jvm-local-objectref;class=editor.Player";
    try
    {
      this.localPlayerFlavor = new DataFlavor(str);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      System.out.println("FormTransferHandler: unable to create data flavor");
    }
    this.chooserPNG.addChoosableFileFilter(this.pngFilter);
    this.chooserPNG.setAcceptAllFileFilterUsed(false);
    this.chooserPNG.setDialogTitle("Save Snapshot");
    JPanel localJPanel1 = new JPanel(new GridLayout(0, 6));
    JPanel localJPanel2 = new JPanel(new BorderLayout());
    JPanel localJPanel3 = new JPanel(new BorderLayout());
    JPanel localJPanel4 = new JPanel(new BorderLayout());
    this.numList = new SquadNumList(this.of);
    String[] arrayOfString = { "Formation", "Formation A", "Formation B" };
    this.altBox = new JComboBox(arrayOfString);
    this.altBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (paramAnonymousActionEvent.getActionCommand() == "y")
        {
          FormPanel.this.posList.alt = FormPanel.this.altBox.getSelectedIndex();
          FormPanel.this.posList.refresh(FormPanel.this.team);
          FormPanel.this.updateRoleBox();
          FormPanel.this.teamSetPan.alt = FormPanel.this.altBox.getSelectedIndex();
          FormPanel.this.teamSetPan.refresh(FormPanel.this.team);
          FormPanel.this.pitchPanel.repaint();
          FormPanel.this.adPanel.repaint();
        }
      }
    });
    this.roleBox = new JComboBox();
    this.roleBox.setPreferredSize(new Dimension(56, 25));
    this.roleBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (paramAnonymousActionEvent.getActionCommand() == "y")
        {
          int i = FormPanel.this.squadList.getSelectedIndex();
          FormPanel.Role localRole = (FormPanel.Role)FormPanel.this.roleBox.getSelectedItem();
          if ((i >= 0) && (i < 11) && (localRole.index != -1))
          {
            int j = 670641 + 628 * FormPanel.this.team + 6232 + i;
            int k = FormPanel.this.of.data[(j + FormPanel.this.altBox.getSelectedIndex() * 171)];
            int m = 670620 + 628 * FormPanel.this.team + 6232 + i + FormPanel.this.altBox.getSelectedIndex() * 171;
            int n = 670630 + 628 * FormPanel.this.team + 6232 + i + FormPanel.this.altBox.getSelectedIndex() * 171;
            int i1 = FormPanel.this.of.data[n];
            if (k != localRole.index)
            {
              if ((k < 10) && (localRole.index > 9)) {
                if (localRole.index < 29) {
                  FormPanel.this.of.data[m] = 25;
                } else {
                  FormPanel.this.of.data[m] = 41;
                }
              }
              if ((k > 9) && (k < 29)) {
                if (localRole.index < 10) {
                  FormPanel.this.of.data[m] = 8;
                } else if (localRole.index > 28) {
                  FormPanel.this.of.data[m] = 41;
                }
              }
              if ((k > 28) && (localRole.index < 29)) {
                if (localRole.index < 10) {
                  FormPanel.this.of.data[m] = 8;
                } else {
                  FormPanel.this.of.data[m] = 25;
                }
              }
              if (((localRole.index == 8) || (localRole.index == 15) || (localRole.index == 22) || (localRole.index == 29)) && (k != 8) && (k != 15) && (k != 22) && (k != 29) && (i1 > 50)) {
                FormPanel.this.of.data[n] = 28;
              }
              if (((localRole.index == 9) || (localRole.index == 16) || (localRole.index == 23) || (localRole.index == 30)) && (k != 9) && (k != 16) && (k != 23) && (k != 30) && (i1 < 54)) {
                FormPanel.this.of.data[n] = 76;
              }
            }
            FormPanel.this.of.data[(j + FormPanel.this.altBox.getSelectedIndex() * 171)] = FormPanel.this.of.toByte(localRole.index);
            if ((k > 0) && (k < 8) && ((localRole.index < 1) || (localRole.index > 7)))
            {
              int i2 = FormPanel.this.of.data[(670785 + 628 * FormPanel.this.team + 6232 + FormPanel.this.altBox.getSelectedIndex() * 171)];
              if (i == i2)
              {
                FormPanel.this.of.data[(670785 + 628 * FormPanel.this.team + 6232 + FormPanel.this.altBox.getSelectedIndex() * 171)] = 0;
                FormPanel.this.of.data[(670784 + 628 * FormPanel.this.team + 6232 + FormPanel.this.altBox.getSelectedIndex() * 171)] = 0;
              }
              i2 = FormPanel.this.of.data[(670612 + 628 * FormPanel.this.team + 6232)];
              if ((FormPanel.this.altBox.getSelectedIndex() == 0) && (i == i2)) {
                FormPanel.this.of.data[(670612 + 628 * FormPanel.this.team + 6232)] = 0;
              }
            }
            FormPanel.this.updateRoleBox();
            FormPanel.this.posList.refresh(FormPanel.this.team);
            FormPanel.this.teamSetPan.refresh(FormPanel.this.team);
            FormPanel.this.stratPan.refresh(FormPanel.this.team);
            FormPanel.this.pitchPanel.repaint();
            FormPanel.this.adPanel.repaint();
          }
        }
      }
    });
    this.squadList = new SquadList(this.of, true);
    this.squadList.addListSelectionListener(this);
    new DropTarget(this.squadList, this);
    DragSource localDragSource = new DragSource();
    localDragSource.createDefaultDragGestureRecognizer(this.squadList, 2, this);
    this.posList = new PositionList(this.of, false);
    this.teamSetPan = new TeamSetPanel(this.of, this.squadList, this.posList);
    this.stratPan = new StrategyPanel(this.of, this.squadList, this.posList);
    this.adPanel = new AttDefPanel(this.of, this.altBox);
    this.pitchPanel = new PitchPanel(this.of, this.squadList, this.adPanel, this.altBox, this.numList);
    this.adPanel.pitch = this.pitchPanel;
    this.sFK = new JobList(this.of, 670614, " C-L ");
    this.sFK.setToolTipText("Left corner");
    this.lFK = new JobList(this.of, 670615, " C-R");
    this.lFK.setToolTipText("Right corner");
    this.rCR = new JobList(this.of, 670616, " F-L ");
    this.rCR.setToolTipText("Long free kick");
    this.lCR = new JobList(this.of, 670617, " F-S ");
    this.lCR.setToolTipText("Short free kick");
    this.pk = new JobList(this.of, 670618, " PK ");
    this.pk.setToolTipText("Penalty");
    this.cap = new JobList(this.of, 670619, " C ");
    this.cap.setToolTipText("Captain");
    this.formBox = new JComboBox();
    this.formBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        int i = FormPanel.this.formBox.getSelectedIndex();
        if ((i != -1) && (paramAnonymousActionEvent.getActionCommand() == "y"))
        {
          int j = 670621 + 628 * FormPanel.this.team + 6232 + FormPanel.this.altBox.getSelectedIndex() * 171;
          if (i != 0) {
            System.arraycopy(FormPanel.this.formData, (i - 1) * 31, FormPanel.this.of.data, j, 31);
          }
          int k = FormPanel.this.of.data[(670785 + 628 * FormPanel.this.team + 6232 + FormPanel.this.altBox.getSelectedIndex() * 171)];
          int m = FormPanel.this.of.data[(670641 + 628 * FormPanel.this.team + 6232 + k + FormPanel.this.altBox.getSelectedIndex() * 171)];
          if ((m < 1) || (m > 7))
          {
            FormPanel.this.of.data[(670785 + 628 * FormPanel.this.team + 6232 + FormPanel.this.altBox.getSelectedIndex() * 171)] = 0;
            FormPanel.this.of.data[(670784 + 628 * FormPanel.this.team + 6232 + FormPanel.this.altBox.getSelectedIndex() * 171)] = 0;
          }
          k = FormPanel.this.of.data[(670612 + 628 * FormPanel.this.team + 6232)];
          m = FormPanel.this.of.data[(670641 + 628 * FormPanel.this.team + 6232 + k)];
          if ((FormPanel.this.altBox.getSelectedIndex() == 0) && ((m < 1) || (m > 7))) {
            FormPanel.this.of.data[(670612 + 628 * FormPanel.this.team + 6232)] = 0;
          }
          FormPanel.this.posList.refresh(FormPanel.this.team);
          FormPanel.this.stratPan.refresh(FormPanel.this.team);
          FormPanel.this.teamSetPan.refresh(FormPanel.this.team);
          FormPanel.this.pitchPanel.repaint();
          FormPanel.this.adPanel.repaint();
          FormPanel.this.updateRoleBox();
        }
      }
    });
    this.snapButton = new JButton("Snapshot");
    this.snapButton.setToolTipText("Save the formation diagram to a .png image file");
    this.snapButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        FormPanel.this.savePNG();
      }
    });
    localJPanel1.add(this.sFK);
    localJPanel1.add(this.lFK);
    localJPanel1.add(this.rCR);
    localJPanel1.add(this.lCR);
    localJPanel1.add(this.pk);
    localJPanel1.add(this.cap);
    JPanel localJPanel5 = new JPanel(new BorderLayout());
    localJPanel5.add(this.numList, "West");
    localJPanel5.add(this.posList, "East");
    localJPanel2.add(localJPanel5, "West");
    localJPanel2.add(this.squadList, "Center");
    localJPanel2.add(localJPanel1, "East");
    localJPanel3.add(localJPanel2, "Center");
    localJPanel3.add(this.formBox, "North");
    localJPanel4.add(this.pitchPanel, "Center");
    JPanel localJPanel6 = new JPanel();
    JPanel localJPanel7 = new JPanel(new GridLayout(1, 3));
    localJPanel6.add(this.adPanel);
    localJPanel6.add(this.roleBox);
    JPanel localJPanel8 = new JPanel(new BorderLayout());
    JTabbedPane localJTabbedPane = new JTabbedPane();
    localJPanel8.add(localJPanel6, "North");
    localJTabbedPane.addTab("Team", null, this.teamSetPan, null);
    localJTabbedPane.addTab("Strategy", null, this.stratPan, null);
    localJPanel8.add(localJTabbedPane, "Center");
    localJPanel7.add(this.altBox);
    localJPanel7.add(this.formBox);
    localJPanel7.add(this.snapButton);
    localJPanel4.add(localJPanel7, "North");
    localJPanel4.add(localJPanel8, "South");
    add(localJPanel3);
    add(localJPanel4);
  }
  
  public void refresh(int paramInt)
  {
    this.team = paramInt;
    this.altBox.setActionCommand("n");
    this.altBox.setSelectedIndex(0);
    this.altBox.setActionCommand("y");
    this.ok = false;
    this.squadList.refresh(paramInt, false);
    this.ok = true;
    int i = paramInt;
    if (paramInt > 63) {
      i = paramInt + 10;
    }
    this.numList.refresh(i);
    this.posList.alt = this.altBox.getSelectedIndex();
    this.posList.refresh(paramInt);
    updateRoleBox();
    this.sFK.refresh(paramInt);
    this.lFK.refresh(paramInt);
    this.rCR.refresh(paramInt);
    this.lCR.refresh(paramInt);
    this.pk.refresh(paramInt);
    this.cap.refresh(paramInt);
    this.teamSetPan.alt = this.altBox.getSelectedIndex();
    this.teamSetPan.refresh(paramInt);
    this.stratPan.refresh(paramInt);
    this.pitchPanel.selected = -1;
    this.pitchPanel.squad = paramInt;
    this.pitchPanel.repaint();
    this.adPanel.selected = -1;
    this.adPanel.squad = paramInt;
    this.adPanel.repaint();
  }
  
  public void valueChanged(ListSelectionEvent paramListSelectionEvent)
  {
    if (fromPitch)
    {
      fromPitch = false;
      updateRoleBox();
    }
    else if ((!paramListSelectionEvent.getValueIsAdjusting()) && (this.ok))
    {
      int i = this.squadList.getSelectedIndex();
      updateRoleBox();
      if ((i >= 0) && (i < 11))
      {
        this.pitchPanel.selected = i;
        this.adPanel.selected = i;
      }
      else
      {
        this.pitchPanel.selected = -1;
        this.adPanel.selected = -1;
      }
      this.pitchPanel.repaint();
      this.adPanel.repaint();
    }
  }
  
  public boolean saveComponentAsPNG(Component paramComponent, File paramFile)
  {
    boolean bool = false;
    Dimension localDimension = paramComponent.getSize();
    byte[] arrayOfByte1 = new byte[8];
    byte[] arrayOfByte2 = new byte[8];
    byte[] arrayOfByte3 = new byte[8];
    for (int i = 0; i < 8; i++)
    {
      arrayOfByte1[i] = ((byte)this.pitchPanel.colour[i].getRed());
      arrayOfByte2[i] = ((byte)this.pitchPanel.colour[i].getGreen());
      arrayOfByte3[i] = ((byte)this.pitchPanel.colour[i].getBlue());
    }
    IndexColorModel localIndexColorModel = new IndexColorModel(8, 8, arrayOfByte1, arrayOfByte2, arrayOfByte3);
    BufferedImage localBufferedImage = new BufferedImage(localDimension.width, localDimension.height, 13, localIndexColorModel);
    Graphics2D localGraphics2D = localBufferedImage.createGraphics();
    paramComponent.paint(localGraphics2D);
    try
    {
      ImageIO.write(localBufferedImage, "png", paramFile);
      bool = true;
    }
    catch (Exception localException) {}
    return bool;
  }
  
  private void updateRoleBox()
  {
    countForm();
    this.roleBox.setActionCommand("n");
    this.roleBox.removeAllItems();
    int i = this.squadList.getSelectedIndex();
    int j = this.of.data[(670641 + 628 * this.team + 6232 + i + this.altBox.getSelectedIndex() * 171)];
    this.roleBox.setEnabled(true);
    if ((i > 0) && (i < 11))
    {
      int k = 0;
      int n = 0;
      Object localObject = null;
      Role localRole1 = new Role(j);
      this.roleBox.addItem(localRole1);
      for (int i2 = 1; i2 < 41; i2++)
      {
        int m = 1;
        int i1;
        if ((i2 == 4) || (i2 == 5))
        {
          m = 0;
        }
        else
        {
          if (i2 == 15) {
            for (int i3 = 0; (m != 0) && (i3 < 11); i3++)
            {
              i1 = this.of.data[(670641 + 628 * this.team + 6232 + i3 + this.altBox.getSelectedIndex() * 171)];
              if ((i1 != j) && ((i1 == 8) || (i1 == 22))) {
                m = 0;
              }
            }
          }
          if (i2 == 16) {
            for (int i3 = 0; (m != 0) && (i3 < 11); i3++)
            {
              i1 = this.of.data[(670641 + 628 * this.team + 6232 + i3 + this.altBox.getSelectedIndex() * 171)];
              if ((i1 != j) && ((i1 == 9) || (i1 == 23))) {
                m = 0;
              }
            }
          }
          if ((j != 15) && ((i2 == 8) || (i2 == 22))) {
            for (int i3 = 0; (m != 0) && (i3 < 11); i3++)
            {
              i1 = this.of.data[(670641 + 628 * this.team + 6232 + i3 + this.altBox.getSelectedIndex() * 171)];
              if (i1 == 15) {
                m = 0;
              }
            }
          }
          if ((j != 16) && ((i2 == 9) || (i2 == 23))) {
            for (int i3 = 0; (m != 0) && (i3 < 11); i3++)
            {
              i1 = this.of.data[(670641 + 628 * this.team + 6232 + i3 + this.altBox.getSelectedIndex() * 171)];
              if (i1 == 16) {
                m = 0;
              }
            }
          }
          if (isDef(j))
          {
            if ((this.def <= 2) && (!isDef(i2))) {
              m = 0;
            }
            if ((this.mid >= 6) && (isMid(i2))) {
              m = 0;
            }
            if ((this.att >= 5) && (isAtt(i2))) {
              m = 0;
            }
          }
          if (isMid(j))
          {
            if ((this.mid <= 2) && (!isMid(i2))) {
              m = 0;
            }
            if ((this.def >= 5) && (isDef(i2))) {
              m = 0;
            }
            if ((this.att >= 5) && (isAtt(i2))) {
              m = 0;
            }
          }
          if (isAtt(j))
          {
            if ((this.att <= 1) && (!isAtt(i2))) {
              m = 0;
            }
            if ((this.mid >= 6) && (isMid(i2))) {
              m = 0;
            }
            if ((this.def >= 5) && (isDef(i2))) {
              m = 0;
            }
          }
        }
        for (int i3 = 0; (m != 0) && (i3 < 11); i3++)
        {
          i1 = this.of.data[(670641 + 628 * this.team + 6232 + i3 + this.altBox.getSelectedIndex() * 171)];
          if (i1 == i2) {
            m = 0;
          }
        }
        if (m != 0)
        {
          Role localRole2 = new Role(i2);
          if (!localRole1.name.equals(localRole2.name))
          {
            if (localObject == null)
            {
              localObject = localRole2;
              this.roleBox.addItem(localRole2);
              k++;
            }
            else if ((!((Role)localObject).name.equals(localRole2.name)) && ((!localRole2.name.equals("CBT")) || ((localRole2.name.equals("CBT")) && (n == 0))))
            {
              localObject = new Role(i2);
              this.roleBox.addItem(localObject);
              k++;
            }
            if (localRole2.name.equals("CBT")) {
              n = 1;
            }
          }
        }
      }
    }
    else if (i == 0)
    {
      this.roleBox.addItem(new Role(0));
    }
    else
    {
      this.roleBox.setEnabled(false);
    }
    this.roleBox.setActionCommand("y");
  }
  
  private void countForm()
  {
    this.def = 0;
    this.mid = 0;
    this.att = 0;
    for (int j = 1; j < 11; j++)
    {
      int i = this.of.data[(670641 + 628 * this.team + 6232 + j + this.altBox.getSelectedIndex() * 171)];
      if (isDef(i)) {
        this.def += 1;
      } else if (isMid(i)) {
        this.mid += 1;
      } else if (isAtt(i)) {
        this.att += 1;
      }
    }
    this.formBox.setActionCommand("n");
    this.formName[0] = (String.valueOf(this.def) + "-" + String.valueOf(this.mid) + "-" + String.valueOf(this.att) + " *");
    DefaultComboBoxModel localDefaultComboBoxModel = new DefaultComboBoxModel(this.formName);
    this.formBox.setModel(localDefaultComboBoxModel);
    this.formBox.setActionCommand("y");
  }
  
  private void savePNG()
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
      if (saveComponentAsPNG(this.pitchPanel, localFile)) {
        JOptionPane.showMessageDialog(null, localFile.getName() + "\nSaved in:\n" + localFile.getParent(), "File Successfully Saved", 1);
      } else {
        i = 1;
      }
      if (i != 0) {
        JOptionPane.showMessageDialog(null, "Could not access file", "Error", 0);
      }
    }
  }
  
  private boolean isDef(int paramInt)
  {
    boolean bool = false;
    if ((paramInt > 0) && (paramInt < 10)) {
      bool = true;
    }
    return bool;
  }
  
  private boolean isMid(int paramInt)
  {
    boolean bool = false;
    if ((paramInt > 9) && (paramInt < 29)) {
      bool = true;
    }
    return bool;
  }
  
  private boolean isAtt(int paramInt)
  {
    boolean bool = false;
    if ((paramInt > 28) && (paramInt < 41)) {
      bool = true;
    }
    return bool;
  }
  
  public void dragEnter(DropTargetDragEvent paramDropTargetDragEvent) {}
  
  public void dragExit(DropTargetEvent paramDropTargetEvent) {}
  
  public void dragOver(DropTargetDragEvent paramDropTargetDragEvent)
  {
    int i = this.squadList.locationToIndex(paramDropTargetDragEvent.getLocation());
    Player localPlayer = (Player)this.squadList.getModel().getElementAt(i);
    this.squadList.setSelectedIndex(i);
    if ((i != -1) && (i != this.sourceIndex) && (localPlayer.index != 0)) {
      paramDropTargetDragEvent.acceptDrag(2);
    } else {
      paramDropTargetDragEvent.rejectDrag();
    }
  }
  
  public void drop(DropTargetDropEvent paramDropTargetDropEvent)
  {
    Transferable localTransferable = paramDropTargetDropEvent.getTransferable();
    int i = this.squadList.getSelectedIndex();
    if (localTransferable.isDataFlavorSupported(this.localPlayerFlavor))
    {
      paramDropTargetDropEvent.acceptDrop(2);
      int j = 670512 + 628 * this.team + 6232 + this.sourceIndex;
      int k = 670512 + 628 * this.team + 6232 + i;
      int m = this.of.data[j];
      this.of.data[j] = this.of.data[k];
      this.of.data[k] = (byte)m;
      if ((this.sourceIndex < 11) && (i < 11))
      {
        for (int n = 670614 + 628 * this.team + 6232; n < 670620 + 628 * this.team + 6232; n++) {
          if (this.of.toInt(this.of.data[n]) == this.sourceIndex) {
            this.of.data[n] = this.of.toByte(i);
          } else if (this.of.toInt(this.of.data[n]) == i) {
            this.of.data[n] = this.of.toByte(this.sourceIndex);
          }
        }
        this.sFK.refresh(this.team);
        this.lFK.refresh(this.team);
        this.rCR.refresh(this.team);
        this.lCR.refresh(this.team);
        this.pk.refresh(this.team);
        this.cap.refresh(this.team);
      }
      this.ok = false;
      int n = this.team;
      if (this.team > 63) {
        n = this.team + 10;
      }
      this.numList.refresh(n);
      this.squadList.refresh(this.team, false);
      this.teamSetPan.refresh(this.team);
      this.stratPan.refresh(this.team);
      this.pitchPanel.repaint();
      this.ok = true;
      paramDropTargetDropEvent.getDropTargetContext().dropComplete(true);
    }
    else
    {
      paramDropTargetDropEvent.rejectDrop();
    }
  }
  
  public void dropActionChanged(DropTargetDragEvent paramDropTargetDragEvent) {}
  
  public void dragGestureRecognized(DragGestureEvent paramDragGestureEvent)
  {
    this.sourceIndex = this.squadList.getSelectedIndex();
    Player localPlayer = (Player)this.squadList.getSelectedValue();
    if ((this.sourceIndex != -1) && (localPlayer.index != 0))
    {
      this.posList.selectPos(this.squadList, this.sourceIndex);
      this.roleBox.setActionCommand("n");
      this.roleBox.removeAllItems();
      this.roleBox.setEnabled(false);
      this.roleBox.setActionCommand("y");
      this.pitchPanel.selected = -1;
      this.adPanel.selected = -1;
      this.pitchPanel.repaint();
      this.adPanel.repaint();
      PlayerTransferable localPlayerTransferable = new PlayerTransferable(localPlayer);
      paramDragGestureEvent.getDragSource().startDrag(paramDragGestureEvent, null, localPlayerTransferable, this);
    }
  }
  
  public void dragDropEnd(DragSourceDropEvent paramDragSourceDropEvent)
  {
    this.squadList.clearSelection();
    this.posList.clearSelection();
  }
  
  public void dragEnter(DragSourceDragEvent paramDragSourceDragEvent) {}
  
  public void dragExit(DragSourceEvent paramDragSourceEvent) {}
  
  public void dragOver(DragSourceDragEvent paramDragSourceDragEvent) {}
  
  public void dropActionChanged(DragSourceDragEvent paramDragSourceDragEvent) {}
  
  private class Role
  {
    String name = "---";
    int index;
    
    public Role(int paramInt)
    {
      this.index = paramInt;
      if (this.index == 0) {
        this.name = "GK";
      }
      if (((this.index > 0) && (this.index < 4)) || ((this.index > 5) && (this.index < 8))) {
        this.name = "CBT";
      }
      if (this.index == 4) {
        this.name = "CWP";
      }
      if (this.index == 5) {
        this.name = "ASW";
      }
      if (this.index == 8) {
        this.name = "LB";
      }
      if (this.index == 9) {
        this.name = "RB";
      }
      if ((this.index > 9) && (this.index < 15)) {
        this.name = "DM";
      }
      if (this.index == 15) {
        this.name = "LWB";
      }
      if (this.index == 16) {
        this.name = "RWB";
      }
      if ((this.index > 16) && (this.index < 22)) {
        this.name = "CM";
      }
      if (this.index == 22) {
        this.name = "LM";
      }
      if (this.index == 23) {
        this.name = "RM";
      }
      if ((this.index > 23) && (this.index < 29)) {
        this.name = "AM";
      }
      if (this.index == 29) {
        this.name = "LW";
      }
      if (this.index == 30) {
        this.name = "RW";
      }
      if ((this.index > 30) && (this.index < 36)) {
        this.name = "SS";
      }
      if ((this.index > 35) && (this.index < 41)) {
        this.name = "CF";
      }
      if (this.index > 40) {
        this.name = String.valueOf(this.index);
      }
    }
    
    public String toString()
    {
      return this.name;
    }
  }
  
  public class PlayerTransferable
    implements Transferable
  {
    Player data;
    
    public PlayerTransferable(Player paramPlayer)
    {
      this.data = paramPlayer;
    }
    
    public Object getTransferData(DataFlavor paramDataFlavor)
      throws UnsupportedFlavorException
    {
      if (!isDataFlavorSupported(paramDataFlavor)) {
        throw new UnsupportedFlavorException(paramDataFlavor);
      }
      return this.data;
    }
    
    public DataFlavor[] getTransferDataFlavors()
    {
      return new DataFlavor[] { FormPanel.this.localPlayerFlavor };
    }
    
    public boolean isDataFlavorSupported(DataFlavor paramDataFlavor)
    {
      return FormPanel.this.localPlayerFlavor.equals(paramDataFlavor);
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/FormPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
