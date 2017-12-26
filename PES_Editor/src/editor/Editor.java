package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;

public class Editor
  extends JFrame
{
  private JFileChooser chooser;
  private OptionFile of;
  private OptionFile of2;
  private File currentFile = null;
  private OptionFilterWE9 filter;
  protected FlagPanel flagPanel;
  protected ImagePanel imagePanel;
  protected TransferPanel tranPanel;
  protected WENShopPanel wenShop;
  protected StadiumPanel stadPan;
  protected TeamPanel teamPan;
  protected LeaguePanel leaguePan;
  JTabbedPane tabbedPane;
  PlayerImportDialog plImpDia;
  KitImportDialog kitImpDia;
  EmblemImportDialog flagImpDia;
  FlagImportDialog imageImpDia;
  PlayerDialog playerDia;
  Stats stats;
  Stats stats2;
  EmblemChooserDialog flagChooser;
  TeamDialog teamDia;
  ImportPanel importPanel;
  LogoChooserDialog logoChooser;
  private CSVMaker csvMaker;
  private JMenuItem csvItem;
  private JMenuItem psdItem;
  private JMenuItem open2Item;
  private JMenuItem saveItem;
  private JMenuItem saveAsItem;
  private JFileChooser csvChooser;
  private CSVSwitch csvSwitch;
  private GlobalPanel globalPanel;
  private HelpDialog helpDia;
  private File settingsFile;
  private JMenuItem convertItem;
  
  public Editor()
  {
    super("PESFan Editor");
    setIcon();
    JProgressBar localJProgressBar = new JProgressBar(0, 4);
    localJProgressBar.setForeground(new Color(229, 110, 39));
    JPanel localJPanel = new JPanel(new BorderLayout());
    localJPanel.add(new JLabel(getPesfanIcon()), "Center");
    localJPanel.add(new JLabel("            Loading, please wait...            "), "North");
    localJPanel.add(localJProgressBar, "South");
    getContentPane().add(localJPanel);
    pack();
    setDefaultCloseOperation(3);
    setResizable(false);
    setVisible(true);
    setCursor(Cursor.getPredefinedCursor(3));
    filter = new OptionFilterWE9();
    tabbedPane = new JTabbedPane();
    csvMaker = new CSVMaker();
    csvChooser = new JFileChooser();
    csvSwitch = new CSVSwitch();
    of = new OptionFile();
    of2 = new OptionFile();
    csvChooser.addChoosableFileFilter(new CSVFilter());
    csvChooser.setAcceptAllFileFilterUsed(false);
    csvChooser.setAccessory(csvSwitch);
    stats = new Stats(of);
    stats2 = new Stats(of2);
    localJProgressBar.setValue(1);
    flagChooser = new EmblemChooserDialog(this, of);
    logoChooser = new LogoChooserDialog(this, of);
    plImpDia = new PlayerImportDialog(this, of, of2, stats2);
    kitImpDia = new KitImportDialog(this, of2);
    flagImpDia = new EmblemImportDialog(this, of2);
    imageImpDia = new FlagImportDialog(this, of, of2, true);
    playerDia = new PlayerDialog(this, of, plImpDia, stats);
    teamDia = new TeamDialog(this, of);
    localJProgressBar.setValue(2);
    tranPanel = new TransferPanel(playerDia, of, stats, teamDia);
    imagePanel = new ImagePanel(of, imageImpDia);
    globalPanel = new GlobalPanel(stats, of, tranPanel);
    localJProgressBar.setValue(3);
    teamPan = new TeamPanel(of, tranPanel, flagChooser, of2, imagePanel, globalPanel, kitImpDia, logoChooser);
    flagPanel = new FlagPanel(of, flagImpDia, teamPan);
    teamPan.flagPan = flagPanel;
    wenShop = new WENShopPanel(of);
    stadPan = new StadiumPanel(of, teamPan);
    localJProgressBar.setValue(4);
    leaguePan = new LeaguePanel(of);
    importPanel = new ImportPanel(of, of2, wenShop, stadPan, leaguePan, teamPan, flagPanel, imagePanel, tranPanel);
    helpDia = new HelpDialog(this);
    tabbedPane.addTab("Transfer", null, tranPanel, null);
    tabbedPane.addTab("Team", null, teamPan, null);
    tabbedPane.addTab("Emblem", null, flagPanel, null);
    tabbedPane.addTab("Logo", null, imagePanel, null);
    tabbedPane.addTab("Stadium", null, stadPan, null);
    tabbedPane.addTab("League", null, leaguePan, null);
    tabbedPane.addTab("PES / Shop", null, wenShop, null);
    tabbedPane.addTab("Stat Adjust", null, globalPanel, null);
    tabbedPane.addTab("OF2 Import", null, importPanel, null);
    settingsFile = new File("PFE_settings.dat");
    chooser = new JFileChooser(loadSet());
    chooser.setAcceptAllFileFilterUsed(false);
    chooser.addChoosableFileFilter(filter);
    chooser.setAccessory(new OptionPreview(chooser));
    setCursor(Cursor.getDefaultCursor());
    setVisible(false);
    getContentPane().remove(localJPanel);
    buildMenu();
    getContentPane().add(tabbedPane);
    pack();
    tabbedPane.setVisible(false);
    setVisible(true);
    openFile();
  }
  
  private void buildMenu()
  {
    JMenuBar mb = new JMenuBar();
    JMenu menu = new JMenu("File");
    JMenu help = new JMenu("Help");
    JMenu tool = new JMenu("Tools");
    JMenuItem localJMenuItem1 = new JMenuItem("Open...");
    open2Item = new JMenuItem("Open OF2...");
    saveItem = new JMenuItem("Save");
    saveAsItem = new JMenuItem("Save As...");
    JMenuItem localJMenuItem2 = new JMenuItem("Exit");
    JMenuItem localJMenuItem3 = new JMenuItem("PESFan Editor Help...");
    JMenuItem localJMenuItem4 = new JMenuItem("About...");
    convertItem = new JMenuItem("Convert OF2 > OF1");
    csvItem = new JMenuItem("Make csv stats file...");
    psdItem = new JMenuItem("Get PSD Stats...");
    localJMenuItem2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        System.exit(0);
      }
    });
    localJMenuItem1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        openFile();
      }
    });
    open2Item.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        int i = chooser.showOpenDialog(getContentPane());
        if ((i == 0) && (filter.accept(chooser.getSelectedFile()))) {
          if ((chooser.getSelectedFile().isFile()) && (of2.readXPS(chooser.getSelectedFile())))
          {
            FormFixer.fixAll(of2);
            plImpDia.refresh();
            flagImpDia.of2Open = true;
            imageImpDia.refresh();
            importPanel.refresh();
            flagPanel.refresh();
            teamPan.list.setToolTipText("Double click to import kit from OF2");
            if (of.fileName != null) {
              convertItem.setEnabled(true);
            } else {
              convertItem.setEnabled(false);
            }
          }
          else
          {
            teamPan.list.setToolTipText(null);
            plImpDia.of2Open = false;
            flagImpDia.of2Open = false;
            imageImpDia.of2Open = false;
            flagPanel.refresh();
            importPanel.refresh();
            convertItem.setEnabled(false);
            openFailMsg();
          }
        }
      }
    });
    saveItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (currentFile != null) {
          if ((currentFile.delete()) && (of.saveXPS(currentFile)))
          {
            saveOkMsg(currentFile);
            chooser.setSelectedFile(null);
          }
          else
          {
            saveFailMsg();
          }
        }
      }
    });
    saveAsItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (currentFile != null)
        {
          setFilter();
          int i = chooser.showSaveDialog(getContentPane());
          saveSet();
          if (i == 0)
          {
            File localFile = chooser.getSelectedFile();
            if ((of.format == 0) && ((PES4Utils.getExtension(localFile) == null) || (!PES4Utils.getExtension(localFile).equals("xps")))) {
              localFile = new File(localFile.getParent() + File.separator + localFile.getName() + ".xps");
            }
            if ((of.format == 2) && ((PES4Utils.getExtension(localFile) == null) || (!PES4Utils.getExtension(localFile).equals("xsv")))) {
              localFile = new File(localFile.getParent() + File.separator + localFile.getName() + ".xsv");
            }
            if ((of.format == 3) && ((PES4Utils.getExtension(localFile) == null) || (!PES4Utils.getExtension(localFile).equals("zip")))) {
              localFile = new File(localFile.getParent() + File.separator + localFile.getName() + ".zip");
            }
            if (fileNameLegal(localFile.getName()))
            {
              if (localFile.exists())
              {
                int j = JOptionPane.showConfirmDialog(getContentPane(), localFile.getName() + "\nAlready exists in:\n" + localFile.getParent() + "\nAre you sure you want to replace this file?", "Replace:  " + localFile.getName(), 0, 2, null);
                if (j == 0) {
                  if ((localFile.delete()) && (of.saveXPS(localFile)))
                  {
                    currentFile = localFile;
                    setTitle("PESFan Editor - " + currentFile.getName());
                    saveOkMsg(localFile);
                    chooser.setSelectedFile(null);
                  }
                  else
                  {
                    saveFailMsg();
                  }
                }
              }
              else if (of.saveXPS(localFile))
              {
                currentFile = localFile;
                setTitle("PESFan Editor - " + currentFile.getName());
                saveOkMsg(localFile);
                chooser.setSelectedFile(null);
              }
              else
              {
                saveFailMsg();
              }
            }
            else {
              illNameMsg();
            }
          }
        }
      }
    });
    localJMenuItem3.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        helpDia.setVisible(true);
      }
    });
    localJMenuItem4.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        JOptionPane.showMessageDialog(getContentPane(), "PESFan Editor\nversion 5.09 - psd import added by lazanet\n\n© Copyright 2005-6 Compulsion\n\nWebsite:\nwww.purplehaze.eclipse.co.uk\n\nContact:\npes_compulsion@yahoo.co.uk\n\nThanks to:\nArsenal666, djsaunders, Jayz123, PLF, SFCMike, TheBoss, Tricky\ngothi (XBox authKey)\nsixlegs Java PNG decoder", "About PESFan Editor", -1, getPesfanIcon());
      }
    });
    csvItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        if (currentFile != null)
        {
          int i = csvChooser.showSaveDialog(getContentPane());
          if (i == 0)
          {
            File localFile = csvChooser.getSelectedFile();
            boolean bool1 = csvSwitch.head.isSelected();
            boolean bool2 = csvSwitch.extra.isSelected();
            boolean bool3 = csvSwitch.create.isSelected();
            if ((PES4Utils.getExtension(localFile) == null) || (!PES4Utils.getExtension(localFile).equals("csv"))) {
              localFile = new File(localFile.getParent() + File.separator + localFile.getName() + ".csv");
            }
            if (fileNameLegal(localFile.getName()))
            {
              if (localFile.exists())
              {
                int j = JOptionPane.showConfirmDialog(getContentPane(), localFile.getName() + "\nAlready exists in:\n" + localFile.getParent() + "\nAre you sure you want to replace this file?", "Replace:  " + localFile.getName(), 0, 2, null);
                if (j == 0) {
                  if ((localFile.delete()) && (csvMaker.makeFile(of, stats, localFile, bool1, bool2, bool3))) {
                    saveOkMsg(localFile);
                  } else {
                    saveFailMsg();
                  }
                }
              }
              else if (csvMaker.makeFile(of, stats, localFile, bool1, bool2, bool3))
              {
                saveOkMsg(localFile);
              }
              else
              {
                saveFailMsg();
              }
            }
            else {
              illNameMsg();
            }
          }
        }
      }
    });

    psdItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent c)
			{
				System.out.println("PSD init");
				new PSDConnPanel();
			}
		});
    convertItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        System.arraycopy(of2.data, OptionFile.block[2], of.data, OptionFile.block[2], OptionFile.blockSize[2]);
        System.arraycopy(of2.data, OptionFile.block[3], of.data, OptionFile.block[3], OptionFile.blockSize[3]);
        System.arraycopy(of2.data, OptionFile.block[4], of.data, OptionFile.block[4], OptionFile.blockSize[4]);
        System.arraycopy(of2.data, OptionFile.block[5], of.data, OptionFile.block[5], OptionFile.blockSize[5]);
        System.arraycopy(of2.data, OptionFile.block[6], of.data, OptionFile.block[6], OptionFile.blockSize[6]);
        System.arraycopy(of2.data, OptionFile.block[7], of.data, OptionFile.block[7], OptionFile.blockSize[7]);
        System.arraycopy(of2.data, OptionFile.block[8], of.data, OptionFile.block[8], OptionFile.blockSize[8]);
        System.arraycopy(of2.data, 656872, of.data, 656872, 828);
        flagPanel.refresh();
        imagePanel.refresh();
        tranPanel.refresh();
        stadPan.refresh();
        teamPan.refresh();
        leaguePan.refresh();
        importPanel.disableAll();
        convertItem.setEnabled(false);
      }
    });
    menu.add(localJMenuItem1);
    menu.add(open2Item);
    menu.add(saveItem);
    menu.add(saveAsItem);
    menu.add(localJMenuItem2);
    help.add(localJMenuItem3);
    help.add(localJMenuItem4);
    tool.add(csvItem);
    tool.add(psdItem);
    tool.add(convertItem);
    mb.add(menu);
    mb.add(tool);
    mb.add(help);
    setJMenuBar(mb);
    csvItem.setEnabled(false);
    psdItem.setEnabled(false);
    open2Item.setEnabled(false);
    saveItem.setEnabled(false);
    saveAsItem.setEnabled(false);
    convertItem.setEnabled(false);
  }
  
  private boolean fileNameLegal(String paramString)
  {
    boolean bool = true;
    if (paramString.indexOf("\\") != -1) {
      bool = false;
    }
    if (paramString.indexOf("/") != -1) {
      bool = false;
    }
    if (paramString.indexOf(":") != -1) {
      bool = false;
    }
    if (paramString.indexOf("*") != -1) {
      bool = false;
    }
    if (paramString.indexOf("?") != -1) {
      bool = false;
    }
    if (paramString.indexOf("\"") != -1) {
      bool = false;
    }
    if (paramString.indexOf("<") != -1) {
      bool = false;
    }
    if (paramString.indexOf(">") != -1) {
      bool = false;
    }
    if (paramString.indexOf("|") != -1) {
      bool = false;
    }
    return bool;
  }
  
  private void saveFailMsg()
  {
    JOptionPane.showMessageDialog(getContentPane(), "Save failed", "Error", 0);
  }
  
  private void openFailMsg()
  {
    JOptionPane.showMessageDialog(getContentPane(), "Could not open file", "Error", 0);
  }
  
  private void saveOkMsg(File paramFile)
  {
    JOptionPane.showMessageDialog(getContentPane(), paramFile.getName() + "\nSaved in:\n" + paramFile.getParent(), "File Successfully Saved", 1);
  }
  
  private void illNameMsg()
  {
    JOptionPane.showMessageDialog(getContentPane(), "File name cannot contain the following characters:\n\\ / : * ? \" < > |", "Error", 0);
  }
  
  private void setFilter() {}
  
  private void setIcon()
  {
    URL localURL = getClass().getResource("data/icon.png");
    if (localURL != null)
    {
      ImageIcon localImageIcon = new ImageIcon(localURL);
      setIconImage(localImageIcon.getImage());
    }
  }
  
  private ImageIcon getPesfanIcon()
  {
    ImageIcon localImageIcon = null;
    URL localURL = getClass().getResource("data/pesfan.png");
    if (localURL != null) {
      localImageIcon = new ImageIcon(localURL);
    }
    return localImageIcon;
  }
  
  private void openFile()
  {
    int i = chooser.showOpenDialog(getContentPane());
    saveSet();
    if ((i == 0) && (filter.accept(chooser.getSelectedFile()))) {
      if ((chooser.getSelectedFile().isFile()) && (of.readXPS(chooser.getSelectedFile())))
      {
        currentFile = chooser.getSelectedFile();
        setTitle("PESFan Editor - " + currentFile.getName());
        FormFixer.fixAll(of);
        flagPanel.refresh();
        imagePanel.refresh();
        tranPanel.refresh();
        wenShop.wenPanel.refresh();
        wenShop.shopPanel.status.setText("");
        stadPan.refresh();
        teamPan.refresh();
        leaguePan.refresh();
        tabbedPane.setVisible(true);
        importPanel.refresh();
        csvItem.setEnabled(true);
        psdItem.setEnabled(true);
        open2Item.setEnabled(true);
        saveItem.setEnabled(true);
        saveAsItem.setEnabled(true);
        if (of2.fileName != null) {
          convertItem.setEnabled(true);
        } else {
          convertItem.setEnabled(false);
        }
      }
      else
      {
        csvItem.setEnabled(false);
        psdItem.setEnabled(false);
        open2Item.setEnabled(false);
        saveItem.setEnabled(false);
        saveAsItem.setEnabled(false);
        tabbedPane.setVisible(false);
        convertItem.setEnabled(false);
        setTitle("PESFan Editor");
        openFailMsg();
      }
    }
  }
  
  private boolean saveSet()
  {
    boolean bool = true;
    if (chooser.getCurrentDirectory() != null) {
      try
      {
        if (settingsFile.exists()) {
          settingsFile.delete();
        }
        FileOutputStream localFileOutputStream = new FileOutputStream(settingsFile);
        ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localFileOutputStream);
        localObjectOutputStream.writeObject(chooser.getCurrentDirectory());
        localObjectOutputStream.flush();
        localObjectOutputStream.close();
        localFileOutputStream.close();
      }
      catch (IOException localIOException)
      {
        bool = false;
      }
    }
    return bool;
  }
  
  private File loadSet()
  {
    File localFile = null;
    if (settingsFile.exists()) {
      try
      {
        FileInputStream localFileInputStream = new FileInputStream(settingsFile);
        ObjectInputStream localObjectInputStream = new ObjectInputStream(localFileInputStream);
        localFile = (File)localObjectInputStream.readObject();
        localObjectInputStream.close();
        localFileInputStream.close();
        if ((localFile != null) && (!localFile.exists())) {
          localFile = null;
        }
      }
      catch (Exception localException) {}
    }
    return localFile;
  }
  
  public static void main(String[] paramArrayOfString)
    throws IOException
  {
    PES4Utils.systemUI();
    System.setProperty("swing.metalTheme", "steel");
    new Editor();
  }
}


/* Location:              PESFan Editor 5.08.jar!/editor/Editor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
