/*
 * Copyright (c) 2016 Mason H. Wolf, All rights reserved.
 *
 *	 Standard Desktop Configuration Editor Version 1.0
 *	 Written By  Mason H. Wolf
 * 	 Client Systems Technician
 * 	 325th Communications Squadron
 * 	 Tyndall Air Force Base, Florida
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of the author nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  https://build.dev.kthcorp.com/mirror/oracle.com/java/7/
 */
 
import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Scanner; 
import java.io.*;
import java.io.File;
import java.io.OutputStream;
import java.util.prefs.*;
import javax.swing.ImageIcon;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import javax.swing.JToolTip;
import java.lang.ProcessBuilder;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Ellipse2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class sdce extends JFrame {
   public sdce() {
      final JPanel cp = new JPanel();
	 // create the menu bar, assign keystroke listeners 
	 JMenuBar MenuBar = new JMenuBar();
	 JMenu FileMenu = new JMenu("File");
	 JMenu EditMenu = new JMenu("Edit");
	 final JMenuItem NewMenuItem = new JMenuItem("New Session");

	 final JMenuItem OpenItem = new JMenuItem("Open");
	 final JMenuItem SaveItem = new JMenuItem("Save");
      JMenuItem SaveAsItem = new JMenuItem("Save As..");
  
      JMenuItem FindItem = new JMenuItem("Find");
	 FileMenu.add(NewMenuItem);
	 KeyStroke newSession = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
	 NewMenuItem.setAccelerator(newSession);

	 FileMenu.add(OpenItem);
	 KeyStroke open = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
	 OpenItem.setAccelerator(open);
	 FileMenu.add(SaveItem);
	 KeyStroke save = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
	 SaveItem.setAccelerator(save);
   	 FileMenu.add(SaveAsItem);
   	 EditMenu.add(FindItem);
   	 KeyStroke find = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK);
   	 FindItem.setAccelerator(find);

	 MenuBar.add(FileMenu);
	 MenuBar.add(EditMenu);
	 
	 // create editing field with RSyntaxTextArea source code highlighting API
      final RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
      textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSHARP);
      textArea.setCodeFoldingEnabled(true);
      textArea.setDragEnabled(true);
      RTextScrollPane sp = new RTextScrollPane(textArea);
      
      
	//  start new session
 	 NewMenuItem.addActionListener(new ActionListener() {
	 	public void actionPerformed(ActionEvent e) {
	 		int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to start a new session?", "New Session",
	 		JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	 		if(response == JOptionPane.NO_OPTION) {}
	 		else if(response == JOptionPane.YES_OPTION) {
	 			textArea.setText("");
         settingsparser filePathCleanup = new settingsparser();
         filePathCleanup.Set("WORKING_FILE", "");
         filePathCleanup.Set("WORKING_DIRECTORY", "");
         String newSessionlwf = filePathCleanup.Get("LAST_WORKING_FILE");
         if(newSessionlwf == null) {
         filePathCleanup.Set("LAST_WORKING_FILE", "");
         }
         else {
           filePathCleanup.Set("LAST_WORKING_FILE", newSessionlwf.toString());
         }
         setTitle("SDC Editor 1.0 ");
	 		}
	 	}
	 });


    // open file 
    OpenItem.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      JFileChooser c = new JFileChooser();
    	settingsparser Settings = new settingsparser();
	    String WorkingDirectoryAsString = Settings.Get("WORKING_DIRECTORY");
      if(WorkingDirectoryAsString != null) {
	    File WorkingDirectoryAsFile = new File(WorkingDirectoryAsString);
	    c.setCurrentDirectory(WorkingDirectoryAsFile);
      }
      int rVal = c.showOpenDialog(sdce.this);
      if (rVal == JFileChooser.APPROVE_OPTION) {
        // assign working directory
      File fileToSave = c.getSelectedFile();
      File location = c.getCurrentDirectory();
      String directory = location.getAbsolutePath();
      Settings.Set("WORKING_DIRECTORY", directory);
       java.io.File file = c.getSelectedFile();
       
       setTitle("SDC Editor 1.0 " + file.toString());
       
		try {
		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		textArea.read(br, null);
		br.close();
		textArea.setCaretPosition(0);
    		Settings.Set("WORKING_FILE", c.getSelectedFile().toString());
      }
      catch (Exception error) {}
      }
      if (rVal == JFileChooser.CANCEL_OPTION) {}
    }
  });
  
  // save 
  SaveItem.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      settingsparser wf = new settingsparser();
      String WorkingFile = wf.Get("WORKING_FILE");


      if(WorkingFile.toString().isEmpty()) {
        
JFrame parentFrame = new JFrame();
JFileChooser fileChooser = new JFileChooser();
fileChooser.setDialogTitle("Save As..");    
	settingsparser Settings = new settingsparser();
	String WorkingDirectoryAsString = Settings.Get("WORKING_DIRECTORY");
	File WorkingDirectoryAsFile = new File(WorkingDirectoryAsString);
	fileChooser.setCurrentDirectory(WorkingDirectoryAsFile);
int userSelection = fileChooser.showSaveDialog(parentFrame);

if (userSelection == JFileChooser.APPROVE_OPTION) {

    File fileToSave = fileChooser.getSelectedFile();
    File location = fileChooser.getCurrentDirectory();
    String directory = location.getAbsolutePath();
    Settings.Set("WORKING_DIRECTORY", directory);
    Settings.Set("WORKING_FILE", fileToSave.toString());

    String contents = textArea.getText();
    try {
    	FileWriter fw = new FileWriter(fileToSave);
    	fw.write(contents);
    	fw.close();
    	System.out.println("\nFile saved: " + fileToSave.toString());
       setTitle("SDC Editor 1.0 " + fileToSave.toString());
    }
    catch (Exception SaveException) {}
    
    
}
      }
      else {
        String fileToSave = wf.Get("WORKING_FILE");
            try {
    	FileWriter fw = new FileWriter(fileToSave);
      String contents = textArea.getText();
    	fw.write(contents);
    	fw.close();

     System.out.println("\nFile saved: " + fileToSave.toString());
     setTitle("SDC Editor 1.0 " + fileToSave.toString());
    }
    catch (Exception SaveException) {}
      
      }
    }
  });
  
  // save as 
 SaveAsItem.addActionListener(new ActionListener() {
  	public void actionPerformed(ActionEvent e) {

JFrame parentFrame = new JFrame();
JFileChooser fileChooser = new JFileChooser();
fileChooser.setDialogTitle("Save As..");    
	settingsparser Settings = new settingsparser();
	String WorkingDirectoryAsString = Settings.Get("WORKING_DIRECTORY");
	File WorkingDirectoryAsFile = new File(WorkingDirectoryAsString);
	fileChooser.setCurrentDirectory(WorkingDirectoryAsFile);
int userSelection = fileChooser.showSaveDialog(parentFrame);

if (userSelection == JFileChooser.APPROVE_OPTION) {

    File fileToSave = fileChooser.getSelectedFile();
    File location = fileChooser.getCurrentDirectory();
    String directory = location.getAbsolutePath();
    Settings.Set("WORKING_DIRECTORY", directory);
 
    String contents = textArea.getText();
    try {
    	FileWriter fw = new FileWriter(fileToSave);
    	fw.write(contents);
    	fw.close();
    	System.out.println("\nFile saved: " + fileToSave.toString());
    }
    catch (Exception SaveException) {}
}

  	}
  });



  // find

  FindItem.addActionListener(new ActionListener() {
  	public void actionPerformed(ActionEvent e) {

	 SearchContext context = new SearchContext();
      String text = JOptionPane.showInputDialog("Search: ");
      if (text.length() == 0) {
         return;
      }
      context.setSearchFor(text);
      
      /* alternative filters
      context.setMatchCase(matchCaseCB.isSelected());
      context.setRegularExpression(regexCB.isSelected());
      context.setSearchForward(forward);
      context.setWholeWord(false);
	*/
      boolean found = SearchEngine.find(textArea, context).wasFound();
      if (!found) {
         System.out.println("not found");
      }
  	}
  });
  
