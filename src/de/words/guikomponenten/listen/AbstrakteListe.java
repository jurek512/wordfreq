/**
 * 
 */
package de.words.guikomponenten.listen;

import java.util.Collection;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;

import de.words.JawtList;
import de.words.Mediator;
import de.words.model.I_WordModel;

/**
 * @author Jurek
 *
 */
public abstract class AbstrakteListe extends JawtList {

	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	protected Mediator med;
	protected I_WordModel wordModel;


	public AbstrakteListe(int laenge, I_WordModel pWordModel, Mediator med) {
		super(laenge);
		this.med = med;
		this.wordModel = pWordModel;
	}
	
	protected abstract void selectWords();

	// ----------------------------------
	public void valueChanged(ListSelectionEvent ls) {
		JList liste = (JList) ls.getSource();
		if (liste.getSelectedIndex() >= 0)
			selectWords();
		else
			med.deselect();
	}

	public void fillWordList(Collection<String> words) {
		this.removeAllElements();
		for(String s : words) {
			super.add(s);
		}
	}

}
