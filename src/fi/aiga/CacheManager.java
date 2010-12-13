package fi.aiga;

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
			if (cacheFiles.length > 0){
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
			
		}
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
