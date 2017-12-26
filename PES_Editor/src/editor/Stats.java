package editor;

public class Stats
{
  Stat gk;
  Stat cbwL;
  Stat cbwS;
  Stat cbt;
  Stat sb;
  Stat dm;
  Stat wb;
  Stat cm;
  Stat sm;
  Stat om;
  Stat ss;
  Stat cf;
  Stat wg;
  Stat regPos;
  Stat height;
  Stat foot;
  Stat favSide;
  Stat wfa;
  Stat wff;
  Stat attack;
  Stat defence;
  Stat balance;
  Stat stamina;
  Stat speed;
  Stat accel;
  Stat response;
  Stat agility;
  Stat dribAcc;
  Stat dribSpe;
  Stat sPassAcc;
  Stat sPassSpe;
  Stat lPassAcc;
  Stat lPassSpe;
  Stat shotAcc;
  Stat shotPow;
  Stat shotTec;
  Stat fk;
  Stat curling;
  Stat heading;
  Stat jump;
  Stat tech;
  Stat aggress;
  Stat mental;
  Stat consistency;
  Stat gkAbil;
  Stat team;
  Stat condition;
  Stat statX;
  Stat drib;
  Stat dribKeep;
  Stat post;
  Stat posit;
  Stat offside;
  Stat linePos;
  Stat scorer;
  Stat play;
  Stat pass;
  Stat bff;
  Stat pk;
  Stat k11;
  Stat longThrow;
  Stat direct;
  Stat side;
  Stat centre;
  Stat outside;
  Stat man;
  Stat dLine;
  Stat slide;
  Stat gkKick;
  Stat keeperPK;
  Stat keeper11;
  Stat midShot;
  Stat cover;
  Stat injury;
  Stat dribSty;
  Stat freekick;
  Stat pkStyle;
  Stat dkSty;
  Stat age;
  Stat weight;
  Stat nation;
  Stat callName;
  Stat statEdited;
  private OptionFile of;
  
  public Stats(OptionFile paramOptionFile)
  {
    this.of = paramOptionFile;
    refresh();
  }
  
