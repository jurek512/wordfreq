package de.words.guikomponenten.buttons;

import java.awt.event.ActionListener;

import de.words.Mediator;

public class ButtonMoveFromKnownWords extends AbstrakterButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButtonMoveFromKnownWords(ActionListener act, Mediator med) {
		super("<--", act, med);
	    med.registerSave(this);
	}

	public void execute() {
		med.removeFromKnown();
	}

}
