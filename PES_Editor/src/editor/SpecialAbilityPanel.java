package editor;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class SpecialAbilityPanel
  extends JPanel
{
  int player;
  JCheckBox[] checkBox;
  String[] ability = { "Dribbling", "Tactical dribble", "Positioning", "Reaction", "Playmaking", "Passing", "Scoring", "1-1 Scoring", "Post player", "Lines", "Middle shooting", "Side", "Centre", "Penalties", "1-Touch pass", "Outside", "Marking", "Sliding", "Covering", "D-Line control", "Penalty stopper", "1-On-1 stopper", "Long throw" };
  
  public SpecialAbilityPanel()
  {
    super(new GridLayout(0, 1));
    setBorder(BorderFactory.createTitledBorder("Special Ability"));
    this.checkBox = new JCheckBox[this.ability.length];
    for (int i = 0; i < this.ability.length; i++)
    {
      this.checkBox[i] = new JCheckBox(this.ability[i]);
      add(this.checkBox[i]);
    }
  }
  
  public void load(Stats paramStats, int paramInt)
  {
    this.player = paramInt;
    if (paramStats.drib.getValue(this.player) == 1) {
      this.checkBox[0].setSelected(true);
    } else {
      this.checkBox[0].setSelected(false);
    }
    if (paramStats.dribKeep.getValue(this.player) == 1) {
      this.checkBox[1].setSelected(true);
    } else {
      this.checkBox[1].setSelected(false);
    }
    if (paramStats.post.getValue(this.player) == 1) {
      this.checkBox[8].setSelected(true);
    } else {
      this.checkBox[8].setSelected(false);
    }
    if (paramStats.posit.getValue(this.player) == 1) {
      this.checkBox[2].setSelected(true);
    } else {
      this.checkBox[2].setSelected(false);
    }
    if (paramStats.offside.getValue(this.player) == 1) {
      this.checkBox[3].setSelected(true);
    } else {
      this.checkBox[3].setSelected(false);
    }
    if (paramStats.linePos.getValue(this.player) == 1) {
      this.checkBox[9].setSelected(true);
    } else {
      this.checkBox[9].setSelected(false);
    }
    if (paramStats.scorer.getValue(this.player) == 1) {
      this.checkBox[6].setSelected(true);
    } else {
      this.checkBox[6].setSelected(false);
    }
    if (paramStats.play.getValue(this.player) == 1) {
      this.checkBox[4].setSelected(true);
    } else {
      this.checkBox[4].setSelected(false);
    }
    if (paramStats.pass.getValue(this.player) == 1) {
      this.checkBox[5].setSelected(true);
    } else {
      this.checkBox[5].setSelected(false);
    }
    if (paramStats.midShot.getValue(this.player) == 1) {
      this.checkBox[10].setSelected(true);
    } else {
      this.checkBox[10].setSelected(false);
    }
    if (paramStats.pk.getValue(this.player) == 1) {
      this.checkBox[13].setSelected(true);
    } else {
      this.checkBox[13].setSelected(false);
    }
    if (paramStats.k11.getValue(this.player) == 1) {
      this.checkBox[7].setSelected(true);
    } else {
      this.checkBox[7].setSelected(false);
    }
    if (paramStats.longThrow.getValue(this.player) == 1) {
      this.checkBox[22].setSelected(true);
    } else {
      this.checkBox[22].setSelected(false);
    }
    if (paramStats.direct.getValue(this.player) == 1) {
      this.checkBox[14].setSelected(true);
    } else {
      this.checkBox[14].setSelected(false);
    }
    if (paramStats.side.getValue(this.player) == 1) {
      this.checkBox[11].setSelected(true);
    } else {
      this.checkBox[11].setSelected(false);
    }
    if (paramStats.centre.getValue(this.player) == 1) {
      this.checkBox[12].setSelected(true);
    } else {
      this.checkBox[12].setSelected(false);
    }
    if (paramStats.outside.getValue(this.player) == 1) {
      this.checkBox[15].setSelected(true);
    } else {
      this.checkBox[15].setSelected(false);
    }
    if (paramStats.man.getValue(this.player) == 1) {
      this.checkBox[16].setSelected(true);
    } else {
      this.checkBox[16].setSelected(false);
    }
    if (paramStats.dLine.getValue(this.player) == 1) {
      this.checkBox[19].setSelected(true);
    } else {
      this.checkBox[19].setSelected(false);
    }
    if (paramStats.slide.getValue(this.player) == 1) {
      this.checkBox[17].setSelected(true);
    } else {
      this.checkBox[17].setSelected(false);
    }
    if (paramStats.cover.getValue(this.player) == 1) {
      this.checkBox[18].setSelected(true);
    } else {
      this.checkBox[18].setSelected(false);
    }
    if (paramStats.keeperPK.getValue(this.player) == 1) {
      this.checkBox[20].setSelected(true);
    } else {
      this.checkBox[20].setSelected(false);
    }
    if (paramStats.keeper11.getValue(this.player) == 1) {
      this.checkBox[21].setSelected(true);
    } else {
      this.checkBox[21].setSelected(false);
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/SpecialAbilityPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */