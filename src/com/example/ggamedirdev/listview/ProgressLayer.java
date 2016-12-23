package com.example.ggamedirdev.listview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.LabelLayer;

public class ProgressLayer extends ButtonLayer{
	private float currentProgress;
	private Path backgroundPath, progressPath;
	private float backgroundPathWidth, progressPathWidth;
	private LabelLayer labelLayer;
	
//	public interface OnCheckStatusChangedListener{
//		public void onChanged(ButtonLayer buttonLayer, boolean isChecked);
//	}
	
//	private OnCheckStatusChangedListener onCheckStatusChangedListener = new OnCheckStatusChangedListener() {
//		
//		@Override
//		public void onChanged(ButtonLayer buttonLayer, boolean isChecked) {
//			// TODO Auto-generated method stub
//			
//		}
//	};	
	
	public ProgressLayer() {
		super(0, 0, false);
		// TODO Auto-generated constructor stub
		setBackgroundColor(Color.BLUE);
		
//		initButtons();
//		initSprites();
//		initClipSprites();
		
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
		
//		setOnLayerClickListener(new ALayer.OnLayerClickListener() {
//			@Override
//			public void onClick(ILayer layer) {
//				// TODO Auto-generated method stub
//				setBackgroundColor(Color.CYAN);
//				setChecked(true);
//			}
//		});
//		
//		final Layer layer = new Layer();
//		
//		ButtonLayer buttonLayer = new ButtonLayer("XX", 100, 50, false);
//		buttonLayer.setOnClickListener(new ButtonLayer.OnClickListener() {
//			
//			@Override
//			public void onClick(ButtonLayer buttonLayer) {
//				// TODO Auto-generated method stub
//				layer.setBackgroundColor(Color.MAGENTA);
//			}
//		});
		
	}
	
//	public void setChecked(boolean isChecked){
//		if(this.isChecked != isChecked){
//			this.isChecked = isChecked;
//			onCheckStatusChangedListener.onChanged(this, getChecked());
//		}
//	}
//	
//	public boolean getChecked(){
//		return isChecked;
//	}
//	
//	public void setOnCheckStatusChangedListener(OnCheckStatusChangedListener onCheckStatusChangedListener){
//		this.onCheckStatusChangedListener = onCheckStatusChangedListener;
//	}
	
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
	}
	
	@Override
	protected void drawBitmap(Canvas canvas, Paint paint, int oldColor,
			Style oldStyle, int oldAlpha, boolean isDrawBackgroundColor) {
		// TODO Auto-generated method stub
		super.drawBitmap(canvas, paint, oldColor, oldStyle, oldAlpha,
				isDrawBackgroundColor);
		
		dd(canvas, paint);
	}
	/*
	@Override
	protected void doDrawself(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
//		super.doDrawself(canvas, paint);
		if(getBackgroundColor()!=NONE_COLOR || bitmap!=null){
			canvas.save();
			
			do {
				canvas = getC(canvas, paint);
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
						canvas.drawRect(getFrameInScene(), paint);
					}
				}else if(originalPaint!=null){
					canvas.drawRect(getFrameInScene(), paint);
				}
				
				if(isComposite()){
					
					
					if(originalPaint==null && getPaint()!=null && getBackgroundColor()!=NONE_COLOR){
	//					if(isClipOutside)
	//						dst.intersect(getParent().getDst());
	//					canvas.drawRect(dst, paint);
						getPaint().setColor(oldColor);
						getPaint().setStyle(oldStyle);
						getPaint().setAlpha(oldAlpha);
					}
					if(bitmap!=null){
						canvas.drawBitmap(bitmap, src, dst, paint);
						dd(canvas, paint);
					}
				}else{
					if(originalPaint==null && getPaint()!=null && getBackgroundColor()!=NONE_COLOR){
	//					canvas.drawRect(getFrame(), paint);
						getPaint().setColor(oldColor);
						getPaint().setStyle(oldStyle);
						getPaint().setAlpha(oldAlpha);
					}
				}
				
				//use input paint first
				paint = originalPaint;
			} while (false);
			
			canvas.restore();
		}
	}
	*/
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
