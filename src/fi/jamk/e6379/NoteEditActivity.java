package fi.jamk.e6379;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.DatePickerDialog;

public class NoteEditActivity extends Activity {
	public static final int DATE_DIALOG_ID = 0;
	public static final int ADD_NOTE = 0;
	public static final int EDIT_NOTE = 1;
	protected TextView date;
	protected int day;
	protected int month;
	protected int year;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noteeditlayout);
        
        date = (TextView)findViewById(R.id.notedate_value);
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
  	  	month = calendar.get( Calendar.MONTH );
  	  	year = calendar.get( Calendar.YEAR );
        updateDateDisplay();
        
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
