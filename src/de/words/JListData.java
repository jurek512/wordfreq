package de.words;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

//=========================================
//public class JListData extends AbstractListModel {
public class JListData extends DefaultComboBoxModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Object> data;

	//-----------------------------------------
	public JListData() {
		data = new Vector<Object>();
	}

	//-----------------------------------------
	public int getSize() {
		return data.size();
	}

	//-----------------------------------------
	public Object getElementAt(int index) {
		return data.elementAt(index);
	}

	//-----------------------------------------
	public void addElement(String s) {
		data.addElement(s);
		fireIntervalAdded(this, data.size() - 1, data.size());
	}

	//-----------------------------------------
	public void insertElementAt(String s, int i) {
		data.insertElementAt(s, i);
		fireIntervalAdded(this, i, data.size());
	}

	//-----------------------------------------
	public void removeElement(String s) {
		data.removeElement(s);
		fireIntervalRemoved(this, 0, data.size());
	}
}
