/**
 * 
 */
package de.words.mocktests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import struktura.I_Slowo;
import struktura.Slowo;
import struktura.ZnaneSlowa;

import de.ziminski.words.lieferant.WoerterLieferant;

/**
 * @author Jurek
 *
 */
public class Test1 {

	WoerterLieferant classUnderTest;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetText() {
		EntityManagerFactory emf = EasyMock.createMock(EntityManagerFactory.class);
		classUnderTest = new WoerterLieferant("1a.txt", emf, "", "");
		assertTrue(classUnderTest.getText().length() > 0);
		assertTrue(classUnderTest.getText().startsWith("After decades"));
		assertTrue(classUnderTest.getText().endsWith("Ford County, Mississippi. "));
	}

	@Test
	public void testGetAllWordsFromAktTextAsTab() {
		EntityManagerFactory emf = EasyMock.createMock(EntityManagerFactory.class);
		classUnderTest = new WoerterLieferant("1a.txt", emf, "", "");
		String[] slowa = classUnderTest.getAllWordsFromAktTextAsTab();
		assertTrue(slowa[0].equals("After") );
		assertTrue(slowa[slowa.length-1].equals("Mississippi") );
	}

	@Test
	public void testGetElementsFromNamed() {
		EntityManagerFactory emf = EasyMock.createMock(EntityManagerFactory.class);
		EntityManager em = EasyMock.createMock(EntityManager.class);
		Query q = EasyMock.createMock(Query.class);

		List<I_Slowo> l = new ArrayList<I_Slowo>();
		I_Slowo aSlowo = new ZnaneSlowa();
		Slowo s = new Slowo("aaa");
		aSlowo.setSlowoAllg(s);
		l.add(aSlowo);

		EasyMock.expect(emf.createEntityManager()).andReturn(em).anyTimes();
		EasyMock.expect(em.createNamedQuery("ZnaneSlowa.AllZnaneSlowa")).andReturn(q).anyTimes();
		EasyMock.expect(q.getResultList()).andReturn(l).anyTimes();
		EasyMock.replay(emf);
		EasyMock.replay(em);
		EasyMock.replay(q);
		classUnderTest = new WoerterLieferant("1a.txt", emf, "", "");
		assertTrue(!classUnderTest.getElementsFromNamed("ZnaneSlowa.AllZnaneSlowa").isEmpty());

		classUnderTest = new WoerterLieferant("1a.txt", emf, "", "");
		l = new ArrayList<I_Slowo>();
		EasyMock.reset(emf);
		EasyMock.reset(em);
		EasyMock.reset(q);
		EasyMock.expect(emf.createEntityManager()).andReturn(em).anyTimes();
		EasyMock.expect(em.createNamedQuery("ZnaneSlowa.AllZnaneSlowa")).andReturn(q).anyTimes();
		EasyMock.expect(q.getResultList()).andReturn(l).anyTimes();
		EasyMock.replay(emf);
		EasyMock.replay(em);
		EasyMock.replay(q);
		assertTrue(classUnderTest.getElementsFromNamed("ZnaneSlowa.AllZnaneSlowa").isEmpty());
	}

//	@Test
//	public void testGetNotWellKnownWords() {
//		EntityManagerFactory emf = EasyMock.createMock(EntityManagerFactory.class);
//		EntityManager em = EasyMock.createMock(EntityManager.class);
//		Query q = EasyMock.createMock(Query.class);
//
//		List<I_Slowo> l = new ArrayList<I_Slowo>();
//		I_Slowo aSlowo = new ZnaneSlowa();
//		Slowo s = new Slowo("aaa");
//		aSlowo.setSlowoAllg(s);
//		l.add(aSlowo);
//
//		EasyMock.expect(emf.createEntityManager()).andReturn(em).anyTimes();
//		EasyMock.expect(em.createNamedQuery("ZnaneSlowa.AllZnaneSlowa")).andReturn(q).anyTimes();
//		EasyMock.expect(q.getResultList()).andReturn(l).anyTimes();
//		EasyMock.replay(emf);
//		EasyMock.replay(em);
//		EasyMock.replay(q);
//		classUnderTest = new WoerterLieferant("1a.txt", emf, "", "");
//		EasyMock.careateMock
//		
//		assertTrue(!classUnderTest.getNotWellKnownWords().isEmpty());
//
//		classUnderTest = new WoerterLieferant("1a.txt", emf, "", "");
//		l = new ArrayList<I_Slowo>();
//		EasyMock.reset(emf);
//		EasyMock.reset(em);
//		EasyMock.reset(q);
//		EasyMock.expect(emf.createEntityManager()).andReturn(em).anyTimes();
//		EasyMock.expect(em.createNamedQuery("ZnaneSlowa.AllZnaneSlowa")).andReturn(q).anyTimes();
//		EasyMock.expect(q.getResultList()).andReturn(l).anyTimes();
//		EasyMock.replay(emf);
//		EasyMock.replay(em);
//		EasyMock.replay(q);
//		assertTrue(classUnderTest.getElementsFromNamed("ZnaneSlowa.AllZnaneSlowa").isEmpty());
//	}

}
