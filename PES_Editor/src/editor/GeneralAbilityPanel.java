package editor;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GeneralAbilityPanel
  extends JPanel
{
  int player;
  JComboBox nationBox;
  JTextField ageField;
  JTextField heightField;
  JTextField weightField;
  JComboBox footBox;
  JComboBox wfaBox;
  JComboBox wffBox;
  JComboBox consBox;
  JComboBox condBox;
  JComboBox injuryBox;
  JComboBox fkBox;
  JComboBox pkBox;
  JComboBox dribBox;
  JComboBox dkBox;
  
  public GeneralAbilityPanel()
  {
    super(new GridLayout(0, 2));
    setBorder(BorderFactory.createTitledBorder("General"));
    this.nationBox = new JComboBox(Stat.nation);
    this.ageField = new JTextField(2);
    this.ageField.setInputVerifier(new VerifierAge());
    this.heightField = new JTextField(2);
    this.heightField.setInputVerifier(new VerifierHeight());
    this.weightField = new JTextField(2);
    this.weightField.setInputVerifier(new VerifierWeight());
    String[] arrayOfString1 = { "R foot / R side", "R foot / L side", "R foot / B side", "L foot / L side", "L foot / R side", "L foot / B side" };
    this.footBox = new JComboBox(arrayOfString1);
    this.wfaBox = new JComboBox(Stat.mod18);
    this.wffBox = new JComboBox(Stat.mod18);
    this.consBox = new JComboBox(Stat.mod18);
    this.condBox = new JComboBox(Stat.mod18);
    this.injuryBox = new JComboBox(Stat.modInjury);
    String[] arrayOfString2 = { "1", "2", "3", "4" };
    this.dribBox = new JComboBox(arrayOfString2);
    this.dkBox = new JComboBox(arrayOfString2);
    String[] arrayOfString3 = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    this.fkBox = new JComboBox(arrayOfString3);
    String[] arrayOfString4 = { "1", "2", "3", "4", "5" };
    this.pkBox = new JComboBox(arrayOfString4);
    add(new JLabel("Nationality"));
    add(this.nationBox);
    add(new JLabel("Age"));
    add(this.ageField);
    add(new JLabel("Height"));
    add(this.heightField);
    add(new JLabel("Weight"));
    add(this.weightField);
    add(new JLabel("Foot / Side"));
    add(this.footBox);
    add(new JLabel("Weak Foot Accuracy"));
    add(this.wfaBox);
    add(new JLabel("Weak Foot Frequency"));
    add(this.wffBox);
    add(new JLabel("Consistency"));
    add(this.consBox);
    add(new JLabel("Condition"));
    add(this.condBox);
    add(new JLabel("Injury Tolerancy"));
    add(this.injuryBox);
    add(new JLabel("Dribble Style"));
    add(this.dribBox);
    add(new JLabel("Free Kick Style"));
    add(this.fkBox);
    add(new JLabel("Penalty Style"));
    add(this.pkBox);
    add(new JLabel("Drop Kick Style"));
    add(this.dkBox);
  }
  
  public void load(Stats paramStats, int paramInt)
  {
    this.player = paramInt;
    this.nationBox.setSelectedItem(paramStats.nation.getString(this.player));
    this.ageField.setText(paramStats.age.getString(this.player));
    this.heightField.setText(paramStats.height.getString(this.player));
    this.weightField.setText(paramStats.weight.getString(this.player));
    this.wfaBox.setSelectedItem(paramStats.wfa.getString(this.player));
    this.wffBox.setSelectedItem(paramStats.wff.getString(this.player));
    this.consBox.setSelectedItem(paramStats.consistency.getString(this.player));
    this.condBox.setSelectedItem(paramStats.condition.getString(this.player));
    this.injuryBox.setSelectedItem(paramStats.injury.getString(this.player));
    this.fkBox.setSelectedItem(paramStats.freekick.getString(this.player));
    this.pkBox.setSelectedItem(paramStats.pkStyle.getString(this.player));
    this.dribBox.setSelectedItem(paramStats.dribSty.getString(this.player));
    this.dkBox.setSelectedItem(paramStats.dkSty.getString(this.player));
    int i = paramStats.foot.getValue(this.player);
    int j = paramStats.favSide.getValue(this.player);
    int k = i * 3 + j;
    this.footBox.setSelectedIndex(k);
  }
  
  class VerifierHeight
    extends InputVerifier
  {
    VerifierHeight() {}
    
    public boolean verify(JComponent paramJComponent)
    {
      boolean bool = false;
      JTextField localJTextField = (JTextField)paramJComponent;
      try
      {
        int i = new Integer(localJTextField.getText()).intValue();
        if ((i >= 148) && (i <= 211)) {
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
  
  class VerifierWeight
    extends InputVerifier
  {
    VerifierWeight() {}
    
    public boolean verify(JComponent paramJComponent)
    {
      boolean bool = false;
      JTextField localJTextField = (JTextField)paramJComponent;
      try
      {
        int i = new Integer(localJTextField.getText()).intValue();
        if ((i >= 1) && (i < 128)) {
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
  
  class VerifierAge
    extends InputVerifier
  {
    VerifierAge() {}
    
    public boolean verify(JComponent paramJComponent)
    {
      boolean bool = false;
      JTextField localJTextField = (JTextField)paramJComponent;
      try
      {
        int i = new Integer(localJTextField.getText()).intValue();
        if ((i >= 15) && (i <= 46)) {
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


/* Location:              PESFan Editor 5.08.jar!/editor/GeneralAbilityPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */