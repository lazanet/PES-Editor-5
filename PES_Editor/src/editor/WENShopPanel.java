package editor;

import java.awt.GridLayout;
import javax.swing.JPanel;

public class WENShopPanel
  extends JPanel
{
  WENPanel wenPanel;
  ShopPanel shopPanel;
  
  public WENShopPanel(OptionFile paramOptionFile)
  {
    JPanel localJPanel = new JPanel(new GridLayout(0, 1));
    this.wenPanel = new WENPanel(paramOptionFile);
    this.shopPanel = new ShopPanel(paramOptionFile);
    localJPanel.add(this.wenPanel);
    localJPanel.add(this.shopPanel);
    add(localJPanel);
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/WENShopPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */