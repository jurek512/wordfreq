package de.words.model;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.ziminski.words.lieferant.I_WoerterLieferant;


/**
 * @author tester1
 *
 */
public class WordModel implements I_WordModel {
	private TreeSet<String> knownwords;
	private LinkedHashSet<String> unknownwords;
	private Set<String>  allwords;
	private String inputTextAll;
	private I_WoerterLieferant wl;

	private WordModel(I_WoerterLieferant wl) {
		this.wl = wl;
		inputTextAll = wl.getText();
		allwords = getAllWordsFromAktualText();
		knownwords = wl.getKnownWords();
		knownwords.addAll(wl.getOmitedWords());
		knownwords.addAll(wl.getNotWellKnownWords());
		this.unknownwords = new LinkedHashSet<String>();
//		this.unknownwords = new TreeSet();
	}

	public static I_WordModel regexxFromWoerterLieferant(I_WoerterLieferant wl) {
		return new WordModel(wl);
	}
	
	/* (non-Javadoc)
	 * @see de.words.model.I_WordModel#renew()
	 */
	public void renew() {
		inputTextAll = wl.getText();
		allwords = getAllWordsFromAktualText();
		knownwords = wl.getKnownWords();
		knownwords.addAll(wl.getOmitedWords());
		knownwords.addAll(wl.getNotWellKnownWords());
		this.unknownwords = new LinkedHashSet<String>();
	}

	/* (non-Javadoc)
	 * @see de.words.model.I_WordModel#getAllText()
	 */
	public String getAllText() {
		return inputTextAll;
	}
	
	/* (non-Javadoc)
	 * @see de.words.model.I_WordModel#getListNotKnownWords()
	 */
	public Collection<String> getListNotKnownWords() {
      for (String s1 : allwords) {
    	  if (!knownwords.contains(s1)) {
    		  unknownwords.add(s1);
    	  }
      }
      return unknownwords;
	}
	
	/* (non-Javadoc)
	 * @see de.words.model.I_WordModel#getSentencesIncludingWord(java.lang.String)
	 */
	public String getSentencesIncludingWord(String s) {
		String strResult = "";
		
		// suche nach den Wörtenrn mit Kleinbuchstaben z.B. captain
//		Pattern p = Pattern.compile("\\.[^\\.]*\\s+"+s+"\\.|\\.[^\\.]*\\s+"+s+"[\\s\\.,:;]+[^\\.]*\\.");
		String s1 = "[" + s.substring(0, 1).toUpperCase() + s.substring(0, 1).toLowerCase() + "]" + s.substring(1);
		Pattern p = Pattern.compile("[^.?!]*\\b"+s1+ "\\b[^.?!]*.");
		Matcher m = p.matcher(inputTextAll);
		int i = 0;
		while (m.find(i)) {
			strResult += inputTextAll.substring(m.start(),m.end());
			i = m.end();
		}
		
		return strResult;
	}

	/**
	 * Liest das gesamte Text bilett eine tabelle von Strings aufgrund der vorkommen von Leerzeichen
	 * (Leerzeichen, Tab, etc.) 
	 * 
	 * 
	 * @return
	 */
	private LinkedHashSet<String> getAllWordsFromAktualText() {
		LinkedHashSet<String> ts = new LinkedHashSet<String>();
		String[] stab = getAllWordsFromAktTextAsTab();
		for (int i = 0; i < stab.length; i++) {
			if (stab[i].length() > 1) ts.add(stab[i].toLowerCase());
		}
		return ts;
	}

	/* (non-Javadoc)
	 * @see de.words.model.I_WordModel#getAllWordsFromAktTextAsTab()
	 */
	public String[] getAllWordsFromAktTextAsTab() {
//		return inputTextAll.split("\\b[^a-zA-Z]*\\b");
		return inputTextAll.split("[^a-zA-Z]+");
	}
	
	/* (non-Javadoc)
	 * @see de.words.model.I_WordModel#getNotWellKnownWords()
	 */
	public Collection<String> getNotWellKnownWords(){
		return wl.getNotWellKnownWords();
	}
	
	/* (non-Javadoc)
	 * @see de.words.model.I_WordModel#getWoerterLieferant()
	 */
	public I_WoerterLieferant getWoerterLieferant() {
		return wl;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Regexx regxx = new Regexx("DesignJava.txt", "words.txt", "omitwords.txt", "notwellwords.txt");
//		regxx.listAllWordsKnownWords();
//		regxx.listNotKnownWordsWithsPhrases();
//		System.out.println(regxx.getPhrase("upward"));
//		System.out.println(regxx.getAll());
//		TreeSet ts = regxx.listNotKnownWords();
//		Iterator it = ts.iterator();
//		while(it.hasNext()) {
//			System.out.println(it.next());
//		}
	}
}

/*
\b[Kk](eep|ept)\b[^.?!]*?\b(around|at|away|back|down|in|off|on|out|over|to|up|up with)\b[^.?!]*.*/