package de.words.guikomponenten.buttons;

import java.awt.event.ActionListener;

import de.words.Mediator;


public class ButtonGetExplanation extends AbstrakterButton {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButtonGetExplanation(ActionListener act, Mediator med) {
		super("Get Explanation", act, med);
	    med.registerButton(this);
	}

	public void execute() {
		med.getExplanation();
	}

}
