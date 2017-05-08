package de.words.guikomponenten.buttons;

import java.awt.event.ActionListener;

import  javax.swing.JButton;

import de.words.Command;
import de.words.Mediator;

public abstract class AbstrakterButton extends JButton implements Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Mediator med;

	public AbstrakterButton(String label, ActionListener act, Mediator med) {
		super(label);
		addActionListener(act);
	    this.med = med;
	}
	
	@Override
	public abstract void execute();

}
