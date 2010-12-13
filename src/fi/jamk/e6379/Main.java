package fi.jamk.e6379;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.android.maps.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class Main extends MapActivity implements LocationListener {
	private MapView mapView;
	private MapController mapController;
	private MyLocationOverlay myLocOverlay;
	private List<Overlay> mapOverlays;
	private ArrayList<Cache> caches;
	private ArrayList<Cache> foundCaches;
	private ArrayList<Cache> notFoundCaches;
	private Drawable cacheDrawable;
	private Drawable foundCacheDrawable;
	private CacheOverlay cacheOverlay;
	private CacheOverlay foundCacheOverlay;
	private Cache targetCache;
	private Location currentLocation;
	private DataHelper dh;
	private List<String> foundCacheIds;
	private CacheManager cacheManager;
	static final int SET_TARGET_CACHE_REQUEST = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        caches = null;
        foundCaches = null;
        notFoundCaches = null;
        targetCache = null;
        currentLocation = null;
		
        mapView = (MapView)findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        myLocOverlay = new MyLocationOverlay(this, mapView);
		myLocOverlay.enableMyLocation();
		mapView.getOverlays().add(myLocOverlay);
		
		mapController = mapView.getController();
	    LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, this);
		
		mapOverlays = mapView.getOverlays();
		cacheDrawable = this.getResources().getDrawable(R.drawable.cachemarker);
		foundCacheDrawable = this.getResources().getDrawable( R.drawable.foundcache );
		
		cacheOverlay = new CacheOverlay(cacheDrawable, this );
		foundCacheOverlay = new CacheOverlay( foundCacheDrawable, this );
		
		dh = new DataHelper(this);
		foundCacheIds = null;
		
		cacheManager = new CacheManager();
		caches = cacheManager.getCaches();
		foundCaches = new ArrayList<Cache>();
		notFoundCaches = new ArrayList<Cache>();
		
		generateOverlayItems();
		
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void onResume(){
		generateOverlayItems();
		super.onResume();
	}
	
	@Override
	public void onLocationChanged(Location location) {
		GeoPoint point = myLocOverlay.getMyLocation();
		mapController.animateTo(point);
		currentLocation = location;
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean generateOverlayItems() {
		mapOverlays.remove(cacheOverlay);
		mapOverlays.remove(foundCacheOverlay);
		foundCacheOverlay.clear();
		cacheOverlay.clear();
		if (caches.isEmpty()) {
			Toast.makeText(getApplicationContext(), getString(R.string.nocaches_text), Toast.LENGTH_LONG).show();
			return false;
		}
		
		Iterator<Cache> iter = caches.iterator();		
		List<String> foundCacheIds = dh.selectFoundCacheIds();
		while( iter.hasNext() ) {
			Cache cache = (Cache) iter.next();
			int lon = (int) (cache.getLocation().getLongitude()*1000000);
			int lat = (int) (cache.getLocation().getLatitude()*1000000);
			GeoPoint point = new GeoPoint(lat, lon);
			if(foundCacheIds.contains(cache.getId())){
				foundCaches.add(cache);
				foundCacheOverlay.addOverlay( new OverlayItem(point, cache.getId(), ""));
			}
			else {
				notFoundCaches.add(cache);
				cacheOverlay.addOverlay( new OverlayItem(point, cache.getId(), "") );
			}
			
		}
		
		addOverlaysToMap();
		return true;
	}
	
	public void openCacheView(int index) {
		Intent intent = new Intent( Main.this, CacheDetailsActivity.class );
		intent.putExtra("CacheID", index);
		startActivityForResult(intent, SET_TARGET_CACHE_REQUEST);
	}
	
	public void openCompassView() {
		Intent intent = new Intent( Main.this, CompassActivity.class );
	
		if( targetCache != null && currentLocation != null ) {
			intent.putExtra("targetLongitude", targetCache.getLocation().getLongitude());
			intent.putExtra("targetLatitude", targetCache.getLocation().getLatitude());
			intent.putExtra("currentLongitude", currentLocation.getLongitude() );
			intent.putExtra("currentLatitude", currentLocation.getLatitude() );
			startActivity(intent);
		}
		
		if (targetCache == null ) {
			Toast.makeText(getApplicationContext(), getString(R.string.message_targetcachenostset_text), Toast.LENGTH_LONG).show();
		}
		
		if( currentLocation == null ) {
			Toast.makeText(getApplicationContext(), getString(R.string.message_nolocationfix_text), Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		generateOverlayItems();
		if( requestCode == SET_TARGET_CACHE_REQUEST ) {
			if( resultCode == RESULT_OK ) {
				int cacheIndex = data.getIntExtra("CacheID", -1);
				if( cacheIndex >= 0 ) {
					targetCache = caches.get(cacheIndex);
					Toast.makeText( getApplicationContext(), R.string.targetsetmessage_text, Toast.LENGTH_SHORT).show();
				}
				
			}
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.distance_to_target:
	    	String message = "";
	        if( targetCache != null && currentLocation != null ) {
	        	message = getResources().getString(R.string.message_distancetotarget_text);
	        	double distance = currentLocation.distanceTo(targetCache.getLocation());
	        	DecimalFormat format = new DecimalFormat("#.##");
	        	if( distance < 1000)
	        		message += " "+format.format(distance)+" m";
	        	else
	        		message += " "+format.format(distance/1000)+" km";
	        	
	        }
	        else if( targetCache == null ) {
	        	message = getResources().getString(R.string.message_targetcachenostset_text);
	        }
	        else if( currentLocation == null ) {
	        	message = getResources().getString(R.string.message_nolocationfix_text);
	        }
	        else {
	        	message = "ERROR";
	        }
	        
	        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	        
	        return true;
	    case R.id.center_to_my_location:
	    	GeoPoint point = myLocOverlay.getMyLocation();
			mapController.animateTo(point);
	        return true;
	    case R.id.compassview:
	    	if( targetCache != null )
	    		this.openCompassView();
	    	else
	    		Toast.makeText(getApplicationContext(), R.string.message_targetcachenostset_text, Toast.LENGTH_SHORT).show();
	    	return true;
	    	
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	public void addOverlaysToMap(){
		mapOverlays.add(cacheOverlay);
		if(updateFoundCacheIds())
			mapOverlays.add(foundCacheOverlay);
	}
	

	public boolean updateFoundCacheIds() {
		foundCacheIds = dh.selectFoundCacheIds();
		if(foundCacheIds.size() > 0)
			return true;
		else return false;
	}
	
}