//	preparing to create tool bar

   ImageIcon newIcon = new ImageIcon(
   	sdce.class.getResource("new.png"));

   ImageIcon openIcon = new ImageIcon(
   	sdce.class.getResource("open.png"));

   ImageIcon saveIcon = new ImageIcon(
   	sdce.class.getResource("save.png"));

   ImageIcon buildIcon = new ImageIcon(
   	sdce.class.getResource("build.png"));

   ImageIcon startIcon = new ImageIcon(
   	sdce.class.getResource("start.png"));

   	
   Action newAction = new AbstractAction("New", newIcon) {
   	public void actionPerformed(ActionEvent e) {
   		for(ActionListener a: NewMenuItem.getActionListeners()) {
   			a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {
   				
   			});
   		}
   	}
   };

   Action openAction = new AbstractAction("Open", openIcon) {
   	public void actionPerformed(ActionEvent e) {
   		for(ActionListener a: OpenItem.getActionListeners()) {
   			a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {
   				
   			});
   		}
   	}
   };

   Action saveAction = new AbstractAction("Save", saveIcon) {
   	public void actionPerformed(ActionEvent e) {
   		   		for(ActionListener a: SaveItem.getActionListeners()) {
   			a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {
   				
   			});
   		}
   	}
   };
    
   Action buildAction = new AbstractAction("Build", buildIcon) {
   	public void actionPerformed(ActionEvent e) {
  
       JFrame buildFrame = new JFrame();
       JPanel buildPanel = new JPanel();
       JTextArea console = new JTextArea(25, 100);
       console.setLineWrap(true);
       console.setWrapStyleWord(true);
       TextAreaOutputStream taos = new TextAreaOutputStream(console, 60);
       PrintStream ps = new PrintStream(taos);
       System.setOut(ps);
       System.setErr(ps);
       JScrollPane sp = new JScrollPane(console);
       console.setBackground(new Color(8,44,191));
       console.setForeground(Color.green);
       buildFrame.setIconImage(new ImageIcon(getClass().getResource("sdce.png")).getImage());
       buildPanel.add(sp);
       buildFrame.setSize(900,750);
       buildFrame.setResizable(false);
       buildFrame.setContentPane(buildPanel);
       buildFrame.setVisible(true);
       buildFrame.pack();
 	     buildFrame.setLocationRelativeTo(null);
  try {
      String line;
      settingsparser Settings = new settingsparser();
      String wf = Settings.Get("WORKING_FILE");
    //  Process tempfile = Runtime.getRuntime().exec("cmd.exe /c " + "copy " + " " + wf + " " + "C:\\");
   //  System.out.println("C:\\Windows\\Microsoft.net\\Framework64\\v3.5\\csc.exe /out:demo.exe " + " " + "C:\\demo.cs");
      PrintWriter writer = new PrintWriter("run.bat");
      String compiledFile = removeExtension(wf);
      writer.println("@echo off");
      writer.println("start " + compiledFile + ".exe");
      writer.close();
      Process p = Runtime.getRuntime().exec("C:\\Windows\\Microsoft.net\\Framework64\\v3.5\\csc.exe " + " " + wf);
      BufferedReader bri = new BufferedReader
        (new InputStreamReader(p.getInputStream()));
      BufferedReader bre = new BufferedReader
        (new InputStreamReader(p.getErrorStream()));
      while ((line = bri.readLine()) != null) {
        System.out.println(line);
      }
      bri.close();
      while ((line = bre.readLine()) != null) {
        System.out.println(line);
      }
      bre.close();
      p.waitFor();
    
    
  }
    catch (Exception err) {
      err.printStackTrace();
    }
     }
   };
   
