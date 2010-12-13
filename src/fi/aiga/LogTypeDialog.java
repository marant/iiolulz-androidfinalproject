package fi.aiga;

import fi.aiga.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class LogTypeDialog extends Dialog {
	private LogActivity logActivity;
		
	public LogTypeDialog(Context context) {
		super(context);
		setContentView(R.layout.logtypedialog);
    	setTitle(context.getString(R.string.logtypedialog_title));   
    	
    	
    	Button button = (Button)this.findViewById(R.id.logtypedialog_okButton);
    	button.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/* Dirty hack.... */
				RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
				logActivity = (LogActivity) getOwnerActivity();
				Log log = logActivity.getLog();
				switch( radioGroup.getCheckedRadioButtonId() ) {
		    		case R.id.logtypedialog_didnotfind:
		    			log.setType(Log.TYPE_DIDNOTFIND);
		    		break;
		    		case R.id.logtypedialog_found:
		    			log.setType(Log.TYPE_FOUND);
		    		break;
		    	}
				logActivity.updateTypeDisplay();
				
				dismiss();
			}
		});
	}
}
