package editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Icon;
import javax.swing.SwingConstants;

public class DefaultIcon
  implements Icon, SwingConstants
{
  private int width = 64;
  private int height = 64;
  
  public int getIconHeight()
  {
    return this.height;
  }
  
  public int getIconWidth()
  {
    return this.width;
  }
  
  public void paintIcon(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2)
  {
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
    localGraphics2D.translate(paramInt1, paramInt2);
    localGraphics2D.setFont(new Font("Dialog", 1, 18));
    localGraphics2D.setPaint(Color.black);
    localGraphics2D.drawString("Default", 0, 38);
    localGraphics2D.translate(-paramInt1, -paramInt2);
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/DefaultIcon.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */