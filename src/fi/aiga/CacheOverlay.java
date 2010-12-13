package fi.aiga;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CacheOverlay extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	Context context;
	Main mainActivity;
	
	public CacheOverlay(Drawable defaultMarker, Main mainActivity ) {
		super(boundCenterBottom(defaultMarker));
		this.mainActivity = mainActivity;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	@Override
	protected boolean onTap( int index ) {
		OverlayItem item = mOverlays.get(index);
		mainActivity.openCacheView(item.getTitle());
		return true;
	}
	
	public void clear(){
		mOverlays.clear();
	}
}
