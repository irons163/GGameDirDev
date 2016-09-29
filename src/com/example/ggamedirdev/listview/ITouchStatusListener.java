package com.example.ggamedirdev.listview;

import android.graphics.Bitmap;
import android.view.MotionEvent;

public interface ITouchStatusListener {
	public void onTouched(MotionEvent event);
	public void setTouchedBitmaps(Bitmap[] bitmaps);
	public void setTouchedBitmapsArrayReference(Bitmap[] bitmaps);
	public void setTouchedColors(int[] colors);
	public void setTouchedColorsArrayReference(int[] colors);
	public void setTouchedColorsNone();
}
