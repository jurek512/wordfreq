package de.words.guikomponenten.buttons;

import java.awt.event.ActionListener;

import de.words.Mediator;


public class ButtonGetPhrase extends AbstrakterButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButtonGetPhrase(ActionListener act, Mediator med) {
		super("GetPhrasen", act, med);
	    med.registerButton(this);
	}

	public void execute() {
//		med.getPhrase();
	}

}
