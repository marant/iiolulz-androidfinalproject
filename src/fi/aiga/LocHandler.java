package fi.aiga;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fi.aiga.Cache;

import android.location.Location;


public class LocHandler extends DefaultHandler {
	Cache newcache;
	boolean type;
	boolean name;
	boolean link;
	
	public LocHandler() {
		newcache = new Cache();		
	}
	
	public Cache getNewcache() {
		return newcache;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
        if (localName.equals("name")) { 
            newcache.setId(attributes.getValue("id"));
            name = true;
        }
        else if (localName.equals("type")) { 
            type = true;
        }
        else if (localName.equals("coord")) {
        	Location loc = new Location("Groundspeak");
        	loc.setLatitude(Double.parseDouble(attributes.getValue("lat")));
        	loc.setLongitude(Double.parseDouble(attributes.getValue("lon")));
        	
        	newcache.setLocation(loc);			        	 
        }
        else if (localName.equals("link")) { 
            link = true;
        }
	}
	
	public void characters(char[] ch, int start, int length) {
	     if (type) {
	       newcache.setType(new String(ch, start, length));
	       type = false;
	     }
	     else if (name) {
	    	 String[] namecreator = new String(ch,start,length).split(" by ");
		     newcache.setName(namecreator[0]);
		     newcache.setCreator(namecreator[1]);
		     name = false;
		 }
	     if (link) {
		       newcache.setUrl(new String(ch, start, length));
		       link = false;
		 }
	}
	
	
}
