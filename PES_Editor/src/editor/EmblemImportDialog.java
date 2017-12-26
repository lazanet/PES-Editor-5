package editor;

import java.awt.BorderLayout;
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

public class EmblemImportDialog
  extends JDialog
{
  private JButton[] flagButton;
  private boolean trans = true;
  OptionFile of;
  int slot;
  byte max;
  int type;
  boolean of2Open = false;
  JLabel fileLabel;
  
  public EmblemImportDialog(Frame paramFrame, OptionFile paramOptionFile)
  {
    super(paramFrame, true);
    this.of = paramOptionFile;
    this.max = 100;
    JPanel localJPanel1 = new JPanel(new GridLayout(10, 10));
    this.flagButton = new JButton[this.max];
    this.fileLabel = new JLabel("From:");
    PES4Utils.javaUI();
    for (int i = 0; i < this.max; i++)
    {
      this.flagButton[i] = new JButton(new ImageIcon(Flags.get16(this.of, -1, false, true)));
      this.flagButton[i].setMargin(new Insets(0, 0, 0, 0));
      this.flagButton[i].setActionCommand(new Integer(i).toString());
      this.flagButton[i].addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent paramAnonymousActionEvent)
        {
          EmblemImportDialog.this.slot = new Integer(((JButton)paramAnonymousActionEvent.getSource()).getActionCommand()).intValue();
          if (EmblemImportDialog.this.slot >= Flags.count16(EmblemImportDialog.this.of)) {
            EmblemImportDialog.this.slot = (99 - EmblemImportDialog.this.slot);
          } else {
            EmblemImportDialog.this.slot += 50;
          }
          EmblemImportDialog.this.setVisible(false);
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
        EmblemImportDialog.this.trans = (!EmblemImportDialog.this.trans);
        EmblemImportDialog.this.refresh();
      }
    });
    CancelButton localCancelButton = new CancelButton(this);
    JPanel localJPanel2 = new JPanel(new BorderLayout());
    localJPanel2.add(localJButton, "North");
    localJPanel2.add(localCancelButton, "South");
    localJPanel2.add(localJPanel1, "Center");
    getContentPane().add(this.fileLabel, "North");
    getContentPane().add(localJPanel2, "Center");
    this.slot = -1;
    setResizable(false);
    pack();
  }
  
  public void refresh()
  {
    if ((this.type == 0) || (this.type == 1)) {
      for (int i = 0; i < Flags.count16(this.of); i++)
      {
        this.flagButton[i].setIcon(new ImageIcon(Flags.get16(this.of, i, !this.trans, true)));
        this.flagButton[i].setVisible(true);
      }
    }
    if ((this.type == 0) || (this.type == 2)) {
      for (int i = 0; i < Flags.count128(this.of); i++)
      {
        this.flagButton[(99 - i)].setIcon(new ImageIcon(Flags.get128(this.of, i, !this.trans, true)));
        this.flagButton[(99 - i)].setVisible(true);
      }
    }
    int i = Flags.count16(this.of);
    int j = 100 - Flags.count128(this.of);
    if (this.type == 1) {
      j = 100;
    }
    if (this.type == 2) {
      i = 0;
    }
    for (int k = i; k < j; k++) {
      this.flagButton[k].setVisible(false);
    }
  }
  
  public int getFlag(String paramString, int paramInt)
  {
    this.type = paramInt;
    this.slot = -1;
    setTitle(paramString);
    this.fileLabel.setText("  From:  " + this.of.fileName);
    refresh();
    setVisible(true);
    return this.slot;
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/EmblemImportDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
