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
import com.example.try_gameengine.stage.StageManager;

public class ScrollViewLayer extends Layer{
	public static final int SCROLL_LIMIT_DEFAULT = 0;
	public static final int SCROLL_LIMIT_FOR_CAN_SCOLL_DOWN_WHEN_FIRST_ITEM_IN_THE_TOP = 1;
	public static final int SCROLL_LIMIT_FOR_CAN_SCOLL_UP_WHEN_LAST_ITEM_IN_THE_BOTTOM = 2;
	public static final int SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_CONTENTS_HEIGHT_LESS_THAN_VIEW_HEIGHT = 4;
//	private List<? extends ILayer> mlayers;
	private float itemHeight;
	
//	GestureDetector gestureDetector;
	SingleFingerGesture gestureDetector;
	
	List<ILayer> tabs;
	
	boolean isClickCancled = false;
	
	private Bitmap[] buttonBitmaps = new Bitmap[3];
	private int[] buttonColors = new int[3];
	private boolean hasButtonColors;
	
	private final int NORMAL_INDEX = 0;
	private final int DOWN_INDEX = 1;
	private final int UP_INDEX = 2;
	protected int scrollFlag = SCROLL_LIMIT_DEFAULT;
	protected float topY = 0;
	protected float bottomY = 0;
	
