package fi.aiga;

import java.util.Calendar;

import fi.aiga.R;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.content.Intent;

public class NoteEditActivity extends Activity {
	public static final int DATE_DIALOG_ID = 0;
	public static final int ADD_NOTE = 0;
	public static final int EDIT_NOTE = 1;
	protected TextView date;
	protected int day;
	protected int month;
	protected int year;
	protected Intent intent;
	protected Calendar calendar;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noteeditlayout);
        
        date = (TextView)findViewById(R.id.notedate_value);
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
  	  	month = calendar.get( Calendar.MONTH );
  	  	year = calendar.get( Calendar.YEAR );
  	  	
  	  	intent = getIntent();
  	  	
        updateDateDisplay();
        
    }
    
	public void onResume(){
		intent = getIntent();
		super.onResume();
	}
    
    public void updateDateDisplay() {
    	  String dateText = new String();
    	  dateText += ( Integer.toString(day)+".");
    	  //month is zero based
    	  dateText += ( Integer.toString(month+1)+".");
    	  dateText += ( Integer.toString(year) );
    	  
    	  date.setText(dateText);
    }
    
    public void openDatePickerDialog( View target ) {
    	showDialog(DATE_DIALOG_ID);
    }
    
    @Override
    public Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        year, month, day);
        }
        return null;
    }
	public void saveNote(View target) {
		DataHelper dh = new DataHelper(this);
		Note note = new Note();
		
		note.setDate(calendar);
		note.setTitle(((TextView)findViewById(R.id.nodeedittitle_text)).getText().toString());
		note.setNoteText(((TextView)findViewById(R.id.nodeedittext_text)).getText().toString());
		note.setCacheId(intent.getExtras().getString("cacheID"));
		dh.insertNote(note);
		finish();
	}
    
    protected DatePickerDialog.OnDateSetListener mDateSetListener =
        new DatePickerDialog.OnDateSetListener() {
    		@Override
            public void onDateSet(DatePicker view, int mYear, 
                                  int mMonthOfYear, int mDayOfMonth) {
                year = mYear;
                month = mMonthOfYear;
                day = mDayOfMonth;
                updateDateDisplay();
            }
        };
}
