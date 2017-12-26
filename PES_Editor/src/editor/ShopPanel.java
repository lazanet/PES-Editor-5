package editor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShopPanel
  extends JPanel
{
  OptionFile of;
  JLabel status;
  JButton unlock;
  
  public ShopPanel(OptionFile paramOptionFile)
  {
    this.of = paramOptionFile;
    JPanel localJPanel1 = new JPanel(new GridLayout(0, 1));
    localJPanel1.setBorder(BorderFactory.createTitledBorder("Shop Items"));
    JPanel localJPanel2 = new JPanel();
    this.status = new JLabel("");
    this.unlock = new JButton("Unlock");
    this.unlock.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        ShopPanel.this.of.data['ᐘ'] = -1;
        ShopPanel.this.of.data['ᐙ'] = -1;
        ShopPanel.this.of.data['ᐚ'] = 3;
        ShopPanel.this.of.data['ᐜ'] = -1;
        ShopPanel.this.of.data['ᐝ'] = -1;
        ShopPanel.this.of.data['ᐞ'] = -1;
        ShopPanel.this.of.data['ᐟ'] = -1;
        ShopPanel.this.of.data['ᐠ'] = -1;
        ShopPanel.this.of.data['ᐡ'] = -1;
        ShopPanel.this.of.data['ᐢ'] = -1;
        ShopPanel.this.of.data['ᐣ'] = -1;
        ShopPanel.this.of.data['ᐤ'] = -1;
        ShopPanel.this.of.data['ᐥ'] = -1;
        ShopPanel.this.of.data['ᐦ'] = -1;
        ShopPanel.this.of.data['ᐧ'] = -1;
        ShopPanel.this.of.data['ᐨ'] = -1;
        ShopPanel.this.of.data['ᐩ'] = -1;
        ShopPanel.this.of.data['ᐪ'] = -1;
        ShopPanel.this.of.data['ᐫ'] = -1;
        ShopPanel.this.of.data['ᐬ'] = Byte.MAX_VALUE;
        ShopPanel.this.of.data[52] = 100;
        ShopPanel.this.status.setText("Unlocked");
      }
    });
    localJPanel2.add(this.unlock);
    localJPanel1.add(localJPanel2);
    localJPanel1.add(this.status);
    add(localJPanel1);
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/ShopPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */