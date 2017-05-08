package de.words;

import java.awt.Frame;
import java.util.Collections;

import javax.swing.JOptionPane;

import de.words.guikomponenten.buttons.ButtonGetExplanation;
import de.words.guikomponenten.buttons.ButtonGetPhrase;
import de.words.guikomponenten.buttons.ButtonGetTranslation;
import de.words.guikomponenten.buttons.ButtonMoveFromKnownWords;
import de.words.guikomponenten.buttons.ButtonMoveFromNotImportandWords;
import de.words.guikomponenten.buttons.ButtonMoveFromNotWellKnownWords;
import de.words.guikomponenten.buttons.ButtonMoveToKnownWords;
import de.words.guikomponenten.buttons.ButtonMoveToNotImportantWords;
import de.words.guikomponenten.buttons.ButtonMoveToNotWellKnownWords;
import de.words.guikomponenten.buttons.ButtonSave;
import de.words.guikomponenten.buttons.ButtonToNextPart;
import de.words.guikomponenten.buttons.ButtonToPrevPart;
import de.words.guikomponenten.listen.IWortliste;
import de.words.guikomponenten.listen.ListKnownWords;
import de.words.guikomponenten.listen.ListNotWellKnownWords;
import de.words.guikomponenten.listen.ListOmittedWords;
import de.words.guikomponenten.listen.ListWords;
import de.words.guikomponenten.radiobutton.RadioButtonNachAlfabet;
import de.words.guikomponenten.radiobutton.RadioButtonNachAuftritt;
import de.words.guikomponenten.textfeld.TextFieldAllWords;
import de.words.guikomponenten.textfeld.TextFieldNotWellKnownWords;
import de.words.guikomponenten.textpane.TextPaneOutput;
import de.words.guikomponenten.textpane.TextPaneOutputAll;
import de.words.model.I_WordModel;
import de.ziminski.baseinit.Translator;




public class Mediator {

	private ButtonSave bButtonAdd;
	private ButtonMoveToKnownWords bMoveRight;
	private ButtonMoveFromKnownWords bMoveLeft;
	private ButtonMoveToNotImportantWords bMoveRight1;
	private ButtonMoveFromNotImportandWords bMoveLeft1;
	private ButtonMoveToNotWellKnownWords bMoveRight2;
	private ButtonMoveFromNotWellKnownWords bMoveLeft2;
	private ButtonGetPhrase bGetFhrase;
	private ButtonGetTranslation bGetTranslation;
	private ButtonGetExplanation bGetExplanation;
	
	private ButtonToNextPart bToNextPart;
	private ButtonToPrevPart bToPrevPart;
	private int aktPos = 0;
	private int delta = 5;
	
	private ListWords lListWords;
	private ListKnownWords lListKnownWords;
	private ListOmittedWords lListOmittedWords;
	private ListNotWellKnownWords lListNotWellKnownWords;

	private RadioButtonNachAlfabet bButtonAlfabet;
	private RadioButtonNachAuftritt bButtonAuftritt;
	
	private TextPaneOutput tTextPaneOutput;
	private TextPaneOutputAll tTextPaneOutputAll;
	
	private boolean nachAuftritt = true;

	private TextFieldAllWords bTextFieldWords;
	private TextFieldNotWellKnownWords bTextFielNotWellKnowndWords;
	
	I_WordModel wordModel;
//	private Translator trans;

	private boolean saved = true;
	
	Frame frame;
	
	public Mediator() {
		super();
	}

	public void init(I_WordModel wordModel, Frame frame) {
		this.wordModel = wordModel;
		this.frame = frame;
//		trans = Translator.getInstance();
//		trans.init();
		
	}
	
	public void save() {
		// addName();
		lListKnownWords.save();
		lListOmittedWords.save();
		lListNotWellKnownWords.save();
		tTextPaneOutputAll.setPhraseNew();
		saved = true;
	}

	public void addToKnown() {
		String sel[] = lListWords.getSelectedItems();
		if (sel != null) {
			lListKnownWords.add(sel[0]);
			lListWords.remove(sel[0]);
			bTextFieldWords.setText(Integer.toString(lListWords.getListData().getSize()));
		}
		saved = false;
	}

