package fi.jamk.e6379;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NotesAndLogsActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  Note note1 = new Note();
	  note1.setDate( Calendar.getInstance() );
	  note1.setNoteText("testing");
	  note1.setTitle("test");
	  
	  Note note2 = new Note();
	  note2.setDate( Calendar.getInstance() );
	  note2.setNoteText("testing2");
	  note2.setTitle("test2");
	  List<Note> notes = new ArrayList<Note>();
	  notes.add(note1);
	  notes.add(note2);

	  setListAdapter(new NoteAdapter(this, R.layout.notesandlogslistviewitem, notes));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);
	}
}
