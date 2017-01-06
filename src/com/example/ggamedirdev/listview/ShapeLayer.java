package com.example.ggamedirdev.listview;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.view.MotionEvent;

import com.example.ggamedirdev.listview.ShapeLayer.Shape.ShapeParam;
import com.example.try_gameengine.framework.Layer;

public class ShapeLayer extends Layer{
	Path path = new Path();
	Shape shape = new CircleShape();
//	private boolean isShapeFitToSize = true;
	
	public void setShape(Shape shape){
		this.shape = shape;
//		if(isShapeFitToSize){
//			path = shape.toPath();
//			Matrix scaleMatrix = new Matrix();
//			RectF rectF = new RectF();
//			path.computeBounds(rectF, true);
//			if(rectF.width() != 0 && rectF.height() !=0){
//				scaleMatrix.setScale(this.getWidth()/rectF.width(), this.getHeight()/rectF.height(),rectF.centerX(),rectF.centerY());
//				path.transform(scaleMatrix);
//			}
//		}else
			path = shape.toPath();
	}
	
	public void fitToSize(){
		path = shape.toPath();
		Matrix scaleMatrix = new Matrix();
		RectF rectF = new RectF();
		path.computeBounds(rectF, true);
		if(rectF.width() != 0 && rectF.height() !=0){
			scaleMatrix.setScale(this.getWidth()/rectF.width(), this.getHeight()/rectF.height(),rectF.centerX(),rectF.centerY());
			path.transform(scaleMatrix);
		}
	}
	
	public void clipCanvas(Canvas canvas){
		canvas.clipPath(path);
//		canvas.clipPath(path, op)
	}
	
	public void drawShape(Canvas canvas){
		
	}
	
	@Override
	public void setX(float x) {
		// TODO Auto-generated method stub
		super.setX(x);
//		shape.updateCenter(x);
		shape.setCenter(getCenterX(), shape.getCenter().y);
	}
	
