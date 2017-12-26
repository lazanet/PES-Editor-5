package editor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GlobalPanel
  extends JPanel
{
  private Stats stats;
  private JTextField numField;
  private JComboBox statBox;
  private JComboBox opBox;
  private JButton doButton;
  private JComboBox scopeBox;
  private String[] statNames = { "1-99 Stats", "ATTACK", "DEFENSE", "BALANCE", "STAMINA", "TOP SPEED", "ACCELERATION", "RESPONSE", "AGILITY", "DRIBBLE ACCURACY", "DRIBBLE SPEED", "SHORT PASS ACCURACY", "SHORT PASS SPEED", "LONG PASS ACCURACY", "LONG PASS SPEED", "SHOT ACCURACY", "SHOT POWER", "SHOT TECHNIQUE", "FREE KICK ACCURACY", "CURLING", "HEADING", "JUMP", "TECHNIQUE", "AGGRESSION", "MENTALITY", "GOAL KEEPING", "TEAM WORK", "1-8 Stats", "WEAK FOOT ACCURACY", "WEAK FOOT FREQUENCY", "CONSISTENCY", "CONDITION / FITNESS", "AGE" };
  private String[] ops = { "+", "-", "=", "+ %", "- %" };
  private String[] scopes = { "All Players", "GK", "CWP", "CBT", "SB", "DM", "WB", "CM", "SM", "AM", "WG", "SS", "CF" };
  private JComboBox teamBox;
  private OptionFile of;
  private JCheckBox checkBox;
  private TransferPanel tranPanel;
  
  public GlobalPanel(Stats paramStats, OptionFile paramOptionFile, TransferPanel paramTransferPanel)
  {
    this.stats = paramStats;
    this.of = paramOptionFile;
    this.tranPanel = paramTransferPanel;
    this.statBox = new JComboBox(this.statNames);
    this.numField = new JTextField(2);
    this.opBox = new JComboBox(this.ops);
    JPanel localJPanel1 = new JPanel();
    JPanel localJPanel2 = new JPanel(new GridLayout(2, 3));
    JPanel localJPanel3 = new JPanel(new GridLayout(0, 1));
    localJPanel2.setBorder(BorderFactory.createTitledBorder("Scope"));
    localJPanel1.setBorder(BorderFactory.createTitledBorder("Adjustment"));
    this.scopeBox = new JComboBox(this.scopes);
    this.teamBox = new JComboBox();
    this.checkBox = new JCheckBox();
    this.doButton = new JButton("Adjust");
    this.doButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        int i = GlobalPanel.this.getNum();
        int j = GlobalPanel.this.statBox.getSelectedIndex();
        int k = i;
        int m = GlobalPanel.this.opBox.getSelectedIndex();
        int n = 99;
        int i1 = 1;
        if (j == 32)
        {
          n = 46;
          i1 = 15;
        }
        else if (j > 26)
        {
          n = 8;
        }
        int i2;
        int i3;
        if (i == 0)
        {
          i2 = 1;
          i3 = 99;
          if (m == 2)
          {
            i2 = i1;
            i3 = n;
          }
          JOptionPane.showMessageDialog(null, "Enter a number: " + i2 + "-" + i3, "Error", 0);
        }
        else
        {
          if (j == 0)
          {
            for (i2 = 1; i2 < 5000; i2++) {
              if ((GlobalPanel.this.adj(i2)) && (GlobalPanel.this.adjTeam(i2))) {
                for (i3 = 1; i3 < 27; i3++)
                {
                  switch (m)
                  {
                  case 0: 
                    k = GlobalPanel.this.getStat(i3, i2) + i;
                    break;
                  case 1: 
                    k = GlobalPanel.this.getStat(i3, i2) - i;
                    break;
                  case 2: 
                    k = i;
                    break;
                  case 3: 
                    k = GlobalPanel.this.getStat(i3, i2) + GlobalPanel.this.getStat(i3, i2) * i / 100;
                    break;
                  case 4: 
                    k = GlobalPanel.this.getStat(i3, i2) - GlobalPanel.this.getStat(i3, i2) * i / 100;
                  }
                  if (k > 99) {
                    k = 99;
                  }
                  if (k < 1) {
                    k = 1;
                  }
                  GlobalPanel.this.setStat(i3, i2, k);
                }
              }
            }
            if (GlobalPanel.this.checkBox.isSelected()) {
              for (i2 = 32768; i2 < 32952; i2++) {
                if ((GlobalPanel.this.adj(i2)) && (GlobalPanel.this.adjTeam(i2))) {
                  for (i3 = 1; i3 < 27; i3++)
                  {
                    switch (m)
                    {
                    case 0: 
                      k = GlobalPanel.this.getStat(i3, i2) + i;
                      break;
                    case 1: 
                      k = GlobalPanel.this.getStat(i3, i2) - i;
                      break;
                    case 2: 
                      k = i;
                      break;
                    case 3: 
                      k = GlobalPanel.this.getStat(i3, i2) + GlobalPanel.this.getStat(i3, i2) * i / 100;
                      break;
                    case 4: 
                      k = GlobalPanel.this.getStat(i3, i2) - GlobalPanel.this.getStat(i3, i2) * i / 100;
                    }
                    if (k > 99) {
                      k = 99;
                    }
                    if (k < 1) {
                      k = 1;
                    }
                    GlobalPanel.this.setStat(i3, i2, k);
                  }
                }
              }
            }
          }
          if (j == 27)
          {
            for (i2 = 1; i2 < 5000; i2++) {
              if ((GlobalPanel.this.adj(i2)) && (GlobalPanel.this.adjTeam(i2))) {
                for (i3 = 28; i3 < 32; i3++)
                {
                  switch (m)
                  {
                  case 0: 
                    k = GlobalPanel.this.getStat(i3, i2) + i;
                    break;
                  case 1: 
                    k = GlobalPanel.this.getStat(i3, i2) - i;
                    break;
                  case 2: 
                    k = i;
                    break;
                  case 3: 
                    k = GlobalPanel.this.getStat(i3, i2) + GlobalPanel.this.getStat(i3, i2) * i / 100;
                    break;
                  case 4: 
                    k = GlobalPanel.this.getStat(i3, i2) - GlobalPanel.this.getStat(i3, i2) * i / 100;
                  }
                  if (k > 8) {
                    k = 8;
                  }
                  if (k < 1) {
                    k = 1;
                  }
                  GlobalPanel.this.setStat(i3, i2, k);
                }
              }
            }
            if (GlobalPanel.this.checkBox.isSelected()) {
              for (i2 = 32768; i2 < 32952; i2++) {
                if ((GlobalPanel.this.adj(i2)) && (GlobalPanel.this.adjTeam(i2))) {
                  for (i3 = 28; i3 < 32; i3++)
                  {
                    switch (m)
                    {
                    case 0: 
                      k = GlobalPanel.this.getStat(i3, i2) + i;
                      break;
                    case 1: 
                      k = GlobalPanel.this.getStat(i3, i2) - i;
                      break;
                    case 2: 
                      k = i;
                      break;
                    case 3: 
                      k = GlobalPanel.this.getStat(i3, i2) + GlobalPanel.this.getStat(i3, i2) * i / 100;
                      break;
                    case 4: 
                      k = GlobalPanel.this.getStat(i3, i2) - GlobalPanel.this.getStat(i3, i2) * i / 100;
                    }
                    if (k > 8) {
                      k = 8;
                    }
                    if (k < 1) {
                      k = 1;
                    }
                    GlobalPanel.this.setStat(i3, i2, k);
                  }
                }
              }
            }
          }
          if ((j != 0) && (j != 27))
          {
            for (i2 = 1; i2 < 5000; i2++) {
              if ((GlobalPanel.this.adj(i2)) && (GlobalPanel.this.adjTeam(i2)))
              {
                switch (m)
                {
                case 0: 
                  k = GlobalPanel.this.getStat(j, i2) + i;
                  break;
                case 1: 
                  k = GlobalPanel.this.getStat(j, i2) - i;
                  break;
                case 2: 
                  k = i;
                  break;
                case 3: 
                  k = GlobalPanel.this.getStat(j, i2) + GlobalPanel.this.getStat(j, i2) * i / 100;
                  break;
                case 4: 
                  k = GlobalPanel.this.getStat(j, i2) - GlobalPanel.this.getStat(j, i2) * i / 100;
                }
                if (k > n) {
                  k = n;
                }
                if (k < i1) {
                  k = i1;
                }
                GlobalPanel.this.setStat(j, i2, k);
              }
            }
            if (GlobalPanel.this.checkBox.isSelected()) {
              for (i2 = 32768; i2 < 32952; i2++) {
                if ((GlobalPanel.this.adj(i2)) && (GlobalPanel.this.adjTeam(i2)))
                {
                  switch (m)
                  {
                  case 0: 
                    k = GlobalPanel.this.getStat(j, i2) + i;
                    break;
                  case 1: 
                    k = GlobalPanel.this.getStat(j, i2) - i;
                    break;
                  case 2: 
                    k = i;
                    break;
                  case 3: 
                    k = GlobalPanel.this.getStat(j, i2) + GlobalPanel.this.getStat(j, i2) * i / 100;
                    break;
                  case 4: 
                    k = GlobalPanel.this.getStat(j, i2) - GlobalPanel.this.getStat(j, i2) * i / 100;
                  }
                  if (k > n) {
                    k = n;
                  }
                  if (k < 1) {
                    k = 1;
                  }
                  GlobalPanel.this.setStat(j, i2, k);
                }
              }
            }
          }
          JOptionPane.showMessageDialog(null, "Stats have been adjusted", "Stats Adjusted", 1);
          GlobalPanel.this.tranPanel.refresh();
        }
      }
    });
    localJPanel2.add(new JLabel("Registered Position:"));
    localJPanel2.add(new JLabel("Exclude Team:"));
    localJPanel2.add(new JLabel("Created Players:"));
    localJPanel2.add(this.scopeBox);
    localJPanel2.add(this.teamBox);
    localJPanel2.add(this.checkBox);
    localJPanel1.add(this.statBox);
    localJPanel1.add(this.opBox);
    localJPanel1.add(this.numField);
    localJPanel1.add(this.doButton);
    localJPanel3.add(localJPanel2);
    localJPanel3.add(localJPanel1);
    add(localJPanel3);
  }
  
  private int getNum()
  {
    int j = 99;
    int k = 1;
    int m = this.statBox.getSelectedIndex();
    if (this.opBox.getSelectedIndex() == 2) {
      if (m == 32)
      {
        j = 46;
        k = 15;
      }
      else if (m > 26)
      {
        j = 8;
      }
    }
    int i;
    try
    {
      i = new Integer(this.numField.getText()).intValue();
      if ((i < k) || (i > j)) {
        i = 0;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      i = 0;
    }
    return i;
  }
  
  private void setStat(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 == 32) {
      paramInt3 -= 15;
    } else if (paramInt1 > 26) {
      paramInt3--;
    }
    switch (paramInt1)
    {
    case 1: 
      this.stats.attack.setValue(paramInt2, paramInt3);
      break;
    case 2: 
      this.stats.defence.setValue(paramInt2, paramInt3);
      break;
    case 3: 
      this.stats.balance.setValue(paramInt2, paramInt3);
      break;
    case 4: 
      this.stats.stamina.setValue(paramInt2, paramInt3);
      break;
    case 5: 
      this.stats.speed.setValue(paramInt2, paramInt3);
      break;
    case 6: 
      this.stats.accel.setValue(paramInt2, paramInt3);
      break;
    case 7: 
      this.stats.response.setValue(paramInt2, paramInt3);
      break;
    case 8: 
      this.stats.agility.setValue(paramInt2, paramInt3);
      break;
    case 9: 
      this.stats.dribAcc.setValue(paramInt2, paramInt3);
      break;
    case 10: 
      this.stats.dribSpe.setValue(paramInt2, paramInt3);
      break;
    case 11: 
      this.stats.sPassAcc.setValue(paramInt2, paramInt3);
      break;
    case 12: 
      this.stats.sPassSpe.setValue(paramInt2, paramInt3);
      break;
    case 13: 
      this.stats.lPassAcc.setValue(paramInt2, paramInt3);
      break;
    case 14: 
      this.stats.lPassSpe.setValue(paramInt2, paramInt3);
      break;
    case 15: 
      this.stats.shotAcc.setValue(paramInt2, paramInt3);
      break;
    case 16: 
      this.stats.shotPow.setValue(paramInt2, paramInt3);
      break;
    case 17: 
      this.stats.shotTec.setValue(paramInt2, paramInt3);
      break;
    case 18: 
      this.stats.fk.setValue(paramInt2, paramInt3);
      break;
    case 19: 
      this.stats.curling.setValue(paramInt2, paramInt3);
      break;
    case 20: 
      this.stats.heading.setValue(paramInt2, paramInt3);
      break;
    case 21: 
      this.stats.jump.setValue(paramInt2, paramInt3);
      break;
    case 22: 
      this.stats.tech.setValue(paramInt2, paramInt3);
      break;
    case 23: 
      this.stats.aggress.setValue(paramInt2, paramInt3);
      break;
    case 24: 
      this.stats.mental.setValue(paramInt2, paramInt3);
      break;
    case 25: 
      this.stats.gkAbil.setValue(paramInt2, paramInt3);
      break;
    case 26: 
      this.stats.team.setValue(paramInt2, paramInt3);
      break;
    case 28: 
      this.stats.wfa.setValue(paramInt2, paramInt3);
      break;
    case 29: 
      this.stats.wff.setValue(paramInt2, paramInt3);
      break;
    case 30: 
      this.stats.consistency.setValue(paramInt2, paramInt3);
      break;
    case 31: 
      this.stats.condition.setValue(paramInt2, paramInt3);
      break;
    case 32: 
      this.stats.age.setValue(paramInt2, paramInt3);
    }
    this.stats.statEdited.setValue(paramInt2, 1);
  }
  
  private int getStat(int paramInt1, int paramInt2)
  {
    int i = 0;
    switch (paramInt1)
    {
    case 1: 
      i = this.stats.attack.getValue(paramInt2);
      break;
    case 2: 
      i = this.stats.defence.getValue(paramInt2);
      break;
    case 3: 
      i = this.stats.balance.getValue(paramInt2);
      break;
    case 4: 
      i = this.stats.stamina.getValue(paramInt2);
      break;
    case 5: 
      i = this.stats.speed.getValue(paramInt2);
      break;
    case 6: 
      i = this.stats.accel.getValue(paramInt2);
      break;
    case 7: 
      i = this.stats.response.getValue(paramInt2);
      break;
    case 8: 
      i = this.stats.agility.getValue(paramInt2);
      break;
    case 9: 
      i = this.stats.dribAcc.getValue(paramInt2);
      break;
    case 10: 
      i = this.stats.dribSpe.getValue(paramInt2);
      break;
    case 11: 
      i = this.stats.sPassAcc.getValue(paramInt2);
      break;
    case 12: 
      i = this.stats.sPassSpe.getValue(paramInt2);
      break;
    case 13: 
      i = this.stats.lPassAcc.getValue(paramInt2);
      break;
    case 14: 
      i = this.stats.lPassSpe.getValue(paramInt2);
      break;
    case 15: 
      i = this.stats.shotAcc.getValue(paramInt2);
      break;
    case 16: 
      i = this.stats.shotPow.getValue(paramInt2);
      break;
    case 17: 
      i = this.stats.shotTec.getValue(paramInt2);
      break;
    case 18: 
      i = this.stats.fk.getValue(paramInt2);
      break;
    case 19: 
      i = this.stats.curling.getValue(paramInt2);
      break;
    case 20: 
      i = this.stats.heading.getValue(paramInt2);
      break;
    case 21: 
      i = this.stats.jump.getValue(paramInt2);
      break;
    case 22: 
      i = this.stats.tech.getValue(paramInt2);
      break;
    case 23: 
      i = this.stats.aggress.getValue(paramInt2);
      break;
    case 24: 
      i = this.stats.mental.getValue(paramInt2);
      break;
    case 25: 
      i = this.stats.gkAbil.getValue(paramInt2);
      break;
    case 26: 
      i = this.stats.team.getValue(paramInt2);
      break;
    case 28: 
      i = this.stats.wfa.getValue(paramInt2);
      break;
    case 29: 
      i = this.stats.wff.getValue(paramInt2);
      break;
    case 30: 
      i = this.stats.consistency.getValue(paramInt2);
      break;
    case 31: 
      i = this.stats.condition.getValue(paramInt2);
      break;
    case 32: 
      i = this.stats.age.getValue(paramInt2);
    }
    if (paramInt1 == 32) {
      i += 15;
    } else if (paramInt1 > 26) {
      i++;
    }
    return i;
  }
  
  private boolean adj(int paramInt)
  {
    boolean bool = false;
    int i = this.scopeBox.getSelectedIndex();
    if (i == 0)
    {
      bool = true;
    }
    else
    {
      int j = this.stats.regPos.getValue(paramInt);
      if (i == 1) {
        i--;
      }
      if (j == i) {
        bool = true;
      }
    }
    return bool;
  }
  
  public void updateTeamBox(String[] paramArrayOfString)
  {
    String[] arrayOfString = new String[''];
    arrayOfString[0] = "None";
    System.arraycopy(paramArrayOfString, 0, arrayOfString, 1, 138);
    DefaultComboBoxModel localDefaultComboBoxModel = new DefaultComboBoxModel(arrayOfString);
    this.teamBox.setModel(localDefaultComboBoxModel);
  }
  
  private boolean adjTeam(int paramInt)
  {
    boolean bool = true;
    int i = this.teamBox.getSelectedIndex();
    if (i != 0)
    {
      int j = 667458 + (i - 1) * 64;
      for (int n = 0; (bool) && (n < 32); n++)
      {
        int k = j + n * 2;
        int m = this.of.toInt(this.of.data[(k + 1)]) << 8 | this.of.toInt(this.of.data[k]);
        if (m == paramInt) {
          bool = false;
        }
      }
    }
    return bool;
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/GlobalPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */