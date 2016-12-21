package com.example.ggamedirdev.listview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
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
	}
	
	public void setPathAsBorder(Path path){
		this.path = path;
	}

	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
//		canvas.drawRect(new RectF(), paint);
		canvas.drawPath(path, paint);
//		canvas.clipPath(path);
	}

	@Override
	protected void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}
}
