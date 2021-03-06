package fi.aiga;

import java.util.Calendar;
import java.util.Date;

import fi.aiga.R;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LogActivity extends NoteEditActivity {
	public static final int TYPE_DIALOG_ID = 1;
	private TextView type;
	private Log log;
	private Intent intent;
	private String cacheId;
	DataHelper dh;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loglayout);
		intent = getIntent();
		dh = new DataHelper(this);
		
		cacheId = intent.getExtras().getString("cacheID");
		if( cacheId == null ) {
			this.finish();
		}
	
		log = new Log();
		log.setType( Log.TYPE_FOUND );
		type = (TextView)findViewById(R.id.logtype_value);
		updateTypeDisplay();
		
		date = (TextView)findViewById(R.id.logdate_value);
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
  	  	month = calendar.get( Calendar.MONTH );
  	  	year = calendar.get( Calendar.YEAR );
        updateDateDisplay();     
	}
	
	public void onResume(){
		intent = getIntent();
		cacheId = intent.getExtras().getString("cacheID");
		super.onResume();
	}
	
	public void updateTypeDisplay() {
		switch( log.getType() ) {
		case Log.TYPE_FOUND:
			type.setText( getString( R.string.logtype_found ) );
			break;
		case Log.TYPE_DIDNOTFIND:
			type.setText( getString( R.string.logtype_didnotfind ) );
			break;
		}
	}
	
	@Override
    public Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		
        switch (id) {
        case DATE_DIALOG_ID:
            dialog = new DatePickerDialog(this,
                        mDateSetListener,
                        year, month, day);
            break;
        case TYPE_DIALOG_ID:
        	/*
        	 * For some reason this doesn't work, even though the documentation
        	 * suggests this
        	 * 
        	 * Context context = getApplicationContext();
        	 * dialog = new Dialog(context);
        	 */
        	dialog = new LogTypeDialog(this);
        	dialog.setOwnerActivity(this);
        	break;
        }
        
        return dialog;
    }
	
	public void openLogTypeDialog(View target) {
		showDialog(TYPE_DIALOG_ID);
	}
	
	public void saveLog(View target) {
		log.setDate(calendar);
		log.setTitle(((TextView)findViewById(R.id.logtitle_text)).getText().toString());
		log.setNoteText(((TextView)findViewById(R.id.logeditcomment_text)).getText().toString());
		log.setCacheId(intent.getExtras().getString("cacheID"));
		dh.insertLog(log);
		dh.closeDb();
		finish();
	}
	
	public Log getLog() {
		return log;
	}
	
	
}