	@Override
	public void setY(float y) {
		// TODO Auto-generated method stub
		super.setY(y);
		shape.setCenter(shape.getCenter().x, getCenterY());
	}
	
	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		super.setPosition(x, y);
		shape.setCenter(getCenterX(), getCenterY());
	}
	
	@Override
	public void setWidth(int w) {
		// TODO Auto-generated method stub
//		super.setWidth(w);
//		if(child.isComposite() && child.getLayerParam().isEnabledPercentagePositionX()){
//			child.setX(w * child.getLayerParam().getPercentageX());
//		}
//		if(child.isComposite() && child.getLayerParam().isEnabledPercentageSizeW()){
//			child.setWidth((int)(w * child.getLayerParam().getPercentageW()));
//		}
//		shape.updateCenterByParentWidth(int w);
		
		if(shape.getShapeParam().isEnabledPercentagePositionX()){
			shape.setCenter(w * shape.getShapeParam().getPercentageX(), shape.getCenter().y);
		}
		if(shape.getShapeParam().isEnabledPercentageSizeW()){
			if(w==0){
				path.reset();
			}else{
				
				path = shape.toPath();
				Matrix scaleMatrix = new Matrix();
				RectF rectF = new RectF();
				path.computeBounds(rectF, true);
				if(this.getWidth()==0){
					if(rectF.width() != 0 && rectF.height() !=0)
						scaleMatrix.setScale((w * shape.getShapeParam().getPercentageW())/rectF.width(), 1,rectF.centerX(),rectF.centerY());
				}else{
					scaleMatrix.setScale((w * shape.getShapeParam().getPercentageW())/this.getWidth(), 1,rectF.centerX(),rectF.centerY());
				}
				
				path.transform(scaleMatrix); 
			}
			
		}
		
		super.setWidth(w);	
	}
	
	@Override
	public void setHeight(int h) {
		// TODO Auto-generated method stub
//		if(h==0){
//			path.reset();
//		}else{
//			path = shape.toPath();
//			Matrix scaleMatrix = new Matrix();
//			RectF rectF = new RectF();
//			path.computeBounds(rectF, true);
//			scaleMatrix.setScale(h/this.getHeight(), 1,rectF.centerX(),rectF.centerY());
//			path.transform(scaleMatrix); 
//		}
		
		if(shape.getShapeParam().isEnabledPercentagePositionY()){
			shape.setCenter(shape.getCenter().x, h * shape.getShapeParam().getPercentageY());
		}
		if(shape.getShapeParam().isEnabledPercentageSizeH()){
			if(h==0){
				path.reset();
			}else{
				
				path = shape.toPath();
				Matrix scaleMatrix = new Matrix();
				RectF rectF = new RectF();
				path.computeBounds(rectF, true);
				if(this.getWidth()==0){
					if(rectF.width() != 0 && rectF.height() !=0)
						scaleMatrix.setScale(1, (h * shape.getShapeParam().getPercentageH())/rectF.height(),rectF.centerX(),rectF.centerY());
				}else{
					scaleMatrix.setScale(1, (h * shape.getShapeParam().getPercentageH())/rectF.height(),rectF.centerX(),rectF.centerY());
				}
				
				path.transform(scaleMatrix); 
			}
			
		}
		
		super.setHeight(h);
	}
	
	@Override
	public void setSize(int w, int h) {
		// TODO Auto-generated method stub
		if(h==0){
			path.reset();
		}else{
			path = shape.toPath();
			Matrix scaleMatrix = new Matrix();
			RectF rectF = new RectF();
			path.computeBounds(rectF, true);
			scaleMatrix.setScale(h/this.getHeight(), 1,rectF.centerX(),rectF.centerY());
			path.transform(scaleMatrix); 
		}
		
		super.setSize(w, h);
		
		
	}
	
	@Override
	public void doDrawself(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.doDrawself(canvas, paint);
	}
	
	@Override
	protected void doDrawSelfWithClipedCanvas(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.doDrawSelfWithClipedCanvas(canvas, paint);
//		clipCanvas(canvas);
		getFrameInScene();
		shape.draw(canvas, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event, int touchEventFlag) {
		// TODO Auto-generated method stub
		
		return super.onTouchEvent(event, touchEventFlag);
	}
	
	@Override
	protected boolean isTouched(RectF f, float touchedPointX,
			float touchedPointY) {
		// TODO Auto-generated method stub
		if(!super.isTouched(f, touchedPointX, touchedPointY))
			return false;
//		Matrix matrix = new Matrix();
//		RectF rectF = null; rectF.centerX();
//		Point point;
//
//		Path path1 = new Path();
//		path1.addCircle(10, 10, 4, Path.Direction.CW);
//		Path path2 = new Path();
//		path2.addCircle(15, 15, 8, Path.Direction.CW);

//		Region clip = new Region(0, 0, getWidth(), getHeight());
		
		Rect r = new Rect();
		f.roundOut(r);
		Region clip = new Region(r);
		
		Region region1 = new Region();
		path = shape.toPath();
		path.offset(f.left, f.top);
		region1.setPath(path, clip);
//		Region region2 = new Region();
//		Rect r = new Rect();
//		f.roundOut(r);
//		region2.set(r);
//
//		if (!region1.quickReject(region2) && region1.op(region2, Region.Op.INTERSECT)) {
//		    // Collision!
//			
//		}
		
		boolean isTouched = region1.contains((int)touchedPointX, (int)touchedPointY);
		
		return isTouched;
	}
	
	
	public static abstract class Shape{
		Path path = new Path();
		Paint paint = new Paint();
		protected Paint drawPaint;
		private PointF center = new PointF();
		private ShapeParam shapeParam = new ShapeParam();
		
		static class ShapeParam extends LayerParam{
			
			public ShapeParam() {
				super();
			}
			
			public ShapeParam(ShapeParam shapeParam) {
				super(shapeParam);
			}
		}
		
		public void setCenter(float cx, float cy){
			center.set(cx, cy);
		}
		
		public PointF getCenter(){
			return center;
		}
		
		public Paint getPaint(){
			return paint;
		}
		
		public void draw(Canvas canvas, Paint paint){
			if(paint == null){
				paint = getPaint();
			}
			
			drawPaint = paint;
		}
		
		public Path toPath(){
			path.reset();
			return path;
		}
		
		public void setShapeParam(ShapeParam shapeParam){
			this.shapeParam = shapeParam;
		}
		
		public ShapeParam getShapeParam(){
			return shapeParam;
		}
	}

	class MaskShape {
		Shape shape;
		Path path = new Path();
		
		public MaskShape(Shape shape) {
			this.shape = shape;
			path = shape.toPath();
		}
		
		public void clipCanvas(Canvas canvas){
			canvas.clipPath(path);
		}
		
		public void setShape(Shape shape){
			this.shape = shape;
			path = shape.toPath();
		}
	}

	class RectShape extends Shape{
		RectF rectF = new RectF();
		
		@Override
		public void setCenter(float cx, float cy) {
			// TODO Auto-generated method stub
			super.setCenter(cx, cy);
			rectF.offset(cx - rectF.centerX(), cy - rectF.centerY());
		}
		
		@Override
		public void draw(Canvas canvas, Paint paint) {
			// TODO Auto-generated method stub
			super.draw(canvas, paint);
			canvas.drawRect(rectF, drawPaint);		
		}
		
		@Override
		public Path toPath() {
			// TODO Auto-generated method stub
			super.toPath();
			
			path.addRect(rectF, Direction.CW);
			return path;
		}
	}

	public static class CircleShape extends Shape{
		float radius;
		
		@Override
		public void setCenter(float cx, float cy) {
			// TODO Auto-generated method stub
			super.setCenter(cx, cy);
		}
		
		public void setCenter(float cx, float cy, float radius){
			setCenter(cx, cy);
			this.radius = radius;
		}
		
		@Override
		public void draw(Canvas canvas, Paint paint) {
			// TODO Auto-generated method stub
			super.draw(canvas, paint);
			canvas.drawCircle(getCenter().x, getCenter().y, radius, drawPaint);
		}
		
		@Override
		public Path toPath() {
			// TODO Auto-generated method stub
			super.toPath();
			
			path.addCircle(getCenter().x, getCenter().y, radius, Direction.CW);
			return path;
		}
	}
	
	public static class PolygonShape extends Shape{
		private Path polygon = new Path();
		
		public void setPath(Path path) {
			// TODO Auto-generated method stub
			this.polygon.set(path);
		}
		
		@Override
		public void setCenter(float cx, float cy) {
			// TODO Auto-generated method stub
			super.setCenter(cx, cy);
		}
		
		@Override
		public void draw(Canvas canvas, Paint paint) {
			// TODO Auto-generated method stub
			super.draw(canvas, paint);
			canvas.drawPath(polygon, drawPaint);
		}
		
		@Override
		public Path toPath() {
			// TODO Auto-generated method stub
			super.toPath();
			path.set(polygon);
			return path;
		}
	}
}

