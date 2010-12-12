package fi.jamk.e6379;

public class Log extends Note {
	public static final int TYPE_DIDNOTFIND = 0;
	public static final int TYPE_FOUND = 1;
	
	private int Type;

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}
}
