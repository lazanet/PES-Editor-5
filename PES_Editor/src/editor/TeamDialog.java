package editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class TeamDialog
  extends JDialog
  implements WindowListener
{
  OptionFile of;
  FormPanel formPan;
  byte[] original = new byte['ɴ'];
  int squadIndex;
  
  public TeamDialog(Frame paramFrame, OptionFile paramOptionFile)
  {
    super(paramFrame, "Edit Formation", true);
    addWindowListener(this);
    this.of = paramOptionFile;
    this.formPan = new FormPanel(this.of);
    JButton localJButton1 = new JButton("Accept");
    localJButton1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        TeamDialog.this.setVisible(false);
      }
    });
    JButton localJButton2 = new JButton("Cancel");
    localJButton2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        int i = 676738 + TeamDialog.this.squadIndex * 628;
        System.arraycopy(TeamDialog.this.original, 0, TeamDialog.this.of.data, i, 628);
        TeamDialog.this.setVisible(false);
      }
    });
    JPanel localJPanel1 = new JPanel();
    JPanel localJPanel2 = new JPanel(new BorderLayout());
    localJPanel1.add(localJButton1);
    localJPanel1.add(localJButton2);
    localJPanel2.add(this.formPan, "Center");
    localJPanel2.add(localJPanel1, "South");
    getContentPane().add(localJPanel2);
    pack();
    setResizable(false);
  }
  
  public void show(int paramInt, String paramString)
  {
    setTitle("Edit Formation - " + paramString);
    this.squadIndex = paramInt;
    int i = 676738 + paramInt * 628;
    System.arraycopy(this.of.data, i, this.original, 0, 628);
    this.formPan.refresh(paramInt);
    setVisible(true);
  }
  
  public void windowClosing(WindowEvent paramWindowEvent)
  {
    int i = 676738 + this.squadIndex * 628;
    System.arraycopy(this.original, 0, this.of.data, i, 628);
  }
  
  public void windowClosed(WindowEvent paramWindowEvent) {}
  
  public void windowActivated(WindowEvent paramWindowEvent) {}
  
  public void windowDeactivated(WindowEvent paramWindowEvent) {}
  
  public void windowDeiconified(WindowEvent paramWindowEvent) {}
  
  public void windowIconified(WindowEvent paramWindowEvent) {}
  
  public void windowOpened(WindowEvent paramWindowEvent) {}
}


/* Location:              PESFan Editor 5.08.jar!/editor/TeamDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */