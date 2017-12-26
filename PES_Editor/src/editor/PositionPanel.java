package editor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PositionPanel
  extends JPanel
  implements ActionListener
{
  JComboBox regBox;
  int player;
  JCheckBox[] checkBox;
  String[] position = { "GK", "?", "CWP", "CBT", "SB", "DM", "WB", "CM", "SM", "AM", "WG", "SS", "CF" };
  int regPos;
  
  public PositionPanel()
  {
    super(new BorderLayout());
    setBorder(BorderFactory.createTitledBorder("Position"));
    JPanel localJPanel1 = new JPanel(new GridLayout(4, 4));
    JLabel localJLabel = new JLabel("Registered Position");
    JPanel localJPanel2 = new JPanel();
    this.checkBox = new JCheckBox[this.position.length];
    for (int i = 0; i < this.position.length; i++)
    {
      this.checkBox[i] = new JCheckBox(this.position[i]);
      if (i != 1)
      {
        this.checkBox[i].setActionCommand(String.valueOf(i));
        this.checkBox[i].addActionListener(this);
        localJPanel1.add(this.checkBox[i]);
      }
      if (i == 0)
      {
        localJPanel1.add(new JPanel());
        localJPanel1.add(new JPanel());
        localJPanel1.add(new JPanel());
      }
    }
    this.regBox = new JComboBox();
    this.regBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (paramAnonymousActionEvent.getActionCommand() == "y")
        {
          String str = (String)PositionPanel.this.regBox.getSelectedItem();
          int i = 0;
          for (int j = 0; j < PositionPanel.this.position.length; j++) {
            if (PositionPanel.this.position[j].equals(str)) {
              i = j;
            }
          }
          PositionPanel.this.regPos = i;
        }
      }
    });
    localJPanel2.add(localJLabel);
    localJPanel2.add(this.regBox);
    add(localJPanel1, "Center");
    add(localJPanel2, "South");
  }
  
  public void load(OptionFile paramOptionFile, Stats paramStats, int paramInt)
  {
    this.player = paramInt;
    this.regPos = paramStats.regPos.getValue(this.player);
    if ((paramStats.gk.getValue(this.player) == 1) || (this.regPos == 0)) {
      this.checkBox[0].setSelected(true);
    } else {
      this.checkBox[0].setSelected(false);
    }
    if ((paramStats.cbwS.getValue(this.player) == 1) || (this.regPos == 2)) {
      this.checkBox[2].setSelected(true);
    } else {
      this.checkBox[2].setSelected(false);
    }
    if ((paramStats.cbt.getValue(this.player) == 1) || (this.regPos == 3)) {
      this.checkBox[3].setSelected(true);
    } else {
      this.checkBox[3].setSelected(false);
    }
    if ((paramStats.sb.getValue(this.player) == 1) || (this.regPos == 4)) {
      this.checkBox[4].setSelected(true);
    } else {
      this.checkBox[4].setSelected(false);
    }
    if ((paramStats.dm.getValue(this.player) == 1) || (this.regPos == 5)) {
      this.checkBox[5].setSelected(true);
    } else {
      this.checkBox[5].setSelected(false);
    }
    if ((paramStats.wb.getValue(this.player) == 1) || (this.regPos == 6)) {
      this.checkBox[6].setSelected(true);
    } else {
      this.checkBox[6].setSelected(false);
    }
    if ((paramStats.cm.getValue(this.player) == 1) || (this.regPos == 7)) {
      this.checkBox[7].setSelected(true);
    } else {
      this.checkBox[7].setSelected(false);
    }
    if ((paramStats.sm.getValue(this.player) == 1) || (this.regPos == 8)) {
      this.checkBox[8].setSelected(true);
    } else {
      this.checkBox[8].setSelected(false);
    }
    if ((paramStats.om.getValue(this.player) == 1) || (this.regPos == 9)) {
      this.checkBox[9].setSelected(true);
    } else {
      this.checkBox[9].setSelected(false);
    }
    if ((paramStats.cf.getValue(this.player) == 1) || (this.regPos == 12)) {
      this.checkBox[12].setSelected(true);
    } else {
      this.checkBox[12].setSelected(false);
    }
    if ((paramStats.wg.getValue(this.player) == 1) || (this.regPos == 10)) {
      this.checkBox[10].setSelected(true);
    } else {
      this.checkBox[10].setSelected(false);
    }
    if ((paramStats.ss.getValue(this.player) == 1) || (this.regPos == 11)) {
      this.checkBox[11].setSelected(true);
    } else {
      this.checkBox[11].setSelected(false);
    }
    updateRegBox();
  }
  
  public void updateRegBox()
  {
    this.regBox.setActionCommand("n");
    this.regBox.removeAllItems();
    for (int i = 0; i < this.position.length; i++) {
      if (this.checkBox[i].isSelected()) {
        this.regBox.addItem(this.position[i]);
      }
    }
    this.regBox.setSelectedItem(this.position[this.regPos]);
    this.regBox.setActionCommand("y");
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    int i = 0;
    try
    {
      i = new Integer(paramActionEvent.getActionCommand()).intValue();
    }
    catch (NumberFormatException localNumberFormatException) {}
    if (this.regPos == i) {
      this.checkBox[i].setSelected(true);
    }
    updateRegBox();
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/PositionPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
