package editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LogoChooserDialog
  extends JDialog
{
  private JButton[] flagButton;
  private boolean trans = true;
  private OptionFile of;
  byte slot;
  private JLabel repLabel;
  
  public LogoChooserDialog(Frame paramFrame, OptionFile paramOptionFile)
  {
    super(paramFrame, true);
    this.of = paramOptionFile;
    JPanel localJPanel1 = new JPanel(new GridLayout(8, 10));
    this.flagButton = new JButton[Logos.total];
    PES4Utils.javaUI();
    for (int i = 0; i < Logos.total; i++)
    {
      this.flagButton[i] = new JButton(new ImageIcon(Logos.get(this.of, -1, false)));
      this.flagButton[i].setMargin(new Insets(0, 0, 0, 0));
      this.flagButton[i].setActionCommand(new Integer(i).toString());
      this.flagButton[i].addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent paramAnonymousActionEvent)
        {
          LogoChooserDialog.this.slot = ((byte)new Integer(((JButton)paramAnonymousActionEvent.getSource()).getActionCommand()).intValue());
          LogoChooserDialog.this.setVisible(false);
        }
      });
      localJPanel1.add(this.flagButton[i]);
    }
    PES4Utils.systemUI();
    JButton localJButton = new JButton("Transparency");
    localJButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        LogoChooserDialog.this.trans = (!LogoChooserDialog.this.trans);
        LogoChooserDialog.this.refresh();
      }
    });
    CancelButton localCancelButton = new CancelButton(this);
    this.repLabel = new JLabel(new ImageIcon(Logos.get(this.of, -1, false)));
    JPanel localJPanel2 = new JPanel(new BorderLayout());
    localJPanel2.add(this.repLabel, "North");
    localJPanel2.add(localJPanel1, "Center");
    getContentPane().add(localJButton, "North");
    getContentPane().add(localCancelButton, "South");
    getContentPane().add(localJPanel2, "Center");
    this.slot = 88;
    setResizable(false);
    pack();
  }
  
  public void refresh()
  {
    for (int i = 0; i < Logos.total; i++) {
      this.flagButton[i].setIcon(new ImageIcon(Logos.get(this.of, i, !this.trans)));
    }
  }
  
  public byte getFlag(String paramString, Image paramImage)
  {
    this.slot = 88;
    setTitle(paramString);
    this.repLabel.setIcon(new ImageIcon(paramImage));
    refresh();
    setVisible(true);
    return this.slot;
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/LogoChooserDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */