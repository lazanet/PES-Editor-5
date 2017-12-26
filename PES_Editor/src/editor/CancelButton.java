package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;

public class CancelButton
  extends JButton
{
  public CancelButton(final JDialog paramJDialog)
  {
    super("Cancel");
    addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        paramJDialog.setVisible(false);
      }
    });
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/CancelButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */