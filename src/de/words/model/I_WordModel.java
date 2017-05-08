package de.words.model;

import java.util.Collection;

import de.ziminski.words.lieferant.I_WoerterLieferant;

public interface I_WordModel {

	public abstract void renew();

	/**
	 * liefert den gesamttext als string
	 * @return
	 */
	public abstract String getAllText();

	/**
	 * 
	 * @return TreeSet nicht bekannte W�rter
	 */
	public abstract Collection<String> getListNotKnownWords();

	/**
	 * Liefert die S�tze, die das Inputwort s beinhaltet, oder
	 * s mit der Gro�buchstabe am Anfang.
	 * 
	 * @param s String
	 * @return String
	 */
	public abstract String getSentencesIncludingWord(String s);

	/**
	 * Liest das gesamte Text bildet eine tabelle von Strings aufgrund der vorkommen von Leerzeichen
	 * (Leerzeichen, Tab, etc.) Falls so gewonnene Wort am Ende , . ; : enth�lt wird dieser entfernt.
	 * 
	 * @return String[]
	 */
	public abstract String[] getAllWordsFromAktTextAsTab();

	public abstract Collection<String> getNotWellKnownWords();

	public abstract I_WoerterLieferant getWoerterLieferant();

}