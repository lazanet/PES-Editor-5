package editor;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;

public class HelpDialog
  extends JDialog
{
  private JEditorPane editpane;
  private URL helpURL;
  
  public HelpDialog(Frame paramFrame)
  {
    super(paramFrame, "PESFan Editor Help", false);
    JScrollPane localJScrollPane = new JScrollPane(20, 31);
    this.helpURL = Editor.class.getResource("help/index.html");
    JButton localJButton = new JButton("Close Help");
    localJButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        HelpDialog.this.setVisible(false);
      }
    });
    this.editpane = new JEditorPane();
    this.editpane.setEditable(false);
    this.editpane.addHyperlinkListener(new MyHyperlinkListener());
    localJScrollPane.setViewportView(this.editpane);
    showPage();
    localJScrollPane.setPreferredSize(new Dimension(430, 550));
    getContentPane().add(localJScrollPane, "Center");
    getContentPane().add(localJButton, "South");
    pack();
  }
  
  public void showPage()
  {
    if (this.helpURL != null) {
      try
      {
        this.editpane.setPage(this.helpURL);
      }
      catch (IOException localIOException)
      {
        System.out.println("IOExecption while loading help page.");
      }
    } else {
      System.out.println("null.");
    }
  }
  
  private class MyHyperlinkListener
    implements HyperlinkListener
  {
    MyHyperlinkListener() {}
    
    public void hyperlinkUpdate(HyperlinkEvent paramHyperlinkEvent)
    {
      if (paramHyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
        try
        {
          HelpDialog.this.editpane.setPage(paramHyperlinkEvent.getURL());
        }
        catch (Exception localException) {}
      }
    }
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/HelpDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */