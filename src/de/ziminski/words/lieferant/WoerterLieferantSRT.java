package de.ziminski.words.lieferant;

import javax.persistence.EntityManagerFactory;



public class WoerterLieferantSRT extends WoerterLieferant {

	
	public WoerterLieferantSRT(String inputText, EntityManagerFactory factory, String startPoint, String endPoint) {
		super(inputText, factory, startPoint, endPoint);
	}

	public String getText() {
		InputFile f = null;
		try {
			f = new InputFile(inputText.getName());
			String fileAsString = getFileAsString(f, start, end);
			return fileAsString;
		} finally {
			f.close();
		}
	}
	
	private String getFileAsString(InputFile f, String pStartpoint, String pEndpoint) {
		if (!checkParams(f, pStartpoint, pEndpoint)) {
			throw new IllegalArgumentException();
		}
		StringBuffer sb = new StringBuffer();
		boolean lese = false;
		String s = f.readLine();
		while (s != null) {
			if (afterStartAndLineWithText(lese, s)) {
				sb.append(s + " ");
			}
			
			if (isFromTo(s)) {
				boolean aktStart = lese;
				lese = getStart(lese, pStartpoint, s);
				lese = getStop(lese, pEndpoint, s);
				if (aktStart && !lese) return sb.toString();
			}
			
			s = f.readLine();
		}
		return sb.toString();
	}

	private boolean checkParams(InputFile f, String pStartpoint, String pEndpoint) {
		if (pStartpoint.equals("00:00") && pEndpoint.equals("00:00")) { 
			return true;
		}
		if (f == null) return false;
		if (!pStartpoint.matches("\\d{2}:\\d{2}")) return false;
		if (!pEndpoint.matches("\\d{2}:\\d{2}")) return false;
		if (minutes(pStartpoint) >= minutes(pEndpoint)) {
			return false;
		}
		return true;
	}
	
	private boolean afterStartAndLineWithText(boolean start, String s) {
		return start && !isNumberOfText(s) && !isFromTo(s);
	}
	
	private int minutes(String pString) {
		return Integer.parseInt(pString.substring(0, 2)) * 60 + Integer.parseInt(pString.substring(3, 5));
	}
	
	private boolean isNumberOfText(String s) {
		return s.matches("^\\d{1,3}$");
	}
	
	private boolean isFromTo(String s) {
		return s.matches("^\\d{2}:\\d{2}:\\d{2},\\d{3} --> \\d{2}:\\d{2}:\\d{2},\\d{3}$");
	}
	
	
	private boolean getStart(boolean start, String sStart, String line) {
//		if (!start  && line.startsWith(sStart)) {
		if (!start  && (line.compareTo(sStart) > 0)) {
			start = true;
		}
		return start;
	}

	private boolean getStop(boolean start, String sStart, String line) {
		if (sStart.equals("00:00")) {
			return start;
		}
		if (start  && line.startsWith(sStart)) {
			start = false;
		}
		return start;
	}

}
