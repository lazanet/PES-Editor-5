package editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.geom.*;
import java.awt.Graphics2D;
import java.awt.geom.Line2D.Double;
import javax.swing.Icon;
import javax.swing.SwingConstants;

public class CopySwapIcon
  implements Icon, SwingConstants
{
  private boolean swap = false;
  private int width = 10;
  private int height = 20;
  
  public CopySwapIcon(boolean paramBoolean)
  {
    this.swap = paramBoolean;
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
    localGraphics2D.draw(new Line2D.Double(5.0D, 0.0D, 5.0D, 20.0D));
    localGraphics2D.draw(new Line2D.Double(5.0D, 20.0D, 0.0D, 15.0D));
    localGraphics2D.draw(new Line2D.Double(5.0D, 20.0D, 10.0D, 15.0D));
    if (this.swap)
    {
      localGraphics2D.draw(new Line2D.Double(5.0D, 0.0D, 0.0D, 5.0D));
      localGraphics2D.draw(new Line2D.Double(5.0D, 0.0D, 10.0D, 5.0D));
    }
    localGraphics2D.translate(-paramInt1, -paramInt2);
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/CopySwapIcon.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
