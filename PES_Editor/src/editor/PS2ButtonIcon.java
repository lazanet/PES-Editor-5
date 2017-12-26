package editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Icon;
import java.awt.geom.*;
import javax.swing.SwingConstants;

public class PS2ButtonIcon
  implements Icon, SwingConstants
{
  private int type = 0;
  private int width = 17;
  private int height = 17;
  
  public PS2ButtonIcon(int paramInt)
  {
    this.type = paramInt;
  }
  
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
    localGraphics2D.setPaint(Color.black);
    localGraphics2D.fill(new Ellipse2D.Double(1.0D, 1.0D, 15.0D, 15.0D));
    if (this.type == 0)
    {
      localGraphics2D.setPaint(Color.pink);
      localGraphics2D.draw(new Rectangle2D.Double(4.0D, 4.0D, 8.0D, 8.0D));
    }
    if (this.type == 1)
    {
      localGraphics2D.setPaint(Color.green);
      localGraphics2D.draw(new Line2D.Double(8.0D, 4.0D, 12.0D, 12.0D));
      localGraphics2D.draw(new Line2D.Double(4.0D, 12.0D, 12.0D, 12.0D));
      localGraphics2D.draw(new Line2D.Double(8.0D, 4.0D, 4.0D, 12.0D));
    }
    if (this.type == 2)
    {
      localGraphics2D.setPaint(Color.red);
      localGraphics2D.draw(new Ellipse2D.Double(4.0D, 4.0D, 8.0D, 8.0D));
    }
    if (this.type == 3)
    {
      localGraphics2D.setPaint(Color.cyan);
      localGraphics2D.draw(new Line2D.Double(4.0D, 4.0D, 12.0D, 12.0D));
      localGraphics2D.draw(new Line2D.Double(4.0D, 12.0D, 12.0D, 4.0D));
    }
    localGraphics2D.translate(-paramInt1, -paramInt2);
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/PS2ButtonIcon.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
