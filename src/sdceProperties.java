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
import java.util.Scanner; import javax.swing.*;
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
    @SuppressWarnings("unchecked")
    public class sdceProperties {
      public void open() {
   final  JFrame p = new JFrame("Properties");
    JPanel c = new JPanel();
		p.setSize(350,400);
		p.setVisible(true);
    c.setLayout(null);
    p.setIconImage(new ImageIcon(getClass().getResource("sdce.png")).getImage());
    p.setLocationRelativeTo(null);
    JLabel compilerLabel = new JLabel("Compiler:");
    final JTextField compilerField = new JTextField();
    JButton compilerButton = new JButton("Browse");
    JLabel buildLabel = new JLabel("Build Parameters:");
    final JTextField buildParam = new JTextField();
    JLabel dlangLabel = new JLabel("Default Language:");
    String[] languages = {"Java", "C", "C++", "C#", "PHP", "HTML", "JavaScript", "CSS", "SQL"};
    final JComboBox languageSelection = new JComboBox(languages);
    JButton applyButton = new JButton("Apply");
    JLabel runLabel = new JLabel("Runtime Parameters:");
    final JTextField runParam = new JTextField();

    
             ActionListener compilerChoice = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
                JFileChooser compilerChooser = new JFileChooser();
                int returnValue = compilerChooser.showOpenDialog(null);
                if(returnValue == JFileChooser.APPROVE_OPTION) {
                    File compilerDirectory = compilerChooser.getSelectedFile();
                    String compilerLocation = compilerDirectory.getAbsolutePath();
                    compilerField.setText(compilerLocation);
                }
         }  
    };
    
    
             ActionListener applyProperties = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         String compiler = compilerField.getText();
         String buildparams = buildParam.getText();
         String runparams = runParam.getText();
         String language = languageSelection.getSelectedItem().toString();
         settingsparser Settings = new settingsparser();
         String WorkingFile = Settings.Get("WORKING_FILE");
         String WorkingDirectory = Settings.Get("WORKING_DIRECTORY");
         Settings.Set("WORKING_FILE", WorkingFile);
         Settings.Set("WORKING_DIRECTORY", WorkingDirectory);
         Settings.Set("COMPILER", "\"" + compiler + "\"");
         Settings.Set("BUILD_PARAMETERS", buildparams);
         Settings.Set("RUNTIME_PARAMETERS", runparams);
         Settings.Set("DEFAULT_LANGUAGE", language);
         p.setVisible(false);
         }  
    };
    
    compilerButton.addActionListener(compilerChoice);
    applyButton.addActionListener(applyProperties);
    compilerLabel.setSize(125, 25);
    compilerLabel.setLocation(25, 25);
    compilerField.setSize(175, 20);
    compilerField.setLocation(60, 55);
    compilerButton.setSize(75, 25);
    compilerButton.setLocation(250, 55);
    
    buildLabel.setSize(125, 25);
    buildLabel.setLocation(25, 90);
    buildParam.setSize(265, 20);
    buildParam.setLocation(60, 120);
    
    runLabel.setSize(125, 25);
    runLabel.setLocation(25, 155);
    runParam.setSize(265, 20);
    runParam.setLocation(60, 185);
    
    dlangLabel.setSize(125, 25);
    dlangLabel.setLocation(25, 230);
    languageSelection.setSize(125, 25);
    languageSelection.setLocation(60, 260);
    applyButton.setSize(75, 25);
    applyButton.setLocation(250, 310);

    c.add(compilerLabel);
    c.add(compilerField);
    c.add(compilerButton);
    c.add(buildLabel);
    c.add(buildParam);
    c.add(runLabel);
    c.add(runParam);
    c.add(dlangLabel);
    c.add(languageSelection);
    c.add(applyButton);
    p.setContentPane(c);
    p.setResizable(false);
 
     
    settingsparser Settings = new settingsparser();
    String currentcompiler = Settings.Get("COMPILER");
    String currentParams = Settings.Get("BUILD_PARAMETERS");
    String currentrParams = Settings.Get("RUNTIME_PARAMETERS");
    String currentLanguage = Settings.Get("DEFAULT_LANGUAGE");
    compilerField.setText(currentcompiler);
    buildParam.setText(currentParams);
    runParam.setText(currentrParams);
    languageSelection.setSelectedItem(currentLanguage);
      }
    }