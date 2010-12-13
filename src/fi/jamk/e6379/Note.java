package fi.jamk.e6379;

import java.util.Calendar;

public class Note {
	private Calendar date;
	private String noteText;
	private String title;
	private String cacheId;
	
	public Note(){
		
	}
	
	public Note(String title, String noteText, Calendar date){
		this.title = title;
		this.noteText = noteText;
		this.date = date;
	}
	
	public String getCacheId() {
		return cacheId;
	}

	public void setCacheId(String cacheId) {
		this.cacheId = cacheId;
	}

	public Calendar getDate() {
		return date;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNoteText() {
		return noteText;
	}
	
	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}	
}
