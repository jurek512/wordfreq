package de.words.guikomponenten.textfeld;

import javax.swing.JTextField;

import de.words.Mediator;
import de.words.model.I_WordModel;

public class AbstraktesTextFeld extends JTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Mediator med;
	protected I_WordModel wordModel;

	public AbstraktesTextFeld(Mediator md, I_WordModel pWordModel) {
		med = md;
		wordModel = pWordModel;
	}

	public I_WordModel getWordModel() {
		return wordModel;
	}
	
	public void setPhrase(String s) {
		if (s != null) {
			setText(s);
		}
	}

}
