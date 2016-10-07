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
import com.example.try_gameengine.framework.ButtonLayer.OnClickListener;

public class CheckboxLayer extends ButtonLayer{
	private List<? extends ILayer> mlayers;
	private float itemHeight;
	private boolean isChecked;
	
	public interface OnCheckStatusChangedListener{
		public void onChanged(ButtonLayer buttonLayer, boolean isChecked);
	}
	
	private OnCheckStatusChangedListener onCheckStatusChangedListener = new OnCheckStatusChangedListener() {
		
		@Override
		public void onChanged(ButtonLayer buttonLayer, boolean isChecked) {
			// TODO Auto-generated method stub
			
		}
	};	
	
	public CheckboxLayer() {
		// TODO Auto-generated constructor stub
		super(0, 0, false);
		setPosition(70, 70);
		setBackgroundColor(Color.BLUE);
		
		itemHeight = 100;
		
//		initButtons();
//		initSprites();
		initClipSprites();
		
		setOnLayerClickListener(new ALayer.OnLayerClickListener() {
			
			@Override
			public void onClick(ILayer layer) {
				// TODO Auto-generated method stub
				setBackgroundColor(Color.CYAN);
				setChecked(true);
			}
		});
		
		final Layer layer = new Layer();
		
		ButtonLayer buttonLayer = new ButtonLayer("XX", 100, 50, false);
		buttonLayer.setOnClickListener(new ButtonLayer.OnClickListener() {
			
			@Override
			public void onClick(ButtonLayer buttonLayer) {
				// TODO Auto-generated method stub
				layer.setBackgroundColor(Color.MAGENTA);
			}
		});
		
	}
	
	private void initClipSprites(){
		List<Sprite> layers = new ArrayList<Sprite>();
		mlayers = layers;
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
	
	public void setChecked(boolean isChecked){
		if(this.isChecked != isChecked){
			this.isChecked = isChecked;
			onCheckStatusChangedListener.onChanged(this, getChecked());
		}
	}
	
	public boolean getChecked(){
		return isChecked;
	}
	
	public void setOnCheckStatusChangedListener(OnCheckStatusChangedListener onCheckStatusChangedListener){
		this.onCheckStatusChangedListener = onCheckStatusChangedListener;
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
		
		
		
		return super.onTouchEvent(event);
	}

	@Override
	protected void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
