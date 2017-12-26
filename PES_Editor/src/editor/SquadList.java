package editor;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JList;

public class SquadList
  extends JList
{
  private OptionFile of;
  public int team;
  
  public SquadList(OptionFile paramOptionFile, boolean paramBoolean)
  {
    this.of = paramOptionFile;
    setSelectionMode(0);
    setLayoutOrientation(0);
    setVisibleRowCount(32);
    if (paramBoolean) {
      setPreferredSize(new Dimension(118, 576));
    }
  }
  
  public void refresh(int paramInt, boolean paramBoolean)
  {
    this.team = paramInt;
    if ((!paramBoolean) && (this.team > 63)) {
      this.team += 10;
    }
    if (this.team == 217)
    {
      Player[] arrayOfPlayer2 = new Player['ᐿ'];
      for (int k = 1; k < 5000; k++) {
        arrayOfPlayer2[(k - 1)] = new Player(this.of, k, 0);
      }
      for (int k = 32768; k < 32952; k++) {
        arrayOfPlayer2[(k - 32768 + 4999)] = new Player(this.of, k, 0);
      }
      List localList = Arrays.asList(arrayOfPlayer2);
      Collections.sort(localList);
      setListData(localList.toArray());
    }
    else
    {
      int j;
      int m;
      int n;
      if (this.team < 72)
      {
        j = 23;
        m = 664054 + this.team * j * 2;
        n = this.team;
      }
      else if (this.team == 72)
      {
        j = 14;
        m = 664054 + this.team * 23 * 2;
        n = this.team;
      }
      else
      {
        j = 32;
        m = 667458 + (this.team - 74) * j * 2;
        n = this.team - 10;
      }
      Player[] arrayOfPlayer1 = new Player[j];
      for (int i1 = 0; i1 < j; i1++)
      {
        int i = m + i1 * 2;
        if (((this.team >= 0) && (this.team < 64)) || ((this.team >= 74) && (this.team < 212)))
        {
          i = 670512 + 628 * n + 6232 + i1;
          i = m + this.of.toInt(this.of.data[i]) * 2;
        }
        arrayOfPlayer1[i1] = new Player(this.of, this.of.toInt(this.of.data[(i + 1)]) << 8 | this.of.toInt(this.of.data[i]), i);
      }
      setListData(arrayOfPlayer1);
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/SquadList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
