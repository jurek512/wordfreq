package de.words.guikomponenten.textfeld;

import de.words.Mediator;
import de.words.model.I_WordModel;


public class TextFieldNotWellKnownWords extends AbstraktesTextFeld {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextFieldNotWellKnownWords(Mediator md, I_WordModel wordModel) {
		super(md, wordModel);
		med.registerTextFielNotWellKnowndWords(this);
		setText(Integer.toString(this.wordModel.getNotWellKnownWords().size()));
	}

}
