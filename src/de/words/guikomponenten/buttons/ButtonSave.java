package de.words.guikomponenten.buttons;

import java.awt.event.ActionListener;

import de.words.Mediator;

public class ButtonSave extends AbstrakterButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ButtonSave(ActionListener act, Mediator med) {
		super("Save selected words", act, med);
	    med.registerSave(this);
	}

	public void execute() {
		med.save();
	}

}
