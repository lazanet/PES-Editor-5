package editor;

import java.awt.Dimension;
import javax.swing.JList;

public class SquadNumList
  extends JList
{
  private OptionFile of;
  
  public SquadNumList(OptionFile paramOptionFile)
  {
    this.of = paramOptionFile;
    setSelectionMode(0);
    setLayoutOrientation(0);
    setVisibleRowCount(1);
    setPreferredSize(new Dimension(16, 576));
  }
  
  public void refresh(int paramInt)
  {
    int i;
    int j;
    int n;
    if (paramInt < 72)
    {
      i = 23;
      j = 657712 + paramInt * i;
      n = paramInt;
    }
    else if (paramInt == 72)
    {
      i = 14;
      j = 664054 + paramInt * 23 * 2;
      n = paramInt;
    }
    else
    {
      i = 32;
      j = 659414 + (paramInt - 74) * i;
      n = paramInt - 10;
    }
    String[] arrayOfString = new String[i];
    for (int i1 = 0; i1 < i; i1++)
    {
      int k = j + i1;
      if (((paramInt >= 0) && (paramInt < 64)) || ((paramInt >= 74) && (paramInt < 212)))
      {
        k = 670512 + 628 * n + 6232 + i1;
        k = j + this.of.toInt(this.of.data[k]);
      }
      int m = this.of.toInt(this.of.data[k]) + 1;
      if (m == 256) {
        arrayOfString[i1] = "##";
      } else {
        arrayOfString[i1] = String.valueOf(m);
      }
    }
    setListData(arrayOfString);
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/SquadNumList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */