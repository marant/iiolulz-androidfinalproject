package fi.aiga;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fi.aiga.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NotesAndLogsActivity extends ListActivity {	
	protected DataHelper dh;
	protected Intent intent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  dh = new DataHelper(this);
	  intent = getIntent();
	  
	  updateLogsAndNotes();
	}
	
	public void updateLogsAndNotes() {
		ArrayList<Note> notes = new ArrayList<Note>();
		  
		  notes.addAll(dh.selectNotes(intent.getExtras().getString("cacheID")));
		  notes.addAll(dh.selectLogs(intent.getExtras().getString("cacheID")));

		  setListAdapter(new NoteAdapter(this, R.layout.notesandlogslistviewitem, notes));

		  ListView lv = getListView();
		  lv.setTextFilterEnabled(true);
	}

	@Override
	protected void onStop() {
		updateLogsAndNotes();
		super.onStop();
	}
	

}
