package editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListModel;

public class TeamSetPanel
  extends JPanel
{
  private OptionFile of;
  private SquadList list;
  private PositionList posList;
  int alt = 0;
  int squad = 0;
  private JComboBox attDefBox;
  private JComboBox defSysBox;
  private JComboBox sweeperBox;
  private JComboBox lineBox;
  private JComboBox pressBox;
  private JComboBox trapBox;
  private JComboBox counterBox;
  private boolean ok = false;
  
  public TeamSetPanel(OptionFile paramOptionFile, SquadList paramSquadList, PositionList paramPositionList)
  {
    super(new GridBagLayout());
    this.of = paramOptionFile;
    this.list = paramSquadList;
    this.posList = paramPositionList;
    String[] arrayOfString1 = { "Manual", "Auto-Defence", "Normal", "Auto-Attack" };
    this.attDefBox = new JComboBox(arrayOfString1);
    this.attDefBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (TeamSetPanel.this.ok) {
          TeamSetPanel.this.of.data[(670786 + 628 * TeamSetPanel.this.squad + 6232 + TeamSetPanel.this.alt * 171)] = ((byte)TeamSetPanel.this.attDefBox.getSelectedIndex());
        }
      }
    });
    String[] arrayOfString2 = { "Normal", "Sweeper", "Line" };
    this.defSysBox = new JComboBox(arrayOfString2);
    this.defSysBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (TeamSetPanel.this.ok)
        {
          TeamSetPanel.this.of.data[(670784 + 628 * TeamSetPanel.this.squad + 6232 + TeamSetPanel.this.alt * 171)] = ((byte)TeamSetPanel.this.defSysBox.getSelectedIndex());
          if ((TeamSetPanel.this.defSysBox.getSelectedIndex() == 1) && (TeamSetPanel.this.of.data[(670785 + 628 * TeamSetPanel.this.squad + 6232 + TeamSetPanel.this.alt * 171)] == 0))
          {
            for (int i = 1; (TeamSetPanel.this.of.data[(670785 + 628 * TeamSetPanel.this.squad + 6232 + TeamSetPanel.this.alt * 171)] == 0) && (i < 11); i++)
            {
              String str = (String)TeamSetPanel.this.posList.getModel().getElementAt(i);
              if ((str.equals("CBT")) || (str.equals("CBW")) || (str.equals("ASW"))) {
                TeamSetPanel.this.of.data[(670785 + 628 * TeamSetPanel.this.squad + 6232 + TeamSetPanel.this.alt * 171)] = ((byte)i);
              }
            }
            if (TeamSetPanel.this.of.data[(670785 + 628 * TeamSetPanel.this.squad + 6232 + TeamSetPanel.this.alt * 171)] == 0) {
              TeamSetPanel.this.of.data[(670784 + 628 * TeamSetPanel.this.squad + 6232 + TeamSetPanel.this.alt * 171)] = 0;
            }
          }
          TeamSetPanel.this.refresh(TeamSetPanel.this.squad);
        }
      }
    });
    this.sweeperBox = new JComboBox();
    this.sweeperBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (TeamSetPanel.this.ok)
        {
          TeamSetPanel.SweItem localSweItem = (TeamSetPanel.SweItem)TeamSetPanel.this.sweeperBox.getSelectedItem();
          if (localSweItem != null) {
            TeamSetPanel.this.of.data[(670785 + 628 * TeamSetPanel.this.squad + 6232 + TeamSetPanel.this.alt * 171)] = localSweItem.index;
          }
        }
      }
    });
    String[] arrayOfString3 = { "A", "B", "C" };
    this.lineBox = new JComboBox(arrayOfString3);
    this.lineBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (TeamSetPanel.this.ok) {
          TeamSetPanel.this.of.data[(670787 + 628 * TeamSetPanel.this.squad + 6232 + TeamSetPanel.this.alt * 171)] = ((byte)TeamSetPanel.this.lineBox.getSelectedIndex());
        }
      }
    });
    this.pressBox = new JComboBox(arrayOfString3);
    this.pressBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (TeamSetPanel.this.ok) {
          TeamSetPanel.this.of.data[(670788 + 628 * TeamSetPanel.this.squad + 6232 + TeamSetPanel.this.alt * 171)] = ((byte)TeamSetPanel.this.pressBox.getSelectedIndex());
        }
      }
    });
    this.trapBox = new JComboBox(arrayOfString3);
    this.trapBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (TeamSetPanel.this.ok) {
          TeamSetPanel.this.of.data[(670789 + 628 * TeamSetPanel.this.squad + 6232 + TeamSetPanel.this.alt * 171)] = ((byte)TeamSetPanel.this.trapBox.getSelectedIndex());
        }
      }
    });
    this.counterBox = new JComboBox(arrayOfString3);
    this.counterBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (TeamSetPanel.this.ok) {
          TeamSetPanel.this.of.data[(670790 + 628 * TeamSetPanel.this.squad + 6232 + TeamSetPanel.this.alt * 171)] = ((byte)TeamSetPanel.this.counterBox.getSelectedIndex());
        }
      }
    });
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.anchor = 13;
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 0;
    add(new JLabel("Back line"), localGridBagConstraints);
    localGridBagConstraints.gridx = 2;
    localGridBagConstraints.gridy = 0;
    add(this.lineBox, localGridBagConstraints);
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 1;
    add(new JLabel("Zone Press"), localGridBagConstraints);
    localGridBagConstraints.gridx = 2;
    localGridBagConstraints.gridy = 1;
    add(this.pressBox, localGridBagConstraints);
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 2;
    add(new JLabel("Offside Trap"), localGridBagConstraints);
    localGridBagConstraints.gridx = 2;
    localGridBagConstraints.gridy = 2;
    add(this.trapBox, localGridBagConstraints);
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 3;
    add(new JLabel("Counter Attack"), localGridBagConstraints);
    localGridBagConstraints.gridx = 2;
    localGridBagConstraints.gridy = 3;
    add(this.counterBox, localGridBagConstraints);
    localGridBagConstraints.anchor = 17;
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    add(new JLabel("Attack/Defence"), localGridBagConstraints);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 1;
    add(this.attDefBox, localGridBagConstraints);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 2;
    add(new JLabel("Defence System"), localGridBagConstraints);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 3;
    add(this.defSysBox, localGridBagConstraints);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 4;
    localGridBagConstraints.gridwidth = 3;
    add(this.sweeperBox, localGridBagConstraints);
  }
  
  public void refresh(int paramInt)
  {
    this.squad = paramInt;
    this.ok = false;
    this.attDefBox.setSelectedIndex(this.of.data[(670786 + 628 * this.squad + 6232 + this.alt * 171)]);
    int i = this.of.data[(670784 + 628 * this.squad + 6232 + this.alt * 171)];
    this.defSysBox.setSelectedIndex(i);
    this.lineBox.setSelectedIndex(this.of.data[(670787 + 628 * this.squad + 6232 + this.alt * 171)]);
    this.pressBox.setSelectedIndex(this.of.data[(670788 + 628 * this.squad + 6232 + this.alt * 171)]);
    this.trapBox.setSelectedIndex(this.of.data[(670789 + 628 * this.squad + 6232 + this.alt * 171)]);
    this.counterBox.setSelectedIndex(this.of.data[(670790 + 628 * this.squad + 6232 + this.alt * 171)]);
    this.sweeperBox.removeAllItems();
    int j = this.of.data[(670785 + 628 * this.squad + 6232 + this.alt * 171)];
    if (j != 0)
    {
      int k = 0;
      int m = -1;
      for (byte b = 1; b < 11; b = (byte)(b + 1))
      {
        String str = (String)this.posList.getModel().getElementAt(b);
        if ((str.equals("CBT")) || (str.equals("CBW")) || (str.equals("ASW")))
        {
          if (b == j) {
            m = k;
          }
          this.sweeperBox.addItem(new SweItem(b));
          k = (byte)(k + 1);
        }
      }
      this.sweeperBox.setSelectedIndex(m);
    }
    if ((i == 1) && (this.sweeperBox.getItemCount() != 0)) {
      this.sweeperBox.setEnabled(true);
    } else {
      this.sweeperBox.setEnabled(false);
    }
    this.ok = true;
  }
  
  private class SweItem
  {
    String name;
    byte index;
    
    public SweItem(byte paramByte)
    {
      this.index = paramByte;
      this.name = ((Player)TeamSetPanel.this.list.getModel().getElementAt(this.index)).name;
    }
    
    public String toString()
    {
      return this.name;
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/TeamSetPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */