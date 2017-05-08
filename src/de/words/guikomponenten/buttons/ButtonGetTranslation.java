package de.words.guikomponenten.buttons;

import java.awt.event.ActionListener;

import de.words.Mediator;

public class ButtonGetTranslation extends AbstrakterButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButtonGetTranslation(ActionListener act, Mediator med) {
		super("GetTranslation", act, med);
	    med.registerButton(this);
	}

	public void execute() {
		med.getTranslation();
	}

}
