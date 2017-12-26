package editor;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JobList
  extends JList
  implements ListSelectionListener
{
  private OptionFile of;
  private int offSet;
  private String job;
  int team;
  private boolean ok = false;
  
  public JobList(OptionFile paramOptionFile, int paramInt, String paramString)
  {
    this.of = paramOptionFile;
    this.offSet = paramInt;
    this.job = paramString;
    refresh(-1);
    setSelectionMode(0);
    setLayoutOrientation(0);
    setVisibleRowCount(32);
    addListSelectionListener(this);
  }
  
  public void refresh(int paramInt)
  {
    this.ok = false;
    this.team = paramInt;
    String[] arrayOfString = new String[11];
    int i = 0;
    for (int j = 0; j < 11; j++) {
      arrayOfString[j] = " ";
    }
    if (paramInt == -1)
    {
      arrayOfString[0] = this.job;
    }
    else
    {
      int j = this.offSet + 628 * paramInt + 6232;
      i = this.of.toInt(this.of.data[j]);
      if ((i >= 0) && (i < 11)) {
        arrayOfString[i] = this.job;
      }
    }
    setListData(arrayOfString);
    setSelectedIndex(i);
    this.ok = true;
  }
  
  public void valueChanged(ListSelectionEvent paramListSelectionEvent)
  {
    if ((!paramListSelectionEvent.getValueIsAdjusting()) && (!isSelectionEmpty()) && (this.ok))
    {
      int i = this.offSet + 628 * this.team + 6232;
      this.of.data[i] = this.of.toByte(getSelectedIndex());
      refresh(this.team);
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/JobList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
