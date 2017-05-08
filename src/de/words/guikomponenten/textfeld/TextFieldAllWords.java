package de.words.guikomponenten.textfeld;

import de.words.Mediator;
import de.words.model.I_WordModel;


public class TextFieldAllWords extends AbstraktesTextFeld {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextFieldAllWords(Mediator md, I_WordModel wordModel) {
		super(md, wordModel);
		med.registerTextFieldWords(this);
		setText(Integer.toString(getWordModel().getListNotKnownWords().size()));
	}

}