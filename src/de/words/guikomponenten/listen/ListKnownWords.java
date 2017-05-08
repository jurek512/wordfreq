package de.words.guikomponenten.listen;

import javax.swing.event.*;

import de.words.Mediator;
import de.words.model.I_WordModel;


public class ListKnownWords extends AbstrakteListe implements
		ListSelectionListener, IWortliste {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListKnownWords(Mediator md, I_WordModel wordModel) {
		super(10, wordModel, md);
		med.registerList(this);
		addListSelectionListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.words.AbstrakteListe#getFilename()
	 */
	public void save() {
		wordModel.getWoerterLieferant().saveKnownWords(this);
	}

	protected void selectWords() {
		med.selectKnownWord();
	}
}
