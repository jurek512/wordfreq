package de.words.guikomponenten.radiobutton;

import java.awt.event.ItemListener;

import javax.swing.JRadioButton;

import de.words.Command;
import de.words.Mediator;


public class RadioButtonNachAlfabet extends JRadioButton implements Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Mediator med;
	
	public RadioButtonNachAlfabet(ItemListener item, Mediator med) {
		super("Alfabet");
		addItemListener(item);
	    this.med = med;
	    med.registerButtonAlfabet(this);
	}

	public void execute() {
		med.setAlfabet();
	}

}
