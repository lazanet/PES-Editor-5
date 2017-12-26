package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class AttDefPanel
  extends JPanel
  implements MouseListener
{
  OptionFile of;
  int squad = 0;
  int selected = -1;
  PitchPanel pitch;
  Rectangle2D[] attSqu = new Rectangle2D[8];
  JComboBox altBox;
  
  public AttDefPanel(OptionFile paramOptionFile, JComboBox paramJComboBox)
  {
    this.of = paramOptionFile;
    this.altBox = paramJComboBox;
    setOpaque(true);
    setPreferredSize(new Dimension(98, 98));
    setBackground(Color.black);
    addMouseListener(this);
    this.attSqu[0] = new Rectangle2D.Double(0.0D, 42.0D, 14.0D, 14.0D);
    this.attSqu[1] = new Rectangle2D.Double(0.0D, 0.0D, 14.0D, 14.0D);
    this.attSqu[2] = new Rectangle2D.Double(42.0D, 0.0D, 14.0D, 14.0D);
    this.attSqu[3] = new Rectangle2D.Double(84.0D, 0.0D, 14.0D, 14.0D);
    this.attSqu[4] = new Rectangle2D.Double(84.0D, 42.0D, 14.0D, 14.0D);
    this.attSqu[5] = new Rectangle2D.Double(84.0D, 84.0D, 14.0D, 14.0D);
    this.attSqu[6] = new Rectangle2D.Double(42.0D, 84.0D, 14.0D, 14.0D);
    this.attSqu[7] = new Rectangle2D.Double(0.0D, 84.0D, 14.0D, 14.0D);
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
    if (this.selected == -1)
    {
      localGraphics2D.setPaint(Color.gray);
      localGraphics2D.fill(new Rectangle2D.Double(0.0D, 0.0D, 98.0D, 98.0D));
    }
    else
    {
      localGraphics2D.setPaint(Color.black);
      localGraphics2D.fill(new Rectangle2D.Double(0.0D, 0.0D, 98.0D, 98.0D));
      int i = this.of.data[(670641 + 628 * this.squad + 6232 + this.selected + this.altBox.getSelectedIndex() * 171)];
      if (i == 0) {
        localGraphics2D.setPaint(Color.yellow);
      } else if ((i > 0) && (i < 10)) {
        localGraphics2D.setPaint(Color.cyan);
      } else if ((i > 9) && (i < 29)) {
        localGraphics2D.setPaint(Color.green);
      } else if ((i > 28) && (i < 41)) {
        localGraphics2D.setPaint(Color.red);
      }
      localGraphics2D.fill(new Ellipse2D.Double(42.0D, 42.0D, 14.0D, 14.0D));
      for (int k = 0; k < 8; k++)
      {
        int j = 670653 + 628 * this.squad + 6232 + this.selected * 9 + k;
        if (this.of.data[(j + this.altBox.getSelectedIndex() * 171)] == 1)
        {
          if (i == 0) {
            localGraphics2D.setPaint(Color.yellow);
          } else if ((i > 0) && (i < 10)) {
            localGraphics2D.setPaint(Color.cyan);
          } else if ((i > 9) && (i < 29)) {
            localGraphics2D.setPaint(Color.green);
          } else if ((i > 28) && (i < 41)) {
            localGraphics2D.setPaint(Color.red);
          }
          localGraphics2D.fill(this.attSqu[k]);
        }
        else
        {
          localGraphics2D.setPaint(Color.gray);
          if (this.selected != 0) {
            localGraphics2D.draw(this.attSqu[k]);
          } else if ((k == 0) || (k == 4)) {
            localGraphics2D.draw(this.attSqu[k]);
          }
        }
      }
      int j = 670751 + 628 * this.squad + 6232 + this.selected;
      if (this.of.data[(j + this.altBox.getSelectedIndex() * 171)] == 1)
      {
        localGraphics2D.setPaint(Color.gray);
        localGraphics2D.draw(new Ellipse2D.Double(21.0D, 21.0D, 14.0D, 14.0D));
        localGraphics2D.setPaint(Color.blue);
        localGraphics2D.fill(new Ellipse2D.Double(21.0D, 63.0D, 14.0D, 14.0D));
      }
      else if (this.of.data[(j + this.altBox.getSelectedIndex() * 171)] == 0)
      {
        localGraphics2D.setPaint(Color.blue);
        localGraphics2D.fill(new Ellipse2D.Double(21.0D, 21.0D, 14.0D, 14.0D));
        localGraphics2D.fill(new Ellipse2D.Double(21.0D, 63.0D, 14.0D, 14.0D));
      }
      else
      {
        localGraphics2D.setPaint(Color.gray);
        localGraphics2D.draw(new Ellipse2D.Double(21.0D, 21.0D, 14.0D, 14.0D));
        localGraphics2D.draw(new Ellipse2D.Double(21.0D, 63.0D, 14.0D, 14.0D));
      }
    }
  }
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    int i = 0;
    int j = 1;
    int m;
    int k;
    if (new Ellipse2D.Double(42.0D, 42.0D, 14.0D, 14.0D).contains(paramMouseEvent.getX(), paramMouseEvent.getY()))
    {
      for (m = 0; m < 8; m++)
      {
        k = 670653 + 628 * this.squad + 6232 + this.selected * 9 + m;
        this.of.data[(k + this.altBox.getSelectedIndex() * 171)] = 0;
      }
      k = 670751 + 628 * this.squad + 6232 + this.selected;
      this.of.data[(k + this.altBox.getSelectedIndex() * 171)] = 2;
    }
    else if (new Ellipse2D.Double(21.0D, 21.0D, 14.0D, 14.0D).contains(paramMouseEvent.getX(), paramMouseEvent.getY()))
    {
      k = 670751 + 628 * this.squad + 6232 + this.selected;
      if (this.of.data[(k + this.altBox.getSelectedIndex() * 171)] == 0) {
        this.of.data[(k + this.altBox.getSelectedIndex() * 171)] = 1;
      } else {
        this.of.data[(k + this.altBox.getSelectedIndex() * 171)] = 0;
      }
    }
    else if (new Ellipse2D.Double(21.0D, 63.0D, 14.0D, 14.0D).contains(paramMouseEvent.getX(), paramMouseEvent.getY()))
    {
      k = 670751 + 628 * this.squad + 6232 + this.selected;
      if (this.of.data[(k + this.altBox.getSelectedIndex() * 171)] == 2) {
        this.of.data[(k + this.altBox.getSelectedIndex() * 171)] = 1;
      } else {
        this.of.data[(k + this.altBox.getSelectedIndex() * 171)] = 2;
      }
    }
    else
    {
      for (m = 0; m < 8; m++)
      {
        k = 670653 + 628 * this.squad + 6232 + this.selected * 9 + m;
        if (this.of.data[(k + this.altBox.getSelectedIndex() * 171)] == 1) {
          i++;
        }
      }
      for (m = 0; (j & (m < 8 ? 1 : 0)) != 0; m++) {
        if (this.attSqu[m].contains(paramMouseEvent.getX(), paramMouseEvent.getY()))
        {
          j = 0;
          k = 670653 + 628 * this.squad + 6232 + this.selected * 9 + m;
          if (this.of.data[(k + this.altBox.getSelectedIndex() * 171)] == 1) {
            this.of.data[(k + this.altBox.getSelectedIndex() * 171)] = 0;
          } else if ((this.of.data[(k + this.altBox.getSelectedIndex() * 171)] == 0) && (i < 2)) {
            if (this.selected != 0) {
              this.of.data[(k + this.altBox.getSelectedIndex() * 171)] = 1;
            } else if ((m == 0) || (m == 4)) {
              this.of.data[(k + this.altBox.getSelectedIndex() * 171)] = 1;
            }
          }
        }
      }
    }
    repaint();
    this.pitch.repaint();
  }
  
  public void mouseReleased(MouseEvent paramMouseEvent) {}
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
}


/* Location:              PESFan Editor 5.08.jar!/editor/AttDefPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
