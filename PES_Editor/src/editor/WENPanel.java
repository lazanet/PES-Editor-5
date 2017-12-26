package editor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WENPanel
  extends JPanel
  implements ActionListener
{
  OptionFile of;
  JLabel current;
  JTextField field;
  
  public WENPanel(OptionFile paramOptionFile)
  {
    this.of = paramOptionFile;
    JPanel localJPanel = new JPanel(new GridLayout(0, 1));
    localJPanel.setBorder(BorderFactory.createTitledBorder("PES Points"));
    this.current = new JLabel("");
    this.field = new JTextField(8);
    this.field.setToolTipText("Enter an amount (0-99999) and press return");
    this.field.addActionListener(this);
    localJPanel.add(this.field);
    localJPanel.add(this.current);
    add(localJPanel);
    refresh();
  }
  
  public void refresh()
  {
    int i = (this.of.toInt(this.of.data[50]) << 16) + (this.of.toInt(this.of.data[49]) << 8) + this.of.toInt(this.of.data[48]);
    this.current.setText("Current:  " + String.valueOf(i));
    this.field.setText("");
  }
  
  public void setWEN(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt <= 99999))
    {
      this.of.data[48] = this.of.toByte(paramInt & 0xFF);
      this.of.data[49] = this.of.toByte((paramInt & 0xFF00) >>> 8);
      this.of.data[50] = this.of.toByte((paramInt & 0xFF0000) >>> 16);
      this.of.data['ᑄ'] = this.of.toByte(paramInt & 0xFF);
      this.of.data['ᑅ'] = this.of.toByte((paramInt & 0xFF00) >>> 8);
      this.of.data['ᑆ'] = this.of.toByte((paramInt & 0xFF0000) >>> 16);
      refresh();
    }
    else
    {
      this.field.setText("");
      JOptionPane.showMessageDialog(null, "Amount must be in the range 0-99999", "Error", 0);
    }
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    try
    {
      setWEN(new Integer(this.field.getText()).intValue());
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/WENPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */