package com.example.ggamedirdev.listview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.Layer;

public class SwitchLayer extends Layer{
	private Path path = new Path();
	
	public SwitchLayer() {
		// TODO Auto-generated constructor stub
		setWidth(300);
		setHeight(150);
		path = new Path();
		path.moveTo(75, 0);
		path.lineTo(225, 0);
		path.arcTo(getFrame(), 90, 180, false);
//		path.quadTo(25, y1, x2, y2)(75, 50);
		path.lineTo(75, 150);
		path.arcTo(getFrame(), 270, -180, false);
		
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		setPaint(paint);
		
		Layer layer = new Layer();
		layer.setSize(50, 50);
		layer.setBitmap(bitmap);
		addChild(layer);
		
		
	}
	
	public void setPathAsBorder(Path path){
		this.path = path;
	}

	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
//		canvas.drawRect(new RectF(), paint);
		Paint originalPaint = paint;
		
		//use input paint first
		int oldColor = 0;
		Style oldStyle = null;
		int oldAlpha = 255;
		if(originalPaint==null && getPaint()!=null){
			paint = getPaint();
//				paint.setAntiAlias(true);
			if(getBackgroundColor()!=NONE_COLOR){
				oldColor = getPaint().getColor();
				oldStyle = getPaint().getStyle();
				oldAlpha = getPaint().getAlpha();
				getPaint().setColor(getBackgroundColor());
				getPaint().setAlpha((int) (getAlpha()*oldAlpha/255.0f));
				getPaint().setStyle(Style.FILL);
				canvas.drawPath(path, paint);
			}
		}else if(originalPaint!=null){
			canvas.drawPath(path, paint);
		}
		
//		canvas.clipPath(path);
	}

	@Override
	protected void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}
}
