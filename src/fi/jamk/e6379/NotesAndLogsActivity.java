package fi.jamk.e6379;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NotesAndLogsActivity extends ListActivity {	
	protected DataHelper dh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  dh = new DataHelper(this);
	  
	  updateLogsAndNotes();
	}
	
	public void updateLogsAndNotes() {
		ArrayList<Note> notes = new ArrayList<Note>();
		  
		  notes.addAll(dh.selectNotes());
		  notes.addAll(dh.selectLogs());

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