	public void removeFromKnown() {
		String sel[] = lListKnownWords.getSelectedItems();
		if (sel != null) {
			lListWords.add(sel[0]);
			if (!nachAuftritt) {
				lListWords.setNachAlfabet();
			}
			lListKnownWords.remove(sel[0]);
			bTextFieldWords.setText(Integer.toString(lListWords.getListData().getSize()));
		}
		saved = false;
	}

	public void addToIgnored() {
		String sel[] = lListWords.getSelectedItems();
		if (sel != null) {
			lListOmittedWords.add(sel[0]);
			lListWords.remove(sel[0]);
			bTextFieldWords.setText(Integer.toString(lListWords.getListData().getSize()));
		}
		saved = false;
	}

	public void removeFromOmitted() {
		String sel[] = lListOmittedWords.getSelectedItems();
		if (sel != null) {
			lListWords.add(sel[0]);
			if (!nachAuftritt) {
				lListWords.setNachAlfabet();
			}
			lListOmittedWords.remove(sel[0]);
			bTextFieldWords.setText(Integer.toString(lListWords.getListData().getSize()));
		}
		saved = false;
	}

	public void addToNotWellKnown() {
		String sel[] = lListWords.getSelectedItems();
		if (sel != null) {
			lListNotWellKnownWords.add(sel[0]);
			lListWords.remove(sel[0]);
			bTextFieldWords.setText(Integer.toString(lListWords.getListData().getSize()));
			bTextFielNotWellKnowndWords.setText(Integer.toString(lListNotWellKnownWords.getListData().getSize()));
		}
		saved = false;
	}

	public void removeFromNotWellKnownWords() {
		String sel[] = lListNotWellKnownWords.getSelectedItems();
		if (sel != null) {
			lListWords.add(sel[0]);
			if (!nachAuftritt) {
				lListWords.setNachAlfabet();
			}
			lListNotWellKnownWords.remove(sel[0]);
			bTextFieldWords.setText(Integer.toString(lListWords.getListData().getSize()));
			bTextFielNotWellKnowndWords.setText(Integer.toString(lListNotWellKnownWords.getListData().getSize()));
		}
		saved = false;
	}

	public void nextPart() {
		if (!weiter()) return;
		aktPos = aktPos + delta;
//		if (aktPos >= 30) {
//			bToNextPart.setEnabled(false);
//		}
		bToNextPart.setText(minutes(aktPos + delta) + "-->");
		bToPrevPart.setText("<--" + minutes(aktPos));
		bToPrevPart.setEnabled(true);
		renewKomponenten();
	}
	
	public void prevPart() {
		if (!weiter()) return;
		aktPos = aktPos - delta;
		if (aktPos <= 0) {
			bToPrevPart.setEnabled(false);
		}
		if (aktPos < 30) {
			bToNextPart.setEnabled(true);
		}
		bToNextPart.setText(minutes(aktPos + delta) + "-->");
		bToPrevPart.setText("<--" + minutes(aktPos));
		renewKomponenten();
	}
	
	public void getPhrase(IWortliste wl) {
		tTextPaneOutput.setPhrase(wl.getSelectedItems()[0]);		
	}

	public void getTranslation() {
		String sel[] = lListWords.getSelectedItems();
		if (sel != null) {
//			new JTextpaneDemo(
//				"http://www.dict.cc/?s="
//					+ sel[0]);
//			new JTextpaneDemo("http://www.wordreference.com/ende/"
//					+ sel[0]);
//				new JTextpaneDemo(
//					"http://dict.leo.org/ende?lp=ende&lang=de&searchLoc=0&cmpType=relaxed&sectHdr=on&spellToler=on&relink=on&search="
//							+ sel[0]);
//			new JTextpaneDemo(
//			"http://dict.tu-chemnitz.de/?service=deen;query=$"+ sel[0]
//			                                                        		);
//			new JTextpaneDemo(
//			"http://odge.de/index.php?ebene=Suche&kw="
//				+ sel[0]);
//			new JTextpaneDemo(
//			"http://www.iee.et.tu-dresden.de/cgi-bin/cgiwrap/wernerr/search.sh?nocase=on&hits=50&string="
//				+ sel[0]);
//				new JTextpaneDemo(
//					"http://www.woerterbuch.info/?s=dict&l=en&query="
//					+ sel[0]);
			String ergb[] = Translator.getTranslations(sel[0]);
			
			TranslationWriter tw = new TranslationWriter(sel[0], ergb);
			tw.beginWrite();
			tw.writeAll();
			tw.endWrite();
			tw.showErgebnis();
		}
	}
	
