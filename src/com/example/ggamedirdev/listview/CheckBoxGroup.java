package com.example.ggamedirdev.listview;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.ButtonLayer.OnClickListener;

public class CheckBoxGroup {
	private List<CheckboxLayer> checkboxLayers = new ArrayList<CheckboxLayer>();
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
		checkboxLayer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonLayer buttonLayer) {
				// TODO Auto-generated method stub
				checkedListener.onCheckStatusChanged(buttonLayer, false);
			}
		});
		checkboxLayer.addChild(checkboxLayer);
	}
	
	public void setOnCheckedListener(OnCheckedListener onCheckedListener){
		this.checkedListener = onCheckedListener;
	}
	
	public void checked(){
		
	}
}
