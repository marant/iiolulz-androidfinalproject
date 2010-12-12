package fi.jamk.e6379;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.location.Location;
import android.os.Environment;

public class CacheManager {
	private ArrayList<Cache> caches;
	private boolean mExternalStorageAvailable;
	@SuppressWarnings("unused")
	private boolean mExternalStorageWriteable;
	
	public CacheManager() {		
		mExternalStorageAvailable = false;
		mExternalStorageWriteable = false;
		caches = new ArrayList<Cache>();
		
		readLocs();
	}

	public ArrayList<Cache> getCaches() {
		return caches;
	}

	public void setCaches(ArrayList<Cache> caches) {
		this.caches = caches;
	}
	
	public void readLocs() {
		
		checkStorageState();
		
		if(mExternalStorageAvailable){
			File path = Environment.getExternalStorageDirectory();
			File[] cacheFiles = path.listFiles(new CacheFilter());
			SAXParserFactory factory = SAXParserFactory.newInstance();
			
			for (File cache : cacheFiles){
				try {
				    SAXParser saxParser = factory.newSAXParser();
				    LocHandler locHandler = new LocHandler();
				    saxParser.parse(cache,locHandler);
				    this.caches.add(locHandler.getNewcache());
				 } catch (Throwable err) {
					 err.printStackTrace ();
				}
			}
		}
		
		/*
		Cache c1 = new Cache();
		Location l1 = new Location("Groundspeak");
		l1.setLatitude(62.234083);
		l1.setLongitude(25.76455);
		c1.setLocation(l1);
		c1.setName("Kuokkalan silta");
		c1.setType("tradi");
		
		Cache c2 = new Cache();
		Location l2 = new Location("Groundspeak");
		l2.setLatitude(62.232);
		l2.setLongitude(25.787333);
		c2.setLocation(l2);
		c2.setName("Lyyli II");
		c2.setType("tradi");
		
		caches.add(c1);
		caches.add(c2);
		*/
	}

	public void checkStorageState() {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
	}
	
	class CacheFilter implements FileFilter {
		  private final String[] okFileExtensions = 
		    new String[] {"loc"};

		  public boolean accept(File file)
		  {
		    for (String extension : okFileExtensions)
		    {
		      if (file.getName().toLowerCase().endsWith(extension))
		      {
		        return true;
		      }
		    }
		    return false;
		  }
	}

}
