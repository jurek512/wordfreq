package de.words;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class TextFileFilter {
	String dir;
  public TextFileFilter(String dir) {
	  this.dir = dir;
  }

  public String getFname() {
	  String name = null;
	    JFileChooser chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new File(dir));

	    chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
	      public boolean accept(File f) {
	        return f.getName().toLowerCase().endsWith(".txt")
	            || f.isDirectory();
	      }

	      public String getDescription() {
	        return "Text Dataein";
	      }
	    });

	    int r = chooser.showOpenDialog(new JFrame());
	    if (r == JFileChooser.APPROVE_OPTION) {
	      name = chooser.getSelectedFile().getAbsolutePath();
	    }
	  return name;
  }
  
  public static void main(String[] args) {
	  TextFileFilter filedemo = new TextFileFilter("c:\\ksiazki\\.");
	  System.out.println(filedemo.getFname());
}
}