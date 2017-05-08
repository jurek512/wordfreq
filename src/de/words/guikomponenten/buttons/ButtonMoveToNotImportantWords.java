package de.words.guikomponenten.buttons;

import java.awt.event.ActionListener;

import de.words.Mediator;

public class ButtonMoveToNotImportantWords extends AbstrakterButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButtonMoveToNotImportantWords(ActionListener act, Mediator med) {
		super("-->", act, med);
	    med.registerButton(this);
	}

	public void execute() {
		med.addToIgnored();
	}

}