  public void refresh()
  {
    this.cbwL = new Stat(this.of, 0, 11, 14, 1, "ASW");
    this.gk = new Stat(this.of, 0, 8, 6, 1, "GK");
    this.cbwS = new Stat(this.of, 0, 8, 7, 1, "CWP");
    this.cbt = new Stat(this.of, 0, 12, 4, 1, "CBT");
    this.sb = new Stat(this.of, 0, 12, 5, 1, "SB");
    this.dm = new Stat(this.of, 0, 12, 6, 1, "DM");
    this.wb = new Stat(this.of, 0, 12, 7, 1, "WB");
    this.cm = new Stat(this.of, 0, 16, 4, 1, "CM");
    this.sm = new Stat(this.of, 0, 16, 5, 1, "SM");
    this.om = new Stat(this.of, 0, 16, 6, 1, "AM");
    this.wg = new Stat(this.of, 0, 16, 7, 1, "WG");
    this.ss = new Stat(this.of, 0, 20, 4, 1, "SS");
    this.cf = new Stat(this.of, 0, 20, 5, 1, "CF");
    this.regPos = new Stat(this.of, 0, 6, 4, 15, "");
    this.height = new Stat(this.of, 1, 41, 0, 63, "Height");
    this.foot = new Stat(this.of, 4, 5, 0, 1, "Foot");
    this.favSide = new Stat(this.of, 0, 20, 6, 3, "Fav side");
    this.wfa = new Stat(this.of, 5, 34, 5, 7, "W Foot Acc");
    this.wff = new Stat(this.of, 5, 35, 0, 7, "W Foot Freq");
    this.attack = new Stat(this.of, 0, 7, 0, 127, "Attack");
    this.defence = new Stat(this.of, 0, 7, 7, 127, "Defense");
    this.balance = new Stat(this.of, 0, 9, 0, 127, "Balance");
    this.stamina = new Stat(this.of, 0, 9, 7, 127, "Stamina");
    this.speed = new Stat(this.of, 0, 10, 6, 127, "Speed");
    this.accel = new Stat(this.of, 0, 11, 5, 127, "Accel");
    this.response = new Stat(this.of, 0, 13, 0, 127, "Response");
    this.agility = new Stat(this.of, 0, 13, 7, 127, "Agility");
    this.dribAcc = new Stat(this.of, 0, 14, 6, 127, "Drib Acc");
    this.dribSpe = new Stat(this.of, 0, 15, 5, 127, "Drib Spe");
    this.sPassAcc = new Stat(this.of, 0, 17, 0, 127, "S Pass Acc");
    this.sPassSpe = new Stat(this.of, 0, 17, 7, 127, "S Pass Spe");
    this.lPassAcc = new Stat(this.of, 0, 18, 6, 127, "L Pass Acc");
    this.lPassSpe = new Stat(this.of, 0, 19, 5, 127, "L Pass Spe");
    this.shotAcc = new Stat(this.of, 0, 21, 0, 127, "Shot Acc");
    this.shotPow = new Stat(this.of, 0, 21, 7, 127, "Shot Power");
    this.shotTec = new Stat(this.of, 0, 22, 6, 127, "Shot Tech");
    this.fk = new Stat(this.of, 0, 23, 5, 127, "FK Acc");
    this.curling = new Stat(this.of, 0, 25, 0, 127, "Curling");
    this.heading = new Stat(this.of, 0, 25, 7, 127, "Heading");
    this.jump = new Stat(this.of, 0, 26, 6, 127, "Jump");
    this.tech = new Stat(this.of, 0, 29, 0, 127, "Tech");
    this.aggress = new Stat(this.of, 0, 29, 7, 127, "Aggression");
    this.mental = new Stat(this.of, 0, 30, 6, 127, "Mentality");
    this.consistency = new Stat(this.of, 5, 33, 7, 7, "Consistency");
    this.gkAbil = new Stat(this.of, 0, 31, 5, 127, "GK");
    this.team = new Stat(this.of, 0, 33, 0, 127, "Team Work");
    this.condition = new Stat(this.of, 5, 34, 2, 7, "Condition");
    this.statX = new Stat(this.of, 0, 27, 5, 127, "StatX");
    this.drib = new Stat(this.of, 0, 24, 4, 1, "Dribbling");
    this.dribKeep = new Stat(this.of, 0, 24, 5, 1, "Anti-Dribble");
    this.post = new Stat(this.of, 0, 32, 4, 1, "Post");
    this.posit = new Stat(this.of, 0, 24, 6, 1, "Positioning");
    this.offside = new Stat(this.of, 0, 24, 7, 1, "Reaction");
    this.linePos = new Stat(this.of, 0, 32, 5, 1, "Line Position");
    this.midShot = new Stat(this.of, 0, 32, 6, 1, "Mid shooting");
    this.scorer = new Stat(this.of, 0, 28, 6, 1, "Scoring");
    this.play = new Stat(this.of, 0, 28, 4, 1, "Playmaking");
    this.pass = new Stat(this.of, 0, 28, 5, 1, "Passing");
    this.bff = new Stat(this.of, 0, 20, 6, 1, "B F Feint");
    this.pk = new Stat(this.of, 0, 35, 6, 1, "Penalties");
    this.k11 = new Stat(this.of, 0, 28, 7, 1, "1-1 Scoring");
    this.longThrow = new Stat(this.of, 0, 36, 7, 1, "Long Throw");
    this.direct = new Stat(this.of, 0, 35, 7, 1, "1-T Pass");
    this.side = new Stat(this.of, 0, 32, 7, 1, "Side");
    this.centre = new Stat(this.of, 0, 35, 5, 1, "Centre");
    this.outside = new Stat(this.of, 0, 36, 0, 1, "Outside");
    this.man = new Stat(this.of, 0, 36, 1, 1, "Marking");
    this.dLine = new Stat(this.of, 0, 36, 4, 1, "D-L Control");
    this.slide = new Stat(this.of, 0, 36, 2, 1, "Sliding");
    this.cover = new Stat(this.of, 0, 36, 3, 1, "Cover");
    this.gkKick = new Stat(this.of, 0, 20, 7, 1, "GK Kick");
    this.keeperPK = new Stat(this.of, 0, 36, 5, 1, "Penalty GK");
    this.keeper11 = new Stat(this.of, 0, 36, 6, 1, "1-on-1 GK");
    this.injury = new Stat(this.of, 6, 35, 3, 3, "Injury T");
    this.dribSty = new Stat(this.of, 5, 6, 0, 3, "");
    this.freekick = new Stat(this.of, 5, 5, 1, 15, "");
    this.pkStyle = new Stat(this.of, 5, 5, 5, 7, "");
    this.dkSty = new Stat(this.of, 5, 6, 2, 3, "");
    this.age = new Stat(this.of, 2, 62, 5, 31, "Age");
    this.weight = new Stat(this.of, 0, 41, 6, 127, "Weight");
    this.nation = new Stat(this.of, 3, 63, 2, 127, "Nationality");
    this.callName = new Stat(this.of, 0, 1, 0, 65535, "");
    this.statEdited = new Stat(this.of, 0, 39, 7, 1, "");
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/Stats.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */