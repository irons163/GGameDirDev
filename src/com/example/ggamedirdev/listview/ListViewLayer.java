package com.example.ggamedirdev.listview;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.ggamedirdev.BitmapUtil;
import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.Layer;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.stage.StageManager;

public class ListViewLayer extends Layer{
	private List<? extends ILayer> mlayers;
	private float itemHeight;
	
	public static final int SCROLL_LIMIT_DEFAULT = 0;
	public static final int SCROLL_LIMIT_FOR_CAN_SCOLL_DOWN_WHEN_FIRST_ITEM_IN_THE_TOP = 1;
	public static final int SCROLL_LIMIT_FOR_CAN_SCOLL_UP_WHEN_LAST_ITEM_IN_THE_BOTTOM = 2;
	public static final int SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_ITEMS_HEIGHT_LESS_THAN_LISTVIEW = 4;
	
	private int listviewFlag = SCROLL_LIMIT_DEFAULT;
	
	SingleFingerGesture gestureDetector;
	
	public void setListViewFlag(int listviewFlag){
		this.listviewFlag = listviewFlag;
	}
	
	public int getListViewFlag(){
		return this.listviewFlag;
	}
	
	public void addListViewFlag(int listviewFlag){
		this.listviewFlag = this.listviewFlag|listviewFlag;
	}
	
	public void removeListViewFlag(int listviewFlag){
		this.listviewFlag &= ~listviewFlag;
	}
	
//	class SingleFingerScroll {
//		boolean startScroll;
//		
//		public boolean onTouchEvent(MotionEvent event){
//			
//			
//			
//			if(!startScroll){
//				if(Math.abs(e1.getY() - e2.getY())>5){
//					startScroll = true;
//					for(ILayer layer : mlayers){
//						MotionEvent cancelEvent = MotionEvent.obtain(e2);
//						cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
//						layer.onTouchEvent(cancelEvent);
//					}
//				}else
//					return true;
//			}
//			
//			for(ILayer layer : mlayers){
////				MotionEvent cancelEvent = MotionEvent.obtain(e2);
////				cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
////				layer.onTouchEvent(cancelEvent);
////				layer.addFlag(TOUCH_MOVE_CAN_OUTSIDE_SELF_RANGE);
//				layer.setY(layer.getY() - distanceY);
//			}
//		}
//	}
	
	public ListViewLayer() {
		// TODO Auto-generated constructor stub
//		setWidth(200);
//		setHeight(1850);
		setPosition(70, 70);
		setBackgroundColor(Color.BLUE);
		addFlag(TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN);
		
		itemHeight = 100;
		
//		initButtons();
//		initSprites();
		initClipSprites();
		


		
		gestureDetector = new SingleFingerGesture(StageManager.getCurrentStage(), new SingleFingerGesture.OnGestureListener() {
			boolean startScroll;
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
//				startScroll = false;
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				// TODO Auto-generated method stub
				if(!startScroll){
//					if(Math.abs(e1.getY() - e2.getY())>5){
						startScroll = true;
						distanceY = distanceY > 0 ? 1:-1;
						for(ILayer layer : mlayers){
							MotionEvent cancelEvent = MotionEvent.obtain(e2);
							cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
							layer.onTouchEvent(cancelEvent);
						}
//					}else
//						return true;
				}
//				if(e1.getY() > e2.getY() + 50)
					for(ILayer layer : mlayers){
//						MotionEvent cancelEvent = MotionEvent.obtain(e2);
//						cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
//						layer.onTouchEvent(cancelEvent);
//						layer.addFlag(TOUCH_MOVE_CAN_OUTSIDE_SELF_RANGE);
						layer.setY(layer.getY() - distanceY);
					}
//				}
					
				return true;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				startScroll = false;
				return false;
			}
		});
		
		gestureDetector.setIsLongpressEnabled(false);
	}
	
	private void initClipSprites(){
		List<Sprite> layers = new ArrayList<Sprite>();
		mlayers = layers;
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
		
		setIsClipOutside(true);
		
		int y = 0;
		for(Sprite layer : layers){ 
//			need work:this issue, camera, view port, canvas concat matrix, spinner view
			layer.setBitmapAndFrameColAndRowNumAndAutoWH(layer.getBitmap(), 7, 2);
			layer.setY(y);
//			layer.setX(getWidth()/2);
			layer.setAnchorPoint(-0.35f, -0.15f);
//			layer.setAnchorPoint(0.5f, 0.5f);
//			layer.setXscale(1.5f);
//			layer.setYscale(1.5f);
			layer.setXscale(0.5f);
			layer.setYscale(0.5f);
			layer.setRotation(45);
			layer.setBackgroundColor(Color.RED);
//			layer.setButtonColors(Color.RED, Color.BLUE, Color.YELLOW);
			addChild(layer);
//			layer.setIsClipOutside(true);
			y += itemHeight;
			layer.setOnLayerClickListener(new OnLayerClickListener() {
				
				@Override
				public void onClick(ILayer layer) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	
	private void initSprites(){
		List<Sprite> layers = new ArrayList<Sprite>();
		mlayers = layers;
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemHeight, false));
		
		setIsClipOutside(true);
		
		int y = 0;
		for(Sprite layer : layers){			
			layer.setY(y);
			layer.setAnchorPoint(-0.65f, -0.15f);
			layer.setXscale(2.0f);
			layer.setYscale(2.0f);
			layer.setRotation(45);
			layer.setBackgroundColor(Color.RED);
//			layer.setButtonColors(Color.RED, Color.BLUE, Color.YELLOW);
			addChild(layer);
//			layer.setIsClipOutside(true);
			y += itemHeight;
			layer.setOnLayerClickListener(new OnLayerClickListener() {
				
				@Override
				public void onClick(ILayer layer) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	
	private void initButtons(){
		List<ButtonLayer> layers = new ArrayList<ButtonLayer>();
		mlayers = layers;
		layers.add(new ButtonLayer("1", 100, (int) itemHeight, false));
		layers.add(new ButtonLayer("2", 100, (int) itemHeight, false));
		layers.add(new ButtonLayer("3", 100, (int) itemHeight, false));
		layers.add(new ButtonLayer("4", 100, (int) itemHeight, false));
		layers.add(new ButtonLayer("5", 100, (int) itemHeight, false));
		layers.add(new ButtonLayer("6", 100, (int) itemHeight, false));
		layers.add(new ButtonLayer("7", 100, (int) itemHeight, false));
		layers.add(new ButtonLayer("8", 100, (int) itemHeight, false));
		layers.add(new ButtonLayer("9", 100, (int) itemHeight, false));
		layers.add(new ButtonLayer("100", 100, (int) itemHeight, false));
		
		setIsClipOutside(true);
		
		int y = 0;
		for(ButtonLayer layer : layers){
			layer.setY(y);
			layer.setBackgroundColor(Color.RED);
			layer.setTextColor(Color.WHITE);
			layer.setButtonColors(Color.RED, Color.BLUE, Color.YELLOW);
			addChild(layer);
//			layer.setIsClipOutside(true);
			y += itemHeight;
			layer.setOnClickListener(new ButtonLayer.OnClickListener() {
				
				@Override
				public void onClick(ButtonLayer buttonLayer) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
			
//		Paint mPaint = new Paint();
//		mPaint.setColor(Color.BLUE);
//		for(ILayer layer : mlayers){
//			canvas.drawRect(layer.getFrame(), mPaint);
//		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP 
				|| (event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP){
			addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
			super.onTouchEvent(event);
			removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
			addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
			super.onTouchEvent(event);
			removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
			return gestureDetector.onTouchEvent(event);
		}else{
			addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
			if(super.onTouchEvent(event)){
				removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
				return gestureDetector.onTouchEvent(event);
			}else{
				removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
				addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
				if(super.onTouchEvent(event)){
					removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
					return gestureDetector.onTouchEvent(event);
				}
				removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
			}
		}
		return false;
	}

	@Override
	protected void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setEnableMultiTouch(boolean isEnableMultiTouch) {
		// TODO Auto-generated method stub
		super.setEnableMultiTouch(isEnableMultiTouch);
		if(gestureDetector!=null)
			gestureDetector.setEnableMultiTouch(isEnableMultiTouch);
	}

}
