import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.JOptionPane;
import java.util.Properties;
import java.io.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class settingsparser {

	String SettingsFilePath = "C:\\ProgramData\\SDCE\\settings.properties";
	
	Properties Settings = new Properties();

	public void FileCheck() {
			File f = new File("C:\\ProgramData\\SDCE\\settings.properties");
	if(f.exists()) {}
	else {
		System.out.println("SDCE settings file does not exist, creating properties file.");
		new File("C:\\ProgramData\\SDCE").mkdir(); // create the directory
		
		try {
		FileWriter fw = new FileWriter("C:\\ProgramData\\SDCE\\settings.properties");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.append("WORKING_DIRECTORY");
		bw.append("\nWORKING_FILE");
		bw.append("\nCOMPILER");
		bw.append("\nBUILD_PARAMETERS");
		bw.append("\nRUNTIME_PARAMETERS");
		bw.append("\nDEFAULT_LANGUAGE");
		bw.close();
		fw.close();
		}
		catch (Exception FileCreationException) {}
	}
	}
	public String Get(String whichKeyToGet) {
		FileCheck();
		String retrievedKey;
		try {
			Settings.load(new FileInputStream(SettingsFilePath));
		}
		catch(Exception e) {}
		retrievedKey = Settings.getProperty(whichKeyToGet);
		return retrievedKey;
	}
	
	public void Set(String whichKeyToSet, String keyValue) {
		FileCheck();
		OutputStream output = null;
		try {
			output = new FileOutputStream(SettingsFilePath);
			Settings.setProperty(whichKeyToSet, keyValue);
			Settings.store(output, null);
			output.close();
		}
		catch(Exception e) {}
	}
	
}

// usage 
// Settings.Set("FIELD", "VALUE");
// Settings.Get("FIELD");￿
