package com.example.ggamedirdev.listview;

import java.util.ArrayList;
import java.util.List;

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

public class GameLevelLayer extends Layer{
	private List<? extends ILayer> mlayers;
	private float itemHeight;
	
	GestureDetector gestureDetector;
	
	List<ILayer> tabs;
	
	public GameLevelLayer() {
		// TODO Auto-generated constructor stub
		setPosition(70, 70);
		setBackgroundColor(Color.BLUE);
		
		itemHeight = 100;
		
//		initButtons();
//		initSprites();
		initClipSprites();
		
		tabs = new ArrayList<>();
		
		tabs.add(new Layer());
		
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
		return gestureDetector.onTouchEvent(event);
	}

	@Override
	protected void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
