package de.words.guikomponenten.textpane;

import java.awt.Color;
import java.util.Collection;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import de.words.Mediator;
import de.words.model.I_WordModel;

public class TextPaneOutputAll extends JTextPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Mediator med;
	I_WordModel wordModel;

	public TextPaneOutputAll(Mediator md, I_WordModel wordModel) {
		med = md;
		this.wordModel = wordModel;
		med.registerTextPaneAll(this);
	}
//
//	// ----------------------------------
//	@SuppressWarnings("unchecked")
//	public void setPhrase() {
//		setText("");
//		Document doc = getStyledDocument();
//		SimpleAttributeSet sas = new SimpleAttributeSet();
//
//		String[] words = rxx.getAllWordsTab();
//		Scanner sc = new Scanner(rxx.getAll());
//		sc.useDelimiter("[a-zA-Z]*");
////		System.out.println(sc.nextLine());
////		sc.next();
//		Collection<String> ts = rxx.listNotKnownWords();
//		Collection<String> ts1 = rxx.getNotWellKnownWords();
//		
//		StyleConstants.setFontSize(sas, 15);
//		int akt_start = 0;
//		String textAll = rxx.getAll();
//		while(sc.hasNext()){
//			sc.next();
//			sc.findInLine("[a-zA-Z]+");
//			MatchResult mr = sc.match();
//			System.out.println(mr.start());
//			System.out.println(mr.end());
//			System.out.println(mr.group());
//			try {
//				if (mr.start() != mr.end()) {
//					// letzter nicht wort
//					System.out.println("#"+textAll.substring(akt_start+1, mr.start()+1)+"#");
//					StyleConstants.setBackground(sas, Color.WHITE);
//					doc.insertString(doc.getLength(), textAll.substring(akt_start+1, mr.start()+1), sas);
//					if (ts.contains(mr.group().toLowerCase())) {
//						StyleConstants.setBackground(sas, Color.PINK);
//						doc.insertString(doc.getLength(), mr.group(), sas);
//						akt_start = mr.end();
//					} else if (ts1.contains(mr.group().toLowerCase())) {
//						StyleConstants.setBackground(sas, Color.YELLOW);
//						doc.insertString(doc.getLength(), mr.group(), sas);
//						akt_start = mr.end();
//					} else {
//						StyleConstants.setBackground(sas, Color.WHITE);
//						doc.insertString(doc.getLength(), mr.group(), sas);
//						akt_start = mr.end();
//					}
//				} else {
////					akt_start++;
//				}
//			} catch (BadLocationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
////		sc.h
////		sc.next();
////		sc.findInLine("\\b[a-zA-Z]*\\b");
//////		sc.next();
////		mr =sc.match();
////		System.out.println(mr.start());
////		System.out.println(mr.end());
////		System.out.println(mr.group());
////		System.out.println(mr.groupCount());
//		
//
//		
////		for (int i = 0; i < words.length; i++) {
////			try {
////				if (ts.contains(words[i].toLowerCase())) {
////					StyleConstants.setBackground(sas, Color.PINK);
////					doc.insertString(doc.getLength(), words[i], sas);
////				} else if (ts1.contains(words[i].toLowerCase())) {
////					StyleConstants.setBackground(sas, Color.YELLOW);
////					doc.insertString(doc.getLength(), words[i], sas);
////				} else {
////					StyleConstants.setBackground(sas, Color.WHITE);
////					doc.insertString(doc.getLength(), words[i], sas);
////				}
////				StyleConstants.setBackground(sas, Color.WHITE);
////				doc.insertString(doc.getLength(), " ", sas);
////
////			} catch (BadLocationException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////		}
//	}

	// ----------------------------------
	public void setPhrase() {
		setText("");
		Document doc = getStyledDocument();
		
		SimpleAttributeSet sas = new SimpleAttributeSet();

		String[] words = wordModel.getAllWordsFromAktTextAsTab();
		String[] trenn = wordModel.getAllText().split("[a-zA-Z]+");
	
		boolean textFangtMitTrenn = !wordModel.getAllText().substring(0,1).matches("[a-zA-Z]");
		
		StyleConstants.setFontSize(sas, 15);

		Collection<String> ts = wordModel.getListNotKnownWords();
		Collection<String> ts1 = wordModel.getNotWellKnownWords();
		
		try {
		int ipom = 0;
		if (textFangtMitTrenn) {
			StyleConstants.setBackground(sas, Color.WHITE);
			doc.insertString(doc.getLength(), trenn[0], sas);
			ipom = 1;
		}

		for (int i = 0; i < words.length; i++) {
			try {
				if (ts.contains(words[i+ipom].toLowerCase())) {
					StyleConstants.setBackground(sas, Color.PINK);
					doc.insertString(doc.getLength(), words[i+ipom], sas);
				} else if (ts1.contains(words[i+ipom].toLowerCase())) {
					StyleConstants.setBackground(sas, Color.YELLOW);
					doc.insertString(doc.getLength(), words[i+ipom], sas);
				} else {
					StyleConstants.setBackground(sas, Color.WHITE);
					doc.insertString(doc.getLength(), words[i+ipom], sas);
				}
				StyleConstants.setBackground(sas, Color.WHITE);
				doc.insertString(doc.getLength(), trenn[i+1], sas);

			} catch (ArrayIndexOutOfBoundsException e) {
				
			}
		}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	// ----------------------------------
//	@SuppressWarnings("unchecked")
//	public void setPhrase() {
//		setText("");
//		Document doc = getStyledDocument();
//		SimpleAttributeSet sas = new SimpleAttributeSet();
//
//		String[] words = rxx.getAllWordsTab();
//		rxx.getAll();
//		
//		StyleConstants.setFontSize(sas, 15);
//
//		Collection<String> ts = rxx.listNotKnownWords();
//		Collection<String> ts1 = rxx.getNotWellKnownWords();
//		
//		for (int i = 0; i < words.length; i++) {
//			try {
//				if (ts.contains(words[i].toLowerCase())) {
//					StyleConstants.setBackground(sas, Color.PINK);
//					doc.insertString(doc.getLength(), words[i], sas);
//				} else if (ts1.contains(words[i].toLowerCase())) {
//					StyleConstants.setBackground(sas, Color.YELLOW);
//					doc.insertString(doc.getLength(), words[i], sas);
//				} else {
//					StyleConstants.setBackground(sas, Color.WHITE);
//					doc.insertString(doc.getLength(), words[i], sas);
//				}
//				StyleConstants.setBackground(sas, Color.WHITE);
//				doc.insertString(doc.getLength(), " ", sas);
//
//			} catch (BadLocationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	public void setPhraseNew() {
		wordModel.renew();
		this.setPhrase();
	}

}
