package editor;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

public class InfoPanel
  extends JScrollPane
{
  OptionFile of;
  Stats stats;
  JEditorPane ta;
  SelectByTeam selector;
  boolean listSquads;
  SimpleAttributeSet attr;
  Document doc;
  
  public InfoPanel(Stats paramStats, SelectByTeam paramSelectByTeam, OptionFile paramOptionFile)
  {
    super(22, 31);
    this.stats = paramStats;
    this.selector = paramSelectByTeam;
    this.of = paramOptionFile;
    init();
    this.listSquads = true;
  }
  
  public InfoPanel(Stats paramStats, OptionFile paramOptionFile)
  {
    super(22, 31);
    this.stats = paramStats;
    this.of = paramOptionFile;
    init();
    this.listSquads = false;
  }
  
  private void init()
  {
    this.ta = new JEditorPane();
    this.ta.setEditable(false);
    setViewportView(this.ta);
    StyledEditorKit localStyledEditorKit = new StyledEditorKit();
    this.ta.setEditorKit(localStyledEditorKit);
    this.ta.setBackground(Color.black);
    this.attr = new SimpleAttributeSet(localStyledEditorKit.getInputAttributes());
    this.doc = this.ta.getDocument();
    setPreferredSize(new Dimension(290, 493));
    StyleConstants.setFontFamily(this.attr, "SansSerif");
  }
  
  public void refresh(int paramInt1, int paramInt2)
  {
    this.ta.setText("");
    if ((paramInt1 > 0) || (paramInt2 > 0))
    {
      try
      {
        if (paramInt2 > 0)
        {
          StyleConstants.setFontSize(this.attr, 10);
          insertName(paramInt1, paramInt2);
          this.doc.insertString(this.doc.getLength(), "\n", this.attr);
          insertRole(paramInt1, paramInt2);
          this.doc.insertString(this.doc.getLength(), "\n", this.attr);
          insertPhys(paramInt1, paramInt2);
          insertStats(paramInt1, paramInt2);
          this.doc.insertString(this.doc.getLength(), "\n", this.attr);
          insertAbilities(paramInt1, paramInt2);
        }
        else
        {
          StyleConstants.setFontSize(this.attr, 12);
          insertName(paramInt1, paramInt2);
          this.doc.insertString(this.doc.getLength(), "\n\n", this.attr);
          insertAgeNat(paramInt1, paramInt2);
          this.doc.insertString(this.doc.getLength(), "\n", this.attr);
          insertPhys(paramInt1, paramInt2);
          this.doc.insertString(this.doc.getLength(), "\n", this.attr);
          insertRole(paramInt1, paramInt2);
          this.doc.insertString(this.doc.getLength(), "\n\n", this.attr);
          insertSquads(paramInt1);
        }
      }
      catch (BadLocationException localBadLocationException) {}
      this.ta.setCaretPosition(0);
    }
  }
  
  private void insertStats(int paramInt1, int paramInt2)
    throws BadLocationException
  {
    StyleConstants.setForeground(this.attr, Color.white);
    insertStat(this.stats.wfa, paramInt1, paramInt2);
    insertStat(this.stats.wff, paramInt1, paramInt2);
    insertStat(this.stats.attack, paramInt1, paramInt2);
    insertStat(this.stats.defence, paramInt1, paramInt2);
    insertStat(this.stats.balance, paramInt1, paramInt2);
    insertStat(this.stats.stamina, paramInt1, paramInt2);
    insertStat(this.stats.speed, paramInt1, paramInt2);
    insertStat(this.stats.accel, paramInt1, paramInt2);
    insertStat(this.stats.response, paramInt1, paramInt2);
    insertStat(this.stats.agility, paramInt1, paramInt2);
    insertStat(this.stats.dribAcc, paramInt1, paramInt2);
    insertStat(this.stats.dribSpe, paramInt1, paramInt2);
    insertStat(this.stats.sPassAcc, paramInt1, paramInt2);
    insertStat(this.stats.sPassSpe, paramInt1, paramInt2);
    insertStat(this.stats.lPassAcc, paramInt1, paramInt2);
    insertStat(this.stats.lPassSpe, paramInt1, paramInt2);
    insertStat(this.stats.shotAcc, paramInt1, paramInt2);
    insertStat(this.stats.shotPow, paramInt1, paramInt2);
    insertStat(this.stats.shotTec, paramInt1, paramInt2);
    insertStat(this.stats.fk, paramInt1, paramInt2);
    insertStat(this.stats.curling, paramInt1, paramInt2);
    insertStat(this.stats.heading, paramInt1, paramInt2);
    insertStat(this.stats.jump, paramInt1, paramInt2);
    insertStat(this.stats.tech, paramInt1, paramInt2);
    insertStat(this.stats.aggress, paramInt1, paramInt2);
    insertStat(this.stats.mental, paramInt1, paramInt2);
    insertStat(this.stats.gkAbil, paramInt1, paramInt2);
    insertStat(this.stats.team, paramInt1, paramInt2);
    insertStat(this.stats.consistency, paramInt1, paramInt2);
    insertStat(this.stats.condition, paramInt1, paramInt2);
  }
  
  private void insertRole(int paramInt1, int paramInt2)
    throws BadLocationException
  {
    String str = "";
    if ((paramInt1 > 0) && (this.stats.gk.getValue(paramInt1) == 1)) {
      str = "GK";
    }
    if ((paramInt2 > 0) && (this.stats.gk.getValue(paramInt2) == 1)) {
      str = str + "\t\tGK";
    }
    str = str + "\n";
    if ((paramInt1 > 0) && (this.stats.cbwS.getValue(paramInt1) == 1)) {
      str = str + "CWP  ";
    }
    if ((paramInt1 > 0) && (this.stats.cbt.getValue(paramInt1) == 1)) {
      str = str + "CB  ";
    }
    if (paramInt2 > 0) {
      str = str + "\t\t";
    }
    if ((paramInt2 > 0) && (this.stats.cbwS.getValue(paramInt2) == 1)) {
      str = str + "CWP  ";
    }
    if ((paramInt2 > 0) && (this.stats.cbt.getValue(paramInt2) == 1)) {
      str = str + "CB  ";
    }
    str = str + "\n";
    if ((paramInt1 > 0) && (this.stats.sb.getValue(paramInt1) == 1)) {
      str = str + "SB  ";
    }
    if ((paramInt1 > 0) && (this.stats.wb.getValue(paramInt1) == 1)) {
      str = str + "WB  ";
    }
    if ((paramInt1 > 0) && (this.stats.dm.getValue(paramInt1) == 1)) {
      str = str + "DM  ";
    }
    if (paramInt2 > 0) {
      str = str + "\t\t";
    }
    if ((paramInt2 > 0) && (this.stats.sb.getValue(paramInt2) == 1)) {
      str = str + "SB  ";
    }
    if ((paramInt2 > 0) && (this.stats.wb.getValue(paramInt2) == 1)) {
      str = str + "WB  ";
    }
    if ((paramInt2 > 0) && (this.stats.dm.getValue(paramInt2) == 1)) {
      str = str + "DM  ";
    }
    str = str + "\n";
    if ((paramInt1 > 0) && (this.stats.cm.getValue(paramInt1) == 1)) {
      str = str + "CM  ";
    }
    if ((paramInt1 > 0) && (this.stats.sm.getValue(paramInt1) == 1)) {
      str = str + "SM  ";
    }
    if ((paramInt1 > 0) && (this.stats.om.getValue(paramInt1) == 1)) {
      str = str + "AM  ";
    }
    if (paramInt2 > 0) {
      str = str + "\t\t";
    }
    if ((paramInt2 > 0) && (this.stats.cm.getValue(paramInt2) == 1)) {
      str = str + "CM  ";
    }
    if ((paramInt2 > 0) && (this.stats.sm.getValue(paramInt2) == 1)) {
      str = str + "SM  ";
    }
    if ((paramInt2 > 0) && (this.stats.om.getValue(paramInt2) == 1)) {
      str = str + "AM  ";
    }
    str = str + "\n";
    if ((paramInt1 > 0) && (this.stats.ss.getValue(paramInt1) == 1)) {
      str = str + "SS  ";
    }
    if ((paramInt1 > 0) && (this.stats.cf.getValue(paramInt1) == 1)) {
      str = str + "CF  ";
    }
    if ((paramInt1 > 0) && (this.stats.wg.getValue(paramInt1) == 1)) {
      str = str + "WG  ";
    }
    if (paramInt2 > 0) {
      str = str + "\t\t";
    }
    if ((paramInt2 > 0) && (this.stats.ss.getValue(paramInt2) == 1)) {
      str = str + "SS  ";
    }
    if ((paramInt2 > 0) && (this.stats.cf.getValue(paramInt2) == 1)) {
      str = str + "CF  ";
    }
    if ((paramInt2 > 0) && (this.stats.wg.getValue(paramInt2) == 1)) {
      str = str + "WG  ";
    }
    if (paramInt2 > 0) {
      StyleConstants.setFontSize(this.attr, 9);
    }
    this.doc.insertString(this.doc.getLength(), str, this.attr);
    if (paramInt2 > 0) {
      StyleConstants.setFontSize(this.attr, 10);
    }
  }
  
  private void insertAbilities(int paramInt1, int paramInt2)
    throws BadLocationException
  {
    insertAbility(this.stats.drib, paramInt1, paramInt2, Color.green);
    insertAbility(this.stats.dribKeep, paramInt1, paramInt2, Color.red);
    insertAbility(this.stats.posit, paramInt1, paramInt2, Color.green);
    insertAbility(this.stats.offside, paramInt1, paramInt2, Color.red);
    insertAbility(this.stats.play, paramInt1, paramInt2, Color.green);
    insertAbility(this.stats.pass, paramInt1, paramInt2, Color.red);
    insertAbility(this.stats.scorer, paramInt1, paramInt2, Color.green);
    insertAbility(this.stats.k11, paramInt1, paramInt2, Color.red);
    insertAbility(this.stats.post, paramInt1, paramInt2, Color.green);
    insertAbility(this.stats.linePos, paramInt1, paramInt2, Color.red);
    insertAbility(this.stats.midShot, paramInt1, paramInt2, Color.green);
    insertAbility(this.stats.side, paramInt1, paramInt2, Color.red);
    insertAbility(this.stats.centre, paramInt1, paramInt2, Color.green);
    insertAbility(this.stats.pk, paramInt1, paramInt2, Color.red);
    insertAbility(this.stats.direct, paramInt1, paramInt2, Color.green);
    insertAbility(this.stats.outside, paramInt1, paramInt2, Color.red);
    insertAbility(this.stats.man, paramInt1, paramInt2, Color.green);
    insertAbility(this.stats.slide, paramInt1, paramInt2, Color.red);
    insertAbility(this.stats.cover, paramInt1, paramInt2, Color.green);
    insertAbility(this.stats.dLine, paramInt1, paramInt2, Color.red);
    insertAbility(this.stats.keeperPK, paramInt1, paramInt2, Color.green);
    insertAbility(this.stats.keeper11, paramInt1, paramInt2, Color.red);
    insertAbility(this.stats.longThrow, paramInt1, paramInt2, Color.green);
    StyleConstants.setForeground(this.attr, Color.white);
  }
  
  private void insertSquads(int paramInt)
    throws BadLocationException
  {
    if (this.listSquads)
    {
      StyleConstants.setForeground(this.attr, Color.white);
      this.doc.insertString(this.doc.getLength(), "Squads:", this.attr);
      int k = 664052;
      do
      {
        k += 2;
        int i = this.of.toInt(this.of.data[(k + 1)]) << 8 | this.of.toInt(this.of.data[k]);
        if (i == paramInt)
        {
          int j;
          if (k < 667394) {
            j = (k - 664054) / 46;
          } else {
            j = (k - 667394) / 64 + 73;
          }
          this.doc.insertString(this.doc.getLength(), "\n" + this.selector.teamBox.getModel().getElementAt(j), this.attr);
        }
      } while (k < 676608);
    }
  }
  
  private void insertName(int paramInt1, int paramInt2)
    throws BadLocationException
  {
    StyleConstants.setForeground(this.attr, Color.white);
    if (paramInt1 > 0) {
      this.doc.insertString(this.doc.getLength(), new Player(this.of, paramInt1, 0).name, this.attr);
    }
    if (paramInt2 > 0) {
      this.doc.insertString(this.doc.getLength(), "\t" + new Player(this.of, paramInt2, 0).name, this.attr);
    }
  }
  
  private void insertAgeNat(int paramInt1, int paramInt2)
    throws BadLocationException
  {
    StyleConstants.setForeground(this.attr, Color.white);
    if (paramInt1 > 0) {
      this.doc.insertString(this.doc.getLength(), this.stats.nation.getString(paramInt1), this.attr);
    }
    if (paramInt2 > 0) {
      this.doc.insertString(this.doc.getLength(), "\t" + this.stats.nation.getString(paramInt2), this.attr);
    }
    if (paramInt1 > 0) {
      this.doc.insertString(this.doc.getLength(), "\nAge: " + this.stats.age.getString(paramInt1), this.attr);
    }
    if (paramInt2 > 0) {
      this.doc.insertString(this.doc.getLength(), "\tAge: " + this.stats.age.getString(paramInt2), this.attr);
    }
  }
  
  private void insertPhys(int paramInt1, int paramInt2)
    throws BadLocationException
  {
    StyleConstants.setForeground(this.attr, Color.white);
    String str;
    if (paramInt1 > 0)
    {
      str = "LF/";
      if (this.stats.foot.getValue(paramInt1) == 1)
      {
        switch (this.stats.favSide.getValue(paramInt1))
        {
        case 0: 
          str = str + "LS";
          break;
        case 1: 
          str = str + "RS";
          break;
        case 2: 
          str = str + "BS";
        }
      }
      else
      {
        str = "RF/";
        switch (this.stats.favSide.getValue(paramInt1))
        {
        case 0: 
          str = str + "RS";
          break;
        case 1: 
          str = str + "LS";
          break;
        case 2: 
          str = str + "BS";
        }
      }
      str = str + ", ";
      this.doc.insertString(this.doc.getLength(), str + this.stats.height.getString(paramInt1) + "cm, " + this.stats.weight.getString(paramInt1) + "Kg", this.attr);
    }
    if (paramInt2 > 0)
    {
      if (paramInt1 > 0) {
        this.doc.insertString(this.doc.getLength(), "\t", this.attr);
      } else {
        this.doc.insertString(this.doc.getLength(), "\t\t", this.attr);
      }
      str = "LF/";
      if (this.stats.foot.getValue(paramInt2) == 1)
      {
        switch (this.stats.favSide.getValue(paramInt2))
        {
        case 0: 
          str = str + "LS";
          break;
        case 1: 
          str = str + "RS";
          break;
        case 2: 
          str = str + "BS";
        }
      }
      else
      {
        str = "RF/";
        switch (this.stats.favSide.getValue(paramInt2))
        {
        case 0: 
          str = str + "RS";
          break;
        case 1: 
          str = str + "LS";
          break;
        case 2: 
          str = str + "BS";
        }
      }
      str = str + ", ";
      this.doc.insertString(this.doc.getLength(), str + this.stats.height.getString(paramInt2) + "cm, " + this.stats.weight.getString(paramInt2) + "Kg", this.attr);
    }
  }
  
  private void insertStat(Stat paramStat, int paramInt1, int paramInt2)
    throws BadLocationException
  {
    int i = paramStat.getValue(paramInt1);
    int j = paramStat.getValue(paramInt2);
    String str1 = paramStat.getString(paramInt1);
    String str2 = paramStat.getString(paramInt2);
    this.doc.insertString(this.doc.getLength(), "\n" + paramStat.name + "\t", this.attr);
    if (paramInt1 > 0)
    {
      setStatColour(paramStat, i);
      this.doc.insertString(this.doc.getLength(), str1, this.attr);
    }
    if (paramInt2 > 0)
    {
      if (paramInt1 > 0)
      {
        int k = i - j;
        String str3 = " ";
        int m = 3;
        int n = 1;
        if ((paramStat == this.stats.wfa) || (paramStat == this.stats.wff) || (paramStat == this.stats.consistency) || (paramStat == this.stats.condition))
        {
          m = 1;
          n = 0;
        }
        int i1;
        if (k > 0)
        {
          k = k / m + n;
          for (i1 = 0; (i1 < k) && (i1 < 10); i1++) {
            str3 = str3 + "*";
          }
          StyleConstants.setForeground(this.attr, Color.green);
          this.doc.insertString(this.doc.getLength(), str3, this.attr);
        }
        if (k < 0)
        {
          k = k / -m + n;
          for (i1 = 0; (i1 < k) && (i1 < 10); i1++) {
            str3 = str3 + "*";
          }
          StyleConstants.setForeground(this.attr, Color.red);
          this.doc.insertString(this.doc.getLength(), str3, this.attr);
        }
      }
      StyleConstants.setForeground(this.attr, Color.white);
      setStatColour(paramStat, j);
      this.doc.insertString(this.doc.getLength(), "\t" + str2, this.attr);
    }
    StyleConstants.setForeground(this.attr, Color.white);
  }
  
  private void insertAbility(Stat paramStat, int paramInt1, int paramInt2, Color paramColor)
    throws BadLocationException
  {
    StyleConstants.setForeground(this.attr, paramColor);
    this.doc.insertString(this.doc.getLength(), "\n" + paramStat.name + "\t", this.attr);
    if ((paramInt1 > 0) && (paramStat.getValue(paramInt1) == 1)) {
      this.doc.insertString(this.doc.getLength(), "O", this.attr);
    }
    if ((paramInt2 > 0) && (paramStat.getValue(paramInt2) == 1)) {
      this.doc.insertString(this.doc.getLength(), "\tO", this.attr);
    }
  }
  
  private void setStatColour(Stat paramStat, int paramInt)
  {
    if ((paramStat == this.stats.wfa) || (paramStat == this.stats.wff) || (paramStat == this.stats.consistency) || (paramStat == this.stats.condition))
    {
      if (paramInt == 7) {
        StyleConstants.setForeground(this.attr, Color.red);
      } else if (paramInt == 6) {
        StyleConstants.setForeground(this.attr, Color.orange);
      }
    }
    else if (paramInt > 94) {
      StyleConstants.setForeground(this.attr, Color.red);
    } else if (paramInt > 89) {
      StyleConstants.setForeground(this.attr, Color.orange);
    } else if (paramInt > 79) {
      StyleConstants.setForeground(this.attr, Color.yellow);
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/InfoPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */