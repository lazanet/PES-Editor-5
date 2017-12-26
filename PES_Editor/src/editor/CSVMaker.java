package editor;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CSVMaker
{
  private RandomAccessFile out;
  private static String[] team;
  private static int seperator = 44;
  private OptionFile of;
  private Stats stats;
  
  public boolean makeFile(OptionFile paramOptionFile, Stats paramStats, File paramFile, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.of = paramOptionFile;
    this.stats = paramStats;
    boolean bool = false;
    try
    {
      this.out = new RandomAccessFile(paramFile, "rw");
      int i = 5000;
      team = Clubs.getNames(this.of);
      if (paramBoolean1)
      {
        writeHeadings();
        this.out.write(13);
        this.out.write(10);
      }
      for (int j = 1; j < 4896; j++) {
        writePlayer(this.out, j);
      }
      if (paramBoolean2) {
        for (int j = 4896; j < i; j++) {
          writePlayer(this.out, j);
        }
      }
      if (paramBoolean3) {
        for (int j = 32768; j < 32952; j++) {
          writePlayer(this.out, j);
        }
      }
      this.out.close();
      bool = true;
    }
    catch (IOException localIOException)
    {
      bool = false;
    }
    return bool;
  }
  
  private void writeName(int paramInt)
    throws IOException
  {
    Player localPlayer = new Player(this.of, paramInt, 0);
    String str = localPlayer.name.replaceAll(",", "");
    this.out.writeBytes(str);
  }
  
  private void writeHeadings()
    throws IOException
  {
    String[] arrayOfString = { "GK  0", "CWP  2", "CBT  3", "SB  4", "DMF  5", "WB  6", "CMF  7", "SMF  8", "AMF  9", "WF 10", "SS  11", "CF  12", "REGISTERED POSITION", "HEIGHT", "FAVOURED FOOT", "FAVOURED SIDE", "WEAK FOOT ACCURACY", "WEAK FOOT FREQUENCY", "ATTACK", "DEFENSE", "BALANCE", "STAMINA", "TOP SPEED", "ACCELERATION", "RESPONSE", "AGILITY", "DRIBBLE ACCURACY", "DRIBBLE SPEED", "SHORT PASS ACCURACY", "SHORT PASS SPEED", "LONG PASS ACCURACY", "LONG PASS SPEED", "SHOT ACCURACY", "SHOT POWER", "SHOT TECHNIQUE", "FREE KICK ACCURACY", "CURLING", "HEADING", "JUMP", "TECHNIQUE", "AGGRESSION", "MENTALITY", "GOAL KEEPING", "TEAM WORK", "CONSISTENCY", "CONDITION / FITNESS", "DRIBBLING", "TACTIAL DRIBBLE", "POSITIONING", "REACTION", "PLAYMAKING", "PASSING", "SCORING", "1-1 SCORING", "POST PLAYER", "LINES", "MIDDLE SHOOTING", "SIDE", "CENTRE", "PENALTIES", "1-TOUCH PASS", "OUTSIDE", "MARKING", "SLIDING", "COVERING", "D-LINE CONTROL", "PENALTY STOPPER", "1-ON-1 STOPPER", "LONG THROW", "INJURY TOLERANCE", "DRIBBLE STYLE", "FREE KICK STYLE", "PK STYLE", "DROP KICK STYLE", "AGE", "WEIGHT", "NATIONALITY", "INTERNATIONAL NUMBER", "CLASSIC NUMBER", "CLUB TEAM", "CLUB NUMBER" };
    this.out.writeBytes("NAME");
    for (int i = 0; i < arrayOfString.length; i++)
    {
      this.out.write(seperator);
      this.out.writeBytes(arrayOfString[i]);
    }
  }
  
  private void writeInterStatus(int paramInt)
    throws IOException
  {
    String str = "0";
    int i = this.stats.nation.getValue(paramInt);
    if (i < 57) {
      for (int n = 0; n < 23; n++)
      {
        int k = this.of.toInt(this.of.data[(664054 + 46 * i + n * 2)]);
        int m = this.of.toInt(this.of.data[(664055 + 46 * i + n * 2)]);
        if ((m << 8 | k) == paramInt)
        {
          int j = this.of.toInt(this.of.data[(657712 + 23 * i + n)]) + 1;
          str = String.valueOf(j);
        }
      }
    }
    this.out.writeBytes(str);
  }
  
  private void writeClassicStatus(int paramInt)
    throws IOException
  {
    String str = "0";
    int i = this.stats.nation.getValue(paramInt);
    int n = 0;
    if ((i == 6) || (i == 8) || (i == 9) || (i == 13) || (i == 15) || (i == 43) || (i == 44))
    {
      if (i == 43) {
        n = 57;
      }
      if (i == 44) {
        n = 58;
      }
      if (i == 6) {
        n = 59;
      }
      if (i == 8) {
        n = 60;
      }
      if (i == 9) {
        n = 61;
      }
      if (i == 13) {
        n = 62;
      }
      if (i == 15) {
        n = 63;
      }
      for (int i1 = 0; i1 < 23; i1++)
      {
        int k = this.of.toInt(this.of.data[(664054 + 46 * n + i1 * 2)]);
        int m = this.of.toInt(this.of.data[(664055 + 46 * n + i1 * 2)]);
        if ((m << 8 | k) == paramInt)
        {
          int j = this.of.toInt(this.of.data[(657712 + 23 * n + i1)]) + 1;
          str = String.valueOf(j);
        }
      }
    }
    this.out.writeBytes(str);
  }
  
  private void writeTeam(int paramInt)
    throws IOException
  {
    String str1 = "0";
    String str2 = "";
    for (int m = 0; m < 138; m++) {
      for (int n = 0; n < 32; n++)
      {
        int j = this.of.toInt(this.of.data[(667458 + 64 * m + n * 2)]);
        int k = this.of.toInt(this.of.data[(667459 + 64 * m + n * 2)]);
        if ((k << 8 | j) == paramInt)
        {
          int i = this.of.toInt(this.of.data[(659414 + 32 * m + n)]) + 1;
          str2 = team[m];
          str1 = String.valueOf(i);
        }
      }
    }
    this.out.writeBytes(str2);
    this.out.write(seperator);
    this.out.writeBytes(str1);
  }
  
  private void writePlayer(RandomAccessFile paramRandomAccessFile, int paramInt)
    throws IOException
  {
    writeName(paramInt);
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.gk.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.cbwS.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.cbt.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.sb.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.dm.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.wb.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.cm.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.sm.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.om.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.wg.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.ss.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.cf.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.regPos.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.height.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.foot.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(getSide(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.wfa.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.wff.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.attack.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.defence.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.balance.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.stamina.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.speed.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.accel.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.response.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.agility.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.dribAcc.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.dribSpe.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.sPassAcc.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.sPassSpe.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.lPassAcc.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.lPassSpe.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.shotAcc.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.shotPow.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.shotTec.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.fk.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.curling.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.heading.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.jump.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.tech.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.aggress.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.mental.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.gkAbil.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.team.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.consistency.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.condition.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.drib.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.dribKeep.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.posit.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.offside.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.play.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.pass.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.scorer.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.k11.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.post.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.linePos.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.midShot.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.side.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.centre.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.pk.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.direct.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.outside.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.man.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.slide.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.cover.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.dLine.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.keeperPK.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.keeper11.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.longThrow.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.injury.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.dribSty.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.freekick.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.pkStyle.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.dkSty.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.age.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.weight.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    paramRandomAccessFile.writeBytes(this.stats.nation.getString(paramInt));
    paramRandomAccessFile.write(seperator);
    writeInterStatus(paramInt);
    paramRandomAccessFile.write(seperator);
    writeClassicStatus(paramInt);
    paramRandomAccessFile.write(seperator);
    writeTeam(paramInt);
    paramRandomAccessFile.write(13);
    paramRandomAccessFile.write(10);
  }
  
  private String getSide(int paramInt)
  {
    String str = "B";
    int i = this.stats.favSide.getValue(paramInt);
    if (i == 0) {
      str = this.stats.foot.getString(paramInt);
    }
    if (i == 1) {
      if (this.stats.foot.getValue(paramInt) == 0) {
        str = "L";
      } else {
        str = "R";
      }
    }
    return str;
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/CSVMaker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
