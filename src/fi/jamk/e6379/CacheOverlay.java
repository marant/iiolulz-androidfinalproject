package fi.jamk.e6379;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CacheOverlay extends ItemizedOverlay<OverlayItem> {
	Context context;
	Main mainActivity;
	
	public CacheOverlay(Drawable defaultMarker, Main mainActivity ) {
		super(boundCenterBottom(defaultMarker));
		this.mainActivity = mainActivity;
	}

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

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
		mainActivity.openCacheView(index);
		return true;
	}
}
