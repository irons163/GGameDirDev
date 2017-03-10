package com.example.ggamedirdev.listview;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.LabelLayer;
import com.example.try_gameengine.framework.Layer;

public class CheckboxLayer extends ButtonLayer{
	private Layer imageLayer;
	private LabelLayer labelLayer;
	private boolean isChecked;
	
	public interface OnCheckStatusChangedListener{
		public void onChanged(ALayer layer, boolean isChecked);
	}
	
	private OnCheckStatusChangedListener onCheckStatusChangedListener = new OnCheckStatusChangedListener() {
		
		@Override
		public void onChanged(ALayer layer, boolean isChecked) {
			// TODO Auto-generated method stub
			
		}
	};	
	
	public CheckboxLayer() {
		// TODO Auto-generated constructor stub
//		super(0, 0, false);
//		addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_NOT_INERT_LAYERS);
		imageLayer = new Layer(); 
		imageLayer.setBackgroundColor(Color.MAGENTA);
		imageLayer.setWidth(30);
		imageLayer.setHeight(30);
		addChild(imageLayer);
		
		labelLayer = new LabelLayer(0.0f, imageLayer.getWidth(), false);
		labelLayer.setText("Label");
		addChild(labelLayer);
		
		setBackgroundColor(Color.BLUE);
		
//		final Layer layer = new Layer();
		
//		ButtonLayer buttonLayer = new ButtonLayer("XX", 100, 50, false);
//		buttonLayer.setPosition(50, 50);
//		buttonLayer.setOnClickListener(new ButtonLayer.OnClickListener() {
//			
//			@Override
//			public void onClick(ButtonLayer buttonLayer) {
//				imageLayer.setBackgroundColor(Color.MAGENTA);
//			}
//		});
		
		
	}
	
	public void setChecked(boolean isChecked){
		if(this.isChecked != isChecked){
			this.isChecked = isChecked;
			if(isChecked){
//			imageLayer.setBitmap(bitmap);
				imageLayer.setBackgroundColor(Color.BLUE);
			}else{
				imageLayer.setBackgroundColor(Color.MAGENTA);
			}
			onCheckStatusChangedListener.onChanged(this, isChecked());
		}
	}
	
	public boolean isChecked(){
		return isChecked;
	}
	
	public void setOnCheckStatusChangedListener(OnCheckStatusChangedListener onCheckStatusChangedListener){
		this.onCheckStatusChangedListener = onCheckStatusChangedListener;
	}
	
	@Override
	public void setOnLayerClickListener(
			OnLayerClickListener onLayerClickListener) {
		// TODO Auto-generated method stub
		super.setOnLayerClickListener(onLayerClickListener);
		
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
	}
	
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//		return super.onTouchEvent(event, TOUCH_EVENT_ONLY_ACTIVE_ON_NOT_INERT_LAYERS);
//	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event, int touchEventFlag) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event, touchEventFlag|TOUCH_EVENT_ONLY_ACTIVE_ON_NOT_INERT_LAYERS);
	}

	@Override
	public void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
		super.onTouched(event);
		
	if((event.getAction()==MotionEvent.ACTION_UP || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP) && isPressed()){
//			setBackgroundColor(Color.CYAN);
			setChecked(!isChecked());
		}	
	}

}
