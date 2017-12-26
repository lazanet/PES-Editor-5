package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class StadiumPanel
  extends JPanel
  implements ActionListener, ListSelectionListener
{
  private OptionFile of;
  private JTextField editor;
  private JList list;
  private TeamPanel teamPanel;
  
  public StadiumPanel(OptionFile paramOptionFile, TeamPanel paramTeamPanel)
  {
    this.of = paramOptionFile;
    this.teamPanel = paramTeamPanel;
    init();
  }
  
  public void init()
  {
    this.editor = new JTextField(15);
    this.editor.setToolTipText("Enter new name and press return");
    this.editor.addActionListener(this);
    this.list = new JList();
    this.list.addListSelectionListener(this);
    this.list.setVisibleRowCount(30);
    JScrollPane localJScrollPane = new JScrollPane(20, 31);
    localJScrollPane.setViewportView(this.list);
    JPanel localJPanel = new JPanel();
    localJPanel.setBorder(BorderFactory.createTitledBorder("Stadium Names"));
    localJPanel.add(localJScrollPane);
    localJPanel.add(this.editor);
    add(localJPanel);
  }
  
  public void refresh()
  {
    this.list.setListData(Stadia.get(this.of));
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    int i = this.list.getSelectedIndex();
    String str = this.editor.getText();
    if ((i != -1) && (str.length() < Stadia.maxLen) && (str.length() > 0))
    {
      Stadia.set(this.of, i, str);
      this.teamPanel.refresh();
      refresh();
      if (i < Stadia.total - 1)
      {
        this.list.setSelectedIndex(i + 1);
        this.list.ensureIndexIsVisible(this.list.getSelectedIndex());
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
        this.editor.setText(Stadia.get(this.of, i));
        this.editor.selectAll();
      }
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/StadiumPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */