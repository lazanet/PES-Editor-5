package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SelectByTeam
  extends JPanel
{
  public SquadList squadList;
  JComboBox teamBox;
  OptionFile of;
  SquadNumList numList;
  PositionList posList;
  boolean normal;
  
  public SelectByTeam(OptionFile paramOptionFile, boolean paramBoolean)
  {
    super(new BorderLayout());
    this.of = paramOptionFile;
    this.normal = paramBoolean;
    this.teamBox = new JComboBox();
    this.teamBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (SelectByTeam.this.teamBox.getSelectedIndex() != -1)
        {
          SelectByTeam.this.squadList.refresh(SelectByTeam.this.teamBox.getSelectedIndex(), true);
          if (SelectByTeam.this.normal)
          {
            SelectByTeam.this.posList.refresh(SelectByTeam.this.teamBox.getSelectedIndex());
            SelectByTeam.this.numList.refresh(SelectByTeam.this.teamBox.getSelectedIndex());
          }
        }
      }
    });
    this.teamBox.setMaximumRowCount(32);
    this.squadList = new SquadList(this.of, paramBoolean);
    add(this.teamBox, "North");
    if (this.normal)
    {
      this.numList = new SquadNumList(this.of);
      this.posList = new PositionList(this.of, true);
      add(this.posList, "West");
      add(this.squadList, "Center");
      add(this.numList, "East");
    }
    else
    {
      JScrollPane localJScrollPane = new JScrollPane(22, 31);
      setPreferredSize(null);
      localJScrollPane.setViewportView(this.squadList);
      add(localJScrollPane, "Center");
    }
    setPreferredSize(new Dimension(164, 601));
  }
  
  public void refresh()
  {
    String[] arrayOfString1;
    if (this.normal) {
      arrayOfString1 = new String['Ù'];
    } else {
      arrayOfString1 = new String['Ú'];
    }
    System.arraycopy(Stat.nation, 0, arrayOfString1, 0, 57);
    System.arraycopy(PES4Utils.extraSquad, 0, arrayOfString1, 57, 17);
    String[] arrayOfString2 = Clubs.getNames(this.of);
    System.arraycopy(arrayOfString2, 0, arrayOfString1, 74, arrayOfString2.length);
    System.arraycopy(PES4Utils.extraSquad, 17, arrayOfString1, 74 + arrayOfString2.length, 5);
    if (!this.normal) {
      arrayOfString1[(79 + arrayOfString2.length)] = "All Players";
    }
    this.teamBox.setModel(new DefaultComboBoxModel(arrayOfString1));
    if (this.normal)
    {
      this.teamBox.setSelectedIndex(74);
      this.numList.refresh(this.teamBox.getSelectedIndex());
      this.posList.refresh(this.teamBox.getSelectedIndex());
    }
    else
    {
      this.teamBox.setSelectedIndex(217);
    }
    this.squadList.refresh(this.teamBox.getSelectedIndex(), true);
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/SelectByTeam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */