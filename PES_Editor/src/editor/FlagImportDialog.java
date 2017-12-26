package editor;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FlagImportDialog
  extends JDialog
{
  private JButton[] flagButton;
  private boolean trans = true;
  private OptionFile of;
  private OptionFile of2;
  JLabel fileLabel;
  boolean of2Open;
  int slot;
  int replacement;
  byte max;
  int adr;
  int size;
  
  public FlagImportDialog(Frame paramFrame, OptionFile paramOptionFile1, OptionFile paramOptionFile2, boolean paramBoolean)
  {
    super(paramFrame, true);
    this.of = paramOptionFile1;
    this.of2 = paramOptionFile2;
    this.fileLabel = new JLabel("From:");
    this.max = Logos.total;
    JPanel localJPanel1 = new JPanel(new GridLayout(8, 10));
    this.flagButton = new JButton[this.max];
    PES4Utils.javaUI();
    for (int i = 0; i < this.max; i++)
    {
      this.flagButton[i] = new JButton(new ImageIcon(Logos.get(this.of, -1, false)));
      this.flagButton[i].setMargin(new Insets(0, 0, 0, 0));
      this.flagButton[i].setActionCommand(new Integer(i).toString());
      this.flagButton[i].addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent paramAnonymousActionEvent)
        {
          FlagImportDialog.this.replacement = new Integer(((JButton)paramAnonymousActionEvent.getSource()).getActionCommand()).intValue();
          FlagImportDialog.this.importFlag();
          FlagImportDialog.this.setVisible(false);
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
        FlagImportDialog.this.trans = (!FlagImportDialog.this.trans);
        FlagImportDialog.this.updateFlags();
      }
    });
    CancelButton localCancelButton = new CancelButton(this);
    JPanel localJPanel2 = new JPanel(new GridLayout(0, 1));
    localJPanel2.add(this.fileLabel);
    localJPanel2.add(localJButton);
    getContentPane().add(localJPanel2, "North");
    getContentPane().add(localCancelButton, "South");
    getContentPane().add(localJPanel1, "Center");
    this.of2Open = false;
    this.slot = 0;
    this.replacement = 0;
    setResizable(false);
    pack();
  }
  
  private void updateFlags()
  {
    for (int i = 0; i < Logos.total; i++) {
      this.flagButton[i].setIcon(new ImageIcon(Logos.get(this.of2, i, !this.trans)));
    }
  }
  
  public void refresh()
  {
    updateFlags();
    this.of2Open = true;
    this.slot = 0;
    this.replacement = 0;
    this.fileLabel.setText("  From:  " + this.of2.fileName);
  }
  
  public void show(int paramInt, String paramString)
  {
    setTitle(paramString);
    this.slot = paramInt;
    setVisible(true);
  }
  
  private void importFlag()
  {
    if (this.max == Flags.total) {
      Flags.importFlag(this.of2, this.replacement, this.of, this.slot);
    } else {
      Logos.importLogo(this.of2, this.replacement, this.of, this.slot);
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/FlagImportDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */