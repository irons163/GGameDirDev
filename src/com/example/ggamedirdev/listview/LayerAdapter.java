package com.example.ggamedirdev.listview;

import com.example.try_gameengine.framework.ALayer;

public interface LayerAdapter {
	int getCount();
	Object getItem(int position);
	long getItemId(int position);
	boolean hasStableIds();
	ALayer getLayer(int position, ALayer layer, ALayer parent);
	boolean isEmpty();
}
