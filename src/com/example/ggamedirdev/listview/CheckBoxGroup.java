package com.example.ggamedirdev.listview;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.try_gameengine.center_notification.NSANotifiable;
import com.example.try_gameengine.center_notification.NSANotification;
import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.Layer;

public class CheckBoxGroup extends Layer implements NSANotifiable{
	private List<CheckboxLayer> layers = new ArrayList<CheckboxLayer>();
	private int currentCheckedNum;
	private int minCheckedNum;
	private int maxCheckedNum = 1;
	
	private OnCheckedListener checkedListener = new OnCheckedListener() {
		
		@Override
		public void onCheckStatusChanged(ALayer layer, boolean checked) {
			// TODO Auto-generated method stub
			
		}
	};
	
	interface OnCheckedListener {
		public void onCheckStatusChanged(ALayer layer, boolean checked);
	}
	
	public void addCheckBox(CheckboxLayer checkboxLayer){
		layers.add(checkboxLayer);
		addChild(checkboxLayer);
		
//		NSANotificationCenter.defaultCenter().addObserver(this, "EditClicked", null);
	}
	
	@Override
	protected boolean dispatchTouchEventToChild(ILayer child,
			MotionEvent event, int touchEventFlag) {
		
		if(!layers.contains(child)){
			return super.dispatchTouchEventToChild(child, event, touchEventFlag);
		}
		
		CheckboxLayer checkboxLayer = (CheckboxLayer)child;
		boolean result = false;
		if((checkboxLayer.isChecked() && currentCheckedNum > minCheckedNum)
			|| (!checkboxLayer.isChecked() && currentCheckedNum < maxCheckedNum)){
			
			result = super.dispatchTouchEventToChild(child, event, touchEventFlag); 
			
			if((Boolean)result && ((event.getAction()==MotionEvent.ACTION_UP 
					|| (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP))
				){
//					&& checkboxLayer.isPressed()){
				if(checkboxLayer.isChecked())
					currentCheckedNum++;
				else
					currentCheckedNum--;
				checkedListener.onCheckStatusChanged(checkboxLayer, checkboxLayer.isChecked());
			}
			
		}
		
		return result;
	}
	
	public void removeCheckBox(CheckboxLayer checkboxLayer){
		layers.remove(checkboxLayer);
		remove(checkboxLayer);
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
	}
	
	@Override
	public List<ILayer> getLayers() {
		// TODO Auto-generated method stub
		return super.getLayers();
	}
	
	@Override
	public List<ILayer> getLayersFromRootLayerToCurrentLayerInComposite() {
		// TODO Auto-generated method stub
		return super.getLayersFromRootLayerToCurrentLayerInComposite();
	}
	
	public void setOnCheckedListener(OnCheckedListener onCheckedListener){
		this.checkedListener = onCheckedListener;
	}
	
	public void checked(){
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event, int touchEventFlag) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event, touchEventFlag);
	}

	@Override
	public void receiveNotification(NSANotification nsaNotification) {
		// TODO Auto-generated method stub
		
	}
	

}
