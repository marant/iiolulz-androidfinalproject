package fi.jamk.e6379;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteAdapter extends ArrayAdapter<Note> {
	private List<Note> notes;
	
	public NoteAdapter(Context context, int textViewResourceId,
			List<Note> notes) {
		super(context, textViewResourceId, notes);
		this.notes = notes;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
	
		if( view == null ) {
			LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.notesandlogslistviewitem, null);
		}
		
		Note note = notes.get(position);
		
		if( note != null ) {
			TextView title = (TextView)view.findViewById(R.id.notesandlogs_title);
			TextView comment = (TextView)view.findViewById(R.id.notesandlogs_text);
			ImageView icon = (ImageView)view.findViewById(R.id.notesandlogs_typeIcon);
			
			if( title != null ) {
				title.setText( note.getTitle() );
			}
			
			if( comment != null ) {
				comment.setText( note.getNoteText() );
			}
			
			if( icon != null ) {
				//TODO: change icon depending on whether note is a Note or a Log
			}
		}
		
		return view;
	}

}
