package editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayerDialog
  extends JDialog
{
  OptionFile of;
  int index;
  Player player;
  GeneralAbilityPanel genPanel;
  PositionPanel posPanel;
  Ability99Panel abiPanel;
  SpecialAbilityPanel spePanel;
  JButton acceptButton;
  JButton cancelButton;
  JButton importButton;
  JButton psdButton;
  Stats stats;
  PlayerImportDialog plImpDia;
  PlayerDialog thisForm;
  
  public PlayerDialog(Frame owner, OptionFile paramOptionFile, PlayerImportDialog paramPlayerImportDialog, Stats paramStats)
  {
    super(owner, "Edit Player", true);
    thisForm = this;
    JPanel panel = new JPanel();
    JPanel lPanel = new JPanel(new BorderLayout());
    JPanel bPanel = new JPanel();
    this.acceptButton = new JButton("Accept");
    this.acceptButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (PlayerDialog.this.check())
        {
          PlayerDialog.this.updateStats();
          PlayerDialog.this.setVisible(false);
        }
      }
    });
    CancelButton localCancelButton = new CancelButton(this);
    this.importButton = new JButton("Import (OF2)");
    this.importButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        PlayerDialog.this.plImpDia.show(PlayerDialog.this.index);
        PlayerDialog.this.setVisible(false);
      }
    });
    
    psdButton = new JButton("Paste PSD Stat");
		psdButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent a)
			{
				new PSDStatPaste(owner,thisForm);
			}
		});
    this.of = paramOptionFile;
    this.plImpDia = paramPlayerImportDialog;
    this.stats = paramStats;
    this.genPanel = new GeneralAbilityPanel();
    this.posPanel = new PositionPanel();
    this.abiPanel = new Ability99Panel();
    this.spePanel = new SpecialAbilityPanel();
    bPanel.add(this.acceptButton);
    bPanel.add(localCancelButton);
    bPanel.add(psdButton);
    bPanel.add(this.importButton);
    lPanel.add(this.genPanel, "North");
    lPanel.add(this.posPanel, "Center");
    lPanel.add(bPanel, "South");
    panel.add(lPanel);
    panel.add(this.abiPanel);
    panel.add(this.spePanel);
    getContentPane().add(panel);
    pack();
    setResizable(false);
  }
  
  public void show(Player paramPlayer)
  {
    this.index = paramPlayer.index;
    this.player = paramPlayer;
    setTitle("Edit Player - " + String.valueOf(this.index) + " - " + paramPlayer.name);
    if (this.plImpDia.of2Open) {
      this.importButton.setVisible(true);
    } else {
      this.importButton.setVisible(false);
    }
    this.genPanel.load(this.stats, this.index);
    this.posPanel.load(this.of, this.stats, this.index);
    this.abiPanel.load(this.stats, this.index);
    this.spePanel.load(this.stats, this.index);
    setVisible(true);
  }
  private void error(String s)
	{
		JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
	}
 private boolean check()
	{
		int v;
		for (int i = 0; i < abiPanel.ability99.length; i++)
		{
			try
			{
				v = new Integer(abiPanel.field[i].getText()).intValue();
				if (v < 1 || v > 99)
				{
					error("Stat number "+i+" out of range!");
					return false;
				}
			}
			catch (NumberFormatException nfe)
			{
				error("Stat number "+i+" is not a number!");
				return false;
			}
		}
		try
		{
			v = new Integer(genPanel.heightField.getText()).intValue();
			if (v < 148 || v > 211)
			{
				error("Height out of range!");
				return false;
			}
		}
		catch (NumberFormatException nfe)
		{
			error("Height is not a number!");
			return false;
		}
		try
		{
			v = new Integer(genPanel.weightField.getText()).intValue();
			if (v < 1 || v > 127)
			{
				error("Weight is out of range!");
				return false;
			}
		}
		catch (NumberFormatException nfe)
		{
			error("Weight is not a number!");
			return false;
		}
		try
		{
			v = new Integer(genPanel.ageField.getText()).intValue();
			if (v < 15 || v > 46)
			{
				error("Age is out of range!");
				return false;
			}
		}
		catch (NumberFormatException nfe)
		{
			error("Age is not a number");
			return false;
		}
		try
		{
			String test = (String)posPanel.regBox.getSelectedItem();
			int count=0;
			for (int i = 0; i < posPanel.checkBox.length; i++)
				if (i != 1)
					count+=(posPanel.checkBox[i].isSelected())?1:0;
			if (count==0)
				throw new Exception("Position");
		}
		catch (Exception ex)
		{
			error("Position is not selected!");
			return false;
		}
		return true;
	}
  
  private void updateStats()
  {
    this.stats.gk.setValue(this.index, boToInt(this.posPanel.checkBox[0].isSelected()));
    this.stats.cbwS.setValue(this.index, boToInt(this.posPanel.checkBox[2].isSelected()));
    this.stats.cbt.setValue(this.index, boToInt(this.posPanel.checkBox[3].isSelected()));
    this.stats.sb.setValue(this.index, boToInt(this.posPanel.checkBox[4].isSelected()));
    this.stats.dm.setValue(this.index, boToInt(this.posPanel.checkBox[5].isSelected()));
    this.stats.wb.setValue(this.index, boToInt(this.posPanel.checkBox[6].isSelected()));
    this.stats.cm.setValue(this.index, boToInt(this.posPanel.checkBox[7].isSelected()));
    this.stats.sm.setValue(this.index, boToInt(this.posPanel.checkBox[8].isSelected()));
    this.stats.om.setValue(this.index, boToInt(this.posPanel.checkBox[9].isSelected()));
    this.stats.cf.setValue(this.index, boToInt(this.posPanel.checkBox[12].isSelected()));
    this.stats.wg.setValue(this.index, boToInt(this.posPanel.checkBox[10].isSelected()));
    this.stats.ss.setValue(this.index, boToInt(this.posPanel.checkBox[11].isSelected()));
    int i = 0;
    for (int j = 0; j < this.posPanel.position.length; j++) {
      if (((String)this.posPanel.regBox.getSelectedItem()).equals(this.posPanel.position[j])) {
        i = j;
      }
    }
    int j;
    this.stats.regPos.setValue(this.index, i);
    this.stats.height.setValue(this.index, this.genPanel.heightField.getText());
    j = this.genPanel.footBox.getSelectedIndex();
    int k = j / 3;
    int m = j - k * 3;
    this.stats.foot.setValue(this.index, k);
    this.stats.favSide.setValue(this.index, m);
    this.stats.wfa.setValue(this.index, (String)this.genPanel.wfaBox.getSelectedItem());
    this.stats.wff.setValue(this.index, (String)this.genPanel.wffBox.getSelectedItem());
    this.stats.attack.setValue(this.index, this.abiPanel.field[0].getText());
    this.stats.defence.setValue(this.index, this.abiPanel.field[1].getText());
    this.stats.balance.setValue(this.index, this.abiPanel.field[2].getText());
    this.stats.stamina.setValue(this.index, this.abiPanel.field[3].getText());
    this.stats.speed.setValue(this.index, this.abiPanel.field[4].getText());
    this.stats.accel.setValue(this.index, this.abiPanel.field[5].getText());
    this.stats.response.setValue(this.index, this.abiPanel.field[6].getText());
    this.stats.agility.setValue(this.index, this.abiPanel.field[7].getText());
    this.stats.dribAcc.setValue(this.index, this.abiPanel.field[8].getText());
    this.stats.dribSpe.setValue(this.index, this.abiPanel.field[9].getText());
    this.stats.sPassAcc.setValue(this.index, this.abiPanel.field[10].getText());
    this.stats.sPassSpe.setValue(this.index, this.abiPanel.field[11].getText());
    this.stats.lPassAcc.setValue(this.index, this.abiPanel.field[12].getText());
    this.stats.lPassSpe.setValue(this.index, this.abiPanel.field[13].getText());
    this.stats.shotAcc.setValue(this.index, this.abiPanel.field[14].getText());
    this.stats.shotPow.setValue(this.index, this.abiPanel.field[15].getText());
    this.stats.shotTec.setValue(this.index, this.abiPanel.field[16].getText());
    this.stats.fk.setValue(this.index, this.abiPanel.field[17].getText());
    this.stats.curling.setValue(this.index, this.abiPanel.field[18].getText());
    this.stats.heading.setValue(this.index, this.abiPanel.field[19].getText());
    this.stats.jump.setValue(this.index, this.abiPanel.field[20].getText());
    this.stats.tech.setValue(this.index, this.abiPanel.field[21].getText());
    this.stats.aggress.setValue(this.index, this.abiPanel.field[22].getText());
    this.stats.mental.setValue(this.index, this.abiPanel.field[23].getText());
    this.stats.consistency.setValue(this.index, (String)this.genPanel.consBox.getSelectedItem());
    this.stats.gkAbil.setValue(this.index, this.abiPanel.field[24].getText());
    this.stats.team.setValue(this.index, this.abiPanel.field[25].getText());
    this.stats.condition.setValue(this.index, (String)this.genPanel.condBox.getSelectedItem());
    this.stats.drib.setValue(this.index, boToInt(this.spePanel.checkBox[0].isSelected()));
    this.stats.dribKeep.setValue(this.index, boToInt(this.spePanel.checkBox[1].isSelected()));
    this.stats.post.setValue(this.index, boToInt(this.spePanel.checkBox[8].isSelected()));
    this.stats.posit.setValue(this.index, boToInt(this.spePanel.checkBox[2].isSelected()));
    this.stats.offside.setValue(this.index, boToInt(this.spePanel.checkBox[3].isSelected()));
    this.stats.linePos.setValue(this.index, boToInt(this.spePanel.checkBox[9].isSelected()));
    this.stats.scorer.setValue(this.index, boToInt(this.spePanel.checkBox[6].isSelected()));
    this.stats.play.setValue(this.index, boToInt(this.spePanel.checkBox[4].isSelected()));
    this.stats.pass.setValue(this.index, boToInt(this.spePanel.checkBox[5].isSelected()));
    this.stats.midShot.setValue(this.index, boToInt(this.spePanel.checkBox[10].isSelected()));
    this.stats.pk.setValue(this.index, boToInt(this.spePanel.checkBox[13].isSelected()));
    this.stats.k11.setValue(this.index, boToInt(this.spePanel.checkBox[7].isSelected()));
    this.stats.longThrow.setValue(this.index, boToInt(this.spePanel.checkBox[22].isSelected()));
    this.stats.direct.setValue(this.index, boToInt(this.spePanel.checkBox[14].isSelected()));
    this.stats.side.setValue(this.index, boToInt(this.spePanel.checkBox[11].isSelected()));
    this.stats.centre.setValue(this.index, boToInt(this.spePanel.checkBox[12].isSelected()));
    this.stats.outside.setValue(this.index, boToInt(this.spePanel.checkBox[15].isSelected()));
    this.stats.man.setValue(this.index, boToInt(this.spePanel.checkBox[16].isSelected()));
    this.stats.dLine.setValue(this.index, boToInt(this.spePanel.checkBox[19].isSelected()));
    this.stats.slide.setValue(this.index, boToInt(this.spePanel.checkBox[17].isSelected()));
    this.stats.cover.setValue(this.index, boToInt(this.spePanel.checkBox[18].isSelected()));
    this.stats.keeperPK.setValue(this.index, boToInt(this.spePanel.checkBox[20].isSelected()));
    this.stats.keeper11.setValue(this.index, boToInt(this.spePanel.checkBox[21].isSelected()));
    this.stats.injury.setValue(this.index, (String)this.genPanel.injuryBox.getSelectedItem());
    this.stats.freekick.setValue(this.index, (String)this.genPanel.fkBox.getSelectedItem());
    this.stats.pkStyle.setValue(this.index, (String)this.genPanel.pkBox.getSelectedItem());
    this.stats.age.setValue(this.index, this.genPanel.ageField.getText());
    this.stats.weight.setValue(this.index, this.genPanel.weightField.getText());
    this.stats.nation.setValue(this.index, (String)this.genPanel.nationBox.getSelectedItem());
    this.stats.dribSty.setValue(this.index, (String)this.genPanel.dribBox.getSelectedItem());
    this.stats.dkSty.setValue(this.index, (String)this.genPanel.dkBox.getSelectedItem());
    this.stats.statEdited.setValue(this.index, 1);
  }
  
  private int boToInt(boolean paramBoolean)
  {
    int i = 0;
    if (paramBoolean) {
      i = 1;
    }
    return i;
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/PlayerDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
