package fi.jamk.e6379;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CacheDetailsActivity extends Activity {
	private OnClickListener targetButtonListener;
	public static int index;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cachedetailslayout);
		Intent callingIntent = getIntent();
		Cache cache = null;
		CacheManager cacheManager = new CacheManager();
		TextView cachename = (TextView) findViewById(R.id.cachename_value);
		TextView cachecreator = (TextView) findViewById( R.id.cachecreator_value );
		TextView cachetype = (TextView) findViewById(R.id.cachetype_value);
		ArrayList<Cache> caches = cacheManager.fetchCaches();
		index = callingIntent.getIntExtra("CacheID", -1);
		
		targetButtonListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("CacheID", index);
				setResult(RESULT_OK, intent);
				finish();
			}
			
		};
		
		findViewById(R.id.Button01).setOnClickListener( targetButtonListener );
		
		cache = caches.get(index);
		
		cachename.setText( cache.getId() );
		cachecreator.setText( cache.getCreator() );
		cachetype.setText( cache.getType() );
	}
}
