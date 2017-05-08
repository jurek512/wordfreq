package de.words;

public class TranslationWriter {

	private StringBuffer anf = new StringBuffer();
	private String[] translation;
	private String suchwort;
	
	public TranslationWriter(String suchwort, String[] translation) {
		this.translation = translation;
		this.suchwort = suchwort;
	}
	
	public void writeAll() {
		for (int i = 0; i < translation.length; i++,i++) {
			if ((i/2) % 2 == 0) {
				writeOddRow(translation[i], translation[i + 1]);
			} else {
				writeNotOddRow(translation[i], translation[i + 1]);
			}
		}
	}
	
	public void beginWrite() {
		anf.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"><html><head><meta http-equiv=\"content-type\" content=\"text/html\" charset=\"utf-8\"></head><body><table>");
		anf.append("<tr><td align=\"center\" colspan=2><h1>" + suchwort + "</h1></td></tr>");
	}

	public void endWrite() {
		String ende = "</table></body></html>";
		anf.append(ende);
	}

	public void writeNotOddFirstCol(String toWrite) {
		anf.append("<td  bgcolor=00ffff>");
		anf.append(toWrite);
		anf.append("</td>");
	}

	public void writeOddFirstCol(String toWrite) {
		anf.append("<td>");
		anf.append(toWrite);
		anf.append("</td>");
	}

	public void writeSecondCol(String toWrite) {
		String[] toWriteAll = (toWrite+"#").split(suchwort);
		anf.append("<td>");
		anf.append(toWriteAll[0]);
		for (int i = 1; i < toWriteAll.length; i++) {
			anf.append("<span style=\"color:red\">" + suchwort + "</span>");
			if (!toWriteAll[i].equals("#"))
				anf.append(toWriteAll[i].replace('#', ' '));
		}
		anf.append("</td>");
	}

	public void writeOddRow(String col1, String col2) {
		anf.append("<tr>");
		writeOddFirstCol(col1);
		writeSecondCol(col2);
		anf.append("</tr>");
	}

	public void writeNotOddRow(String col1, String col2) {
		anf.append("<tr>");
		writeNotOddFirstCol(col1);
		writeSecondCol(col2);
		anf.append("</tr>");
	}

	public void showErgebnis() {
		new JTextpaneDemo1(anf.toString());
	}
}
