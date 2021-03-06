package com.example.ggamedirdev.listview;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.ggamedirdev.BitmapUtil;
import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.Layer;
import com.example.try_gameengine.framework.Sprite;

public class SelectViewLayer extends Layer{
	private List<? extends ILayer> mlayers;
	private float itemHeight;
	GestureDetector gestureDetector;
	List<ILayer> tabs;
	boolean isClickCancled = false;
	private Bitmap[] buttonBitmaps = new Bitmap[3];
	private int[] buttonColors = new int[3];
	private final int NORMAL_INDEX = 0;
	private final int DOWN_INDEX = 1;
	private final int UP_INDEX = 2;
	
	public SelectViewLayer() {
		// TODO Auto-generated constructor stub
		setPosition(270, 270);
		setBackgroundColor(Color.BLUE);
		
		itemHeight = 100;
		
//		initButtons();
//		initSprites();
		initClipSpritess();
		

		
		final Layer layer = new Layer();
		
		ButtonLayer buttonLayer = new ButtonLayer("XX", 100, 50, false);
		buttonLayer.setOnClickListener(new ButtonLayer.OnClickListener() {
			
			@Override
			public void onClick(ButtonLayer buttonLayer) {
				// TODO Auto-generated method stub
				layer.setBackgroundColor(Color.MAGENTA);
			}
		});
		
		gestureDetector = new GestureDetector(null, new GestureDetector.OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
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
				return false;
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
				return false;
			}
		});
	}
	
	private void initClipSpritess(){
		List<ButtonLayer> layers = new ArrayList<ButtonLayer>();
		mlayers = layers;
		layers.add(new ButtonLayer(BitmapUtil.icon, 100, (int) itemHeight, false));
		layers.add(new ButtonLayer(BitmapUtil.icon, 100, (int) itemHeight, false));
		layers.add(new ButtonLayer(BitmapUtil.icon, 100, (int) itemHeight, false));
		
		setIsClipOutside(true);
		
		int x = 0;
		for(ButtonLayer layer : layers){
			layer.setX(x);
			layer.setAnchorPoint(-0.55f, -0.15f);
			layer.setButtonColors(Color.RED, Color.BLUE, Color.YELLOW);layer.setFlag(TOUCH_UP_CAN_OUTSIDE_SELF_RANGE);
			addChild(layer);
			
			x += itemHeight;
			layer.setOnLayerClickListener(new OnLayerClickListener() {
				
				@Override
				public void onClick(ILayer layer) {
					// TODO Auto-generated method stub
					
				}
			});
		}
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
			layer.setBitmapAndFrameColAndRowNumAndAutoWH(layer.getBitmap(), 7, 2);
			layer.setY(y);
			layer.setAnchorPoint(-0.55f, -0.15f);
			layer.setXscale(1.5f);
			layer.setYscale(1.5f);
			layer.setRotation(45);
			layer.setBackgroundColor(Color.RED);
			addChild(layer);
			y += itemHeight;
			layer.setOnLayerClickListener(new OnLayerClickListener() {
				
				@Override
				public void onClick(ILayer layer) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	
	public void setSeleteView(){
		
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
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
	public void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction()==MotionEvent.ACTION_DOWN && isPressed()){
			setBackgroundColor(buttonColors[DOWN_INDEX]);
			if(buttonBitmaps[DOWN_INDEX]!=null){
				this.setBitmap(buttonBitmaps[DOWN_INDEX]);
			}
			isClickCancled = false;
		}else if(event.getAction()==MotionEvent.ACTION_MOVE && isPressed()){
		}else if(event.getAction()==MotionEvent.ACTION_MOVE && !isPressed()){
			isClickCancled = true;
		}else if(event.getAction()==MotionEvent.ACTION_UP && isClickCancled && !isPressed()){
			setBackgroundColor(buttonColors[UP_INDEX]);
			if(buttonBitmaps[UP_INDEX]!=null){
				this.setBitmap(buttonBitmaps[UP_INDEX]);
			}
		}else if(event.getAction()==MotionEvent.ACTION_UP && isPressed() && !isClickCancled){
			setBackgroundColor(buttonColors[UP_INDEX]);
			if(buttonBitmaps[UP_INDEX]!=null){
				this.setBitmap(buttonBitmaps[UP_INDEX]);
			}
		}
	}
}
