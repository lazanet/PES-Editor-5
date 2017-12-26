package editor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JList;

public class NationalityList
  extends JList
{
  private OptionFile of;
  private Stats stats;
  
  public NationalityList(OptionFile paramOptionFile, Stats paramStats)
  {
    this.of = paramOptionFile;
    this.stats = paramStats;
    setSelectionMode(0);
    setLayoutOrientation(0);
    setVisibleRowCount(32);
  }
  
  public void refresh(int paramInt, boolean paramBoolean)
  {
    int k = 0;
    Player[] arrayOfPlayer2 = new Player['ᐿ'];
    if (paramInt == 114)
    {
      for (int m = 1; m < 5000; m++) {
        arrayOfPlayer2[(m - 1)] = new Player(this.of, m, 0);
      }
      for (int m = 32768; m < 32952; m++) {
        arrayOfPlayer2[(m - 32768 + 4999)] = new Player(this.of, m, 0);
      }
      if (paramBoolean)
      {
        List localList1 = Arrays.asList(arrayOfPlayer2);
        Collections.sort(localList1);
        setListData(localList1.toArray());
      }
      else
      {
        setListData(arrayOfPlayer2);
      }
    }
    else
    {
      int n;
      Player[] arrayOfPlayer1;
      if (paramInt == 113)
      {
        int i;
        int j;
        for (int i5 = 1; i5 < 1312; i5++)
        {
          n = 1;
          i = 667456;
          do
          {
            i += 2;
            j = this.of.toInt(this.of.data[(i + 1)]) << 8 | this.of.toInt(this.of.data[i]);
            if (j == i5) {
              n = 0;
            }
          } while ((i < 676288) && (j != i5));
          if (n != 0)
          {
            arrayOfPlayer2[k] = new Player(this.of, i5, 0);
            k++;
          }
        }
        for (int i5 = 1473; i5 < 4535; i5++)
        {
          n = 1;
          i = 667456;
          do
          {
            i += 2;
            j = this.of.toInt(this.of.data[(i + 1)]) << 8 | this.of.toInt(this.of.data[i]);
            if (j == i5) {
              n = 0;
            }
          } while ((i < 676288) && (j != i5));
          if (n != 0)
          {
            arrayOfPlayer2[k] = new Player(this.of, i5, 0);
            k++;
          }
        }
        if (paramBoolean)
        {
          arrayOfPlayer1 = new Player[k];
          System.arraycopy(arrayOfPlayer2, 0, arrayOfPlayer1, 0, k);
          List localList7 = Arrays.asList(arrayOfPlayer1);
          Collections.sort(localList7);
          setListData(localList7.toArray());
        }
        else
        {
          arrayOfPlayer1 = new Player[k];
          System.arraycopy(arrayOfPlayer2, 0, arrayOfPlayer1, 0, k);
          setListData(arrayOfPlayer1);
        }
      }
      else if (paramInt == 112)
      {
        arrayOfPlayer2 = new Player['ᎇ'];
        for (n = 1; n < 5000; n++) {
          arrayOfPlayer2[(n - 1)] = new Player(this.of, n, 0);
        }
        List localList2 = Arrays.asList(arrayOfPlayer2);
        Collections.sort(localList2);
        arrayOfPlayer2 = (Player[])localList2.toArray();
        arrayOfPlayer1 = new Player[arrayOfPlayer2.length];
        for (int i6 = 0; i6 < arrayOfPlayer2.length; i6++)
        {
          int i7 = 0;
          if ((i6 != 0) && (arrayOfPlayer2[i6].name.equals(arrayOfPlayer2[(i6 - 1)].name)))
          {
            arrayOfPlayer1[k] = arrayOfPlayer2[i6];
            k++;
            i7 = 1;
          }
          if ((i7 == 0) && (i6 != arrayOfPlayer2.length - 1) && (arrayOfPlayer2[i6].name.equals(arrayOfPlayer2[(i6 + 1)].name)))
          {
            arrayOfPlayer1[k] = arrayOfPlayer2[i6];
            k++;
          }
        }
        arrayOfPlayer2 = new Player[k];
        System.arraycopy(arrayOfPlayer1, 0, arrayOfPlayer2, 0, k);
        setListData(arrayOfPlayer2);
      }
      else if (paramInt == 111)
      {
        for (int i1 = 4686; i1 < 4886; i1++)
        {
          arrayOfPlayer2[k] = new Player(this.of, i1, 0);
          k++;
        }
        if (paramBoolean)
        {
          arrayOfPlayer1 = new Player[k];
          System.arraycopy(arrayOfPlayer2, 0, arrayOfPlayer1, 0, k);
          List localList3 = Arrays.asList(arrayOfPlayer1);
          Collections.sort(localList3);
          setListData(localList3.toArray());
        }
        else
        {
          arrayOfPlayer1 = new Player[k];
          System.arraycopy(arrayOfPlayer2, 0, arrayOfPlayer1, 0, k);
          setListData(arrayOfPlayer1);
        }
      }
      else if (paramInt == 110)
      {
        for (int i2 = 4886; i2 < 4896; i2++)
        {
          arrayOfPlayer2[k] = new Player(this.of, i2, 0);
          k++;
        }
        if (paramBoolean)
        {
          arrayOfPlayer1 = new Player[k];
          System.arraycopy(arrayOfPlayer2, 0, arrayOfPlayer1, 0, k);
          List localList4 = Arrays.asList(arrayOfPlayer1);
          Collections.sort(localList4);
          setListData(localList4.toArray());
        }
        else
        {
          arrayOfPlayer1 = new Player[k];
          System.arraycopy(arrayOfPlayer2, 0, arrayOfPlayer1, 0, k);
          setListData(arrayOfPlayer1);
        }
      }
      else if (paramInt == 109)
      {
        for (int i3 = 4896; i3 < 5000; i3++)
        {
          arrayOfPlayer2[k] = new Player(this.of, i3, 0);
          k++;
        }
        if (paramBoolean)
        {
          arrayOfPlayer1 = new Player[k];
          System.arraycopy(arrayOfPlayer2, 0, arrayOfPlayer1, 0, k);
          List localList5 = Arrays.asList(arrayOfPlayer1);
          Collections.sort(localList5);
          setListData(localList5.toArray());
        }
        else
        {
          arrayOfPlayer1 = new Player[k];
          System.arraycopy(arrayOfPlayer2, 0, arrayOfPlayer1, 0, k);
          setListData(arrayOfPlayer1);
        }
      }
      else
      {
        k = 0;
        for (int i4 = 1; i4 < 5000; i4++) {
          if (this.stats.nation.getValue(i4) == paramInt)
          {
            arrayOfPlayer2[k] = new Player(this.of, i4, 0);
            k++;
          }
        }
        for (int i4 = 32768; i4 < 32952; i4++) {
          if (this.stats.nation.getValue(i4) == paramInt)
          {
            arrayOfPlayer2[k] = new Player(this.of, i4, 0);
            k++;
          }
        }
        if (paramBoolean)
        {
          arrayOfPlayer1 = new Player[k];
          System.arraycopy(arrayOfPlayer2, 0, arrayOfPlayer1, 0, k);
          List localList6 = Arrays.asList(arrayOfPlayer1);
          Collections.sort(localList6);
          setListData(localList6.toArray());
        }
        else
        {
          arrayOfPlayer1 = new Player[k];
          System.arraycopy(arrayOfPlayer2, 0, arrayOfPlayer1, 0, k);
          setListData(arrayOfPlayer1);
        }
      }
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/NationalityList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
