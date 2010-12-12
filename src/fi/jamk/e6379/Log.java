package fi.jamk.e6379;

import java.util.Calendar;

public class Log extends Note {
	public static final int TYPE_DIDNOTFIND = 0;
	public static final int TYPE_FOUND = 1;
	
	private int Type;
	
	public Log() {
		
	}
	
	public Log(String title, String noteText, Calendar date, int Type){
		super(title,noteText,date);
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}
}
