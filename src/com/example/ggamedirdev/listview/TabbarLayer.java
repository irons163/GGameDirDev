package com.example.ggamedirdev.listview;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.Layer;

public class TabbarLayer extends Layer{
	private List<? extends ILayer> mlayers;
	
	List<ILayer> tabs;
	
	ALayer contentLayer = new Layer();
	
	public TabbarLayer() {
		// TODO Auto-generated constructor stub
		setBackgroundColor(Color.BLUE);
		
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
	
	public void setTabs(List<ILayer> buttonLayers){
		tabs = buttonLayers;
		calculationSize();
	}
	
	private void calculationSize() {
		int tabNumber = tabs.size();
		float tabW = tabNumber/getWidth();
		for(int i = 0; i < tabs.size(); i++){
			ILayer layer = tabs.get(i);
			layer.setX(i*tabW);
			layer.setY(getHeight());
			layer.setWidth((int)tabW);
		}
	}
	
	OnTabSelectedListener onTabSelectedListener = new OnTabSelectedListener() {
		
		@Override
		public void onTabSelected(int position, ButtonLayer tab) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public interface OnTabSelectedListener{
		public void onTabSelected(int position, ButtonLayer tab);
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
	public boolean onTouchEvent(MotionEvent event, int touchEventFlag) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event, touchEventFlag);
	}
}
