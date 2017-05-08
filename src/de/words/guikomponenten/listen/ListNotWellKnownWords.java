package de.words.guikomponenten.listen;

import java.util.Collection;
import java.util.TreeSet;

import javax.swing.event.*;

import de.words.JListData;
import de.words.Mediator;
import de.words.model.I_WordModel;

public class ListNotWellKnownWords extends AbstrakteListe implements
		ListSelectionListener, IWortliste {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListNotWellKnownWords(Mediator md, I_WordModel wordModel) {
		super(10, wordModel, md);
		Collection<String> ts = wordModel.getNotWellKnownWords();
    	fillWordList(ts);
		med.registerList(this);
		addListSelectionListener(this);
	}

	protected void selectWords() {
		med.selectNotWellKnownWord();
	}

	public void add(String s) {
		TreeSet<String> tsa = new TreeSet<String>();
		tsa.add(s);
		JListData jld = this.getListData();
		if (jld.getSize() != 0) {
			for (int i = jld.getSize(); i > 0; i--) {
				tsa.add((String)jld.getElementAt(0));
				jld.removeElement((String)jld.getElementAt(0));
			}
		}
		
		this.removeAllElements();
		fillWordList(tsa);
	}

	public void save() {
		wordModel.getWoerterLieferant().saveNotWellKnownWords(this);
	}

}
