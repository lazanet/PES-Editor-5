package editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class KitImportDialog
  extends JDialog
  implements MouseListener
{
  OptionFile of2;
  JLabel fileLabel;
  JList list;
  int index;
  boolean national;
  
  public KitImportDialog(Frame paramFrame, OptionFile paramOptionFile)
  {
    super(paramFrame, "Import Kit", true);
    this.of2 = paramOptionFile;
    JPanel localJPanel = new JPanel(new BorderLayout());
    this.fileLabel = new JLabel("From:");
    this.list = new JList();
    this.list.addMouseListener(this);
    this.list.setSelectionMode(0);
    this.list.setLayoutOrientation(0);
    this.list.setVisibleRowCount(20);
    JScrollPane localJScrollPane = new JScrollPane(22, 31);
    localJScrollPane.setViewportView(this.list);
    CancelButton localCancelButton = new CancelButton(this);
    localJPanel.add(this.fileLabel, "North");
    localJPanel.add(localJScrollPane, "Center");
    localJPanel.add(localCancelButton, "South");
    getContentPane().add(localJPanel);
    this.index = 0;
  }
  
  public int show(int paramInt)
  {
    this.index = -1;
    refresh(paramInt);
    setVisible(true);
    return this.index;
  }
  
  public void refresh(int paramInt)
  {
    if (paramInt < 138)
    {
      this.list.setListData(Clubs.getNames(this.of2));
      this.national = false;
    }
    else
    {
      this.national = true;
      String[] arrayOfString = new String[64];
      System.arraycopy(Stat.nation, 0, arrayOfString, 0, 57);
      System.arraycopy(PES4Utils.extraSquad, 0, arrayOfString, 57, 7);
      this.list.setListData(arrayOfString);
    }
    this.fileLabel.setText("  From:  " + this.of2.fileName);
    pack();
  }
  
  public void mousePressed(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mouseClicked(MouseEvent paramMouseEvent)
  {
    int i = paramMouseEvent.getClickCount();
    JList localJList = (JList)paramMouseEvent.getSource();
    int j = localJList.getSelectedIndex();
    if ((i == 2) && (j != -1))
    {
      this.index = j;
      if ((j != -1) && (this.national)) {
        this.index += 138;
      }
      setVisible(false);
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/KitImportDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */