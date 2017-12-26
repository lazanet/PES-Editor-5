package editor;

import java.awt.BorderLayout;
import java.awt.Component;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TransferPanel
  extends JPanel
  implements MouseListener, DropTargetListener, DragSourceListener, DragGestureListener
{
  private SelectByTeam selectorL;
  private SelectByTeam selectorR;
  private SelectByNation freeList;
  private OptionFile of;
  private NameEditor nameEditor;
  private NumEditor numEditor;
  private InfoPanel infoPanel;
  private ShirtNameEditor shirtEditor;
  SquadTidy squadTidy;
  private PlayerDialog playerDia;
  private Stats stats;
  private TeamDialog teamDia;
  private JCheckBox autoRel = new JCheckBox("Auto Release");
  private JCheckBox autoRep = new JCheckBox("Auto Sub");
  private JCheckBox safeMode = new JCheckBox("Safe Mode");
  private JButton compare;
  private int releasedIndex = 0;
  private DragSource sourceF = null;
  private DragSource sourceL = null;
  private DragSource sourceR = null;
  private Component sourceComp = null;
  private int sourceIndex = -1;
  private DataFlavor localPlayerFlavor;
  private int compIndex = 0;
  private int lastIndex = 0;
  
  public TransferPanel(PlayerDialog paramPlayerDialog, OptionFile paramOptionFile, Stats paramStats, TeamDialog paramTeamDialog)
  {
    this.of = paramOptionFile;
    this.stats = paramStats;
    this.teamDia = paramTeamDialog;
    this.playerDia = paramPlayerDialog;
    this.squadTidy = new SquadTidy(this.of, this.stats);
    this.autoRel.setToolTipText("When a player is transfered to a club squad he will be automatically released from his old squad");
    this.autoRel.setSelected(true);
    this.autoRep.setToolTipText("Gaps made in a team's first 11 will be automatically filled with the most appropriate sub");
    this.autoRep.setSelected(true);
    this.safeMode.setToolTipText("Only transfers that are possible in-game will be allowed");
    this.safeMode.setSelected(true);
    this.compare = new JButton("Compare Stats");
    this.compare.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (TransferPanel.this.compIndex == 0)
        {
          TransferPanel.this.compIndex = TransferPanel.this.lastIndex;
          int i;
          if (TransferPanel.this.nameEditor.source == 2)
          {
            i = TransferPanel.this.selectorL.teamBox.getSelectedIndex();
            if ((i < 64) || ((i > 73) && (i < 212))) {
              TransferPanel.this.selectorL.posList.selectPos(TransferPanel.this.selectorL.squadList, TransferPanel.this.selectorL.squadList.getSelectedIndex());
            }
          }
          else if (TransferPanel.this.nameEditor.source == 3)
          {
            i = TransferPanel.this.selectorR.teamBox.getSelectedIndex();
            if ((i < 64) || ((i > 73) && (i < 212))) {
              TransferPanel.this.selectorR.posList.selectPos(TransferPanel.this.selectorR.squadList, TransferPanel.this.selectorR.squadList.getSelectedIndex());
            }
          }
        }
        else
        {
          TransferPanel.this.compIndex = 0;
          TransferPanel.this.selectorL.posList.clearSelection();
          TransferPanel.this.selectorR.posList.clearSelection();
        }
        TransferPanel.this.infoPanel.refresh(TransferPanel.this.lastIndex, TransferPanel.this.compIndex);
      }
    });
    this.freeList = new SelectByNation(this.of, this.stats);
    this.selectorL = new SelectByTeam(this.of, true);
    this.nameEditor = new NameEditor();
    this.numEditor = new NumEditor();
    this.shirtEditor = new ShirtNameEditor();
    JPanel localJPanel1 = new JPanel(new GridLayout(0, 1));
    JPanel localJPanel2 = new JPanel(new GridLayout(0, 1));
    JPanel localJPanel3 = new JPanel(new BorderLayout());
    JPanel localJPanel4 = new JPanel(new BorderLayout());
    this.selectorR = new SelectByTeam(this.of, true);
    addListen();
    this.freeList.freeList.addMouseListener(this);
    this.selectorL.squadList.addMouseListener(this);
    this.selectorR.squadList.addMouseListener(this);
    String str = "application/x-java-jvm-local-objectref;class=editor.Player";
    try
    {
      this.localPlayerFlavor = new DataFlavor(str);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      System.out.println("FormTransferHandler: unable to create data flavor");
    }
    new DropTarget(this.freeList.freeList, this);
    new DropTarget(this.selectorL.squadList, this);
    new DropTarget(this.selectorR.squadList, this);
    this.sourceF = new DragSource();
    this.sourceF.createDefaultDragGestureRecognizer(this.freeList.freeList, 2, this);
    this.sourceL = new DragSource();
    this.sourceL.createDefaultDragGestureRecognizer(this.selectorL.squadList, 2, this);
    this.sourceR = new DragSource();
    this.sourceR.createDefaultDragGestureRecognizer(this.selectorR.squadList, 2, this);
    this.infoPanel = new InfoPanel(this.stats, this.selectorL, this.of);
    this.selectorL.squadList.setToolTipText("Double click to edit player, right click to edit formation");
    this.selectorR.squadList.setToolTipText("Double click to edit player, right click to edit formation");
    this.freeList.freeList.setToolTipText("Double click to edit player");
    localJPanel1.add(this.nameEditor);
    localJPanel1.add(this.shirtEditor);
    localJPanel2.add(this.autoRel);
    localJPanel2.add(this.autoRep);
    localJPanel2.add(this.safeMode);
    JPanel localJPanel5 = new JPanel();
    JPanel localJPanel6 = new JPanel(new BorderLayout());
    localJPanel5.add(this.numEditor);
    localJPanel5.add(localJPanel1);
    localJPanel5.add(localJPanel2);
    localJPanel6.add(localJPanel5, "North");
    localJPanel6.add(this.infoPanel, "Center");
    localJPanel6.add(this.compare, "South");
    localJPanel3.add(this.selectorL, "Center");
    localJPanel4.add(this.selectorR, "Center");
    JPanel localJPanel7 = new JPanel(new GridLayout(0, 3));
    localJPanel7.add(this.freeList);
    localJPanel7.add(localJPanel3);
    localJPanel7.add(localJPanel4);
    add(localJPanel7);
    add(localJPanel6);
  }
  
  private int getNumAdr(int paramInt)
  {
    return 657712 + (paramInt - 664054) / 2;
  }
  
  public void refresh()
  {
    this.freeList.refresh();
    this.selectorL.refresh();
    this.selectorR.refresh();
    this.nameEditor.setText("");
    this.numEditor.setText("");
    this.shirtEditor.setText("");
    this.compIndex = 0;
    this.lastIndex = 0;
    this.infoPanel.refresh(this.lastIndex, this.compIndex);
  }
  
  public void refreshLists()
  {
    this.freeList.freeList.refresh(this.freeList.nationBox.getSelectedIndex(), this.freeList.alpha);
    this.selectorL.squadList.refresh(this.selectorL.teamBox.getSelectedIndex(), true);
    this.selectorR.squadList.refresh(this.selectorR.teamBox.getSelectedIndex(), true);
    this.selectorL.numList.refresh(this.selectorL.teamBox.getSelectedIndex());
    this.selectorR.numList.refresh(this.selectorR.teamBox.getSelectedIndex());
    this.selectorL.posList.refresh(this.selectorL.teamBox.getSelectedIndex());
    this.selectorR.posList.refresh(this.selectorR.teamBox.getSelectedIndex());
    this.nameEditor.setText("");
    this.numEditor.setText("");
    this.shirtEditor.setText("");
    this.compIndex = 0;
    this.lastIndex = 0;
    this.infoPanel.refresh(this.lastIndex, this.compIndex);
  }
  
  public int getShirt(int paramInt1, int paramInt2)
  {
    int i;
    if (paramInt1 == 2) {
      i = ((Player)this.selectorL.squadList.getModel().getElementAt(paramInt2)).adr;
    } else {
      i = ((Player)this.selectorR.squadList.getModel().getElementAt(paramInt2)).adr;
    }
    i = getNumAdr(i);
    int j = this.of.toInt(this.of.data[i]) + 1;
    if (j == 256) {
      j = 0;
    }
    return j;
  }
  
  public void setShirt(int paramInt1, int paramInt2, int paramInt3)
  {
    int i;
    if (paramInt1 == 2) {
      i = ((Player)this.selectorL.squadList.getModel().getElementAt(paramInt2)).adr;
    } else {
      i = ((Player)this.selectorR.squadList.getModel().getElementAt(paramInt2)).adr;
    }
    i = getNumAdr(i);
    int j = this.of.toInt(this.of.data[i]) + 1;
    if (j != 256) {
      this.of.data[i] = this.of.toByte(paramInt3 - 1);
    }
  }
  
  public void mousePressed(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mouseClicked(MouseEvent paramMouseEvent)
  {
    int i = paramMouseEvent.getClickCount();
    Object localObject;
    if ((paramMouseEvent.getButton() == 1) && (i == 2))
    {
      localObject = (JList)paramMouseEvent.getSource();
      Player localPlayer = (Player)((JList)localObject).getSelectedValue();
      int k = localPlayer.index;
      if (k != 0)
      {
        this.playerDia.show(localPlayer);
        refreshLists();
      }
    }
    if ((paramMouseEvent.getButton() == 3) && (i == 1) && (paramMouseEvent.getSource() != this.freeList.freeList))
    {
      localObject = (SquadList)paramMouseEvent.getSource();
      int j = ((SquadList)localObject).team;
      if ((j >= 0) && (j < 64))
      {
        this.teamDia.show(j, (String)this.selectorL.teamBox.getItemAt(j));
        FormFixer.fixForm(this.of, j, false);
        refreshLists();
      }
      if ((j >= 74) && (j < 212))
      {
        this.teamDia.show(j - 10, (String)this.selectorL.teamBox.getItemAt(j));
        FormFixer.fixForm(this.of, j, false);
        refreshLists();
      }
    }
  }
  
  private int clubRelease(int paramInt, boolean paramBoolean)
  {
    int i = 667456;
    int k = -1;
    do
    {
      i += 2;
      int j = this.of.toInt(this.of.data[(i + 1)]) << 8 | this.of.toInt(this.of.data[i]);
      if (j == paramInt)
      {
        int m = (i - 667458) / 64 + 74;
        int n = (i - 667458) % 64;
        if ((k == -1) && ((!paramBoolean) || (n < 22)))
        {
          k = m;
          if (paramBoolean) {
            this.releasedIndex = (n / 2);
          }
        }
        if (paramBoolean)
        {
          this.of.data[i] = 0;
          this.of.data[(i + 1)] = 0;
          this.of.data[getNumAdr(i)] = -1;
          if (n >= 22)
          {
            this.squadTidy.tidy(m);
          }
          else if (this.autoRep.isSelected())
          {
            int i1 = m;
            if (i1 > 73) {
              i1 -= 10;
            }
            int i2 = this.of.toInt(this.of.data[(670641 + 628 * i1 + 6232 + n / 2)]);
            this.squadTidy.tidy11(m, n / 2, i2);
          }
        }
      }
    } while (i < 676288);
    return k;
  }
  
  private byte getNextNum(int paramInt)
  {
    byte b1 = -1;
    int i;
    int j;
    if (paramInt < 74)
    {
      i = 23;
      j = 657712 + paramInt * i;
    }
    else
    {
      i = 32;
      j = 659414 + (paramInt - 74) * i;
    }
    for (byte b2 = 0; (b1 == -1) && (b2 < 99); b2 = (byte)(b2 + 1))
    {
      int n = 1;
      for (int i1 = 0; (n != 0) && (i1 < i); i1++)
      {
        int k = j + i1;
        int m = this.of.data[k];
        if (m == b2) {
          n = 0;
        }
      }
      if (n != 0) {
        b1 = b2;
      }
    }
    if (b1 == -1) {
      b1 = 0;
    }
    return b1;
  }
  
  private int countPlayers(int paramInt)
  {
    int m = 0;
    int i;
    int j;
    if (paramInt < 74)
    {
      i = 23;
      j = 664054 + paramInt * i * 2;
    }
    else
    {
      i = 32;
      j = 667458 + (paramInt - 74) * i * 2;
    }
    for (int i1 = 0; i1 < i; i1++)
    {
      int n = j + i1 * 2;
      int k = this.of.toInt(this.of.data[(n + 1)]) << 8 | this.of.toInt(this.of.data[n]);
      if (k != 0) {
        m++;
      }
    }
    return m;
  }
  
  private boolean inSquad(int paramInt1, int paramInt2)
  {
    boolean bool = false;
    if (paramInt2 != 0)
    {
      int i;
      int j;
      if (paramInt1 < 74)
      {
        i = 23;
        j = 664054 + paramInt1 * i * 2;
      }
      else
      {
        i = 32;
        j = 667458 + (paramInt1 - 74) * i * 2;
      }
      for (int n = 0; (!bool) && (n < i); n++)
      {
        int m = j + n * 2;
        int k = this.of.toInt(this.of.data[(m + 1)]) << 8 | this.of.toInt(this.of.data[m]);
        if (k == paramInt2) {
          bool = true;
        }
      }
    }
    return bool;
  }
  
  public void dragEnter(DropTargetDragEvent paramDropTargetDragEvent) {}
  
  public void dragExit(DropTargetEvent paramDropTargetEvent) {}
  
  public void dragOver(DropTargetDragEvent paramDropTargetDragEvent)
  {
    JList localJList = (JList)paramDropTargetDragEvent.getDropTargetContext().getComponent();
    int i = localJList.locationToIndex(paramDropTargetDragEvent.getLocation());
    Player localPlayer;
    if (i != -1) {
      localPlayer = (Player)localJList.getModel().getElementAt(i);
    } else {
      localPlayer = new Player(this.of, 0, 0);
    }
    boolean bool = checkSafeDrag(this.safeMode.isSelected(), localJList, localPlayer);
    localJList.setSelectedIndex(i);
    if (bool) {
      paramDropTargetDragEvent.acceptDrag(2);
    } else {
      paramDropTargetDragEvent.rejectDrag();
    }
  }
  
  public void drop(DropTargetDropEvent paramDropTargetDropEvent)
  {
    Transferable localTransferable = paramDropTargetDropEvent.getTransferable();
    if (localTransferable.isDataFlavorSupported(this.localPlayerFlavor))
    {
      JList localJList1 = (JList)this.sourceComp;
      JList localJList2 = (JList)paramDropTargetDropEvent.getDropTargetContext().getComponent();
      Player localPlayer1 = (Player)localJList1.getModel().getElementAt(this.sourceIndex);
      int i = localPlayer1.index;
      Player localPlayer2;
      int j;
      if (localJList2.getSelectedIndex() != -1)
      {
        localPlayer2 = (Player)localJList2.getSelectedValue();
        j = localPlayer2.index;
      }
      else
      {
        localPlayer2 = new Player(this.of, 0, 0);
        j = 0;
      }
      if ((localJList1 != this.freeList.freeList) && (localJList2 != this.freeList.freeList))
      {
        int k = ((SelectByTeam)localJList1.getParent()).teamBox.getSelectedIndex();
        int m = ((SelectByTeam)localJList2.getParent()).teamBox.getSelectedIndex();
        if (localJList1 == localJList2)
        {
          if (((k < 64) || ((k > 73) && (k < 212))) && (i != j))
          {
            paramDropTargetDropEvent.acceptDrop(2);
            transferS(localPlayer1, localPlayer2, k, m, localJList1, localJList2);
          }
        }
        else if ((localJList1 == this.selectorL.squadList) && (localJList2 == this.selectorR.squadList))
        {
          paramDropTargetDropEvent.acceptDrop(2);
          transferLR(localPlayer1);
        }
        else if ((localJList1 == this.selectorR.squadList) && (localJList2 == this.selectorL.squadList))
        {
          paramDropTargetDropEvent.acceptDrop(2);
          transferRL(localPlayer1);
        }
      }
      else if ((localJList1 == this.freeList.freeList) && (localJList2 == this.selectorL.squadList))
      {
        paramDropTargetDropEvent.acceptDrop(2);
        transferFL(i);
      }
      else if ((localJList1 == this.freeList.freeList) && (localJList2 == this.selectorR.squadList))
      {
        paramDropTargetDropEvent.acceptDrop(2);
        transferFR(i);
      }
      else if ((localJList1 == this.selectorL.squadList) && (localJList2 == this.freeList.freeList))
      {
        paramDropTargetDropEvent.acceptDrop(2);
        tranRelL(localPlayer1, this.sourceIndex);
      }
      else if ((localJList1 == this.selectorR.squadList) && (localJList2 == this.freeList.freeList))
      {
        paramDropTargetDropEvent.acceptDrop(2);
        tranRelR(localPlayer1, this.sourceIndex);
      }
      else
      {
        paramDropTargetDropEvent.rejectDrop();
      }
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
    this.sourceComp = paramDragGestureEvent.getComponent();
    if ((this.sourceComp instanceof JList))
    {
      JList localJList = (JList)this.sourceComp;
      this.sourceIndex = localJList.getSelectedIndex();
      Player localPlayer = (Player)localJList.getSelectedValue();
      if ((this.sourceIndex != -1) && (localPlayer.index != 0))
      {
        removeListen();
        this.lastIndex = 0;
        this.compIndex = 0;
        this.infoPanel.refresh(this.lastIndex, this.compIndex);
        this.nameEditor.setText("");
        this.shirtEditor.setText("");
        this.nameEditor.source = 0;
        this.shirtEditor.source = 0;
        PlayerTransferable localPlayerTransferable = new PlayerTransferable(localPlayer);
        if (localJList != this.freeList.freeList)
        {
          int i = ((SelectByTeam)localJList.getParent()).teamBox.getSelectedIndex();
          if ((i < 64) || ((i > 73) && (i < 212))) {
            if (localJList == this.selectorL.squadList) {
              this.selectorL.posList.selectPos(this.selectorL.squadList, this.selectorL.squadList.getSelectedIndex());
            } else if (localJList == this.selectorR.squadList) {
              this.selectorR.posList.selectPos(this.selectorR.squadList, this.selectorR.squadList.getSelectedIndex());
            }
          }
        }
        paramDragGestureEvent.getDragSource().startDrag(paramDragGestureEvent, null, localPlayerTransferable, this);
      }
    }
  }
  
  public void dragDropEnd(DragSourceDropEvent paramDragSourceDropEvent)
  {
    if (!paramDragSourceDropEvent.getDropSuccess()) {
      refreshLists();
    }
    addListen();
  }
  
  public void dragEnter(DragSourceDragEvent paramDragSourceDragEvent) {}
  
  public void dragExit(DragSourceEvent paramDragSourceEvent) {}
  
  public void dragOver(DragSourceDragEvent paramDragSourceDragEvent) {}
  
  public void dropActionChanged(DragSourceDragEvent paramDragSourceDragEvent) {}
  
  private boolean checkSafeDrag(boolean paramBoolean, JList paramJList, Player paramPlayer)
  {
    int i = 1;
    int j = 1;
    int k = 1;
    int m = 1;
    int n = 1;
    int i1 = 1;
    int i2 = 1;
    int i3 = 1;
    int i4 = 1;
    int i5 = -1;
    int i6 = -1;
    JList localJList = (JList)this.sourceComp;
    int i7 = ((Player)localJList.getModel().getElementAt(this.sourceIndex)).index;
    int i8 = paramPlayer.index;
    int i9 = -1;
    int i10 = 0;
    int i12, i13, i14, i15, i16, i17;
    if (localJList == this.freeList.freeList)
    {
      i10 = i7;
      i2 = 0;
    }
    else if (paramJList == this.freeList.freeList)
    {
      i10 = i8;
      i2 = 0;
    }
    int i11 = 0;
    if (localJList == this.selectorL.squadList)
    {
      i11 = i7;
      i3 = 0;
      i9 = ((SelectByTeam)localJList.getParent()).teamBox.getSelectedIndex();
    }
    else if (paramJList == this.selectorL.squadList)
    {
      i11 = i8;
      i3 = 0;
    }
    i12 = 0;
    if (localJList == this.selectorR.squadList)
    {
      i12 = i7;
      i4 = 0;
      i9 = ((SelectByTeam)localJList.getParent()).teamBox.getSelectedIndex();
    }
    else if (paramJList == this.selectorR.squadList)
    {
      i12 = i8;
      i4 = 0;
    }
    i5 = this.selectorL.teamBox.getSelectedIndex();
    i6 = this.selectorR.teamBox.getSelectedIndex();
    if (paramBoolean)
    {
      i13 = 16;
      i14 = 16;
      if (i5 < 74) {
        i13 = 23;
      }
      if (i6 < 74) {
        i14 = 23;
      }
      if ((i10 > 4685) && (i10 < 32768))
      {
        i = 0;
        j = 0;
      }
      if ((i10 > 4534) && (i10 < 4558))
      {
        i = 0;
        j = 0;
      }
      if ((i10 > 32767) && (i10 < 32920) && (i5 > 56)) {
        i = 0;
      }
      if ((i10 > 32767) && (i10 < 32920) && (i6 > 56)) {
        j = 0;
      }
      if ((i5 >= 64) || ((i6 >= 64) || ((i5 > 73) && (i5 < 212))))
      {
        i15 = clubRelease(i10, false);
        if (this.autoRel.isSelected())
        {
          if (i15 != -1)
          {
            i17 = countPlayers(i15);
            if (i17 <= 16) {
              i = 0;
            }
          }
        }
        else if (i15 != -1) {
          i = 0;
        }
      }
      if ((i6 > 73) && (i6 < 212))
      {
        i15 = clubRelease(i10, false);
        if (this.autoRel.isSelected())
        {
          if (i15 != -1)
          {
            i17 = countPlayers(i15);
            if (i17 <= 16) {
              j = 0;
            }
          }
        }
        else if (i15 != -1) {
          j = 0;
        }
      }
      if (((i5 > 56) && (i5 < 74)) || (i5 > 211))
      {
        i = 0;
        if ((i5 == 64) || (i5 == 65) || (i5 == 212) || (i5 == 217)) {
          k = 0;
        } else if ((i5 > 65) && (i5 < 73) && (i6 > 63)) {
          k = 0;
        }
        m = 0;
        n = 0;
      }
      else
      {
        i15 = countPlayers(i5);
        if (i15 <= i13)
        {
          n = 0;
          if ((this.autoRel.isSelected()) && (i5 > 73)) {
            k = 0;
          }
        }
        if (inSquad(i5, i12)) {
          m = 0;
        }
        if (inSquad(i5, i10)) {
          i = 0;
        }
        if ((!this.autoRel.isSelected()) && (i5 > 73) && (i5 < 212))
        {
          i17 = clubRelease(i12, false);
          if (i17 != -1) {
            m = 0;
          }
        }
      }
      if (((i6 > 56) && (i6 < 74)) || (i6 > 211))
      {
        k = 0;
        j = 0;
        if ((i6 == 64) || (i6 == 65) || (i6 == 212) || (i6 == 217)) {
          m = 0;
        } else if ((i6 > 65) && (i6 < 73) && (i5 > 63)) {
          m = 0;
        }
        i1 = 0;
      }
      else
      {
        i16 = countPlayers(i6);
        if (i16 <= i14)
        {
          i1 = 0;
          if ((this.autoRel.isSelected()) && (i6 > 73)) {
            m = 0;
          }
        }
        if (inSquad(i6, i11)) {
          k = 0;
        }
        if (inSquad(i6, i10)) {
          j = 0;
        }
        if ((!this.autoRel.isSelected()) && (i6 > 73) && (i6 < 212))
        {
          i17 = clubRelease(i11, false);
          if (i17 != -1) {
            k = 0;
          }
        }
      }
      if (i6 == i5)
      {
        k = 0;
        m = 0;
      }
      if (i5 < 64)
      {
        i16 = i5;
        switch (i16)
        {
        case 57: 
          i16 = 42;
          break;
        case 58: 
          i16 = 43;
          break;
        case 59: 
          i16 = 6;
          break;
        case 60: 
          i16 = 8;
          break;
        case 61: 
          i16 = 9;
          break;
        case 62: 
          i16 = 13;
          break;
        case 63: 
          i16 = 15;
        }
        if (i2 == 0)
        {
          i17 = this.stats.nation.getValue(i10);
          if ((i17 != 108) && (i17 != i16)) {
            i = 0;
          }
        }
        if (i4 == 0)
        {
          i17 = this.stats.nation.getValue(i12);
          if ((i17 != 108) && (i17 != i16)) {
            m = 0;
          }
        }
      }
      if (i6 < 64)
      {
        i16 = i6;
        switch (i16)
        {
        case 57: 
          i16 = 42;
          break;
        case 58: 
          i16 = 43;
          break;
        case 59: 
          i16 = 6;
          break;
        case 60: 
          i16 = 8;
          break;
        case 61: 
          i16 = 9;
          break;
        case 62: 
          i16 = 13;
          break;
        case 63: 
          i16 = 15;
        }
        if (i2 == 0)
        {
          i17 = this.stats.nation.getValue(i10);
          if ((i17 != 108) && (i17 != i16)) {
            j = 0;
          }
        }
        if (i3 == 0)
        {
          i17 = this.stats.nation.getValue(i11);
          if ((i17 != 108) && (i17 != i16)) {
            k = 0;
          }
        }
      }
    }
    i13 = 0;
    if ((localJList != this.freeList.freeList) && (paramJList != this.freeList.freeList))
    {
      if (localJList == paramJList)
      {
        if (((i9 < 64) || ((i9 > 73) && (i9 < 212))) && (i7 != i8)) {
          i13 = 1;
        }
      }
      else if ((localJList == this.selectorL.squadList) && (paramJList == this.selectorR.squadList) && (k != 0) && (i7 != 0)) {
        i13 = 1;
      } else if ((localJList == this.selectorR.squadList) && (paramJList == this.selectorL.squadList) && (m != 0) && (i7 != 0)) {
        i13 = 1;
      }
    }
    else if ((localJList == this.freeList.freeList) && (paramJList == this.selectorL.squadList) && (i != 0)) {
      i13 = 1;
    } else if ((localJList == this.freeList.freeList) && (paramJList == this.selectorR.squadList) && (j != 0)) {
      i13 = 1;
    } else if ((localJList == this.selectorL.squadList) && (paramJList == this.freeList.freeList) && (n != 0)) {
      i13 = 1;
    } else if ((localJList == this.selectorR.squadList) && (paramJList == this.freeList.freeList) && (i1 != 0)) {
      i13 = 1;
    }
    return i13>0;
  }
  
  private void transferFL(int paramInt)
  {
    int i = ((Player)this.selectorL.squadList.getSelectedValue()).adr;
    int j = this.selectorL.teamBox.getSelectedIndex();
    int k = -1;
    if ((j >= 74) && (j < 212) && (this.autoRel.isSelected())) {
      k = clubRelease(paramInt, true);
    }
    this.of.data[i] = this.of.toByte(paramInt & 0xFF);
    this.of.data[(i + 1)] = this.of.toByte((paramInt & 0xFF00) >>> 8);
    if (this.of.data[getNumAdr(i)] == -1) {
      this.of.data[getNumAdr(i)] = getNextNum(j);
    }
    if (this.selectorL.squadList.getSelectedIndex() > 10) {
      this.squadTidy.tidy(j);
    }
    refreshLists();
    if (k != -1)
    {
      this.selectorR.teamBox.setSelectedIndex(k);
      this.selectorR.posList.clearSelection();
      this.selectorR.posList.setSelectedIndex(this.releasedIndex);
    }
  }
  
  private void transferFR(int paramInt)
  {
    int i = ((Player)this.selectorR.squadList.getSelectedValue()).adr;
    int j = this.selectorR.teamBox.getSelectedIndex();
    int k = -1;
    if ((j >= 74) && (j < 212) && (this.autoRel.isSelected())) {
      k = clubRelease(paramInt, true);
    }
    this.of.data[i] = this.of.toByte(paramInt & 0xFF);
    this.of.data[(i + 1)] = this.of.toByte((paramInt & 0xFF00) >>> 8);
    if (this.of.data[getNumAdr(i)] == -1) {
      this.of.data[getNumAdr(i)] = getNextNum(j);
    }
    if (this.selectorR.squadList.getSelectedIndex() > 10) {
      this.squadTidy.tidy(j);
    }
    refreshLists();
    if (k != -1)
    {
      this.selectorL.teamBox.setSelectedIndex(k);
      this.selectorL.posList.clearSelection();
      this.selectorL.posList.setSelectedIndex(this.releasedIndex);
    }
  }
  
  private void transferLR(Player paramPlayer)
  {
    int i = ((Player)this.selectorR.squadList.getSelectedValue()).adr;
    int j = paramPlayer.index;
    if (j != 0)
    {
      int k = this.selectorR.teamBox.getSelectedIndex();
      int m = this.selectorL.teamBox.getSelectedIndex();
      int n = -1;
      if ((k >= 74) && (k < 212) && (this.autoRel.isSelected())) {
        n = clubRelease(j, true);
      }
      this.of.data[i] = this.of.toByte(j & 0xFF);
      this.of.data[(i + 1)] = this.of.toByte((j & 0xFF00) >>> 8);
      if (this.of.data[getNumAdr(i)] == -1) {
        this.of.data[getNumAdr(i)] = getNextNum(k);
      }
      if (this.selectorR.squadList.getSelectedIndex() > 10) {
        this.squadTidy.tidy(this.selectorR.teamBox.getSelectedIndex());
      }
      refreshLists();
      if ((n != -1) && ((m < 74) || (m > 211)))
      {
        this.selectorL.teamBox.setSelectedIndex(n);
        this.selectorL.posList.clearSelection();
        this.selectorL.posList.setSelectedIndex(this.releasedIndex);
      }
    }
  }
  
  private void transferRL(Player paramPlayer)
  {
    int i = ((Player)this.selectorL.squadList.getSelectedValue()).adr;
    int j = paramPlayer.index;
    if (j != 0)
    {
      int k = this.selectorL.teamBox.getSelectedIndex();
      int m = this.selectorR.teamBox.getSelectedIndex();
      int n = -1;
      if ((k >= 74) && (k < 212) && (this.autoRel.isSelected())) {
        n = clubRelease(j, true);
      }
      this.of.data[i] = this.of.toByte(j & 0xFF);
      this.of.data[(i + 1)] = this.of.toByte((j & 0xFF00) >>> 8);
      if (this.of.data[getNumAdr(i)] == -1) {
        this.of.data[getNumAdr(i)] = getNextNum(k);
      }
      if (this.selectorL.squadList.getSelectedIndex() > 10) {
        this.squadTidy.tidy(this.selectorL.teamBox.getSelectedIndex());
      }
      refreshLists();
      if ((n != -1) && ((m < 74) || (m > 211)))
      {
        this.selectorR.teamBox.setSelectedIndex(n);
        this.selectorR.posList.clearSelection();
        this.selectorR.posList.setSelectedIndex(this.releasedIndex);
      }
    }
  }
  
  private void transferS(Player paramPlayer1, Player paramPlayer2, int paramInt1, int paramInt2, JList paramJList1, JList paramJList2)
  {
    int i = paramPlayer1.adr;
    int j = paramPlayer1.index;
    int k = paramPlayer2.adr;
    int m = paramPlayer2.index;
    this.of.data[i] = this.of.toByte(m & 0xFF);
    this.of.data[(i + 1)] = this.of.toByte((m & 0xFF00) >>> 8);
    this.of.data[k] = this.of.toByte(j & 0xFF);
    this.of.data[(k + 1)] = this.of.toByte((j & 0xFF00) >>> 8);
    if (paramInt1 == paramInt2)
    {
      int n = this.of.data[getNumAdr(k)];
      this.of.data[getNumAdr(k)] = this.of.data[getNumAdr(i)];
      this.of.data[getNumAdr(i)] = (byte)n;
    }
    if ((j == 0) || (m == 0))
    {
      if (this.sourceIndex > 10) {
        this.squadTidy.tidy(paramInt1);
      } else if (this.autoRep.isSelected()) {
        this.squadTidy.tidy11(paramInt1, this.sourceIndex, ((SelectByTeam)paramJList1.getParent()).posList.posNum[this.sourceIndex]);
      }
      if (paramJList2.getSelectedIndex() > 10) {
        this.squadTidy.tidy(paramInt2);
      } else if ((this.autoRep.isSelected()) && (paramJList1 != paramJList2)) {
        this.squadTidy.tidy11(paramInt2, paramJList2.getSelectedIndex(), ((SelectByTeam)paramJList2.getParent()).posList.posNum[paramJList2.getSelectedIndex()]);
      }
    }
    refreshLists();
  }
  
  private void tranRelL(Player paramPlayer, int paramInt)
  {
    int i = paramPlayer.adr;
    this.of.data[i] = 0;
    this.of.data[(i + 1)] = 0;
    this.of.data[getNumAdr(i)] = -1;
    if (paramInt > 10) {
      this.squadTidy.tidy(this.selectorL.teamBox.getSelectedIndex());
    } else if (this.autoRep.isSelected()) {
      this.squadTidy.tidy11(this.selectorL.teamBox.getSelectedIndex(), paramInt, this.selectorL.posList.posNum[paramInt]);
    }
    refreshLists();
  }
  
  private void tranRelR(Player paramPlayer, int paramInt)
  {
    int i = paramPlayer.adr;
    this.of.data[i] = 0;
    this.of.data[(i + 1)] = 0;
    this.of.data[getNumAdr(i)] = -1;
    if (paramInt > 10) {
      this.squadTidy.tidy(this.selectorR.teamBox.getSelectedIndex());
    } else if (this.autoRep.isSelected()) {
      this.squadTidy.tidy11(this.selectorR.teamBox.getSelectedIndex(), paramInt, this.selectorR.posList.posNum[paramInt]);
    }
    refreshLists();
  }
  
  private void addListen()
  {
    this.selectorL.squadList.addListSelectionListener(this.nameEditor);
    this.selectorR.squadList.addListSelectionListener(this.nameEditor);
    this.freeList.freeList.addListSelectionListener(this.nameEditor);
    this.selectorL.squadList.addListSelectionListener(this.shirtEditor);
    this.selectorR.squadList.addListSelectionListener(this.shirtEditor);
    this.freeList.freeList.addListSelectionListener(this.shirtEditor);
    this.selectorL.numList.addListSelectionListener(this.numEditor);
    this.selectorR.numList.addListSelectionListener(this.numEditor);
  }
  
  private void removeListen()
  {
    this.selectorL.squadList.removeListSelectionListener(this.nameEditor);
    this.selectorR.squadList.removeListSelectionListener(this.nameEditor);
    this.freeList.freeList.removeListSelectionListener(this.nameEditor);
    this.selectorL.squadList.removeListSelectionListener(this.shirtEditor);
    this.selectorR.squadList.removeListSelectionListener(this.shirtEditor);
    this.freeList.freeList.removeListSelectionListener(this.shirtEditor);
    this.selectorL.numList.removeListSelectionListener(this.numEditor);
    this.selectorR.numList.removeListSelectionListener(this.numEditor);
  }
  
  private class NameEditor
    extends JTextField
    implements ListSelectionListener, ActionListener
  {
    int source = 0;
    
    public NameEditor()
    {
      super();
      addActionListener(this);
      setToolTipText("Enter new name and press return");
    }
    
    public void valueChanged(ListSelectionEvent paramListSelectionEvent)
    {
      if (!paramListSelectionEvent.getValueIsAdjusting())
      {
        if (paramListSelectionEvent.getSource() == TransferPanel.this.freeList.freeList) {
          if (TransferPanel.this.freeList.freeList.isSelectionEmpty())
          {
            setText("");
            TransferPanel.this.lastIndex = 0;
          }
          else
          {
            setText(((Player)TransferPanel.this.freeList.freeList.getSelectedValue()).name);
            this.source = 1;
            selectAll();
            TransferPanel.this.lastIndex = ((Player)TransferPanel.this.freeList.freeList.getSelectedValue()).index;
          }
        }
        if (paramListSelectionEvent.getSource() == TransferPanel.this.selectorL.squadList) {
          if (TransferPanel.this.selectorL.squadList.isSelectionEmpty())
          {
            setText("");
            TransferPanel.this.lastIndex = 0;
          }
          else
          {
            if (((Player)TransferPanel.this.selectorL.squadList.getSelectedValue()).index != 0) {
              setText(((Player)TransferPanel.this.selectorL.squadList.getSelectedValue()).name);
            } else {
              setText("");
            }
            this.source = 2;
            selectAll();
            TransferPanel.this.lastIndex = ((Player)TransferPanel.this.selectorL.squadList.getSelectedValue()).index;
          }
        }
        if (paramListSelectionEvent.getSource() == TransferPanel.this.selectorR.squadList) {
          if (TransferPanel.this.selectorR.squadList.isSelectionEmpty())
          {
            setText("");
            TransferPanel.this.lastIndex = 0;
          }
          else
          {
            if (((Player)TransferPanel.this.selectorR.squadList.getSelectedValue()).index != 0) {
              setText(((Player)TransferPanel.this.selectorR.squadList.getSelectedValue()).name);
            } else {
              setText("");
            }
            this.source = 3;
            selectAll();
            TransferPanel.this.lastIndex = ((Player)TransferPanel.this.selectorR.squadList.getSelectedValue()).index;
          }
        }
        TransferPanel.this.infoPanel.refresh(TransferPanel.this.lastIndex, TransferPanel.this.compIndex);
      }
    }
    
    public void actionPerformed(ActionEvent paramActionEvent)
    {
      int i;
      int j;
      if ((this.source == 1) && (!TransferPanel.this.freeList.freeList.isSelectionEmpty()) && (getText().length() < 16) && (getText().length() != 0))
      {
        i = TransferPanel.this.freeList.freeList.getSelectedIndex();
        if (!((Player)TransferPanel.this.freeList.freeList.getSelectedValue()).name.equals(getText()))
        {
          ((Player)TransferPanel.this.freeList.freeList.getSelectedValue()).setName(getText());
          j = ((Player)TransferPanel.this.freeList.freeList.getSelectedValue()).index;
          TransferPanel.this.shirtEditor.setShirtName(j, TransferPanel.this.shirtEditor.makeShirt(getText()));
          TransferPanel.this.refreshLists();
        }
        if ((!TransferPanel.this.freeList.alpha) && (i < TransferPanel.this.freeList.freeList.getModel().getSize() - 1))
        {
          TransferPanel.this.freeList.freeList.setSelectedIndex(i + 1);
          TransferPanel.this.freeList.freeList.ensureIndexIsVisible(i + 1);
        }
      }
      if ((this.source == 2) && (!TransferPanel.this.selectorL.squadList.isSelectionEmpty()) && (getText().length() < 16) && (getText().length() != 0))
      {
        i = TransferPanel.this.selectorL.squadList.getSelectedIndex();
        if (!((Player)TransferPanel.this.selectorL.squadList.getSelectedValue()).name.equals(getText()))
        {
          ((Player)TransferPanel.this.selectorL.squadList.getSelectedValue()).setName(getText());
          j = ((Player)TransferPanel.this.selectorL.squadList.getSelectedValue()).index;
          TransferPanel.this.shirtEditor.setShirtName(j, TransferPanel.this.shirtEditor.makeShirt(getText()));
          TransferPanel.this.refreshLists();
        }
        if (i < TransferPanel.this.selectorL.squadList.getModel().getSize() - 1) {
          TransferPanel.this.selectorL.squadList.setSelectedIndex(i + 1);
        }
      }
      if ((this.source == 3) && (!TransferPanel.this.selectorR.squadList.isSelectionEmpty()) && (getText().length() < 16) && (getText().length() != 0))
      {
        i = TransferPanel.this.selectorR.squadList.getSelectedIndex();
        if (!((Player)TransferPanel.this.selectorR.squadList.getSelectedValue()).name.equals(getText()))
        {
          ((Player)TransferPanel.this.selectorR.squadList.getSelectedValue()).setName(getText());
          j = ((Player)TransferPanel.this.selectorR.squadList.getSelectedValue()).index;
          TransferPanel.this.shirtEditor.setShirtName(j, TransferPanel.this.shirtEditor.makeShirt(getText()));
          TransferPanel.this.refreshLists();
        }
        if (i < TransferPanel.this.selectorR.squadList.getModel().getSize() - 1) {
          TransferPanel.this.selectorR.squadList.setSelectedIndex(i + 1);
        }
      }
    }
  }
  
  private class NumEditor
    extends JTextField
    implements ListSelectionListener, ActionListener
  {
    int source = 0;
    
    public NumEditor()
    {
      super();
      addActionListener(this);
      setToolTipText("Enter new squad number and press return");
    }
    
    public void valueChanged(ListSelectionEvent paramListSelectionEvent)
    {
      if (paramListSelectionEvent.getSource() == TransferPanel.this.selectorL.numList) {
        if (TransferPanel.this.selectorL.numList.isSelectionEmpty())
        {
          setText("");
        }
        else
        {
          this.source = 2;
          setText(String.valueOf(TransferPanel.this.getShirt(this.source, TransferPanel.this.selectorL.numList.getSelectedIndex())));
          TransferPanel.this.selectorR.numList.clearSelection();
          selectAll();
        }
      }
      if (paramListSelectionEvent.getSource() == TransferPanel.this.selectorR.numList) {
        if (TransferPanel.this.selectorR.numList.isSelectionEmpty())
        {
          setText("");
        }
        else
        {
          this.source = 3;
          setText(String.valueOf(TransferPanel.this.getShirt(this.source, TransferPanel.this.selectorR.numList.getSelectedIndex())));
          TransferPanel.this.selectorL.numList.clearSelection();
          selectAll();
        }
      }
    }
    
    public void actionPerformed(ActionEvent paramActionEvent)
    {
      int i;
      int j;
      if ((this.source == 2) && (!TransferPanel.this.selectorL.numList.isSelectionEmpty()))
      {
        i = TransferPanel.this.selectorL.numList.getSelectedIndex();
        try
        {
          j = new Integer(getText()).intValue();
          if ((j != 0) && (j <= 99)) {
            TransferPanel.this.setShirt(this.source, i, j);
          }
          TransferPanel.this.selectorR.numList.refresh(TransferPanel.this.selectorR.teamBox.getSelectedIndex());
          TransferPanel.this.selectorL.numList.refresh(TransferPanel.this.selectorL.teamBox.getSelectedIndex());
          if (i < TransferPanel.this.selectorL.squadList.getModel().getSize() - 1) {
            TransferPanel.this.selectorL.numList.setSelectedIndex(i + 1);
          }
        }
        catch (NumberFormatException localNumberFormatException1) {}
      }
      if ((this.source == 3) && (!TransferPanel.this.selectorR.numList.isSelectionEmpty()))
      {
        i = TransferPanel.this.selectorR.numList.getSelectedIndex();
        try
        {
          j = new Integer(getText()).intValue();
          if ((j != 0) && (j <= 99)) {
            TransferPanel.this.setShirt(this.source, i, j);
          }
          TransferPanel.this.selectorR.numList.refresh(TransferPanel.this.selectorR.teamBox.getSelectedIndex());
          TransferPanel.this.selectorL.numList.refresh(TransferPanel.this.selectorL.teamBox.getSelectedIndex());
          if (i < TransferPanel.this.selectorR.squadList.getModel().getSize() - 1) {
            TransferPanel.this.selectorR.numList.setSelectedIndex(i + 1);
          }
        }
        catch (NumberFormatException localNumberFormatException2) {}
      }
    }
  }
  
  private class ShirtNameEditor
    extends JTextField
    implements ListSelectionListener, ActionListener
  {
    int source = 0;
    
    public ShirtNameEditor()
    {
      super();
      addActionListener(this);
      setToolTipText("Enter new shirt name and press return");
    }
    
    public void valueChanged(ListSelectionEvent paramListSelectionEvent)
    {
      if (!paramListSelectionEvent.getValueIsAdjusting())
      {
        int i;
        if (paramListSelectionEvent.getSource() == TransferPanel.this.freeList.freeList) {
          if (TransferPanel.this.freeList.freeList.isSelectionEmpty())
          {
            setText("");
          }
          else
          {
            i = ((Player)TransferPanel.this.freeList.freeList.getSelectedValue()).index;
            setText(getShirtName(i));
            this.source = 1;
            selectAll();
          }
        }
        if (paramListSelectionEvent.getSource() == TransferPanel.this.selectorL.squadList) {
          if (TransferPanel.this.selectorL.squadList.isSelectionEmpty())
          {
            setText("");
          }
          else
          {
            i = ((Player)TransferPanel.this.selectorL.squadList.getSelectedValue()).index;
            setText(getShirtName(i));
            this.source = 2;
            selectAll();
          }
        }
        if (paramListSelectionEvent.getSource() == TransferPanel.this.selectorR.squadList) {
          if (TransferPanel.this.selectorR.squadList.isSelectionEmpty())
          {
            setText("");
          }
          else
          {
            i = ((Player)TransferPanel.this.selectorR.squadList.getSelectedValue()).index;
            setText(getShirtName(i));
            this.source = 3;
            selectAll();
          }
        }
      }
    }
    
    public void actionPerformed(ActionEvent paramActionEvent)
    {
      if ((this.source == 1) && (!TransferPanel.this.freeList.freeList.isSelectionEmpty()) && (getText().length() < 16))
      {
        TransferPanel.this.shirtEditor.setShirtName(((Player)TransferPanel.this.freeList.freeList.getSelectedValue()).index, getText());
        TransferPanel.this.refreshLists();
      }
      int i;
      if ((this.source == 2) && (!TransferPanel.this.selectorL.squadList.isSelectionEmpty()) && (getText().length() < 16))
      {
        i = TransferPanel.this.selectorL.squadList.getSelectedIndex();
        TransferPanel.this.shirtEditor.setShirtName(((Player)TransferPanel.this.selectorL.squadList.getSelectedValue()).index, getText());
        TransferPanel.this.refreshLists();
        if (i < TransferPanel.this.selectorL.squadList.getModel().getSize() - 1) {
          TransferPanel.this.selectorL.squadList.setSelectedIndex(i + 1);
        }
      }
      if ((this.source == 3) && (!TransferPanel.this.selectorR.squadList.isSelectionEmpty()) && (getText().length() < 16))
      {
        i = TransferPanel.this.selectorR.squadList.getSelectedIndex();
        TransferPanel.this.shirtEditor.setShirtName(((Player)TransferPanel.this.selectorR.squadList.getSelectedValue()).index, getText());
        TransferPanel.this.refreshLists();
        if (i < TransferPanel.this.selectorR.squadList.getModel().getSize() - 1) {
          TransferPanel.this.selectorR.squadList.setSelectedIndex(i + 1);
        }
      }
    }
    
    public String getShirtName(int paramInt)
    {
      String str = "";
      int i = 36904 + paramInt * 124;
      if (paramInt > 4999) {
        i = 14076 + (paramInt - 32768) * 124;
      }
      if (TransferPanel.this.of.data[i] != 0)
      {
        byte[] arrayOfByte = new byte[16];
        System.arraycopy(TransferPanel.this.of.data, i, arrayOfByte, 0, 16);
        for (int j = 0; j < 16; j++) {
          if (arrayOfByte[j] == 0) {
            arrayOfByte[j] = 33;
          }
        }
        str = new String(arrayOfByte);
        str = str.replaceAll("!", "");
      }
      return str;
    }
    
    public void setShirtName(int paramInt, String paramString)
    {
      if ((paramString.length() < 16) && (paramInt != 0))
      {
        int i = 36904 + paramInt * 124;
        if (paramInt > 4999) {
          i = 14076 + (paramInt - 32768) * 124;
        }
        byte[] arrayOfByte1 = new byte[16];
        paramString = paramString.toUpperCase();
        byte[] arrayOfByte2 = paramString.getBytes();
        for (int j = 0; j < arrayOfByte2.length; j++) {
          if (((arrayOfByte2[j] < 65) || (arrayOfByte2[j] > 90)) && (arrayOfByte2[j] != 46) && (arrayOfByte2[j] != 32) && (arrayOfByte2[j] != 95)) {
            arrayOfByte2[j] = 32;
          }
        }
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, arrayOfByte2.length);
        System.arraycopy(arrayOfByte1, 0, TransferPanel.this.of.data, i, 16);
        Stat localStat = new Stat(TransferPanel.this.of, 0, 3, 1, 1, "");
        localStat.setValue(paramInt, 1);
      }
    }
    
    public String makeShirt(String paramString)
    {
      String str1 = "";
      String str2 = "";
      int i = paramString.length();
      if ((i < 9) && (i > 5)) {
        str2 = " ";
      } else if ((i < 6) && (i > 3)) {
        str2 = "  ";
      } else if (i == 3) {
        str2 = "    ";
      } else if (i == 2) {
        str2 = "        ";
      }
      paramString = paramString.toUpperCase();
      byte[] arrayOfByte = paramString.getBytes();
      for (int j = 0; j < arrayOfByte.length; j++) {
        if (((arrayOfByte[j] < 65) || (arrayOfByte[j] > 90)) && (arrayOfByte[j] != 46) && (arrayOfByte[j] != 32) && (arrayOfByte[j] != 95)) {
          arrayOfByte[j] = 32;
        }
      }
      paramString = new String(arrayOfByte);
      for (int j = 0; j < paramString.length() - 1; j++) {
        str1 = str1 + paramString.substring(j, j + 1) + str2;
      }
      str1 = str1 + paramString.substring(paramString.length() - 1, paramString.length());
      return str1;
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
      return new DataFlavor[] { TransferPanel.this.localPlayerFlavor };
    }
    
    public boolean isDataFlavorSupported(DataFlavor paramDataFlavor)
    {
      return TransferPanel.this.localPlayerFlavor.equals(paramDataFlavor);
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/TransferPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
