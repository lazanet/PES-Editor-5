package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.ListModel;

public class PitchPanel
  extends JPanel
  implements MouseListener, MouseMotionListener
{
  OptionFile of;
  SquadList list;
  AttDefPanel adPanel;
  int squad = 0;
  int selected = -1;
  boolean attack = true;
  boolean defence = true;
  boolean numbers = true;
  boolean roleOn = true;
  int adj = 14;
  Color[] colour = { new Color(0, 0, 0), new Color(255, 255, 255), new Color(255, 255, 0), new Color(0, 255, 255), new Color(0, 255, 0), new Color(255, 0, 0), new Color(0, 0, 255), Color.gray };
  JComboBox altBox;
  SquadNumList numList;
  int xadj = 0;
  int yadj = 0;
  
  public PitchPanel(OptionFile paramOptionFile, SquadList paramSquadList, AttDefPanel paramAttDefPanel, JComboBox paramJComboBox, SquadNumList paramSquadNumList)
  {
    this.of = paramOptionFile;
    this.list = paramSquadList;
    this.adPanel = paramAttDefPanel;
    this.altBox = paramJComboBox;
    this.numList = paramSquadNumList;
    setOpaque(true);
    setPreferredSize(new Dimension(329 + this.adj * 2, 200 + this.adj * 2));
    setBackground(Color.black);
    addMouseListener(this);
    addMouseMotionListener(this);
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
    localGraphics2D.setPaint(Color.black);
    localGraphics2D.fill(new Rectangle2D.Double(0.0D, 0.0D, 329 + this.adj * 2, 200 + this.adj * 2));
    localGraphics2D.setPaint(Color.gray);
    localGraphics2D.draw(new Rectangle2D.Double(13.0D, 13.0D, 331.0D, 202.0D));
    localGraphics2D.draw(new Line2D.Double(178.0D, 13.0D, 178.0D, 215.0D));
    localGraphics2D.draw(new Ellipse2D.Double(145.0D, 81.0D, 66.0D, 66.0D));
    localGraphics2D.draw(new Rectangle2D.Double(13.0D, 62.0D, 46.0D, 104.0D));
    localGraphics2D.draw(new Rectangle2D.Double(298.0D, 62.0D, 46.0D, 104.0D));
    localGraphics2D.draw(new Rectangle2D.Double(13.0D, 85.0D, 17.0D, 58.0D));
    localGraphics2D.draw(new Rectangle2D.Double(327.0D, 85.0D, 17.0D, 58.0D));
    localGraphics2D.draw(new Arc2D.Double(40.0D, 89.0D, 38.0D, 49.0D, 270.0D, 180.0D, 0));
    localGraphics2D.draw(new Arc2D.Double(279.0D, 89.0D, 38.0D, 49.0D, 90.0D, 180.0D, 0));
    for (int i1 = 0; i1 < 11; i1++)
    {
      int n = this.of.data[(670641 + 628 * this.squad + 6232 + i1 + this.altBox.getSelectedIndex() * 171)];
      int i;
      int j;
      if (i1 == 0)
      {
        i = 0 + this.adj;
        j = 90 + this.adj;
      }
      else
      {
        int k = 670620 + 628 * this.squad + 6232 + i1;
        int m = 670630 + 628 * this.squad + 6232 + i1;
        i = (this.of.data[(k + this.altBox.getSelectedIndex() * 171)] - 2) * 7 + this.adj;
        j = (this.of.data[(m + this.altBox.getSelectedIndex() * 171)] - 6) * 2 + this.adj;
      }
      if (i1 == this.selected) {
        localGraphics2D.setPaint(Color.white);
      } else if (n == 0) {
        localGraphics2D.setPaint(Color.yellow);
      } else if ((n > 0) && (n < 10)) {
        localGraphics2D.setPaint(Color.cyan);
      } else if ((n > 9) && (n < 29)) {
        localGraphics2D.setPaint(Color.green);
      } else if ((n > 28) && (n < 41)) {
        localGraphics2D.setPaint(Color.red);
      }
      localGraphics2D.fill(new Ellipse2D.Double(i, j, 14.0D, 14.0D));
      int i2;
      int i4;
      int i5;
      int i6;
      if (this.roleOn)
      {
        localGraphics2D.setFont(new Font("Dialog", 1, 10));
        i2 = 670653 + 628 * this.squad + 6232 + i1 * 9 + 2;
        i4 = 0;
        if ((n == 30) || (n == 16)) {
          i4 = -1;
        }
        i5 = this.of.data[(i2 + this.altBox.getSelectedIndex() * 171)] == 1 ? 1 : 0;
        i2 = 670653 + 628 * this.squad + 6232 + i1 * 9 + 6;
        i6 = this.of.data[(i2 + this.altBox.getSelectedIndex() * 171)] == 1 ? 1 : 0;
        if ((i5 != 0) && (i6 != 0))
        {
          localGraphics2D.drawString(getPosLabel(n).substring(0, 1), i + 15, j + 6);
          localGraphics2D.drawString(getPosLabel(n).substring(1, 2), i + 15, j + 16);
        }
        else if ((n == 9) || (n == 16) || (n == 23) || (n == 30))
        {
          if (i6 == 0) {
            localGraphics2D.drawString(getPosLabel(n), i + i4, j + 24);
          } else {
            localGraphics2D.drawString(getPosLabel(n), i + i4, j - 2);
          }
        }
        else if (i5 != 0)
        {
          localGraphics2D.drawString(getPosLabel(n), i + i4, j + 24);
        }
        else
        {
          localGraphics2D.drawString(getPosLabel(n), i + i4, j - 2);
        }
      }
      int i7;
      int i8;
      if (this.attack)
      {
        i2 = i + 7;
        i4 = j + 7;
        i5 = i2;
        i6 = i4;
        for (i7 = 0; i7 < 8; i7++)
        {
          i8 = 670653 + 628 * this.squad + 6232 + i1 * 9 + i7;
          if (this.of.data[(i8 + this.altBox.getSelectedIndex() * 171)] == 1)
          {
            switch (i7)
            {
            case 0: 
              i5 = i2 - 21;
              i6 = i4;
              break;
            case 1: 
              i5 = i2 - 15;
              i6 = i4 - 15;
              break;
            case 2: 
              i5 = i2;
              i6 = i4 - 21;
              break;
            case 3: 
              i5 = i2 + 15;
              i6 = i4 - 15;
              break;
            case 4: 
              i5 = i2 + 21;
              i6 = i4;
              break;
            case 5: 
              i5 = i2 + 15;
              i6 = i4 + 15;
              break;
            case 6: 
              i5 = i2;
              i6 = i4 + 21;
              break;
            case 7: 
              i5 = i2 - 15;
              i6 = i4 + 15;
            }
            localGraphics2D.draw(new Line2D.Double(i2, i4, i5, i6));
          }
        }
      }
      if (this.numbers)
      {
        localGraphics2D.setFont(new Font("Dialog", 1, 10));
        localGraphics2D.setPaint(Color.black);
        String str = (String)this.numList.getModel().getElementAt(i1);
        i4 = 0;
        if (str.length() == 1) {
          i4 = 3;
        }
        if (str.startsWith("1")) {
          i4--;
        }
        localGraphics2D.drawString(str, i + 2 + i4, j + 11);
      }
      if (this.defence)
      {
        localGraphics2D.setPaint(Color.blue);
        int i3 = 6;
        i4 = i + 7 - 13 - i3 / 2;
        i5 = j + 7 - 5 - i3 / 2;
        i6 = i + 7 - 13 - i3 / 2;
        i7 = j + 7 + 5 - i3 / 2;
        i8 = 670751 + 628 * this.squad + 6232 + i1;
        if (this.of.data[(i8 + this.altBox.getSelectedIndex() * 171)] == 1)
        {
          localGraphics2D.fill(new Ellipse2D.Double(i6, i7, i3, i3));
        }
        else if (this.of.data[(i8 + this.altBox.getSelectedIndex() * 171)] == 0)
        {
          localGraphics2D.fill(new Ellipse2D.Double(i4, i5, i3, i3));
          localGraphics2D.fill(new Ellipse2D.Double(i6, i7, i3, i3));
        }
      }
    }
  }
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    this.selected = -1;
    Ellipse2D.Double localDouble;
    for (int n = 1; (this.selected == -1) && (n < 11); n++)
    {
      int k = 670620 + 628 * this.squad + 6232 + n;
      int m = 670630 + 628 * this.squad + 6232 + n;
      int i = (this.of.data[(k + this.altBox.getSelectedIndex() * 171)] - 2) * 7 + this.adj;
      int j = (this.of.data[(m + this.altBox.getSelectedIndex() * 171)] - 6) * 2 + this.adj;
      localDouble = new Ellipse2D.Double(i, j, 14.0D, 14.0D);
      if (localDouble.contains(paramMouseEvent.getX(), paramMouseEvent.getY()))
      {
        this.selected = n;
        this.xadj = (paramMouseEvent.getX() - i);
        this.yadj = (paramMouseEvent.getY() - j);
      }
    }
    FormPanel.fromPitch = true;
    if (this.selected != -1)
    {
      this.list.setSelectedIndex(this.selected);
      this.adPanel.selected = this.selected;
    }
    else
    {
      localDouble = new Ellipse2D.Double(0 + this.adj, 90 + this.adj, 14.0D, 14.0D);
      if (localDouble.contains(paramMouseEvent.getX(), paramMouseEvent.getY()))
      {
        this.selected = 0;
        this.list.setSelectedIndex(this.selected);
        this.adPanel.selected = this.selected;
      }
      else
      {
        this.list.clearSelection();
        this.adPanel.selected = -1;
      }
    }
    repaint();
    this.adPanel.repaint();
  }
  
  public void mouseReleased(MouseEvent paramMouseEvent) {}
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    if (this.selected > 0)
    {
      int i = 670620 + 628 * this.squad + 6232 + this.selected;
      int j = 670630 + 628 * this.squad + 6232 + this.selected;
      int k = paramMouseEvent.getX() - this.xadj;
      int m = paramMouseEvent.getY() - this.yadj;
      int n = this.of.data[(670641 + 628 * this.squad + 6232 + this.selected + this.altBox.getSelectedIndex() * 171)];
      if (k < 0 + this.adj) {
        k = 0 + this.adj;
      }
      if (m < 0 + this.adj) {
        m = 0 + this.adj;
      }
      if (k > 315 + this.adj) {
        k = 315 + this.adj;
      }
      if (m > 186 + this.adj) {
        m = 186 + this.adj;
      }
      k = (k - this.adj) / 7 + 2;
      m = (m - this.adj) / 2 + 6;
      if ((n > 0) && (n < 10))
      {
        if (k > 15) {
          k = 15;
        }
      }
      else if ((n > 9) && (n < 29))
      {
        if (k < 16) {
          k = 16;
        } else if (k > 34) {
          k = 34;
        }
      }
      else if ((n > 28) && (n < 41) && (k < 35)) {
        k = 35;
      }
      if (((n == 8) || (n == 15) || (n == 22) || (n == 29)) && (m > 50)) {
        m = 50;
      }
      if (((n == 9) || (n == 16) || (n == 23) || (n == 30)) && (m < 54)) {
        m = 54;
      }
      this.of.data[(i + this.altBox.getSelectedIndex() * 171)] = ((byte)k);
      this.of.data[(j + this.altBox.getSelectedIndex() * 171)] = ((byte)m);
      repaint();
    }
  }
  
  public void mouseMoved(MouseEvent paramMouseEvent) {}
  
  private String getPosLabel(int paramInt)
  {
    String str = "";
    if (paramInt == 0) {
      str = "GK";
    }
    if ((paramInt > 0) && (paramInt < 8)) {
      str = "CB";
    }
    if (paramInt == 8) {
      str = "LB";
    }
    if (paramInt == 9) {
      str = "RB";
    }
    if ((paramInt > 9) && (paramInt < 15)) {
      str = "DM";
    }
    if (paramInt == 15) {
      str = "LW";
    }
    if (paramInt == 16) {
      str = "RW";
    }
    if ((paramInt > 16) && (paramInt < 22)) {
      str = "CM";
    }
    if (paramInt == 22) {
      str = "LM";
    }
    if (paramInt == 23) {
      str = "RM";
    }
    if ((paramInt > 23) && (paramInt < 29)) {
      str = "AM";
    }
    if (paramInt == 29) {
      str = "LW";
    }
    if (paramInt == 30) {
      str = "RW";
    }
    if ((paramInt > 30) && (paramInt < 36)) {
      str = "SS";
    }
    if ((paramInt > 35) && (paramInt < 41)) {
      str = "CF";
    }
    if (paramInt > 40) {
      str = String.valueOf(paramInt);
    }
    return str;
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/PitchPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