	public void getExplanation() {
		String sel[] = lListWords.getSelectedItems();
		if (sel != null) {
			new JTextpaneDemo(
					"http://www.merriam-webster.com/cgi-bin/dictionary?book=Dictionary&va="
							+ sel[0]);
		}
		
	}

	public void selectWord() {
		lListKnownWords.deselect();
		lListOmittedWords.deselect();
		getPhrase(lListWords);
		this.bGetExplanation.setEnabled(true);
		this.bGetTranslation.setEnabled(true);
		this.bMoveRight.setEnabled(true);
		this.bMoveRight1.setEnabled(true);
		this.bMoveRight2.setEnabled(true);
	}

	public void selectKnownWord() {
		lListWords.deselect();
		lListOmittedWords.deselect();
		this.bMoveLeft.setEnabled(true);
	}

	public void selectOmittedWord() {
		lListWords.deselect();
		lListKnownWords.deselect();
		this.bMoveLeft1.setEnabled(true);
	}

	public void selectNotWellKnownWord() {
		lListWords.deselect();
		lListOmittedWords.deselect();
		this.bMoveLeft2.setEnabled(true);
		getPhrase(lListNotWellKnownWords);
	}

	public void deselect() {
		this.bGetExplanation.setEnabled(false);
		this.bGetTranslation.setEnabled(false);
		this.bMoveRight.setEnabled(false);
		this.bMoveRight1.setEnabled(false);
		this.bMoveRight2.setEnabled(false);
		this.bMoveLeft.setEnabled(false);
		this.bMoveLeft1.setEnabled(false);
		this.bMoveLeft2.setEnabled(false);
		
	}

	public void getAll() {
//		tTextPaneOutputAll.setPhrase(lListWords.getSelectedItems()[0]);		
		tTextPaneOutputAll.setPhrase();		
	}

	public void setAuftritt() {
		lListWords.setNachAuftritt();
		nachAuftritt = true;
	}
	
	public void setAlfabet() {
		lListWords.setNachAlfabet();
		nachAuftritt = false;
	}
	
	public void registerSave(ButtonSave bButtonAdd) {
		this.bButtonAdd = bButtonAdd;
	}
	
	public void registerSave(ButtonMoveToKnownWords bMoveRight) {
		this.bMoveRight = bMoveRight;
		this.bMoveRight.setEnabled(false);
	}
	
	public void registerSave(ButtonMoveFromKnownWords bMoveLeft) {
		this.bMoveLeft = bMoveLeft;
		this.bMoveLeft.setEnabled(false);
	}
	
	public void registerButton(ButtonMoveToNotImportantWords bMoveRight1) {
		this.bMoveRight1 = bMoveRight1;
		this.bMoveRight1.setEnabled(false);
	}
	
	public void registerButton(ButtonMoveFromNotImportandWords bMoveLeft1) {
		this.bMoveLeft1 = bMoveLeft1;
		this.bMoveLeft1.setEnabled(false);
	}
	
	public void registerButton(ButtonGetPhrase bGetFhrase) {
		this.bGetFhrase = bGetFhrase;
		this.bGetFhrase.setEnabled(false);
	}
	
	public void registerButton(ButtonGetTranslation bGetTranslation) {
		this.bGetTranslation = bGetTranslation;
		this.bGetTranslation.setEnabled(false);
	}
	
	public void registerButton(ButtonGetExplanation bGetExplanation) {
		this.bGetExplanation = bGetExplanation;
		this.bGetExplanation.setEnabled(false);
	}
	
	public void registerButton(ButtonMoveToNotWellKnownWords bGetExplanation) {
		this.bMoveRight2 = bGetExplanation;
		this.bMoveRight2.setEnabled(false);
	}
	