Action startAction = new AbstractAction("Start", startIcon) {
   	public void actionPerformed(ActionEvent e) {
       /*
        JFrame buildFrame = new JFrame();
       JPanel buildPanel = new JPanel();
       JTextArea console = new JTextArea(25, 100);
       console.setLineWrap(true);
       console.setWrapStyleWord(true);
       TextAreaOutputStream taos = new TextAreaOutputStream(console, 60);
       PrintStream ps = new PrintStream(taos);
       System.setOut(ps);
       System.setErr(ps);
       JScrollPane sp = new JScrollPane(console);
       console.setBackground(new Color(8,44,191));
       console.setForeground(Color.green);
       buildFrame.setIconImage(new ImageIcon(getClass().getResource("sdce.png")).getImage());
       buildPanel.add(sp);
       buildFrame.setSize(900,750);
       buildFrame.setResizable(false);
       buildFrame.setContentPane(buildPanel);
       buildFrame.setVisible(true);
       buildFrame.pack();
 	     buildFrame.setLocationRelativeTo(null);
        */
   		  try {
      String line;
      settingsparser Settings = new settingsparser();
      String rp = Settings.Get("RUNTIME_PARAMETERS");
      String wf = Settings.Get("WORKING_FILE");
   //   String wfex = wf.substring(0, wf.lastIndexOf('.'));
   //   System.out.println(wfex);
      String exe = removeExtension(wf);
  //    System.out.println(exe);
      Process p = Runtime.getRuntime().exec("cmd.exe /c" + "run.bat");
      BufferedReader bri = new BufferedReader
        (new InputStreamReader(p.getInputStream()));
      BufferedReader bre = new BufferedReader
        (new InputStreamReader(p.getErrorStream()));
      while ((line = bri.readLine()) != null) {
        System.out.println(line);
      }
      bri.close();
      while ((line = bre.readLine()) != null) {
        System.out.println(line);
      }
      bre.close();
      p.waitFor();
    
    
  }
    catch (Exception err) {
      err.printStackTrace();
    }
   	}
   };

      JToolBar toolBar = new JToolBar();
      toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
  	 toolBar.add(Box.createHorizontalGlue());
	 toolBar.setPreferredSize(new Dimension(50,30));    
   	  toolBar.add(newAction);
      toolBar.add(openAction);
      toolBar.add(saveAction);
      toolBar.add(buildAction);
      toolBar.add(startAction);
      toolBar.setFloatable(false);
  	 setIconImage(new ImageIcon(getClass().getResource("sdce.png")).getImage());
      cp.setPreferredSize(new Dimension(800, 640));
	 cp.setLayout(new BorderLayout());
	 cp.add(sp, BorderLayout.CENTER);
	 cp.add(toolBar, BorderLayout.NORTH);
      setContentPane(cp);
      setJMenuBar(MenuBar);
      setTitle("SDC Editor 1.0");
	 setDefaultCloseOperation(EXIT_ON_CLOSE);
   

      pack();
      setLocationRelativeTo(null);
      // on shutdown do some cleanup

      Runtime.getRuntime().addShutdownHook(new Thread() {
      	public void run() {
      		settingsparser Settings = new settingsparser();
			
			String wf = Settings.Get("WORKING_FILE");
			System.out.println("Removed " + wf + " as working file.");
      
      // restablish settings

      Settings.Set("WORKING_FILE", "");
      Settings.Set("WORKING_DIRECTORY", "");

      
      	}
      });
      
   }
public static String removeExtension(String s) {

    String separator = System.getProperty("file.separator");
    String filename;

    // Remove the path upto the filename.
    int lastSeparatorIndex = s.lastIndexOf(separator);
    if (lastSeparatorIndex == -1) {
        filename = s;
    } else {
        filename = s.substring(lastSeparatorIndex + 1);
    }

    // Remove the extension.
    int extensionIndex = filename.lastIndexOf(".");
    if (extensionIndex == -1)
        return filename;

    return filename.substring(0, extensionIndex);
}
   public static void main(String[] args) {
      try {
      	UIManager.setLookAndFeel(
      		UIManager.getSystemLookAndFeelClassName());
      }
      catch (Exception e) {
      	
      }
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new sdce().setVisible(true);
            settingsparser properties = new settingsparser();
            properties.FileCheck();
         }
      });
   }

 
}
