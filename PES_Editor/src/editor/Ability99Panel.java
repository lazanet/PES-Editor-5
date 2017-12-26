package editor;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class Ability99Panel
  extends JPanel
  implements ActionListener, CaretListener, KeyListener
{
  int player;
  JTextField[] field;
  String[] ability99 = { "Attack", "Defence", "Balance", "Stamina", "Speed", "Acceleration", "Response", "Agility", "Dribble Accuracy", "Dribble Speed", "Short Pass Accuracy", "Short Pass Speed", "Long Pass Accuracy", "Long Pass Speed", "Shot Accuracy", "Shot Power", "Shot Technique", "Free Kick", "Curling", "Heading", "Jump", "Technique", "Aggression", "Mentality", "GK Ability", "Team Work" };
  String[] initVal;
  int f;
  
  public Ability99Panel()
  {
    super(new GridBagLayout());
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.ipadx = 2;
    setBorder(BorderFactory.createTitledBorder("1-99 Ability"));
    this.field = new JTextField[this.ability99.length];
    this.initVal = new String[this.ability99.length];
    Verifier99 localVerifier99 = new Verifier99();
    for (int i = 0; i < this.ability99.length; i++)
    {
      this.field[i] = new JTextField(2);
      localGridBagConstraints.anchor = 13;
      localGridBagConstraints.gridx = 0;
      localGridBagConstraints.gridy = i;
      add(new JLabel(this.ability99[i]), localGridBagConstraints);
      localGridBagConstraints.anchor = 10;
      localGridBagConstraints.gridx = 1;
      localGridBagConstraints.gridy = i;
      add(this.field[i], localGridBagConstraints);
      this.field[i].setActionCommand(String.valueOf(i));
      this.field[i].addActionListener(this);
      this.field[i].setInputVerifier(localVerifier99);
      this.field[i].addCaretListener(this);
      this.field[i].addKeyListener(this);
    }
  }
  
  public void load(Stats paramStats, int paramInt)
  {
    this.player = paramInt;
    this.initVal[0] = paramStats.attack.getString(this.player);
    this.initVal[1] = paramStats.defence.getString(this.player);
    this.initVal[2] = paramStats.balance.getString(this.player);
    this.initVal[3] = paramStats.stamina.getString(this.player);
    this.initVal[4] = paramStats.speed.getString(this.player);
    this.initVal[5] = paramStats.accel.getString(this.player);
    this.initVal[6] = paramStats.response.getString(this.player);
    this.initVal[7] = paramStats.agility.getString(this.player);
    this.initVal[8] = paramStats.dribAcc.getString(this.player);
    this.initVal[9] = paramStats.dribSpe.getString(this.player);
    this.initVal[10] = paramStats.sPassAcc.getString(this.player);
    this.initVal[11] = paramStats.sPassSpe.getString(this.player);
    this.initVal[12] = paramStats.lPassAcc.getString(this.player);
    this.initVal[13] = paramStats.lPassSpe.getString(this.player);
    this.initVal[14] = paramStats.shotAcc.getString(this.player);
    this.initVal[15] = paramStats.shotPow.getString(this.player);
    this.initVal[16] = paramStats.shotTec.getString(this.player);
    this.initVal[17] = paramStats.fk.getString(this.player);
    this.initVal[18] = paramStats.curling.getString(this.player);
    this.initVal[19] = paramStats.heading.getString(this.player);
    this.initVal[20] = paramStats.jump.getString(this.player);
    this.initVal[21] = paramStats.tech.getString(this.player);
    this.initVal[22] = paramStats.aggress.getString(this.player);
    this.initVal[23] = paramStats.mental.getString(this.player);
    this.initVal[24] = paramStats.gkAbil.getString(this.player);
    this.initVal[25] = paramStats.team.getString(this.player);
    this.field[0].setText(paramStats.attack.getString(this.player));
    this.field[1].setText(paramStats.defence.getString(this.player));
    this.field[2].setText(paramStats.balance.getString(this.player));
    this.field[3].setText(paramStats.stamina.getString(this.player));
    this.field[4].setText(paramStats.speed.getString(this.player));
    this.field[5].setText(paramStats.accel.getString(this.player));
    this.field[6].setText(paramStats.response.getString(this.player));
    this.field[7].setText(paramStats.agility.getString(this.player));
    this.field[8].setText(paramStats.dribAcc.getString(this.player));
    this.field[9].setText(paramStats.dribSpe.getString(this.player));
    this.field[10].setText(paramStats.sPassAcc.getString(this.player));
    this.field[11].setText(paramStats.sPassSpe.getString(this.player));
    this.field[12].setText(paramStats.lPassAcc.getString(this.player));
    this.field[13].setText(paramStats.lPassSpe.getString(this.player));
    this.field[14].setText(paramStats.shotAcc.getString(this.player));
    this.field[15].setText(paramStats.shotPow.getString(this.player));
    this.field[16].setText(paramStats.shotTec.getString(this.player));
    this.field[17].setText(paramStats.fk.getString(this.player));
    this.field[18].setText(paramStats.curling.getString(this.player));
    this.field[19].setText(paramStats.heading.getString(this.player));
    this.field[20].setText(paramStats.jump.getString(this.player));
    this.field[21].setText(paramStats.tech.getString(this.player));
    this.field[22].setText(paramStats.aggress.getString(this.player));
    this.field[23].setText(paramStats.mental.getString(this.player));
    this.field[24].setText(paramStats.gkAbil.getString(this.player));
    this.field[25].setText(paramStats.team.getString(this.player));
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    this.f = new Integer(paramActionEvent.getActionCommand()).intValue();
    try
    {
      int i = new Integer(((JTextField)paramActionEvent.getSource()).getText()).intValue();
      if ((i > 0) && (i < 100))
      {
        if (this.f < this.ability99.length - 1)
        {
          this.field[(this.f + 1)].requestFocus();
          this.field[(this.f + 1)].selectAll();
        }
        else
        {
          this.field[0].requestFocus();
          this.field[0].selectAll();
        }
      }
      else
      {
        this.field[this.f].setText(this.initVal[this.f]);
        this.field[this.f].selectAll();
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.field[this.f].setText(this.initVal[this.f]);
      this.field[this.f].selectAll();
    }
  }
  
  public void caretUpdate(CaretEvent paramCaretEvent)
  {
    JTextField localJTextField = (JTextField)paramCaretEvent.getSource();
    String str = localJTextField.getText();
    if (str != "") {
      try
      {
        int i = new Integer(((JTextField)paramCaretEvent.getSource()).getText()).intValue();
        if ((i >= 80) && (i < 90)) {
          localJTextField.setBackground(Color.yellow);
        } else if ((i >= 90) && (i < 95)) {
          localJTextField.setBackground(Color.orange);
        } else if ((i >= 80) && (i < 100)) {
          localJTextField.setBackground(Color.red);
        } else {
          localJTextField.setBackground(Color.white);
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        localJTextField.setBackground(Color.white);
      }
    } else {
      localJTextField.setBackground(Color.white);
    }
  }
  
  public void keyTyped(KeyEvent paramKeyEvent) {}
  
  public void keyPressed(KeyEvent paramKeyEvent)
  {
    JTextField localJTextField = (JTextField)paramKeyEvent.getSource();
    int i = new Integer(localJTextField.getText()).intValue();
    int j = paramKeyEvent.getKeyCode();
    if ((j == 38) && (i < 99))
    {
      i++;
      localJTextField.setText(String.valueOf(i));
    }
    if ((j == 40) && (i > 1))
    {
      i--;
      localJTextField.setText(String.valueOf(i));
    }
  }
  
  public void keyReleased(KeyEvent paramKeyEvent) {}
  
  class Verifier99
    extends InputVerifier
  {
    Verifier99() {}
    
    public boolean verify(JComponent paramJComponent)
    {
      boolean bool = false;
      JTextField localJTextField = (JTextField)paramJComponent;
      try
      {
        int i = new Integer(localJTextField.getText()).intValue();
        if ((i > 0) && (i < 100)) {
          bool = true;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        bool = false;
      }
      return bool;
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/Ability99Panel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */