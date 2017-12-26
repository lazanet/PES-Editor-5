package editor;

public class SquadTidy
{
  OptionFile of;
  Stats stats;
  
  public SquadTidy(OptionFile paramOptionFile, Stats paramStats)
  {
    this.of = paramOptionFile;
    this.stats = paramStats;
  }
  
  public void tidy(int paramInt)
  {
    if (((paramInt >= 0) && (paramInt < 64)) || ((paramInt >= 74) && (paramInt < 212)))
    {
      int i;
      int j;
      int k;
      if (paramInt < 74)
      {
        i = 23;
        j = 664054 + paramInt * i * 2;
        k = 657712 + paramInt * i;
      }
      else
      {
        i = 32;
        j = 667458 + (paramInt - 74) * i * 2;
        k = 659414 + (paramInt - 74) * i;
      }
      byte[] arrayOfByte1 = new byte[(i - 11) * 2];
      byte[] arrayOfByte2 = new byte[i - 11];
      int i1 = 0;
      for (int i2 = 11; i2 < i; i2++)
      {
        int n = j + i2 * 2;
        int m = k + i2;
        if ((this.of.data[n] != 0) || (this.of.data[(n + 1)] != 0))
        {
          arrayOfByte1[(i1 * 2)] = this.of.data[n];
          arrayOfByte1[(i1 * 2 + 1)] = this.of.data[(n + 1)];
          arrayOfByte2[i1] = this.of.data[m];
          i1++;
        }
      }
      for (int i2 = i1; i2 < i - 11; i2++) {
        arrayOfByte2[i2] = -1;
      }
      System.arraycopy(arrayOfByte1, 0, this.of.data, j + 22, arrayOfByte1.length);
      System.arraycopy(arrayOfByte2, 0, this.of.data, k + 11, arrayOfByte2.length);
    }
  }
  
