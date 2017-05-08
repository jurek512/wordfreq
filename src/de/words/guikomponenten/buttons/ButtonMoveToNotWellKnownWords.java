package de.words.guikomponenten.buttons;

import java.awt.event.ActionListener;

import de.words.Mediator;

public class ButtonMoveToNotWellKnownWords extends AbstrakterButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButtonMoveToNotWellKnownWords(ActionListener act, Mediator med) {
		super("-->", act, med);
	    med.registerButton(this);
	}

	public void execute() {
		med.addToNotWellKnown();
	}

}
