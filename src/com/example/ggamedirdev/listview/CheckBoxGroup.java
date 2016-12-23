package com.example.ggamedirdev.listview;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;

import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.ButtonLayer.OnClickListener;
import com.example.try_gameengine.framework.Layer;

public class CheckBoxGroup extends Layer{
	private List<CheckboxLayer> checkboxLayers = new ArrayList<CheckboxLayer>();
	private int minCheckedNum;
	private int maxCheckedNum = Integer.MAX_VALUE;
	
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
//		checkboxLayer.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(ButtonLayer buttonLayer) {
//				// TODO Auto-generated method stub
//				checkedListener.onCheckStatusChanged(buttonLayer, false);
//			}
//		});
//		checkboxLayer.addChild(checkboxLayer);
		checkboxLayers.add(checkboxLayer);
		
		addChild(checkboxLayer);
	}
	
	public void setOnCheckedListener(OnCheckedListener onCheckedListener){
		this.checkedListener = onCheckedListener;
	}
	
	public void checked(){
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event, int touchEventFlag) {
		// TODO Auto-generated method stub
		for(CheckboxLayer checkboxLayer : checkboxLayers){
			if(checkboxLayer.onTouchEvent(event, touchEventFlag)){
				if((event.getAction()==MotionEvent.ACTION_UP 
						|| (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP)
						&& checkboxLayer.isPressed()){
					checkedListener.onCheckStatusChanged(checkboxLayer, checkboxLayer.isChecked());
				}
			}
		}//改用動態代理？
		/*
		 * onTouch{
		 * 		check Min & Max
		 * 		ontouch
		 * if((event.getAction()==MotionEvent.ACTION_UP 
						|| (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP)
						&& checkboxLayer.isPressed()){
					checkedListener.onCheckStatusChanged(checkboxLayer, checkboxLayer.isChecked());
				}
		 * }
		 */
		
		return super.onTouchEvent(event, touchEventFlag);
	}
}