  public void tidy11(int paramInt1, int paramInt2, int paramInt3)
  {
    if (((paramInt1 >= 0) && (paramInt1 < 64)) || ((paramInt1 >= 74) && (paramInt1 < 212)))
    {
      Stat localStat = this.stats.gk;
      int[] arrayOfInt1 = new int[21];
      int i = 0;
      if (((paramInt3 > 0) && (paramInt3 < 4)) || ((paramInt3 > 5) && (paramInt3 < 8)))
      {
        localStat = this.stats.cbt;
        i = 1;
      }
      if ((paramInt3 == 4) || (paramInt3 == 5))
      {
        localStat = this.stats.cbwS;
        i = 1;
      }
      if (paramInt3 == 8)
      {
        localStat = this.stats.sb;
        i = 2;
      }
      if (paramInt3 == 9)
      {
        localStat = this.stats.sb;
        i = 2;
      }
      if ((paramInt3 > 9) && (paramInt3 < 15))
      {
        localStat = this.stats.dm;
        i = 3;
      }
      if (paramInt3 == 15)
      {
        localStat = this.stats.sb;
        i = 2;
      }
      if (paramInt3 == 16)
      {
        localStat = this.stats.wb;
        i = 2;
      }
      if ((paramInt3 > 16) && (paramInt3 < 22))
      {
        localStat = this.stats.cm;
        i = 4;
      }
      if (paramInt3 == 22)
      {
        localStat = this.stats.sm;
        i = 5;
      }
      if (paramInt3 == 23)
      {
        localStat = this.stats.sm;
        i = 5;
      }
      if ((paramInt3 > 23) && (paramInt3 < 29))
      {
        localStat = this.stats.om;
        i = 6;
      }
      if ((paramInt3 > 35) && (paramInt3 < 41))
      {
        localStat = this.stats.cf;
        i = 7;
      }
      if ((paramInt3 > 30) && (paramInt3 < 36))
      {
        localStat = this.stats.ss;
        i = 6;
      }
      if (paramInt3 == 29)
      {
        localStat = this.stats.wg;
        i = 8;
      }
      if (paramInt3 == 30)
      {
        localStat = this.stats.wg;
        i = 8;
      }
      int j;
      int k;
      int m;
      if (paramInt1 < 74)
      {
        j = 23;
        k = 664054 + paramInt1 * j * 2;
        m = 657712 + paramInt1 * j;
      }
      else
      {
        j = 32;
        k = 667458 + (paramInt1 - 74) * j * 2;
        m = 659414 + (paramInt1 - 74) * j;
      }
      int i1 = 0;
      int i2 = -1;
      int[] arrayOfInt2 = new int[21];
      for (int i3 = 11; (i2 != 0) && (i3 < j); i3++)
      {
        i1 = i3 - 11;
        int n = k + i3 * 2;
        i2 = this.of.toInt(this.of.data[(n + 1)]) << 8 | this.of.toInt(this.of.data[n]);
        if (i2 != 0)
        {
          arrayOfInt2[i1] = i2;
          switch (i)
          {
          case 0: 
            arrayOfInt1[i1] = (this.stats.defence.getValue(i2) + this.stats.balance.getValue(i2) + this.stats.response.getValue(i2) + this.stats.gkAbil.getValue(i2) + this.stats.team.getValue(i2));
            break;
          case 1: 
            arrayOfInt1[i1] = (this.stats.defence.getValue(i2) + this.stats.balance.getValue(i2) + this.stats.response.getValue(i2) + this.stats.speed.getValue(i2) + this.stats.team.getValue(i2));
            break;
          case 2: 
            arrayOfInt1[i1] = (this.stats.defence.getValue(i2) + this.stats.balance.getValue(i2) + this.stats.stamina.getValue(i2) + this.stats.response.getValue(i2) + this.stats.speed.getValue(i2) + this.stats.lPassAcc.getValue(i2) + this.stats.team.getValue(i2));
            break;
          case 3: 
            arrayOfInt1[i1] = (this.stats.defence.getValue(i2) + this.stats.balance.getValue(i2) + this.stats.response.getValue(i2) + this.stats.sPassAcc.getValue(i2) + this.stats.team.getValue(i2));
            break;
          case 4: 
            arrayOfInt1[i1] = (this.stats.defence.getValue(i2) + this.stats.attack.getValue(i2) + this.stats.dribAcc.getValue(i2) + this.stats.sPassAcc.getValue(i2) + this.stats.tech.getValue(i2) + this.stats.team.getValue(i2));
            break;
          case 5: 
            arrayOfInt1[i1] = (this.stats.attack.getValue(i2) + this.stats.speed.getValue(i2) + this.stats.stamina.getValue(i2) + this.stats.dribAcc.getValue(i2) + this.stats.speed.getValue(i2) + this.stats.lPassAcc.getValue(i2) + this.stats.team.getValue(i2));
            break;
          case 6: 
            arrayOfInt1[i1] = (this.stats.attack.getValue(i2) + this.stats.dribAcc.getValue(i2) + this.stats.sPassAcc.getValue(i2) + this.stats.tech.getValue(i2) + this.stats.team.getValue(i2));
            break;
          case 7: 
            arrayOfInt1[i1] = (this.stats.attack.getValue(i2) + this.stats.response.getValue(i2) + this.stats.shotAcc.getValue(i2) + this.stats.shotTec.getValue(i2) + this.stats.tech.getValue(i2) + this.stats.team.getValue(i2));
            break;
          case 8: 
            arrayOfInt1[i1] = (this.stats.attack.getValue(i2) + this.stats.speed.getValue(i2) + this.stats.dribAcc.getValue(i2) + this.stats.dribSpe.getValue(i2) + this.stats.speed.getValue(i2) + this.stats.sPassAcc.getValue(i2) + this.stats.lPassAcc.getValue(i2) + this.stats.shotAcc.getValue(i2) + this.stats.tech.getValue(i2) + this.stats.team.getValue(i2));
          }
        }
      }
      int i3 = 0;
      int i4 = 0;
      int i5 = 0;
      int i6 = 0;
      int i7 = 0;
      for (int i8 = 0; i8 < 21; i8++) {
        if (arrayOfInt2[i8] != 0)
        {
          i7 = localStat.getValue(arrayOfInt2[i8]);
          if ((i7 == 1) && (arrayOfInt1[i8] > i4))
          {
            i4 = arrayOfInt1[i8];
            i3 = i8;
          }
          if ((i7 == 0) && (arrayOfInt1[i8] > i6))
          {
            i6 = arrayOfInt1[i8];
            i5 = i8;
          }
        }
      }
      if (i4 != 0) {
        i5 = i3;
      }
      i5 += 11;
      this.of.data[(k + 2 * paramInt2)] = this.of.toByte(arrayOfInt2[(i5 - 11)] & 0xFF);
      this.of.data[(k + 2 * paramInt2 + 1)] = this.of.toByte((arrayOfInt2[(i5 - 11)] & 0xFF00) >>> 8);
      this.of.data[(k + 2 * i5)] = 0;
      this.of.data[(k + 2 * i5 + 1)] = 0;
      this.of.data[(m + paramInt2)] = this.of.data[(m + i5)];
      this.of.data[(m + i5)] = -1;
      tidy(paramInt1);
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/SquadTidy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
