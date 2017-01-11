package com.example.ggamedirdev.listview;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.Tab;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.Layer;

public class TabbarLayer extends Layer{
	private List<? extends ILayer> mlayers;
	private float itemHeight;
	
	GestureDetector gestureDetector;
	
	List<ILayer> tabs;
	
	ALayer contentLayer = new Layer();
	
	public TabbarLayer() {
		// TODO Auto-generated constructor stub
		setBackgroundColor(Color.BLUE);
		
		itemHeight = 100;
		
		tabs = new ArrayList<ILayer>();
		tabs.add(new Layer());
		tabs.add(new Layer());
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
		
		contentLayer.addChild(buttonLayer);
	}
	
	public void setTabs(ButtonLayer buttonLayer){
		buttonLayer.setOnLayerClickListener(new OnLayerClickListener() {
			
			@Override
			public void onClick(ILayer layer) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	OnTabSelectedListener onTabSelectedListener;
	
	public interface OnTabSelectedListener{
		public void onTabSelected(ButtonLayer tab);
	}
	
	public void setOnTabSelectedListemer(OnTabSelectedListener onTabSelectedListener){
		this.onTabSelectedListener = onTabSelectedListener;
	}
	
	public void addToContentLayer(){
		
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
