package de.words.guikomponenten.buttons;

import java.awt.event.ActionListener;

import de.words.Mediator;

public class ButtonToNextPart extends AbstrakterButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String pfeil = "00:00-->";

	public ButtonToNextPart(ActionListener act, Mediator med) {
		super(pfeil, act, med);
	    med.registerButton(this);
	}

	public void execute() {
		med.nextPart();
	}

	public String getLabelString() {
		return pfeil;
	}
}
