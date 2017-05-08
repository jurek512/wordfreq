package de.words.guikomponenten.listen;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionListener;

import de.words.JListData;
import de.words.Mediator;
import de.words.model.I_WordModel;

public class ListWords extends AbstrakteListe implements ListSelectionListener, ListDataListener, IWortliste {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListWords(Mediator md, I_WordModel wordModel) {
		super(20, wordModel, md);
		med.registerList(this);
		addListSelectionListener(this);
		getListData().addListDataListener(this);
		Collection<String> ts = this.wordModel.getListNotKnownWords();
		fillWordList(ts);
	}

	protected void selectWords() {
		med.selectWord();
	}

//	//----------------------------------
//	private void fillWordList(Collection<String> ts, String s) {
////		this.removeAllElements();
//		Iterator<String> it = ts.iterator();
//		JListData jld = this.getListData();
//		int jld_size = jld.getSize();
//		int i;
//		for (i = 0; i < jld_size; i++) {
//			if (!it.hasNext()) break;
//			String sFromRexx = it.next();
//			String sFromJlistData = (String)jld.getElementAt(i);
//			if (sFromRexx.equals(sFromJlistData)) continue;
//			if (!sFromJlistData.equals(sFromRexx) && sFromRexx.equals(s)) {
//				jld.insertElementAt(s, i);
//				break;
//			}
//			if (!sFromRexx.equals(sFromJlistData)) {
//				i--;
//			}
//		}
//		if (i == jld_size) {
//			jld.insertElementAt(s, i);
//		}
//	}

	/**
	 * ts - beinhaltet den vollen umfang der nicht gakannten wörter
	 * jld - beinhaltet ts ohne wörter , die inzwischen in die anderen listen
	 * verschoben würden (z.B. in die gekannten Wörtern, nicht gut gekannten
	 * oder unwichtige) 
	 */
	private void fillWordList(Collection<String> ts, String s) {
		JListData jld = this.getListData();
		int posInDerListe = 0;
		int listeLaenge = jld.getSize();
		for (String sFromRexx : ts) {
			String sFromJlistData = (String)jld.getElementAt(posInDerListe);
			if (!sFromJlistData.equals(sFromRexx) && sFromRexx.equals(s)) {
				break; // an dieser Stelle soll das Wort eingefügt werden
			}
			if (!sFromJlistData.equals(sFromRexx) && !sFromRexx.equals(s)) {
				continue; // das Wort aus dem Model ist nicht in der lListe
			}
			if (sFromJlistData.equals(sFromRexx)) {
				posInDerListe++; // die Wörter stimmen überein, beide listen iteriert
			}
			if (posInDerListe >= listeLaenge) {
				break; // das Wort soll am Ende der Liste angefügt werden
			}
		}
		
		if (posInDerListe < listeLaenge) {
			jld.insertElementAt(s, posInDerListe);
		} else {
			jld.addElement(s);
		}
	}

	public void setNachAuftritt() {
		fillWordList(setNach());
	}

	public void setNachAlfabet() {
		String[] o = setNach().toArray(new String[0]);
		Arrays.sort(o);
		fillWordList(Arrays.asList(o));

	}
	
	private Collection<String> setNach() {
		Collection<String> ts = wordModel.getListNotKnownWords();
		LinkedHashSet<String> ts1 = (LinkedHashSet<String>)ts;
		ts1 = (LinkedHashSet<String>)ts1.clone();
		JListData jld = this.getListData();
		for (String s : ts) {
			if (!contains(jld,s)) ts1.remove(s);
		}
		return ts1;
	}

	public void add(String s) {
		Collection<String> ts = wordModel.getListNotKnownWords();
		fillWordList(ts, s);
	}

	private boolean contains(JListData jdl, String s) {
		for (int i = 0; i < jdl.getSize(); i++) {
			if (jdl.getElementAt(i).equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	public void contentsChanged(ListDataEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("contentC");
	}

	public void intervalAdded(ListDataEvent arg0) {
		// TODO Auto-generated method stub
		JListData listData = (JListData)arg0.getSource();
		System.out.println("intervalA"+listData.getElementAt(listData.getSize()-1));
	}

	public void intervalRemoved(ListDataEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("intervalr"+arg0.getSource());
	}

}
