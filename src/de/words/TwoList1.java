package de.words;

//Demonstration of simple Two-list program
//using awt controls

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

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
import de.words.model.WordModel;
import de.ziminski.words.lieferant.I_WoerterLieferant;
import de.ziminski.words.lieferant.WoerterLieferant;
import de.ziminski.words.lieferant.WoerterLieferantSRT;

public class TwoList1 extends Frame implements ActionListener, ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private enum LAYOUT_CONSTS {
		West,
		East,
		South,
		Center,
		North;
	}

	private I_WordModel wordModel;
	private Mediator med;

	public TwoList1(String verzeichnis, EntityManagerFactory factory) {
		super("Two Lists");
		setCloseClick();
		String filename = new TextFileFilter(verzeichnis).getFname();
		//		regxx = new Regexx(filename, "words.txt", "omitwords.txt", "notwellwords.txt");
		if (filename == null) {
			throw new RuntimeException("Empty File");
		}
		if (filename.matches(".{1,}\\.(s|S)(r|R)(t|T)")) {
			wordModel = WordModel.regexxFromWoerterLieferant((I_WoerterLieferant)new WoerterLieferantSRT(filename, factory, "00:00", "00:05"));
		} else {
			wordModel = WordModel.regexxFromWoerterLieferant((I_WoerterLieferant)new WoerterLieferant(filename, factory, "", ""));
		}
		med = new Mediator();
		med.init(wordModel, this);
		setGUI();
	}

	private void setLeftPanel() {
		Panel pLeft = new Panel();

		add(LAYOUT_CONSTS.West.name(), pLeft);

		pLeft.setLayout(new BorderLayout());
		// Panel wörter und save (linke Selte)
		pLeft.add(LAYOUT_CONSTS.North.name(), getLeftPanelNord());

		ListWords lListWords = new ListWords(med, wordModel);
		pLeft.add(LAYOUT_CONSTS.Center.name(), lListWords);

//		Panel pBottomWest = new Panel();
		pLeft.add(LAYOUT_CONSTS.South.name(), getLeftPanelSouth());
	}
	
	private Panel getLeftPanelNord() {
		Panel pTop = new Panel();
		ButtonSave bSaveButton = new ButtonSave(this, med);
		pTop.add(bSaveButton);
		return pTop;
	}

	private Panel getLeftPanelSouth() {
		Panel pBottomWest = new Panel();
		RadioButtonNachAlfabet nachAlfabet = new RadioButtonNachAlfabet(this, med);
		RadioButtonNachAuftritt nachAuftritt = new RadioButtonNachAuftritt(this, med);
		pBottomWest.add(nachAuftritt);
		pBottomWest.add(nachAlfabet);
		ButtonGroup bgroup = new ButtonGroup();
		bgroup.add(nachAuftritt);
		bgroup.add(nachAlfabet);
		return pBottomWest;
	}
	
	private void setCenterPanel() {
		Panel pCenter = new Panel();
		add(LAYOUT_CONSTS.Center.name(), pCenter);

		pCenter.setLayout(new BorderLayout());
		//center
		TextPaneOutputAll tTextPaneOutputAll = new TextPaneOutputAll(med, wordModel);
		tTextPaneOutputAll
				.setText("                                                                                                          \n\n\n\n\n\n");
		JScrollPane jsp1 = new JScrollPane(tTextPaneOutputAll);
		jsp1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		pCenter.add(jsp1, BorderLayout.CENTER);
		jsp1.setBorder(new TitledBorder("AAAAAAAAAAAAAAAAAAAAAAAAAAA"));
		tTextPaneOutputAll.setPhrase();
	}
	
	private void setRightPanel() {
		Panel pRight = new Panel();

		add(LAYOUT_CONSTS.East.name(), pRight);

		pRight.setLayout(new GridLayout(3, 2));
		//right border contains add and remove buttons
		// known words
		Panel rbTop = new Panel();
		rbTop.add(rightPanelButtonsKnown());
		pRight.add(rbTop);

		ListKnownWords lListKnownWords = new ListKnownWords(med, wordModel);
		pRight.add(lListKnownWords);

		// omitted words
		Panel rbTop1 = new Panel();
		rbTop1.add(rightPanelButtonsOmitted());
		pRight.add(rbTop1);

		ListOmittedWords lListOmittedWords = new ListOmittedWords(med, wordModel);
		pRight.add(lListOmittedWords);

		// not well known words
		Panel rbTop2 = new Panel();
		rbTop2.add(rightPanelButtonsNotWellKnown());
		pRight.add(rbTop2);

		ListNotWellKnownWords lListNotWellKnownWords = new ListNotWellKnownWords(med, wordModel);
		pRight.add(lListNotWellKnownWords);
	}

	private Panel rightPanelButtonsKnown() {
		Panel rbTop1but = new Panel();
		rbTop1but.setLayout(new BoxLayout(rbTop1but, BoxLayout.PAGE_AXIS));
		ButtonMoveToKnownWords bMoveRightToKnown = new ButtonMoveToKnownWords(this, med);
		ButtonMoveFromKnownWords bMoveLeftFromKnown = new ButtonMoveFromKnownWords(this, med);
		rbTop1but.add(new JLabel("Known words"));
		rbTop1but.add(bMoveRightToKnown);
		rbTop1but.add(bMoveLeftFromKnown);
		return rbTop1but;
	}	


	
	private Panel rightPanelButtonsOmitted() {
		Panel rbTop1but1 = new Panel();
		rbTop1but1.setLayout(new BoxLayout(rbTop1but1, BoxLayout.PAGE_AXIS));
		rbTop1but1.add(new JLabel("Omitted words"));
		ButtonMoveToNotImportantWords bMoveRightToOmitted = new ButtonMoveToNotImportantWords(this, med);
		ButtonMoveFromNotImportandWords bMoveLeftFromOmitted = new ButtonMoveFromNotImportandWords(this, med);
		rbTop1but1.add(bMoveRightToOmitted);
		rbTop1but1.add(bMoveLeftFromOmitted);
		return rbTop1but1;
	}	

	
	private Panel rightPanelButtonsNotWellKnown() {
		Panel rbTop1but2 = new Panel();
		rbTop1but2.setLayout(new BoxLayout(rbTop1but2, BoxLayout.PAGE_AXIS));
		rbTop1but2.add(new JLabel("Not well Known"));
		ButtonMoveToNotWellKnownWords bMoveRightToNotwellknown = new ButtonMoveToNotWellKnownWords(this, med);
		ButtonMoveFromNotWellKnownWords bMoveLeftFromNotwellknown = new ButtonMoveFromNotWellKnownWords(this, med);
		rbTop1but2.add(bMoveRightToNotwellknown);
		rbTop1but2.add(bMoveLeftFromNotwellknown);
		return rbTop1but2;
	}

	private void setBottomPanel() {
		Panel pBottom = new Panel();

		add(LAYOUT_CONSTS.South.name(), pBottom);
		// unteres Teil
		TextPaneOutput tTextPaneOutput = new TextPaneOutput(med, wordModel);
		tTextPaneOutput
				.setText("                                                                                                          \n\n\n\n\n\n");
		JScrollPane jsp = new JScrollPane(tTextPaneOutput);
		jsp.setBorder(new TitledBorder("AAAAAAAAAAAAAAAAAAAAAAAAAAA"));

		pBottom.add(jsp);
		//panel für untere Ttasten
		pBottom.add(setBottomButtonsPanel());
		// ende
	}

	private Panel setBottomButtonsPanel() {
		Panel rbBottomBut = new Panel();
		rbBottomBut.setLayout(new BoxLayout(rbBottomBut, BoxLayout.PAGE_AXIS));
		ButtonGetPhrase bButtonGetPhrase = new ButtonGetPhrase(this, med);
		ButtonGetTranslation bButtonGetTranslation = new ButtonGetTranslation(this, med);
		ButtonGetExplanation bButtonGetExplanation = new ButtonGetExplanation(this, med);

		rbBottomBut.add(bButtonGetPhrase);
		rbBottomBut.add(bButtonGetTranslation);
		rbBottomBut.add(bButtonGetExplanation);
		return rbBottomBut;
	}
	
	
	private void setTopPanel() {
		Panel pUpper = new Panel();

		add(LAYOUT_CONSTS.North.name(), pUpper);

		pUpper.add(setTopPanelPanel());
	}

	private Panel setTopPanelPanel() {
		Panel rbBottomBut = new Panel();
		rbBottomBut.setLayout(new BoxLayout(rbBottomBut, BoxLayout.LINE_AXIS));
		ButtonToPrevPart bToPrev = new ButtonToPrevPart(this, med);
		rbBottomBut.add(bToPrev);
		ButtonToNextPart bToNext = new ButtonToNextPart(this, med);
		rbBottomBut.add(bToNext);
		JLabel notKnownWordsLabel = new JLabel("Not known words:");
		rbBottomBut.add(notKnownWordsLabel);
		TextFieldAllWords txt = new TextFieldAllWords(med, wordModel);
		rbBottomBut.add(txt);
		TextFieldNotWellKnownWords txt1 = new TextFieldNotWellKnownWords(med, wordModel);
		JLabel notWellKnownWordsLabel = new JLabel("Not well known words:");
		rbBottomBut.add(notWellKnownWordsLabel);
		rbBottomBut.add(txt1);
		return rbBottomBut;
	}
	
	//--------------------------------------------
	private void setGUI() {
		setLayout(new BorderLayout()); //two columns
		setBackground(Color.lightGray);

		setLeftPanel();
		setCenterPanel();
		setRightPanel();
		setBottomPanel();
		setTopPanel();

		//top panel contains text field and 

		setSize(new Dimension(600, 500));
		setVisible(true);
	}

	//-----------------------------------------  
	private void setCloseClick() {
		//create window listener to respond to window close click
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	//---------------------------------------
	public void actionPerformed(ActionEvent e) {
		Command comd = (Command) e.getSource();
		comd.execute();
	}

	public void itemStateChanged(ItemEvent e) {
		Command comd = (Command) e.getSource();
		if (e.getStateChange() == ItemEvent.SELECTED)
			comd.execute();
	}

	//--------------------------------------------
	static public void main(String argv[]) {
		final String PERSISTENCE_UNIT_NAME = "wordsfreq";
		EntityManagerFactory factory;
		
		String verz = null;
		if (argv.length == 0) {
			verz = ".";
		} else {
			verz = argv[0];
		}
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		new TwoList1(verz, factory);
	}
}
