package editor;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListModel;

public class StrategyPanel
  extends JPanel
{
  private OptionFile of;
  private SquadList list;
  int squad = 0;
  private JComboBox[] butBox = new JComboBox[4];
  private JButton autoButton;
  private JComboBox overBox;
  private boolean ok = false;
  private boolean auto = false;
  private JLabel[] label = new JLabel[4];
  
  public StrategyPanel(OptionFile paramOptionFile, SquadList paramSquadList, PositionList paramPositionList)
  {
    super(new GridBagLayout());
    this.of = paramOptionFile;
    this.list = paramSquadList;
    ActionListener local1 = new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (StrategyPanel.this.ok)
        {
          int i = new Integer(paramAnonymousActionEvent.getActionCommand()).intValue();
          int j = (byte)StrategyPanel.this.butBox[i].getSelectedIndex();
          StrategyPanel.this.of.data[(670608 + i + 628 * StrategyPanel.this.squad + 6232)] = (byte)j;
          if ((j == 7) && (StrategyPanel.this.of.data[(670612 + 628 * StrategyPanel.this.squad + 6232)] == 0)) {
            for (int k = 1; (StrategyPanel.this.of.data[(670612 + 628 * StrategyPanel.this.squad + 6232)] == 0) && (k < 11); k++)
            {
              int m = StrategyPanel.this.of.data[(670641 + 628 * StrategyPanel.this.squad + 6232 + k)];
              if ((m > 0) && (m < 8)) {
                StrategyPanel.this.of.data[(670612 + 628 * StrategyPanel.this.squad + 6232)] = ((byte)k);
              }
            }
          }
          StrategyPanel.this.refresh(StrategyPanel.this.squad);
        }
      }
    };
    String[] arrayOfString = { "No Strategy", "Normal", "Centre Att.", "R. Side Att.", "L. Side Att.", "Opp. Side Att.", "Change Sides", "CB Overlap", "Zone Press", "Counter Attack", "Offside Trap", "Formation A", "Formation B" };
    for (int i = 0; i < 4; i++)
    {
      this.label[i] = new JLabel();
      this.label[i].setPreferredSize(new Dimension(42, 17));
      this.butBox[i] = new JComboBox(arrayOfString);
      this.butBox[i].setActionCommand(String.valueOf(i));
      this.butBox[i].addActionListener(local1);
    }
    this.autoButton = new JButton("Manual");
    this.autoButton.setPreferredSize(new Dimension(93, 26));
    this.autoButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (StrategyPanel.this.ok)
        {
          StrategyPanel.this.auto = (!StrategyPanel.this.auto);
          if (StrategyPanel.this.auto)
          {
            StrategyPanel.this.of.data[(670613 + 628 * StrategyPanel.this.squad + 6232)] = 1;
            StrategyPanel.this.autoButton.setText("Semi-Auto");
          }
          else
          {
            StrategyPanel.this.of.data[(670613 + 628 * StrategyPanel.this.squad + 6232)] = 0;
            StrategyPanel.this.autoButton.setText("Manual");
          }
          StrategyPanel.this.refresh(StrategyPanel.this.squad);
        }
      }
    });
    this.overBox = new JComboBox();
    this.overBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (StrategyPanel.this.ok)
        {
          StrategyPanel.SweItem localSweItem = (StrategyPanel.SweItem)StrategyPanel.this.overBox.getSelectedItem();
          if (localSweItem != null) {
            StrategyPanel.this.of.data[(670612 + 628 * StrategyPanel.this.squad + 6232)] = localSweItem.index;
          }
        }
      }
    });
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    int j = 0;
    int k = 0;
    for (int m = 0; m < 4; m++)
    {
      if (m < 2)
      {
        j = m + 1;
        k = 0;
      }
      else
      {
        j = m - 1;
        k = 2;
      }
      localGridBagConstraints.gridx = j;
      localGridBagConstraints.gridy = k;
      add(this.label[m], localGridBagConstraints);
      localGridBagConstraints.gridx = j;
      localGridBagConstraints.gridy = (k + 1);
      add(this.butBox[m], localGridBagConstraints);
    }
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    add(this.autoButton, localGridBagConstraints);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 4;
    add(new JLabel("Overlap CB:"), localGridBagConstraints);
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.gridwidth = 2;
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 4;
    add(this.overBox, localGridBagConstraints);
  }
  
  public void refresh(int paramInt)
  {
    this.squad = paramInt;
    this.ok = false;
    int i = this.of.data[(670613 + 628 * this.squad + 6232)];
    if (i == 1)
    {
      this.auto = true;
      this.autoButton.setText("Semi-Auto");
      for (int j = 0; j < 4; j++)
      {
        if (j == 0) {
          this.label[j].setText("L2");
        } else {
          this.label[j].setText("AUTO " + j);
        }
        this.label[j].setIcon(null);
      }
    }
    else
    {
      this.auto = false;
      this.autoButton.setText("Manual");
      for (int j = 0; j < 4; j++)
      {
        this.label[j].setText(null);
        this.label[j].setIcon(new PS2ButtonIcon(j));
      }
    }
    int j = 0;
    for (int k = 0; k < 4; k++)
    {
     int m = this.of.data[(670608 + k + 628 * this.squad + 6232)];
      this.butBox[k].setSelectedIndex(m);
      if (m == 7) {
        j = 1;
      }
    }
    this.overBox.removeAllItems();
    int k = this.of.data[(670612 + 628 * this.squad + 6232)];
    int m = 0;
    int n = -1;
    int i2;
    for (int i1 = 1; i1 < 11; i1++)
    {
      int i3 = this.of.data[(670641 + 628 * this.squad + 6232 + i1)];
      if ((i3 > 0) && (i3 < 8))
      {
        if (i1 == k) {
          n = m;
        }
        this.overBox.addItem(new SweItem((byte)i1));
        m = (byte)(m + 1);
      }
    }
    this.overBox.setSelectedIndex(n);
    if ((j != 0) && (this.overBox.getItemCount() != 0)) {
      this.overBox.setEnabled(true);
    } else {
      this.overBox.setEnabled(false);
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
      this.name = ((Player)StrategyPanel.this.list.getModel().getElementAt(this.index)).name;
    }
    
    public String toString()
    {
      return this.name;
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/StrategyPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
