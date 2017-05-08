package de.words.guikomponenten.listen;

import javax.swing.event.*;

import de.words.Mediator;
import de.words.model.I_WordModel;

public class ListOmittedWords extends AbstrakteListe implements
		ListSelectionListener, IWortliste {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ListOmittedWords(Mediator md, I_WordModel wordModel) {
		super(10, wordModel, md);
		med.registerList(this);
		addListSelectionListener(this);
	}

	protected void selectWords() {
		med.selectOmittedWord();
	}


	public void save() {
		wordModel.getWoerterLieferant().saveUnimportantWords(this);
	}
}
