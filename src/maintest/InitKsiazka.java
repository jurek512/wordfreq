package maintest;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


import de.words.model.I_WordModel;
import de.words.model.WordModel;
import de.ziminski.words.lieferant.WoerterLieferant;
import de.ziminski.words.lieferant.WoerterLieferantSRT;

import struktura.Ksiaska;
import struktura.MaloZnaneSlowa;
import struktura.Slowo;
import struktura.SlowoWKsiazce;

public class InitKsiazka {
	private EntityManagerFactory factory;
	private EntityManager em;
	private Ksiaska ks;
	
	public void setDatabase(EntityManagerFactory factory) {
		this.factory = factory;
		em = factory.createEntityManager();
	}

	public void setUpKsiazka(String autor, String titel) {
		em.getTransaction().begin();

		// Read the existing entries
		Ksiaska ks1 = ksiazkaExist(em, autor, titel);
		if (!ks1.exists()) {
			ks1 = new Ksiaska(autor, titel);
			em.persist(ks1);
		}
		ks = ks1;

		em.getTransaction().commit();
	}

	public void leseTextUndMaloZnane(String pfadUndFilename) {
		leseText(pfadUndFilename);
		leseMaloZnaneSlowa(pfadUndFilename);
	}

	public void leseTexte(String pfad, List<String> fileNameListe) {
		for (String fileName : fileNameListe) {
			leseTextUndMaloZnane(pfad + fileName);
			System.out.println(fileName + " Fertig.");
		}
		System.out.println("Fertig.");
	}
	
	public void leseText(String filename) {
		WoerterLieferant wl;
		if (filename.matches(".{1,}\\.(s|S)(r|R)(t|T)")) {
			wl = new WoerterLieferantSRT(filename, factory, "00:00", "11:05");
		} else {
			wl = new WoerterLieferant(filename, factory,"","");
		}
		I_WordModel wm = WordModel.regexxFromWoerterLieferant(wl);
		
		EntityManager em = factory.createEntityManager();

		String[] woerter = wm.getAllWordsFromAktTextAsTab();
		em.getTransaction().begin();

//		Ksiaska ks = ksiazkaExist(em, "John Grisham", "The Last Juror");
//		assertTrue(ks != null);
		
		for(String wort : woerter) {
			if (wort == null || wort.length() == 0) continue;
			wort = wort.toLowerCase();
			Slowo mySlowo1 = isErfasst(em, wort);
			SlowoWKsiazce slwWks = null;
			if (mySlowo1.isEmpty()) {
				Slowo slow = new Slowo(wort);
				em.persist(slow);
				slwWks = new SlowoWKsiazce(ks, slow);
			} else {
				slwWks = isErfasst(em, mySlowo1, ks);
				if (slwWks.notExists()) {
					slwWks = new SlowoWKsiazce(ks, mySlowo1);
				} else {
					slwWks.setIleRazyWKsiazce(slwWks.getIleRazyWKsiazce() + 1);
				}
			}
			em.persist(slwWks);
		}
		em.getTransaction().commit();
		em.close();
	}

	public void leseMaloZnaneSlowa(String filename) {
		WoerterLieferant wl = new WoerterLieferant(filename, factory,"","");
		I_WordModel wm = WordModel.regexxFromWoerterLieferant(wl);

		Collection<String> notwellknown = wm.getNotWellKnownWords();

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		for(String lNotWell : notwellknown) {
			MaloZnaneSlowa myMaloZnane = isErfasstMaloZnane(em, lNotWell);
			if (!myMaloZnane.exists()) {
				Slowo mysl = isErfasst(em, lNotWell);
				MaloZnaneSlowa mzs = null;
				if (mysl.isEmpty()) {
					Slowo sl = new Slowo(lNotWell);
					em.persist(sl);
					mzs = new MaloZnaneSlowa(sl);
				} else {
					mzs = new MaloZnaneSlowa(mysl);
				}
				em.persist(mzs);
			}
		}
		em.getTransaction().commit();
	}

	
	
