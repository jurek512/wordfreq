package de.ziminski.words.lieferant;

import java.util.TreeSet;

import de.words.guikomponenten.listen.IWortliste;


public interface I_WoerterLieferant {

	public abstract String getText();

	/**
	 * Liest Die Datei mit den bekannten W�rtern
	 * 
	 * @return TreeSet bekannte W�rter
	 */
	public abstract TreeSet<String> getKnownWords();

	/**
	 * Liest die Datei mit den W�rtern die ausgelassen werden sollen
	 * 
	 * @return TreeSet die W�rter
	 */
	public abstract TreeSet<String> getOmitedWords();

	/**
	 * Liest die Datei mit den W�rtern die ausgelassen werden sollen
	 * 
	 * @return TreeSet die W�rter
	 */
	public abstract TreeSet<String> getNotWellKnownWords();

	public abstract void saveKnownWords(IWortliste wl);

	public abstract void saveUnimportantWords(IWortliste wl);

	public abstract void saveNotWellKnownWords(IWortliste wl);

	public void setStarPoint(String start);
	
	public void setEndPoint(String end);
}