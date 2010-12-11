package fi.jamk.e6379;

import java.util.ArrayList;

import android.location.Location;

/*
 * a simple class that only returns a list of caches emulating
 */
public class CacheManager {
	public ArrayList<Cache> fetchCaches() {
		ArrayList<Cache> caches = new ArrayList<Cache>();
		
		Cache cache1 = new Cache();
		cache1.setCreator("testi1");
		cache1.setFound(false);
		cache1.setId("GC0001");
		cache1.setType("Traditional");
		Location cache1location = new Location("testing");
		cache1location.setLatitude(62.241717);
		cache1location.setLongitude(25.761083);
		cache1.setLocation( cache1location );
		
		Cache cache2 = new Cache();
		cache2.setCreator("testi2");
		cache2.setFound(false);
		cache2.setId("GC0002");
		cache2.setType("Multi");
		Location cache2location = new Location("testing");
		cache2location.setLatitude(62.240067);
		cache2location.setLongitude(25.751833);
		cache2.setLocation( cache2location );
		
		caches.add( cache1 );
		caches.add( cache2 );
		
		return caches;
	}
}
