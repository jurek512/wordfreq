package de.words.guikomponenten.buttons;

import java.awt.event.ActionListener;

import de.words.Mediator;

public class ButtonMoveToKnownWords extends AbstrakterButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String pfeil = "-->";

	public ButtonMoveToKnownWords(ActionListener act, Mediator med) {
		super(pfeil, act, med);
	    med.registerSave(this);
	}

	public void execute() {
		med.addToKnown();
	}

	public String getLabelString() {
		return pfeil;
	}
}
