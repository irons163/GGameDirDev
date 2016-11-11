package com.example.ggamedirdev.listview;

public abstract class BaseLayerAdapter implements LayerAdapter{

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return getCount()==0;
	}
}
