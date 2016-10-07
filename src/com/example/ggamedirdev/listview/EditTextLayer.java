package com.example.ggamedirdev.listview;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.Layer;

public class EditTextLayer extends Layer{
	private List<? extends ILayer> mlayers;
	private float itemHeight;
	private boolean hasButtonColors;
	private int[] buttonColors = new int[3];
	private Bitmap[] buttonBitmaps = new Bitmap[3];
	
	private final int NORMAL_INDEX = 0;
	private final int DOWN_INDEX = 1;
	private final int UP_INDEX = 2;
	
	boolean isClickCancled = false;
	
	GestureDetector gestureDetector;
	
	List<ILayer> tabs;
	
	public EditTextLayer() {
		// TODO Auto-generated constructor stub
		setPosition(70, 70);
		setBackgroundColor(Color.BLUE);
		
		itemHeight = 100;
		
//		initButtons();
//		initSprites();
//		initClipSprites();
		
		String text;
		
		Bitmap mouse;
		
		Bitmap frame;
		
		
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

}