	public void registerButton(ButtonMoveFromNotWellKnownWords bGetExplanation) {
		this.bMoveLeft2 = bGetExplanation;
		this.bMoveLeft2.setEnabled(false);
	}
	
	public void registerButton(ButtonToNextPart bToNextPart) {
		this.bToNextPart = bToNextPart;
		this.bToNextPart.setEnabled(true);
		this.bToNextPart.setText(minutes(aktPos + delta) + "-->");
	}
	
	public void registerButton(ButtonToPrevPart bToPrevPart) {
		this.bToPrevPart = bToPrevPart;
		this.bToPrevPart.setEnabled(false);
		this.bToPrevPart.setText("<--" + minutes(aktPos));
	}
	
	public void registerList(ListWords lListWords) {
		this.lListWords = lListWords;
	}
	
	public void registerList(ListKnownWords lListKnownWords) {
		this.lListKnownWords = lListKnownWords;
	}
	
	public void registerList(ListOmittedWords lListOmittedWords) {
		this.lListOmittedWords = lListOmittedWords;
	}
	
	public void registerList(ListNotWellKnownWords lListOmittedWords) {
		this.lListNotWellKnownWords = lListOmittedWords;
	}
	
	public void registerTextPane(TextPaneOutput tTextPaneOutput) {
		this.tTextPaneOutput = tTextPaneOutput;
	}
	
	public void registerTextPaneAll(TextPaneOutputAll tTextPaneOutputAll) {
		this.tTextPaneOutputAll = tTextPaneOutputAll;
	}
	
	public void registerButtonAuftritt(RadioButtonNachAuftritt bButtonAuftritt) {
		this.bButtonAuftritt = bButtonAuftritt;
	}
	
	public void registerButtonAlfabet(RadioButtonNachAlfabet bButtonAlfabet) {
		this.bButtonAlfabet = bButtonAlfabet;
	}
	
	public void registerTextFieldWords(TextFieldAllWords bTextFieldWords) {
		this.bTextFieldWords = bTextFieldWords;
	}
	
	public void registerTextFielNotWellKnowndWords(TextFieldNotWellKnownWords bTextFielNotWellKnowndWords) {
		this.bTextFielNotWellKnowndWords = bTextFielNotWellKnowndWords;
	}
	
	private String minutes(int pMinutes) {
		String hours = String.valueOf(pMinutes / 60);
		String minutes = String.valueOf(pMinutes % 60);
		if (hours.length() == 1) hours = "0" + hours;
		if (minutes.length() == 1) minutes = "0" + minutes;
		return hours + ":" + minutes;
	}

	private void renewKomponenten() {
		wordModel.getWoerterLieferant().setStarPoint(minutes(aktPos));
		wordModel.getWoerterLieferant().setEndPoint(minutes(aktPos + delta));
		wordModel.renew();
		tTextPaneOutputAll.setPhrase();
		tTextPaneOutput.setPhrase(null);
		lListWords.fillWordList(wordModel.getListNotKnownWords());
		lListNotWellKnownWords.fillWordList(wordModel.getNotWellKnownWords());
		lListKnownWords.fillWordList(Collections.EMPTY_LIST);
		lListOmittedWords.fillWordList(Collections.EMPTY_LIST);
		bMoveRight.setEnabled(false);
		bMoveLeft.setEnabled(false);
		bMoveRight1.setEnabled(false);
		bMoveLeft1.setEnabled(false);
		bMoveRight2.setEnabled(false);
		bMoveLeft2.setEnabled(false);
		bGetFhrase.setEnabled(false);
		bGetTranslation.setEnabled(false);
		bGetExplanation.setEnabled(false);
		bTextFieldWords.setText(Integer.toString(lListWords.getListData().getSize()));
		bTextFielNotWellKnowndWords.setText(Integer.toString(lListNotWellKnownWords.getListData().getSize()));
		saved = true;
	}

	private boolean weiter() {
		int ans = JOptionPane.OK_OPTION;
		if (!saved) {
			ans = JOptionPane.showConfirmDialog(
				    frame,
				    "Model nicht gespeichert, weiter?",
				    "",
				    JOptionPane.YES_NO_OPTION);

		}
		if (ans == JOptionPane.NO_OPTION) return false; 
		return true;
	}
}
