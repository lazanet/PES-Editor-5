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
import javax.swing.JPanel;

public class EmblemChooserDialog
  extends JDialog
{
  private JButton[] flagButton;
  private boolean trans = true;
  private OptionFile of;
  int slot;
  byte max;
  int type;
  
  public EmblemChooserDialog(Frame paramFrame, OptionFile paramOptionFile)
  {
    super(paramFrame, true);
    this.of = paramOptionFile;
    this.max = 100;
    JPanel localJPanel = new JPanel(new GridLayout(10, 10));
    this.flagButton = new JButton[this.max];
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
          EmblemChooserDialog.this.slot = new Integer(((JButton)paramAnonymousActionEvent.getSource()).getActionCommand()).intValue();
          if (EmblemChooserDialog.this.slot >= Flags.count16(EmblemChooserDialog.this.of)) {
            EmblemChooserDialog.this.slot = (99 - EmblemChooserDialog.this.slot);
          } else {
            EmblemChooserDialog.this.slot += 50;
          }
          EmblemChooserDialog.this.setVisible(false);
        }
      });
      localJPanel.add(this.flagButton[i]);
    }
    PES4Utils.systemUI();
    JButton localJButton = new JButton("Transparency");
    localJButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        EmblemChooserDialog.this.trans = (!EmblemChooserDialog.this.trans);
        EmblemChooserDialog.this.refresh();
      }
    });
    CancelButton localCancelButton = new CancelButton(this);
    getContentPane().add(localJButton, "North");
    getContentPane().add(localCancelButton, "South");
    getContentPane().add(localJPanel, "Center");
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
    refresh();
    setVisible(true);
    return this.slot;
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/EmblemChooserDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
