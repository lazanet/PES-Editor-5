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
        ShopPanel.this.of.data[5144] = -1;
        ShopPanel.this.of.data[5145] = -1;
        ShopPanel.this.of.data[5146] = 3;
        ShopPanel.this.of.data[5148] = -1;
        ShopPanel.this.of.data[5149] = -1;
        ShopPanel.this.of.data[5150] = -1;
        ShopPanel.this.of.data[5151] = -1;
        ShopPanel.this.of.data[5152] = -1;
        ShopPanel.this.of.data[5153] = -1;
        ShopPanel.this.of.data[5154] = -1;
        ShopPanel.this.of.data[5155] = -1;
        ShopPanel.this.of.data[5156] = -1;
        ShopPanel.this.of.data[5157] = -1;
        ShopPanel.this.of.data[5158] = -1;
        ShopPanel.this.of.data[5159] = -1;
        ShopPanel.this.of.data[5160] = -1;
        ShopPanel.this.of.data[5161] = -1;
        ShopPanel.this.of.data[5162] = -1;
        ShopPanel.this.of.data[5163] = -1;
        ShopPanel.this.of.data[5164] = Byte.MAX_VALUE;
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
