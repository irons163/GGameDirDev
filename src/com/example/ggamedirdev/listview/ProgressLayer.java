package com.example.ggamedirdev.listview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path.Direction;
import android.graphics.PointF;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.LabelLayer;

public class ProgressLayer extends ButtonLayer{
	private float currentProgress;
	private Path backgroundPath, progressPath;
	private float backgroundPathWidth, progressPathWidth;
	private LabelLayer labelLayer;
	private Type type;
	
	public enum Type{
		circle, line
	}
	
	public ProgressLayer() {
		super(0, 0, false);
		// TODO Auto-generated constructor stub
		setBackgroundColor(Color.BLUE);
		
		setPaint(new Paint());
		progressPath = new Path(); //betize
		backgroundPath = new Path();
		labelLayer = new LabelLayer(0f, 0f, false);
		labelLayer.setAnchorPoint(0.5f, 0.5f);
		LayerParam layerParam = new LayerParam();
		layerParam.setEnabledPercentagePositionX(true);
		layerParam.setEnabledPercentagePositionY(true);
		layerParam.setPercentageX(0.5f);
		layerParam.setPercentageY(0.5f);
		labelLayer.setLayerParam(layerParam);
		addChild(labelLayer);
		
	}
	
	public float getCurrentProgress() {
		return currentProgress;
	}

	public void setCurrentProgress(float currentProgress) {
		this.currentProgress = currentProgress;
		labelLayer.setText(currentProgress + "%");
	}
	
	public Path getBackgroundPath() {
		return backgroundPath;
	}

	public void setBackgroundPath(Path backgroundPath) {
		this.backgroundPath = backgroundPath;
		getPaint().setStrokeWidth(backgroundPathWidth);
	}

	public float getBackgroundPathWidth() {
		return backgroundPathWidth;
	}

	public void setBackgroundPathWidth(float backgroundPathWidth) {
		this.backgroundPathWidth = backgroundPathWidth;
	}

	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
	}
	
	protected void dd(Canvas canvas, Paint paint2) {
		if(type == Type.circle){
//		path = new Path(); //betize
//		path.addArc(getFrameInScene(), (float)(100/100f*Math.PI*2), (float)-Math.PI/2);
		
//		path2 = new Path();
		backgroundPath.reset();
		progressPath.reset();
		backgroundPath.addArc(getFrameInScene(), (float)((((100/100f*Math.PI*2)) ) * (180 / Math.PI)), (float)(((-Math.PI/2)-((100/100f*Math.PI*2))) * (180 / Math.PI)));
//		path.addArc(getFrameInScene(), (float)(currentProgress/100f*Math.PI*2) * (180 / (float)Math.PI), (((float)(currentProgress/100f*Math.PI*2) - (float)-Math.PI/2) )* (float)(180 / Math.PI));
		progressPath.addArc(getFrameInScene(), (float)((-Math.PI/2) * (180 / Math.PI)), (float)((((currentProgress/100f*Math.PI*2) - (-Math.PI/2)) ) * (180 / Math.PI)));
		
		paint2.setColor(Color.RED);
		paint2.setStyle(Style.STROKE);
		paint2.setStrokeWidth(10);
		canvas.drawPath(backgroundPath, paint2);
		paint2.setColor(Color.GREEN);
		canvas.drawPath(progressPath, paint2);
		
//		progressPath
		
		backgroundPath.reset();
		progressPath.reset();
		paint2.setColor(Color.RED);
		paint2.setStyle(Style.STROKE);
		paint2.setStrokeWidth(10);
		canvas.drawPath(backgroundPath, paint2);
		paint2.setColor(Color.GREEN);
		canvas.drawPath(progressPath, paint2);
		
		}else if(type == Type.line){
			backgroundPath.reset();
			progressPath.reset();
			backgroundPath.addRect(getFrameInScene(), Direction.CW);
//			path.addArc(getFrameInScene(), (float)(currentProgress/100f*Math.PI*2) * (180 / (float)Math.PI), (((float)(currentProgress/100f*Math.PI*2) - (float)-Math.PI/2) )* (float)(180 / Math.PI));
			RectF rectF = new RectF(getFrameInScene());
			float currentProgressWidth = rectF.width() * (currentProgress/100);
			rectF.right = rectF.left + currentProgressWidth;
			progressPath.addRect(rectF, Direction.CW);
			
			paint2.setColor(Color.RED);
			paint2.setStyle(Style.STROKE);
			paint2.setStrokeWidth(10);
			canvas.drawPath(backgroundPath, paint2);
			paint2.setColor(Color.GREEN);
			canvas.drawPath(progressPath, paint2);
			
//			progressPath
			
			backgroundPath.reset();
			progressPath.reset();
			paint2.setColor(Color.RED);
			paint2.setStyle(Style.STROKE);
			paint2.setStrokeWidth(10);
			canvas.drawPath(backgroundPath, paint2);
			paint2.setColor(Color.GREEN);
			canvas.drawPath(progressPath, paint2);
		}
	}
	
	@Override
	protected void drawBitmap(Canvas canvas, Paint paint, int oldColor,
			Style oldStyle, int oldAlpha, boolean isDrawBackgroundColor) {
		// TODO Auto-generated method stub
		super.drawBitmap(canvas, paint, oldColor, oldStyle, oldAlpha,
				isDrawBackgroundColor);
		
		dd(canvas, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		return super.onTouchEvent(event);
	}


}
