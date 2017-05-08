package de.ziminski.baseinit;

//import Database;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Jurek
 *
 */
public class TranslatorInit  {

	private static Database db = new Database("com.mysql.jdbc.Driver");
	
	public static final TranslatorInit INSTANCE = new TranslatorInit();
	
	private TranslatorInit() {
		super();
//		db = new Database("com.mysql.jdbc.Driver");
		db.Open("jdbc:mysql://localhost:3306/englissch?user=root&password=root", "englissch");
	}

	public static TranslatorInit getInstance() {
		return INSTANCE;
	}
	
//	public void init() {
//		db = new Database("com.mysql.jdbc.Driver");
//		db.Open("jdbc:mysql://localhost:3306/englissch?user=root&password=root", "englissch");
//	}
	
	public static String[] getTranslations() {
		resultSet rs = db.Execute("select * from woerter limit 0, 10");
		Collection<String> c = new ArrayList<String>();
		while(rs.hasMoreElements()) {
			c.add(rs.getColumnValue(1));
			c.add(rs.getColumnValue(2));
		}
		return c.toArray(new String[0]);
	}
	

	public static void close() {
		db.close();
	}
	
	private static String[] teile(String s) {
		return s.split("\\|");
	}
	
	private static boolean containsBalken(String s) {
		return s.contains("|");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Translator t = Translator.getInstance();
//		t.init();
		String s[] = TranslatorInit.getTranslations();
		TranslatorInit.close();
		for (int i = 0; i < s.length; i=i+2) {
			if (TranslatorInit.containsBalken(s[i])) {
				String[] t1 = TranslatorInit.teile(s[i]);
				String[] t2 = TranslatorInit.teile(s[i+1]);
				for (int j = 0; j < t1.length; j++) {
					System.out.println(t1[j].trim()+ "####"+ t2[j].trim());
				}
			} else {
				System.out.println(s[i]+ "####"+ s[i+1]);
			}
		}
//		InputFile f = new InputFile("de-en.txt");
//		String s = f.readLine();
//		String s2 = "";
//		while (s != null) {
//			String ss[] = s.split(" :: ");
//			if (ss.length == 1) {
//				s2 = "";
//			}
//			System.out.println(ss[0]);
//			ss[0] = ss[0].replaceAll("\\\"", "\'");
//			if (ss.length == 2) {
//			ss[1] = ss[1].replaceAll("\\\"", "\'");
//			s2 = ss[1];
//			System.out.println(ss[1]);
//			}
//			try {
//				Statement stmt = db.con.createStatement();
//				stmt.executeUpdate("insert into woerter values(\"" + ss[0] + "\",\"" + s2 + "\")");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				System.exit(0);
//			}
//			s = f.readLine();
//		}
//		
//		
//		f.close();

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
