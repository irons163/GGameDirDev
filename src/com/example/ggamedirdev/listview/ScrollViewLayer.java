package com.example.ggamedirdev.listview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.Layer;
import com.example.try_gameengine.stage.StageManager;

public class ScrollViewLayer extends Layer implements ITouchStatusListener{
	public static final int SCROLL_LIMIT_DEFAULT = 0;
	public static final int SCROLL_LIMIT_FOR_CAN_SCOLL_DOWN_WHEN_FIRST_ITEM_IN_THE_TOP = 1;
	public static final int SCROLL_LIMIT_FOR_CAN_SCOLL_UP_WHEN_LAST_ITEM_IN_THE_BOTTOM = 2;
	public static final int SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_CONTENTS_HEIGHT_LESS_THAN_VIEW_HEIGHT = 4;
//	private List<? extends ILayer> mlayers;
//	private float itemHeight;
	
//	GestureDetector gestureDetector;
	protected SingleFingerGesture gestureDetector;
	
//	List<ILayer> tabs;

	protected int scrollFlag = SCROLL_LIMIT_DEFAULT;
	protected float topY = 0;
	protected float bottomY = 0;
	protected boolean isInvalidate = false;
	protected boolean isAutoRefresh = false;
	
	public ScrollViewLayer() {
		
//		initButtons();
//		initSprites();
//		initClipSpritess();
		
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
				
				if(!checkScrollable(e1, e2, distanceX, distanceY))
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
				
				invalidate();
					
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
//				return false;
				return true;
			}
		});
		
		gestureDetector.setIsLongpressEnabled(false);
	}
	
	protected boolean checkScrollable(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return true;
	}
	
	protected void willStartScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		MotionEvent cancelEvent = MotionEvent.obtain(e2);
		cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
		
		for(ILayer layer : getLayers()){
			layer.onTouchEvent(cancelEvent);
		}
	}
	
	protected void scrollContents(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		for(ILayer layer : getLayers()){
			layer.setY(layer.getY() - distanceY);
		}
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
	
	@Override
	public void addChild(ILayer layer) {
		// TODO Auto-generated method stub
		super.addChild(layer);
		invalidate();
	}
	
	@Override
	public void frameTrig() {
		// TODO Auto-generated method stub
		if(isAutoRefresh() || isInvalidate){
			refresh();
			isInvalidate = false;
		}
		super.frameTrig();
	}
	
	public void refresh(){
		for(ILayer layer : getLayers()){
			float childTop = this.locationInLayer(0, layer.getFrameInScene().top).y;
			topY = childTop < topY ? childTop : topY;
			float childBottom = this.locationInLayer(0, layer.getFrameInScene().bottom).y;
			bottomY = childBottom > bottomY ? childBottom : bottomY;
		}
	}
	
	public void invalidate(){
		isInvalidate = true;
	}
	
	public boolean isAutoRefresh() {
		return isAutoRefresh;
	}
	
	public void setTopChildren(){
		if(getLayers().size()>0){
			topY = this.locationInLayer(0, getLayers().get(0).getFrameInScene().top).y;
			bottomY = this.locationInLayer(0, getLayers().get(getLayers().size()-1).getFrameInScene().bottom).y;
		}
	}

	/**
	 * set auto refresh, if true, it refresh by each game loop process. It infect performance.
	 * @param isAutoRefresh
	 */
	public void setAutoRefresh(boolean isAutoRefresh) {
		this.isAutoRefresh = isAutoRefresh;
	}

	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		super.drawSelf(canvas, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int savedFlag = getFlag();
		if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP 
				|| (event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP){
			addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
			super.onTouchEvent(event, TOUCH_EVENT_ONLY_ACTIVE_ON_NOT_INERT_LAYERS|TOUCH_EVENT_ONLY_ACTIVE_ON_LINEAL_LAYERS);
//			removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
			setFlag(savedFlag);
			addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
			super.onTouchEvent(event, TOUCH_EVENT_ONLY_ACTIVE_ON_NOT_INERT_LAYERS|TOUCH_EVENT_ONLY_ACTIVE_ON_LINEAL_LAYERS);
//			removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
			setFlag(savedFlag);
			return gestureDetector.onTouchEvent(event);
		}else{
			addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
			if(super.onTouchEvent(event, TOUCH_EVENT_ONLY_ACTIVE_ON_NOT_INERT_LAYERS|TOUCH_EVENT_ONLY_ACTIVE_ON_LINEAL_LAYERS)){
//				removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
				setFlag(savedFlag);
				return gestureDetector.onTouchEvent(event);
			}else{
//				removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
				setFlag(savedFlag);
				addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
				if(super.onTouchEvent(event, TOUCH_EVENT_ONLY_ACTIVE_ON_NOT_INERT_LAYERS|TOUCH_EVENT_ONLY_ACTIVE_ON_LINEAL_LAYERS)){
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
	public boolean onTouchEvent(MotionEvent event, int touchEventFlag) {
		// TODO Auto-generated method stub
		touchEventFlag |= (TOUCH_EVENT_ONLY_ACTIVE_ON_NOT_INERT_LAYERS|TOUCH_EVENT_ONLY_ACTIVE_ON_LINEAL_LAYERS);
		int savedFlag = getFlag();
		if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP 
				|| (event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP){
			addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
			super.onTouchEvent(event, touchEventFlag);
//			removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
			setFlag(savedFlag);
			addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
			super.onTouchEvent(event, touchEventFlag);
//			removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
			setFlag(savedFlag);
			return gestureDetector.onTouchEvent(event);
		}else{
			addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
			if(super.onTouchEvent(event, touchEventFlag)){
//				removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
				setFlag(savedFlag);
				return gestureDetector.onTouchEvent(event);
			}else{
//				removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN);
				setFlag(savedFlag);
				addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_SELF);
				if(super.onTouchEvent(event, touchEventFlag)){
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

//	@Override
//	protected void onTouched(MotionEvent event) {
//		
//		if((event.getAction()==MotionEvent.ACTION_DOWN || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_DOWN) && isPressed()){
//			if(hasButtonColors)
//				setBackgroundColor(buttonColors[DOWN_INDEX]);
//			if(buttonBitmaps[DOWN_INDEX]!=null){
//				this.bitmap = buttonBitmaps[DOWN_INDEX];
//			}
//			isClickCancled = false;
//		}else if((event.getAction()==MotionEvent.ACTION_MOVE || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_MOVE) && isPressed()){
//
//		}else if((event.getAction()==MotionEvent.ACTION_MOVE || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_MOVE) && !isPressed()){
//			if(hasButtonColors)
//				setBackgroundColor(buttonColors[NORMAL_INDEX]);
//			if(buttonBitmaps[NORMAL_INDEX]!=null){
//				this.bitmap = buttonBitmaps[NORMAL_INDEX];
//			}
//			isClickCancled = true;
//		}else if((event.getAction()==MotionEvent.ACTION_UP || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP) && isClickCancled && !isPressed()){
//			if(hasButtonColors)	
//				setBackgroundColor(buttonColors[UP_INDEX]);
//			if(buttonBitmaps[UP_INDEX]!=null){
//				this.bitmap = buttonBitmaps[UP_INDEX];
//			}
//		}else if((event.getAction()==MotionEvent.ACTION_UP || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP) && isPressed() && !isClickCancled){
//			if(hasButtonColors)
//				setBackgroundColor(buttonColors[UP_INDEX]);
//			if(buttonBitmaps[UP_INDEX]!=null){
//				this.bitmap = buttonBitmaps[UP_INDEX];
//			}
//		}
//	}
	
	@Override
	public void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
//		super.onTouched(event);
		touchStatusListener.onTouched(event);
	}
	
	public void setBitmap(Bitmap bitmap) {
		super.setBitmap(bitmap);
	}
	
	protected ITouchStatusListener touchStatusListener = new DefaultTouchStatusListener(this);
	
	public void setTouchStatusListener(ITouchStatusListener touchStatusListener){
		this.touchStatusListener = touchStatusListener;
	}
	
	public abstract class TouchStatusListener implements ITouchStatusListener{
		protected boolean isClickCancled = false;
		
		public void onTouched(MotionEvent event) {
			if((event.getAction()==MotionEvent.ACTION_DOWN || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_DOWN) && isPressed()){
				onTouchDown(event);
			}else if((event.getAction()==MotionEvent.ACTION_MOVE || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_MOVE) && isPressed()){
				onTouchMoveInRange(event);
			}else if((event.getAction()==MotionEvent.ACTION_MOVE || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_MOVE) && !isPressed()){
				onTouchMoveOutRange(event);
			}else if((event.getAction()==MotionEvent.ACTION_UP || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP) && isClickCancled && !isPressed()){
				onTouchUpWithOutPress(event);
			}else if((event.getAction()==MotionEvent.ACTION_UP || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP) && isPressed() && !isClickCancled){
				onTouchUpWithPress(event);
			}
		}
		
		public abstract void onTouchDown(MotionEvent event);
		public abstract void onTouchMoveInRange(MotionEvent event);
		public abstract void onTouchMoveOutRange(MotionEvent event);
		public abstract void onTouchUpWithOutPress(MotionEvent event);
		public abstract void onTouchUpWithPress(MotionEvent event);
	}
	
	public class DefaultTouchStatusListener extends TouchStatusListener{
		protected final int NORMAL_INDEX = 0;
		protected final int DOWN_INDEX = 1;
		protected final int UP_INDEX = 2;
		protected final int INDEX_SIZE = 3;
		
		protected Bitmap[] touchedBitmaps = new Bitmap[INDEX_SIZE];
		protected int[] touchedColors = new int[INDEX_SIZE];
		protected boolean hasTouchedColors;
		private ALayer layer;
		
		public DefaultTouchStatusListener(ALayer layer) {
			this.layer = layer;
		}
		
		@Override
		public void onTouchDown(MotionEvent event) {
			// TODO Auto-generated method stub
			if(hasTouchedColors)
				layer.setBackgroundColor(touchedColors[DOWN_INDEX]);
			if(touchedBitmaps[DOWN_INDEX]!=null){
				layer.setBitmap(touchedBitmaps[DOWN_INDEX]);
			}
			isClickCancled = false;
		}

		@Override
		public void onTouchMoveInRange(MotionEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTouchMoveOutRange(MotionEvent event) {
			// TODO Auto-generated method stub
			if(hasTouchedColors)
				layer.setBackgroundColor(touchedColors[NORMAL_INDEX]);
			if(touchedBitmaps[NORMAL_INDEX]!=null){
				layer.setBitmap( touchedBitmaps[NORMAL_INDEX]);
			}
			isClickCancled = true;
		}

		@Override
		public void onTouchUpWithOutPress(MotionEvent event) {
			// TODO Auto-generated method stub
			if(hasTouchedColors)	
				layer.setBackgroundColor(touchedColors[UP_INDEX]);
			if(touchedBitmaps[UP_INDEX]!=null){
				layer.setBitmap( touchedBitmaps[UP_INDEX]);
			}
		}

		@Override
		public void onTouchUpWithPress(MotionEvent event) {
			// TODO Auto-generated method stub
			if(hasTouchedColors)
				layer.setBackgroundColor(touchedColors[UP_INDEX]);
			if(touchedBitmaps[UP_INDEX]!=null){
				layer.setBitmap(touchedBitmaps[UP_INDEX]);
			}
		}
		
		/**
		 * set touched bitmaps.
		 * @param bitmaps with Bitmap normal, Bitmap down , Bitmap up.
		 */
		@Override
		public void setTouchedBitmaps(Bitmap[] bitmaps){
			this.touchedBitmaps[NORMAL_INDEX] = bitmaps[NORMAL_INDEX];
			this.touchedBitmaps[DOWN_INDEX] = bitmaps[DOWN_INDEX];
			this.touchedBitmaps[UP_INDEX] = bitmaps[UP_INDEX];
		}
		
		
		/**
		 * set array reference to touched bitmaps. 
		 * @param bitmaps bitmaps array.
		 */
		@Override
		public void setTouchedBitmapsArrayReference(Bitmap[] bitmaps){
			this.touchedBitmaps = bitmaps;
		}
		
		/**
		 * set touched colors.
		 * @param colors with int normal, int down , int up.
		 */
		@Override
		public void setTouchedColors(int[] colors){
//			setBackgroundColor(normal);
			touchedColors[NORMAL_INDEX] = colors[NORMAL_INDEX];
			touchedColors[DOWN_INDEX] = colors[DOWN_INDEX];
			touchedColors[UP_INDEX] = colors[UP_INDEX];
			hasTouchedColors = true;
		}
		
		/**
		 * set array reference to touched colors. 
		 * @param colors colors array.
		 */
		@Override
		public void setTouchedColorsArrayReference(int[] colors){
			this.touchedColors = colors;
		}
		
		@Override
		public void setTouchedColorsNone(){
//			setBackgroundColorNone();
			touchedColors[NORMAL_INDEX] = NONE_COLOR;
			touchedColors[DOWN_INDEX] = NONE_COLOR;
			touchedColors[UP_INDEX] = NONE_COLOR;
			hasTouchedColors = false;
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

	@Override
	public void setTouchedBitmaps(Bitmap[] bitmaps) {
		// TODO Auto-generated method stub
		touchStatusListener.setTouchedBitmaps(bitmaps);
	}

	@Override
	public void setTouchedBitmapsArrayReference(Bitmap[] bitmaps) {
		// TODO Auto-generated method stub
		touchStatusListener.setTouchedBitmapsArrayReference(bitmaps);
	}

	@Override
	public void setTouchedColors(int[] colors) {
		// TODO Auto-generated method stub
		touchStatusListener.setTouchedColors(colors);
	}

	@Override
	public void setTouchedColorsArrayReference(int[] colors) {
		// TODO Auto-generated method stub
		touchStatusListener.setTouchedColorsArrayReference(colors);
	}

	@Override
	public void setTouchedColorsNone() {
		// TODO Auto-generated method stub
		touchStatusListener.setTouchedColorsNone();
	}
	
	@Override
	protected boolean isInert() {
		// TODO Auto-generated method stub
		return false;
	}
}
