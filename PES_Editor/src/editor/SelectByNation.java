package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SelectByNation
  extends JPanel
{
  public NationalityList freeList;
  JComboBox nationBox;
  JButton sort;
  boolean alpha;
  
  public SelectByNation(OptionFile paramOptionFile, Stats paramStats)
  {
    super(new BorderLayout());
    JScrollPane localJScrollPane = new JScrollPane(22, 31);
    this.freeList = new NationalityList(paramOptionFile, paramStats);
    this.alpha = true;
    this.sort = new JButton("Alpha Order");
    this.sort.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (SelectByNation.this.alpha)
        {
          SelectByNation.this.sort.setText("Index Order");
          SelectByNation.this.alpha = false;
        }
        else
        {
          SelectByNation.this.sort.setText("Alpha Order");
          SelectByNation.this.alpha = true;
        }
        int i = SelectByNation.this.nationBox.getSelectedIndex();
        SelectByNation.this.freeList.refresh(i, SelectByNation.this.alpha);
      }
    });
    String[] arrayOfString = new String[Stat.nation.length + 6];
    arrayOfString[109] = "Unused";
    arrayOfString[110] = "ML Old";
    arrayOfString[111] = "ML Young";
    arrayOfString[112] = "Same name";
    arrayOfString[113] = "Free Agents";
    arrayOfString[114] = "All Players";
    System.arraycopy(Stat.nation, 0, arrayOfString, 0, Stat.nation.length);
    this.nationBox = new JComboBox(arrayOfString);
    this.nationBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        int i = SelectByNation.this.nationBox.getSelectedIndex();
        if (i != -1) {
          SelectByNation.this.freeList.refresh(i, SelectByNation.this.alpha);
        }
      }
    });
    this.nationBox.setMaximumRowCount(32);
    this.freeList.setSelectionMode(0);
    this.freeList.setLayoutOrientation(0);
    this.freeList.setVisibleRowCount(11);
    localJScrollPane.setViewportView(this.freeList);
    add(this.nationBox, "North");
    add(localJScrollPane, "Center");
    add(this.sort, "South");
    setPreferredSize(new Dimension(164, 601));
  }
  
  public void refresh()
  {
    this.nationBox.setSelectedIndex(114);
    this.freeList.refresh(this.nationBox.getSelectedIndex(), this.alpha);
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/SelectByNation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */