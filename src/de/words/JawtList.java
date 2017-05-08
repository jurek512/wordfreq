package de.words;

import javax.swing.*;
import javax.swing.event.*;

//this is a simple adapter class to
//convert List awt methods to Swing methods

public class JawtList extends JScrollPane implements ListSelectionListener,
		I_AwtList {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList listWindow;
	private JListData listContents;

	//-----------------------------------------
	public JawtList(int rows) {
		listContents = new JListData();
		listWindow = new JList(listContents);
		listWindow.setPrototypeCellValue("Abcdefg Hijkmnop");
		getViewport().add(listWindow);
	}

	//-----------------------------------------
	public void addListSelectionListener(ListSelectionListener iList) {
		listWindow.addListSelectionListener(iList);
	}

	public void deselect() {
		listWindow.clearSelection();
	}

	//-----------------------------------------
	public void add(String s) {
		listContents.addElement(s);
	}

	//-----------------------------------------
	public void remove(String s) {
		listContents.removeElement(s);
	}

	public void removeAllElements() {
		for (int i = listContents.getSize(); i > 0; i--) {
			listContents.removeElement((String)listContents.getElementAt(0));
		}
	}

	//-----------------------------------------
	public JListData getListData() {
		return listContents;
 	}

	//-----------------------------------------
	public String[] getSelectedItems() {
		Object[] obj = listWindow.getSelectedValues();
		String[] s = new String[obj.length];
		for (int i = 0; i < obj.length; i++)
			s[i] = obj[i].toString();
		return s;
	}

	public void setSelIndex(int i) {
		listWindow.setSelectedIndex(i);
	}
	
	//-----------------------------------------
	public void valueChanged(ListSelectionEvent e) {
	}

}

