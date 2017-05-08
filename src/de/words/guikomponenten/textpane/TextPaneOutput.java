package de.words.guikomponenten.textpane;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import de.words.Mediator;
import de.words.model.I_WordModel;

public class TextPaneOutput extends JTextPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Mediator med;
	I_WordModel wordModel;

	public TextPaneOutput(Mediator md, I_WordModel wordModel) {
		med = md;
		this.wordModel = wordModel;
		med.registerTextPane(this);
	}

	// ----------------------------------
	public void setPhrase(String s) {
		if (s != null) {
			setText("");
			Document doc = getStyledDocument();
			SimpleAttributeSet sas = new SimpleAttributeSet();

			String s1 = s.substring(0, 1);

			s1 = "[" + s1.toUpperCase() + s1.toLowerCase() + "]"
					+ s.substring(1);

			String myPhrase = wordModel.getSentencesIncludingWord(s);
			Matcher matcher = Pattern.compile(s1).matcher(myPhrase);
			String[] phrasen = myPhrase.split(s1);
			for (int i = 0; i < phrasen.length; i++) {
				try {
					String strx = matcher.find() ? matcher.group() : "";
					doc.insertString(doc.getLength(), phrasen[i], sas);
					StyleConstants.setBackground(sas, Color.YELLOW);
					if (i < phrasen.length - 1)
						doc.insertString(doc.getLength(), strx, sas);
					StyleConstants.setBackground(sas, Color.WHITE);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			setText("");
		}
	}
}
