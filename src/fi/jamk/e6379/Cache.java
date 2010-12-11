package fi.jamk.e6379;

import android.location.Location;

public class Cache {
	private String id;
	private Location location;
	private String creator;
	private String type;
	private boolean found;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isFound() {
		return found;
	}
	
	public void setFound(boolean found) {
		this.found = found;
	}
}
