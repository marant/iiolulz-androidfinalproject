package fi.jamk.e6379;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class CacheDetailsActivity extends Activity {
	private OnClickListener targetButtonListener;
	private Cache cache;
	private ArrayList<Cache> caches;
	private Intent callingIntent;
	private String cacheId;
	CacheManager cacheManager;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cachedetailslayout);
		cacheManager = new CacheManager();
		cache = null;
		
		findViewById(R.id.Button01).setOnClickListener( targetButtonListener );
		
		setDetailCache();
		if(cache != null)
			updateCacheDetails();
		
		targetButtonListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("CacheId", cache.getId());
				setResult(RESULT_OK, intent);
				finish();
			}
			
		};
		
	}
	
	public void setDetailCache(){
		callingIntent = getIntent();
		caches = cacheManager.getCaches();
		cacheId = callingIntent.getStringExtra("cacheId");
		for (Cache cache : caches){
			if(cache.getId().equalsIgnoreCase(cacheId)){
				this.cache = cache;
			}
		}
	}
	
	
	
	@Override
	protected void onResume() {
		setDetailCache();
		if(cache != null)
			updateCacheDetails();
		
		super.onResume();
	}
	
	public void updateCacheDetails(){
		TextView cacheid = (TextView) findViewById(R.id.cacheid_value);
		TextView cachename = (TextView) findViewById(R.id.cachename_value);
		TextView cachecreator = (TextView) findViewById( R.id.cachecreator_value );
		TextView cachetype = (TextView) findViewById(R.id.cachetype_value);
		
		cacheid.setText( cache.getId() );
		cachename.setText( cache.getName() );
		cachecreator.setText( cache.getCreator() );
		cachetype.setText( cache.getType() );
	}



	public void openAddNoteActivity(View target) {
		Intent intent = new Intent(CacheDetailsActivity.this, NoteEditActivity.class);
		if( cache != null ) {
			intent.putExtra("cacheID", cache.getId() );
		}
		startActivity( intent );
	}
	
	public void openLogActivity( View target ) {
		Intent intent = new Intent( CacheDetailsActivity.this, LogActivity.class );
		if( cache != null ) {
			intent.putExtra("cacheID", cache.getId() );
		}
		startActivity( intent );
	}
	
	public void openNotesAndLogsActivity( View target ) {
		Intent intent = new Intent( CacheDetailsActivity.this, NotesAndLogsActivity.class );
		if( cache != null ) {
			intent.putExtra("cacheID", cache.getId() );
		}
		startActivity( intent );
	}
}