	public ScrollViewLayer() {
		// TODO Auto-generated constructor stub
		
//		initButtons();
//		initSprites();
		initClipSpritess();
		
		gestureDetector = new SingleFingerGesture(StageManager.getCurrentStage(), new SingleFingerGesture.OnGestureListener() {
			boolean startScroll;
			
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
				if(listViewLayerListener.numberOfSections()==0 && listViewLayerListener.numberOfItemsInSection(0)==0)
					return false;
				
//				ALayer firstContentLayer = contentLayers.get(0);
//				if(-distanceY + firstContentLayer.getTop() > 0 
//						&& (listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_DOWN_WHEN_FIRST_ITEM_IN_THE_TOP) == 0){
//					return false;
//				}
//				
//				ALayer lastContentLayer = contentLayers.get(contentLayers.size()-1);
//				if(-distanceY + lastContentLayer.getTop() + lastContentLayer.getHeight() < getHeight() 
//						&& (listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_UP_WHEN_LAST_ITEM_IN_THE_BOTTOM) == 0){
//					return false;
//				}
//				
//				if((lastContentLayer.getTop() + lastContentLayer.getHeight() - firstContentLayer.getTop()) < getHeight() 
//						&& (listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_ITEMS_HEIGHT_LESS_THAN_LISTVIEW) == 0){
//					return false;
//				}
				
//				if((bottomY - topY) < getHeight() 
//						&& (listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_ITEMS_HEIGHT_LESS_THAN_LISTVIEW) == 0){
//					return false;
//				}
				
				boolean isAlginTopOrBottom = false;
				do{
					if(-distanceY + topY > 0 
							&& (scrollFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_DOWN_WHEN_FIRST_ITEM_IN_THE_TOP) == 0){
//						if(topY < 0){
//							distanceY = topY;
//							isAlginTopOrBottom = true;
//							break;
//						}else 
						if(topY <= 0 && (scrollFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_CONTENTS_HEIGHT_LESS_THAN_VIEW_HEIGHT) == 0){
							distanceY = topY;
							isAlginTopOrBottom = true;
							break;
						}else if(topY < 0){
							distanceY = topY;
							isAlginTopOrBottom = true;
							break;
						}
//						else if((listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_ITEMS_HEIGHT_LESS_THAN_LISTVIEW) == 0
//								|| ((bottomY - topY > getHeight()) && (listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_ITEMS_HEIGHT_LESS_THAN_LISTVIEW) != 0)){
////							if((listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_ITEMS_HEIGHT_LESS_THAN_LISTVIEW) == 0)
////								break;
////							else
////								distanceY = bottomY - getHeight();
//							return false;
//						}
						else if(((bottomY - topY > getHeight()) && (scrollFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_CONTENTS_HEIGHT_LESS_THAN_VIEW_HEIGHT) != 0)){
//							if((listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_ITEMS_HEIGHT_LESS_THAN_LISTVIEW) == 0)
//								break;
//							else
//								distanceY = bottomY - getHeight();
							return false;
						} 
						else if((scrollFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_CONTENTS_HEIGHT_LESS_THAN_VIEW_HEIGHT) == 0){
//							break;
							return false;
						}
					}else{
						if(-distanceY + topY < 0 && distanceY > 0 && (bottomY - topY <= getHeight()) && (scrollFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_CONTENTS_HEIGHT_LESS_THAN_VIEW_HEIGHT) != 0
								&& (scrollFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_UP_WHEN_LAST_ITEM_IN_THE_BOTTOM) == 0){
							distanceY = topY;
							isAlginTopOrBottom = true;
							break;
						}
//						else if( (bottomY - topY <= getHeight()) && (listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_ITEMS_HEIGHT_LESS_THAN_LISTVIEW) != 0){
//							distanceY = topY;
//							isAlginTopOrBottom = true;
////							if(bottomY - topY > getHeight() && (listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_DOWN_WHEN_FIRST_ITEM_IN_THE_TOP) == 0)
////								return false;
//							break;
//						}
						
						else if(-distanceY + topY > 0 && (scrollFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_DOWN_WHEN_FIRST_ITEM_IN_THE_TOP) == 0){
							return false;
						} 
						else if(distanceY < 0){
							break;
						}
					}
					
					if(-distanceY + bottomY < getHeight() 
							&& (scrollFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_UP_WHEN_LAST_ITEM_IN_THE_BOTTOM) == 0){
						
						if(bottomY >= getHeight() && (scrollFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_CONTENTS_HEIGHT_LESS_THAN_VIEW_HEIGHT) == 0){
							distanceY = bottomY - getHeight();
							isAlginTopOrBottom = true;
							break;
						}else 
						if(bottomY > getHeight()){
							distanceY = bottomY - getHeight();
							isAlginTopOrBottom = true;
							break;
						}
//						else if((listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_ITEMS_HEIGHT_LESS_THAN_LISTVIEW) == 0
//								|| ((bottomY - topY > getHeight()) && (listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_ITEMS_HEIGHT_LESS_THAN_LISTVIEW) != 0)){
////							if((listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_ITEMS_HEIGHT_LESS_THAN_LISTVIEW) == 0)
////								break;
////							else
////								distanceY = topY;
//							return false;
//						}
						else if(((bottomY - topY > getHeight()) && (scrollFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_CONTENTS_HEIGHT_LESS_THAN_VIEW_HEIGHT) != 0)){
//							if((listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_ITEMS_HEIGHT_LESS_THAN_LISTVIEW) == 0)
//								break;
//							else
//								distanceY = topY;
							return false;
						}
						else if((scrollFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_CONTENTS_HEIGHT_LESS_THAN_VIEW_HEIGHT) == 0){
							return false;
						}
//						else {
//							return false;
////							break;
//						}
					}else{
//						if((bottomY - topY <= getHeight()) && (listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_ITEMS_HEIGHT_LESS_THAN_LISTVIEW) != 0){
//							distanceY = bottomY - getHeight();
//							isAlginTopOrBottom = true;
////							if(bottomY - topY > getHeight())
////								return false;
//							break;
//						}
						if(-distanceY + bottomY > getHeight() && distanceY < 0 && (bottomY - topY <= getHeight()) && (scrollFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_CONTENTS_HEIGHT_LESS_THAN_VIEW_HEIGHT) != 0){
							distanceY = bottomY - getHeight();
							isAlginTopOrBottom = true;
//							if(bottomY - topY > getHeight())
//								return false;
							break;
						}
						
//						else if(-distanceY + bottomY < getHeight() && (listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_DOWN_WHEN_FIRST_ITEM_IN_THE_TOP) == 0){
//							return false;
//						}
//						else{
//							return false;
//						}
					}
					
//					if((listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_ITEMS_HEIGHT_LESS_THAN_LISTVIEW) != 0){
//						return false;
//					}
				}while(false);
//				if((lastContentLayer.getTop() + lastContentLayer.getHeight() - firstContentLayer.getTop()) < getHeight() 
//						&& (listviewFlag & SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_ITEMS_HEIGHT_LESS_THAN_LISTVIEW) == 0){
//					return false;
//				}
				
				if(!startScroll){
					startScroll = true;
					if(!isAlginTopOrBottom)
						distanceY = distanceY > 0 ? 1:-1;
					
					willStartScroll(e1, e2, distanceX, distanceY);
				}
				
				scrollContents(e1, e2, distanceX, distanceY);
				
				topY = topY - distanceY;
				bottomY = bottomY - distanceY;
					
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
	
	protected void scrollContents(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		
	}
	
	protected void willStartScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		
	}
	
//	private void initClipSpritess(){
//		List<ButtonLayer> layers = new ArrayList<ButtonLayer>();
//		mlayers = layers;
//		layers.add(new ButtonLayer(BitmapUtil.icon, 100, (int) itemHeight, false));
//		layers.add(new ButtonLayer(BitmapUtil.icon, 100, (int) itemHeight, false));
//		layers.add(new ButtonLayer(BitmapUtil.icon, 100, (int) itemHeight, false));
//		
//		setIsClipOutside(true);
//		
//		int x = 0;
//		for(ButtonLayer layer : layers){
////			layer.setBitmapAndFrameColAndRowNumAndAutoWH(layer.getBitmap(), 7, 2);
//			layer.setY(x);
//			layer.setAnchorPoint(-0.55f, -0.15f);
////			layer.setXscale(1.5f);
////			layer.setYscale(1.5f);
////			layer.setRotation(45);
////			layer.setBackgroundColor(Color.RED);
//			layer.setButtonColors(Color.RED, Color.BLUE, Color.YELLOW);
//			addChild(layer);
////			layer.setIsClipOutside(true);
//			x += itemHeight;
//			layer.setOnLayerClickListener(new OnLayerClickListener() {
//				
//				@Override
//				public void onClick(ILayer layer) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
//		}
//	}
//	
//	private void initClipSprites(){
//		List<Sprite> layers = new ArrayList<Sprite>();
//		mlayers = layers;
//		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
//		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
//		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
//		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
//		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
//		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
//		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
//		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
//		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
//		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemHeight, false));
//		
//		setIsClipOutside(true);
//		
//		int y = 0;
//		for(Sprite layer : layers){
//			layer.setBitmapAndFrameColAndRowNumAndAutoWH(layer.getBitmap(), 7, 2);
//			layer.setY(y);
//			layer.setAnchorPoint(-0.55f, -0.15f);
//			layer.setXscale(1.5f);
//			layer.setYscale(1.5f);
//			layer.setRotation(45);
//			layer.setBackgroundColor(Color.RED);
////			layer.setButtonColors(Color.RED, Color.BLUE, Color.YELLOW);
//			addChild(layer);
////			layer.setIsClipOutside(true);
//			y += itemHeight;
//			layer.setOnLayerClickListener(new OnLayerClickListener() {
//				
//				@Override
//				public void onClick(ILayer layer) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
//		}
//	}
	
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
		int savedFlag = getFlag();
		if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP 
				|| (event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP){
			addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
			super.onTouchEvent(event);
//			removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
			setFlag(savedFlag);
			addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
			super.onTouchEvent(event);
//			removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
			setFlag(savedFlag);
			return gestureDetector.onTouchEvent(event);
		}else{
			addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
			if(super.onTouchEvent(event)){
//				removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
				setFlag(savedFlag);
				return gestureDetector.onTouchEvent(event);
			}else{
//				removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
				setFlag(savedFlag);
				addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
				if(super.onTouchEvent(event)){
//					removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
					setFlag(savedFlag);
					return gestureDetector.onTouchEvent(event);
				}
//				removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
				setFlag(savedFlag);
			}
		}
		return false;
	}

	@Override
	protected void onTouched(MotionEvent event) {
		
		if((event.getAction()==MotionEvent.ACTION_DOWN || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_DOWN) && isPressed()){
			if(hasButtonColors)
				setBackgroundColor(buttonColors[DOWN_INDEX]);
			if(buttonBitmaps[DOWN_INDEX]!=null){
				this.bitmap = buttonBitmaps[DOWN_INDEX];
			}
			isClickCancled = false;
		}else if((event.getAction()==MotionEvent.ACTION_MOVE || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_MOVE) && isPressed()){

		}else if((event.getAction()==MotionEvent.ACTION_MOVE || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_MOVE) && !isPressed()){
			if(hasButtonColors)
				setBackgroundColor(buttonColors[NORMAL_INDEX]);
			if(buttonBitmaps[NORMAL_INDEX]!=null){
				this.bitmap = buttonBitmaps[NORMAL_INDEX];
			}
			isClickCancled = true;
		}else if((event.getAction()==MotionEvent.ACTION_UP || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP) && isClickCancled && !isPressed()){
			if(hasButtonColors)	
				setBackgroundColor(buttonColors[UP_INDEX]);
			if(buttonBitmaps[UP_INDEX]!=null){
				this.bitmap = buttonBitmaps[UP_INDEX];
			}
		}else if((event.getAction()==MotionEvent.ACTION_UP || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP) && isPressed() && !isClickCancled){
			if(hasButtonColors)
				setBackgroundColor(buttonColors[UP_INDEX]);
			if(buttonBitmaps[UP_INDEX]!=null){
				this.bitmap = buttonBitmaps[UP_INDEX];
			}
		}
	}

	public void setScrollFlag(int scrollFlag) {
		this.scrollFlag = scrollFlag;
	}

	public int getScrollFlag() {
		return this.scrollFlag;
	}

	public void addScrollFlag(int scrollFlag) {
		this.scrollFlag = this.scrollFlag|scrollFlag;
	}

	public void removeScrollFlag(int scrollFlag) {
		this.scrollFlag &= ~scrollFlag;
	}
}
