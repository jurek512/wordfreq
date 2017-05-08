package de.words.guikomponenten.listen;

import de.words.JListData;

public interface IWortliste {
	public String[] getSelectedItems();
	public JListData getListData();
	public void setSelIndex(int i);
}
