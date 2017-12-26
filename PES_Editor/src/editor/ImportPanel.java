package editor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImportPanel
  extends JPanel
{
  JLabel importFile;
  JPanel panel;
  private OptionFile of;
  private OptionFile of2;
  private JButton optionsButton;
  private JButton stadiumButton;
  private JButton leagueButton;
  private JButton bootsButton;
  private JButton clubNameButton;
  private JButton playerButton;
  private JButton allKitButton;
  private WENShopPanel wenShop;
  private StadiumPanel stadPan;
  private LeaguePanel leaguePan;
  private TeamPanel teamPan;
  FlagPanel flagPan;
  ImagePanel imagePan;
  private TransferPanel tranPan;
  
  public ImportPanel(OptionFile paramOptionFile1, OptionFile paramOptionFile2, WENShopPanel paramWENShopPanel, StadiumPanel paramStadiumPanel, LeaguePanel paramLeaguePanel, TeamPanel paramTeamPanel, FlagPanel paramFlagPanel, ImagePanel paramImagePanel, TransferPanel paramTransferPanel)
  {
    super(new BorderLayout());
    this.of = paramOptionFile1;
    this.of2 = paramOptionFile2;
    this.wenShop = paramWENShopPanel;
    this.stadPan = paramStadiumPanel;
    this.leaguePan = paramLeaguePanel;
    this.teamPan = paramTeamPanel;
    this.flagPan = paramFlagPanel;
    this.imagePan = paramImagePanel;
    this.tranPan = paramTransferPanel;
    JPanel localJPanel = new JPanel(new GridLayout(0, 1));
    this.importFile = new JLabel("To use the import options you must first use File > Open OF2...");
    this.panel = new JPanel();
    this.optionsButton = new JButton("Options / PES points / Shop items / Cup gallery / Memorial match data");
    this.optionsButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        System.arraycopy(ImportPanel.this.of2.data, OptionFile.block[0], ImportPanel.this.of.data, OptionFile.block[0], OptionFile.blockSize[0]);
        System.arraycopy(ImportPanel.this.of2.data, OptionFile.block[1], ImportPanel.this.of.data, OptionFile.block[1], OptionFile.blockSize[1]);
        System.arraycopy(ImportPanel.this.of2.data, OptionFile.block[9], ImportPanel.this.of.data, OptionFile.block[9], OptionFile.blockSize[9]);
        ImportPanel.this.wenShop.wenPanel.refresh();
        ImportPanel.this.wenShop.shopPanel.status.setText("");
        ImportPanel.this.optionsButton.setEnabled(false);
      }
    });
    this.stadiumButton = new JButton("Stadium names");
    this.stadiumButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        System.arraycopy(ImportPanel.this.of2.data, 9508, ImportPanel.this.of.data, 9508, 2170);
        ImportPanel.this.stadPan.refresh();
        ImportPanel.this.teamPan.refresh();
        ImportPanel.this.stadiumButton.setEnabled(false);
      }
    });
    this.leagueButton = new JButton("League names");
    this.leagueButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        System.arraycopy(ImportPanel.this.of2.data, 11678, ImportPanel.this.of.data, 11678, 2354);
        ImportPanel.this.leaguePan.refresh();
        ImportPanel.this.leagueButton.setEnabled(false);
      }
    });
    this.bootsButton = new JButton("Boots");
    this.bootsButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        System.arraycopy(ImportPanel.this.of2.data, 656872, ImportPanel.this.of.data, 656872, 828);
        ImportPanel.this.bootsButton.setEnabled(false);
      }
    });
    this.playerButton = new JButton("Players / Squads / Formations");
    this.playerButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        System.arraycopy(ImportPanel.this.of2.data, OptionFile.block[3], ImportPanel.this.of.data, OptionFile.block[3], OptionFile.blockSize[3]);
        System.arraycopy(ImportPanel.this.of2.data, OptionFile.block[4], ImportPanel.this.of.data, OptionFile.block[4], OptionFile.blockSize[4]);
        System.arraycopy(ImportPanel.this.of2.data, OptionFile.block[5], ImportPanel.this.of.data, OptionFile.block[5], OptionFile.blockSize[5]);
        ImportPanel.this.tranPan.refresh();
        ImportPanel.this.playerButton.setEnabled(false);
      }
    });
    this.clubNameButton = new JButton("Club names");
    this.clubNameButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        Clubs.importNames(ImportPanel.this.of, ImportPanel.this.of2);
        ImportPanel.this.teamPan.refresh();
        ImportPanel.this.tranPan.refresh();
        ImportPanel.this.clubNameButton.setEnabled(false);
      }
    });
    this.allKitButton = new JButton("Kits / Emblems / Logos / Club stadiums, flags, colours");
    this.allKitButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        Clubs.importData(ImportPanel.this.of, ImportPanel.this.of2);
        System.arraycopy(ImportPanel.this.of2.data, OptionFile.block[7], ImportPanel.this.of.data, OptionFile.block[7], OptionFile.blockSize[7]);
        System.arraycopy(ImportPanel.this.of2.data, OptionFile.block[8], ImportPanel.this.of.data, OptionFile.block[8], OptionFile.blockSize[8]);
        ImportPanel.this.flagPan.refresh();
        ImportPanel.this.imagePan.refresh();
        ImportPanel.this.teamPan.refresh();
        ImportPanel.this.tranPan.refresh();
        ImportPanel.this.allKitButton.setEnabled(false);
      }
    });
    localJPanel.add(this.optionsButton);
    localJPanel.add(this.stadiumButton);
    localJPanel.add(this.leagueButton);
    localJPanel.add(this.bootsButton);
    localJPanel.add(this.playerButton);
    localJPanel.add(this.clubNameButton);
    localJPanel.add(this.allKitButton);
    this.panel.add(localJPanel);
    add(this.importFile, "North");
    add(this.panel, "Center");
    this.panel.setEnabled(false);
  }
  
  public void refresh()
  {
    if (this.of2.fileName == null)
    {
      this.panel.setVisible(false);
      this.importFile.setText("To use the import options you must first use File > Open OF2...");
    }
    else
    {
      this.importFile.setText("From:  " + this.of2.fileName);
      this.panel.setVisible(true);
      if ((this.of.format == this.of2.format) && (this.of.game == this.of2.game)) {
        this.optionsButton.setEnabled(true);
      } else {
        this.optionsButton.setEnabled(false);
      }
      this.stadiumButton.setEnabled(true);
      this.leagueButton.setEnabled(true);
      this.bootsButton.setEnabled(true);
      this.clubNameButton.setEnabled(true);
      this.playerButton.setEnabled(true);
      this.allKitButton.setEnabled(true);
    }
  }
  
  public void disableAll()
  {
    this.optionsButton.setEnabled(false);
    this.stadiumButton.setEnabled(false);
    this.leagueButton.setEnabled(false);
    this.bootsButton.setEnabled(false);
    this.clubNameButton.setEnabled(false);
    this.playerButton.setEnabled(false);
    this.allKitButton.setEnabled(false);
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/ImportPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */