package com.example.ggamedirdev.listview;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.ggamedirdev.BitmapUtil;
import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.Layer;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.framework.ButtonLayer.OnClickListener;

public class ProgressLayer extends ButtonLayer{
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
	
	public ProgressLayer() {super(0, 0, false);
		// TODO Auto-generated constructor stub
		setPosition(70, 70);
		setBackgroundColor(Color.BLUE);
		
		itemHeight = 100;
		
//		initButtons();
//		initSprites();
//		initClipSprites();
		
		Path path = new Path(); //betize
		
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
