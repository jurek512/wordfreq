package de.ziminski.words.lieferant;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import struktura.MaloZnaneSlowa;
import struktura.NiewazneSlowa;
import struktura.Slowo;
import struktura.ZnaneSlowa;
import de.words.JListData;
import de.words.guikomponenten.listen.IWortliste;
import struktura.I_Slowo;

/**
 * @author Jurek
 *
 */
public class WoerterLieferant implements I_WoerterLieferant {

	private enum SlowoArt {
		ZNANESLOWA("select s from ZnaneSlowa s where s.znaneSlowo.id = :sl") {
			public I_Slowo getObjekt() {
				return new ZnaneSlowa();
			}
		},
		NIEWAZNESLOWA("select s from NiewazneSlowa s where s.niewazneSlowo.id = :sl") {
			public I_Slowo getObjekt() {
				return new NiewazneSlowa();
			}
		},
		MALOZNANESLOWA("select s from MaloZnaneSlowa s where s.maloZnaneSlowo.id = :sl") {
			public I_Slowo getObjekt() {
				return new MaloZnaneSlowa();
			}
		};
		
		private String jSQl;
		
		private SlowoArt(String jSQL) {
			this.jSQl = jSQL;
		}
		
		public I_Slowo getObjekt() {
			return this.getObjekt();
		}
		
	}
	
	private EntityManagerFactory factory;
	private EntityManager em;
	
	protected static class Filename {
		String filename;

		Filename(String s) {
			this.filename = s;
		}

		String getName() {
			return filename;
		}
	}

//	String text = null;
	protected Filename inputText;
	String start;
	String end;

	public WoerterLieferant(String inputText, EntityManagerFactory factory, String start, String end) {
		super();
		this.inputText = new Filename(inputText);
		this.factory = factory;
		em = factory.createEntityManager();
		this.start = start;
		this.end = end;
	}

	public void setStarPoint(String start) {
		this.start = start;
	}
	
	public void setEndPoint(String end) {
		this.end = end;
	}
	
	
	/* (non-Javadoc)
	 * @see de.words.lieferant.I_WoerterLieferant#getText()
	 */
	public String getText() {
		InputFile f = null;
		try {
			f = new InputFile(inputText.getName());
			String fileAsString = getFileAsString(f);
			return fileAsString;
		} finally {
			f.close();
		}
	}
	
	private String getFileAsString(InputFile f) {
		StringBuffer sb = new StringBuffer();
		String s = f.readLine();
		while (s != null) {
			sb.append(s + " ");
			s = f.readLine();
		}
		return sb.toString();
	}
	
	/**
	 * Liest das gesamte Text bildet eine tabelle von Strings aufgrund der vorkommen von Leerzeichen
	 * (Leerzeichen, Tab, etc.) Falls so gewonnene Wort am Ende , . ; : enthält wird dieser entfernt.
	 * 
	 * @return String[]
	 */
	public String[] getAllWordsFromAktTextAsTab() {
//		return inputTextAll.split("\\b[^a-zA-Z]*\\b");
		return getText().split("[^a-zA-Z]+");
	}

	/* (non-Javadoc)
	 * @see de.words.lieferant.I_WoerterLieferant#getKnownWords()
	 */
	public TreeSet<String> getKnownWords() {
		return getElementsFromNamed("ZnaneSlowa.AllZnaneSlowa");
	}

	/* (non-Javadoc)
	 * @see de.words.lieferant.I_WoerterLieferant#getOmitedWords()
	 */
	public TreeSet<String> getOmitedWords() {
		return getElementsFromNamed("NiewazneSlowa.AllNiewazneSlowa");
	}

	/* (non-Javadoc)
	 * @see de.words.lieferant.I_WoerterLieferant#getNotWellKnownWords()
	 */
	public TreeSet<String> getNotWellKnownWords() {
		TreeSet<String> allMaloZnane = getElementsFromNamed("MaloZnaneSlowa.AllMaloZnane");
		List<String> slowaWTekscie = Arrays.asList(getText().split("[^a-zA-Z]+"));
		TreeSet<String> exclude = new TreeSet<String>();
		
		for (String s : slowaWTekscie) {
			String lowerCase = s.toLowerCase();
			if (allMaloZnane.contains(lowerCase)) {
				exclude.add(lowerCase);
			}
		}
		
		return exclude;
	}

	public TreeSet<String>  getElementsFromNamed(String query) {
		Query q = em.createNamedQuery(query);
		List<I_Slowo> l = (List<I_Slowo>)q.getResultList();
		TreeSet<String> erg = new TreeSet<String>();
		for (I_Slowo zn : l) {
			erg.add(zn.getSlowoAllg().getSlowo());
		}
		return erg;
	}

	/* (non-Javadoc)
	 * @see de.words.lieferant.I_WoerterLieferant#saveKnownWords(de.words.Wortliste)
	 */
	public void saveKnownWords(IWortliste wl) {
		saveWords(wl, SlowoArt.ZNANESLOWA);
	}

