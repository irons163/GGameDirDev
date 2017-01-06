package com.example.ggamedirdev.listview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.ggamedirdev.BitmapUtil;
import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.Layer;

public class ControllerBarLayer extends Layer{
	int progress = 10;
	int maxProgress;
	ControllerItemLayer controllerItemLayer;
	ALayer controllerBar;
	
	public ControllerBarLayer() {
		// TODO Auto-generated constructor stub
		controllerItemLayer = new ControllerItemLayer();
		controllerItemLayer.setAnchorPoint(0.5f, 0.5f);
		controllerItemLayer.setSize(50, 50);
		controllerItemLayer.setBitmap(BitmapUtil.cloud1);
		
		controllerBar = new Layer();
		controllerBar.setAnchorPoint(0.5f, 0.5f);
		controllerBar.setSize(100, 20);
		
		addChild(controllerItemLayer);
		addChild(controllerBar);
	}
	
	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getMaxProgress() {
		return maxProgress;
	}

	public void setMaxProgress(int maxProgress) {
		this.maxProgress = maxProgress;
	}



	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
		
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event, int touchEventFlag) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event, touchEventFlag);
		
	}
	
	class ControllerItemLayer extends Layer{
		
		@Override
		public boolean onTouchEvent(MotionEvent event, int touchEventFlag) {
			// TODO Auto-generated method stub
			return super.onTouchEvent(event, touchEventFlag);
		}
		boolean isClickCancled = false;
		@Override
		protected void onTouched(MotionEvent event) {
			// TODO Auto-generated method stub
			super.onTouched(event);
			float x = 0,y = 0;
			float dx, dy;
			
			if((event.getAction()==MotionEvent.ACTION_DOWN || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_DOWN) && isPressed()){
				x = event.getX();
				y = event.getY();
				isClickCancled = false;
			}else if((event.getAction()==MotionEvent.ACTION_MOVE || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_MOVE) && isPressed()){
				dx = event.getX() - x;
				dy = event.getY() - y;
				
				float newX = getX() + dx;
				float newY = getY() + dy;
				if(newX < 0){
					this.setX(0);
					return;
				}else if(newX > getParent().getWidth()){
					this.setX(getParent().getWidth());
					return;
				}
				
				this.setX(getX() + dx);
//				this.setY(getY() + dy);
				
			}else if((event.getAction()==MotionEvent.ACTION_MOVE || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_MOVE) && !isPressed()){
				x = y = 0;
				isClickCancled = true;
			}else if((event.getAction()==MotionEvent.ACTION_UP || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP) && isClickCancled && !isPressed()){
				x = y = 0;
			}else if((event.getAction()==MotionEvent.ACTION_UP || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP) && isPressed() && !isClickCancled){
				x = y = 0;
			}else if((event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_CANCEL){
				x = y = 0;
			}
		}
		
		@Override
		public boolean onTouchBegan(MotionEvent event) {
			// TODO Auto-generated method stub
			return super.onTouchBegan(event);
		}
	}
}
