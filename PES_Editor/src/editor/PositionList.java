package editor;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.ListModel;

public class PositionList
  extends JList
{
  private OptionFile of;
  boolean tran;
  int[] posNum;
  int alt = 0;
  
  public PositionList(OptionFile paramOptionFile, boolean paramBoolean)
  {
    this.tran = paramBoolean;
    this.of = paramOptionFile;
    setSelectionMode(2);
    setLayoutOrientation(0);
    setVisibleRowCount(32);
    setBackground(new Color(255, 255, 224));
    setPreferredSize(new Dimension(30, 576));
  }
  
  public void refresh(int paramInt)
  {
    String[] arrayOfString = new String[32];
    this.posNum = new int[32];
    if ((this.tran) && (((paramInt > 63) && (paramInt < 74)) || (paramInt > 211)))
    {
      setListData(arrayOfString);
    }
    else
    {
      if ((this.tran) && (paramInt > 73)) {
        paramInt -= 10;
      }
      arrayOfString[0] = "GK   ";
      this.posNum[0] = 0;
      for (int j = 0; j < 10; j++)
      {
        int i = this.of.data[(670642 + 628 * paramInt + 6232 + j + this.alt * 171)];
        this.posNum[(j + 1)] = i;
        if (i == 0) {
          arrayOfString[(j + 1)] = "GK";
        }
        if (((i > 0) && (i < 4)) || ((i > 5) && (i < 8))) {
          arrayOfString[(j + 1)] = "CBT";
        }
        if (i == 4) {
          arrayOfString[(j + 1)] = "CWP";
        }
        if (i == 5) {
          arrayOfString[(j + 1)] = "ASW";
        }
        if (i == 8) {
          arrayOfString[(j + 1)] = "LB";
        }
        if (i == 9) {
          arrayOfString[(j + 1)] = "RB";
        }
        if ((i > 9) && (i < 15)) {
          arrayOfString[(j + 1)] = "DM";
        }
        if (i == 15) {
          arrayOfString[(j + 1)] = "LWB";
        }
        if (i == 16) {
          arrayOfString[(j + 1)] = "RWB";
        }
        if ((i > 16) && (i < 22)) {
          arrayOfString[(j + 1)] = "CM";
        }
        if (i == 22) {
          arrayOfString[(j + 1)] = "LM";
        }
        if (i == 23) {
          arrayOfString[(j + 1)] = "RM";
        }
        if ((i > 23) && (i < 29)) {
          arrayOfString[(j + 1)] = "AM";
        }
        if (i == 29) {
          arrayOfString[(j + 1)] = "LW";
        }
        if (i == 30) {
          arrayOfString[(j + 1)] = "RW";
        }
        if ((i > 30) && (i < 36)) {
          arrayOfString[(j + 1)] = "SS";
        }
        if ((i > 35) && (i < 41)) {
          arrayOfString[(j + 1)] = "CF";
        }
        if (i > 40) {
          arrayOfString[(j + 1)] = String.valueOf(i);
        }
      }
      for (int j = 11; j < 32; j++) {
        arrayOfString[j] = " ";
      }
      setListData(arrayOfString);
    }
  }
  
  public void selectPos(JList paramJList, int paramInt)
  {
    clearSelection();
    if ((paramInt >= 0) && (paramInt < 11))
    {
      int i = paramJList.getModel().getSize();
      int j = this.posNum[paramInt];
      int[] arrayOfInt1 = new int[32];
      int m = 0;
      Stat localStat = new Stat(this.of, 0, 8, 6, 1, "GK");
      if (((j > 0) && (j < 4)) || ((j > 5) && (j < 8))) {
        localStat = new Stat(this.of, 0, 12, 4, 1, "CBT");
      }
      if (j == 4) {
        new Stat(this.of, 0, 8, 7, 1, "CWP");
      }
      if (j == 5) {
        new Stat(this.of, 0, 8, 7, 1, "CWP");
      }
      if (j == 8) {
        localStat = new Stat(this.of, 0, 12, 5, 1, "SB");
      }
      if (j == 9) {
        localStat = new Stat(this.of, 0, 12, 5, 1, "SB");
      }
      if ((j > 9) && (j < 15)) {
        localStat = new Stat(this.of, 0, 12, 6, 1, "DM");
      }
      if (j == 15) {
        localStat = new Stat(this.of, 0, 12, 7, 1, "WB");
      }
      if (j == 16) {
        localStat = new Stat(this.of, 0, 12, 7, 1, "WB");
      }
      if ((j > 16) && (j < 22)) {
        localStat = new Stat(this.of, 0, 16, 4, 1, "CM");
      }
      if (j == 22) {
        localStat = new Stat(this.of, 0, 16, 5, 1, "SM");
      }
      if (j == 23) {
        localStat = new Stat(this.of, 0, 16, 5, 1, "SM");
      }
      if ((j > 23) && (j < 29)) {
        localStat = new Stat(this.of, 0, 16, 6, 1, "AM");
      }
      if (j == 29) {
        localStat = new Stat(this.of, 0, 16, 7, 1, "WG");
      }
      if (j == 30) {
        localStat = new Stat(this.of, 0, 16, 7, 1, "WG");
      }
      if ((j > 30) && (j < 36)) {
        localStat = new Stat(this.of, 0, 20, 4, 1, "SS");
      }
      if ((j > 35) && (j < 41)) {
        localStat = new Stat(this.of, 0, 20, 5, 1, "CF");
      }
      m = 0;
      for (int n = 0; n < i; n++)
      {
        int k = ((Player)paramJList.getModel().getElementAt(n)).index;
        if ((k != 0) && (localStat.getValue(k) == 1))
        {
          arrayOfInt1[m] = n;
          m++;
        }
      }
      int[] arrayOfInt2 = new int[m];
      System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, m);
      setSelectedIndices(arrayOfInt2);
    }
    else
    {
      clearSelection();
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/PositionList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