	/* (non-Javadoc)
	 * @see de.words.lieferant.I_WoerterLieferant#saveUnimportantWords(de.words.Wortliste)
	 */
	public void saveUnimportantWords(IWortliste wl) {
		saveWords(wl, SlowoArt.NIEWAZNESLOWA);
	}

	/* (non-Javadoc)
	 * @see de.words.lieferant.I_WoerterLieferant#saveNotWellKnownWords(de.words.Wortliste)
	 */
	public void saveNotWellKnownWords(IWortliste wl) {
		saveWords(wl, SlowoArt.MALOZNANESLOWA);
	}
	
	public void removeKnownWord(String s) {
		String q1 = "select s from ZnaneSlowa s where s.znaneSlowo.slowo = :sl";
		removeSlowo(s, q1);
	}

	public void removeUnimportantWord(String s) {
		String q1 = "select s from NiewazneSlowa s where s.niewazneSlowo.slowo = :sl";
		removeSlowo(s, q1);
	}

	public void removeNotWellKnownWord(String s) {
		String q1 = "select s from MaloZnaneSlowa s where s.maloZnaneSlowo.slowo = :sl";
		removeSlowo(s, q1);
	}

	private void saveWords(IWortliste wl, SlowoArt slowoArt) {
		JListData jld = wl.getListData();
		Query q = em.createQuery("select s from Slowo s where s.slowo = :sl");
		Query q1 = em.createQuery(slowoArt.jSQl);
		for (int i = 0; i < jld.getSize(); i++) {
			String slowoZListy = (String) jld.getElementAt(i);
			Slowo slowo = getSlowoFromSlowaByString(q, slowoZListy);
			List<I_Slowo> zna = getListeBySlowo(q1, slowo);
			if (nichtVorhanden(zna)) {
				writeSlowo(slowo, slowoArt);
			}
		}
	}
	
	private Slowo getSlowoFromSlowaByString(Query q, String searched) {
		q.setParameter("sl", searched);
		return (Slowo)q.getSingleResult();
	}

	private List<I_Slowo> getListeBySlowo(Query q1, Slowo searched) {
		q1.setParameter("sl", searched.getId());
		return (List<I_Slowo>)q1.getResultList();
	}
	
	private boolean nichtVorhanden(List<I_Slowo> zna) {
		return zna.size() == 0;
	}
	
	private void writeSlowo(Slowo ss, SlowoArt zn) {
		em.getTransaction().begin();
		I_Slowo wrap = zn.getObjekt();
		wrap.setSlowoAllg(ss);
		em.persist(wrap);
		em.getTransaction().commit();
	}
	
	private void removeSlowo(String s, String query) {
		Query q1 = em.createQuery(query);
		em.getTransaction().begin();
		q1.setParameter("sl", s);
		I_Slowo ss = (I_Slowo)q1.getSingleResult();
		if (ss != null) em.remove(ss);
		em.getTransaction().commit();
	}
	
}

//private TreeSet<String>  getElements(String query) {
//Query q = em.createQuery(query);
//List<I_Slowo> l = (List<I_Slowo>)q.getResultList();
//TreeSet<String> erg = new TreeSet<String>();
//for (I_Slowo zn : l) {
//	erg.add(zn.getSlowoAllg().getSlowo());
//}
//return erg;
//}

/*
 * select m from Slowo m where m.slowo = 'worked'
select m from Slowo m where m.slowo like 'work%'
select m.znaneSlowo.slowo from ZnaneSlowa m where m.znaneSlowo.slowo like 'work%'
select m from Ksiaska m where m.autor = 'John Grisham' and m.titel like 'The Last%'

select m.slowo.slowo, m.ileRazyWKsiazce from SlowoWKsiazce m where
 m.kid in (select n.id from Ksiaska n where n.autor = 'John Grisham' and n.titel = 'The Last Jurror')
 order by m.ileRazyWKsiazce desc 
 
select m.slowo.slowo, m.ileRazyWKsiazce from SlowoWKsiazce m where (m.kid
 in (select n.id from Ksiaska n where n.autor = 'John Grisham' and n.titel = 'The Last Jurror'))
and (m.slowo
not in (select o from ZnaneSlowa o))
order by m.ileRazyWKsiazce desc 

select m.slowo.slowo, m.ileRazyWKsiazce from SlowoWKsiazce m where (m.kid
 in (select n.id from Ksiaska n where n.autor = :myautor and n.titel = :mytitel))
and
 (m.slowo.id not in (select o.znaneSlowo.id from ZnaneSlowa o))
and 
 (m.slowo.id not in (select o.niewazneSlowo.id from NiewazneSlowa o))
and 
 (m.slowo.id not in (select o.maloZnaneSlowo.id from MaloZnaneSlowa o))
order by m.ileRazyWKsiazce desc 
 */