	// prüft ob das Buch existiert schon
	private Ksiaska ksiazkaExist(EntityManager em, String autor, String titel) {
		Query q = em.createQuery("select m from Ksiaska m where m.titel = :titel1 AND m.autor = :autor1");
		q.setParameter("autor1", autor);
		q.setParameter("titel1", titel);
		return q.getResultList().size() == 0 ? new Ksiaska() : (Ksiaska)q.getResultList().get(0);
	}

	// nicht in znane i niewazne
	public List<SlowoWKsiazce> getNieznaneSlowa() {
		Query q = em.createQuery(
				"select m from SlowoWKsiazce m where" +
				" not exists(select c from ZnaneSlowa c where c.znaneSlowo = m.slowo)" +
				" and " +
				"not exists(select d from NiewazneSlowa d where d.niewazneSlowo = m.slowo)" +
				" order by m.ileRazyWKsiazce desc, m.slowo.slowo");
		return (List<SlowoWKsiazce>)q.getResultList();
	}
	
	private MaloZnaneSlowa isErfasstMaloZnane(EntityManager em, String testSl) {
		Query q = em.createQuery("select s from MaloZnaneSlowa s where s.maloZnaneSlowo.slowo = :aktsl");
		q.setParameter("aktsl", testSl);
		List<MaloZnaneSlowa> l = q.getResultList();
		int i = l.size();
		assertTrue(i == 0 || i == 1);
		return i == 1 ? l.get(0) : new MaloZnaneSlowa();
	}

	private Slowo isErfasst(EntityManager em, String testSl) {
		Query q = em.createQuery("select s from Slowo s where s.slowo = :aktsl");
		q.setParameter("aktsl", testSl);
		int i = q.getResultList().size();
		assertTrue("a" + testSl + "b", i == 0 || i == 1);
		return i == 1 ? (Slowo)q.getResultList().get(0) : new Slowo();
	}

	private SlowoWKsiazce isErfasst(EntityManager em, Slowo testSl, Ksiaska ks) {
		Query qslwks = em.createQuery("select k from SlowoWKsiazce k where k.slowo = :slowow and k.kid = :myks");
		qslwks.setParameter("slowow", testSl);
		qslwks.setParameter("myks", ks);
		List<SlowoWKsiazce> slw = qslwks.getResultList();
		int i = slw.size();
		assertTrue(i == 0 || i == 1);
		return i == 1 ? (SlowoWKsiazce)slw.get(0) : new SlowoWKsiazce();
	}
	
	// nicht in znane i niewazne
	public void testJQL() {
		Query q = em.createQuery(
				"select m from SlowoWKsiazce m where" +
				" not exists(select c from ZnaneSlowa c where c.znaneSlowo = m.slowo)" +
				" and " +
				"not exists(select d from NiewazneSlowa d where d.niewazneSlowo = m.slowo)" +
				" order by m.ileRazyWKsiazce desc, m.slowo.slowo");
		List<SlowoWKsiazce> l = (List<SlowoWKsiazce>)q.getResultList();
		for (SlowoWKsiazce sl : l) {
			System.out.println(sl.getSlowo());
		}
		System.out.print("");
	}
	
	public void closeDatabase() {
		factory.close();
	}
	
	/**
	 * Test git
	 * 
	 * @param args
	 * 
	 * Aufruf pfad text1 text2 ... textn
	 */
	public static void main(String[] args) {
		final String PERSISTENCE_UNIT_NAME = "wordsfreq";
		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		InitKsiazka m = new InitKsiazka();
		m.setDatabase(factory);
		m.setUpKsiazka("HBO", "Sopranos Season 1");
		
		String filePfad = args[0] + "\\";
		List<String> fileTexte = new ArrayList<String>();
		for (String fileName : args) 
			fileTexte.add(fileName);
		fileTexte.remove(0); // pfad weg

		m.leseTexte(filePfad, fileTexte);
	}
}
