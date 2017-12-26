package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LeaguePanel
  extends JPanel
  implements ActionListener, ListSelectionListener
{
  private OptionFile of;
  private JTextField editor;
  private JList list;
  
  public LeaguePanel(OptionFile paramOptionFile)
  {
    this.of = paramOptionFile;
    init();
  }
  
  public void init()
  {
    this.editor = new JTextField(15);
    this.editor.setToolTipText("Enter new name and press return");
    this.editor.addActionListener(this);
    this.list = new JList();
    this.list.addListSelectionListener(this);
    JPanel localJPanel = new JPanel();
    localJPanel.setBorder(BorderFactory.createTitledBorder("League Names"));
    localJPanel.add(this.list);
    localJPanel.add(this.editor);
    add(localJPanel);
  }
  
  public void refresh()
  {
    this.list.setListData(Leagues.get(this.of));
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    int i = this.list.getSelectedIndex();
    String str = this.editor.getText();
    if ((i != -1) && (str.length() <= Leagues.maxLen) && (str.length() > 0))
    {
      if (!str.equals(Leagues.get(this.of, i)))
      {
        Leagues.set(this.of, i, str);
        refresh();
      }
      if (i < Leagues.total - 1) {
        this.list.setSelectedIndex(i + 1);
      }
    }
  }
  
  public void valueChanged(ListSelectionEvent paramListSelectionEvent)
  {
    if (!paramListSelectionEvent.getValueIsAdjusting())
    {
      int i = this.list.getSelectedIndex();
      if (i == -1)
      {
        this.editor.setText("");
      }
      else
      {
        this.editor.setText(Leagues.get(this.of, i));
        this.editor.selectAll();
      }
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/LeaguePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */