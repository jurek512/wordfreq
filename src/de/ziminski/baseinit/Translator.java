package de.ziminski.baseinit;

//import Database;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Jurek
 *
 */
public class Translator  {

	private static Database db = new Database("com.mysql.jdbc.Driver");
	
	public static final Translator INSTANCE = new Translator();
	
	private Translator() {
		super();
//		db = new Database("com.mysql.jdbc.Driver");
		db.Open("jdbc:mysql://localhost:3306/englishdictcc?user=root&password=root", "englissch");
	}

	public static Translator getInstance() {
		return INSTANCE;
	}
	
//	public void init() {
//		db = new Database("com.mysql.jdbc.Driver");
//		db.Open("jdbc:mysql://localhost:3306/englissch?user=root&password=root", "englissch");
//	}
	
	public static String[] getTranslations(String s) {
// ist mysql spezifisch
// benötigt fulltext index, tybletype myisam und einstellungen in my.ini
//ft_min_word_len = 1
//ft_stopword_file = 
		
		resultSet rs = db.Execute("select de, en from englishdictcc.woerter where match(en) against('"+s+"' in boolean mode) order by char_length(en), en");

//		resultSet rs = db.Execute("select * from woerter where en REGEXP '^(.*[^a-zA-Z])*"+s+"([^a-zA-Z]+.*)*$' order by char_length(en), en");
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
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Translator t = Translator.getInstance();
//		t.init();
		String s[] = Translator.getTranslations("emergency");
		Translator.close();
		for (int i = 0; i < s.length; i++) {
			System.out.println(s[i]);
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
