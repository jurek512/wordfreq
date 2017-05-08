package de.words;

import java.io.*;

public class OutputFile {
	RandomAccessFile f = null;
	boolean errflag;
//	String s = null;

	public OutputFile(String fname) {
		errflag = false;
		try {
			//open file
			f = new RandomAccessFile(fname, "rw");
			f.seek(f.length());
		} catch (IOException e) {
			//print error if not found
			System.out.println("no file found");
			errflag = true; //and set flag
		}
	}

	//-----------------------------------------
	public boolean checkErr() {
		return errflag;
	}

	//-----------------------------------------
	//-----------------------------------------
	public void writeLine(String str) {
		//read in a line from the file
		try {
			f.writeBytes(str + "\r\n"); //could throw error
			System.out.println(str);
		} catch (IOException e) {
			errflag = true;
			System.out.println("File read error");
		}
	}

	//-----------------------------------------
	public void close() {
		try {
			f.close(); //close file
		} catch (IOException e) {
			System.out.println("File close error");
			errflag = true;
		}
	}
	//-----------------------------------------
}
