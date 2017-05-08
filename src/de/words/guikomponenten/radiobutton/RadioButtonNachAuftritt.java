package de.words.guikomponenten.radiobutton;

import java.awt.event.ItemListener;

import javax.swing.JRadioButton;

import de.words.Command;
import de.words.Mediator;


public class RadioButtonNachAuftritt extends JRadioButton implements Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Mediator med;
	
	public RadioButtonNachAuftritt(ItemListener item, Mediator med) {
		super("Auftritt");
		addItemListener(item);
	    this.med = med;
	    med.registerButtonAuftritt(this);
	}

	public void execute() {
		med.setAuftritt();
	}

}
