package com.example.ggamedirdev.listview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Point;
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
//			path = shape.toPath();
		path.set(shape.toPath());
		
//		if(isBitmapSacleToFitSize()){
//			setWidth(getWidth());
//			setHeight(getHeight());
//		}
		
//		if(getWidth() ==0 || getHeight() ==0){
			
//			setWidth((int)Math.ceil(shape.getShapeBounds().width()));
//			setHeight((int)Math.ceil(shape.getShapeBounds().height()));
			
//			if(isEnableShape){
//				tmpSize = getSize();
//			}
//			
//			isEnableShape = false;
			
//			shape.setEnable(false);
//		}
		
		if(getWidth() ==0){
			if(shape.getShapeParam().isEnabledPercentageSizeW()){
				if(shape.getShapeParam().getPercentageW()!=0)
					super.setWidth((int)Math.ceil(shape.getShapeBounds().width()/shape.getShapeParam().getPercentageW()));
				else
					throw new RuntimeException("PercentageW == 0");
			}else {
				setWidth((int)Math.ceil(shape.getShapeBounds().width()));
			}
		}else{
			if(shape.getShapeParam().isEnabledPercentageSizeW() || shape.getShapeParam().isEnabledPercentagePositionX())
				setWidth(getWidth());
		}
		
		if(getHeight() ==0){
			if(shape.getShapeParam().isEnabledPercentageSizeH()){
				if(shape.getShapeParam().getPercentageH()!=0)
					super.setHeight((int)Math.ceil(shape.getShapeBounds().height()/shape.getShapeParam().getPercentageH()));
				else
					throw new RuntimeException("PercentageH == 0");
			}else{
				setHeight((int)Math.ceil(shape.getShapeBounds().height()));
			}
		}else{
			if(shape.getShapeParam().isEnabledPercentageSizeH() || shape.getShapeParam().isEnabledPercentagePositionY())
				setHeight(getHeight());
		}
		
//		setWidth(getWidth());
//		setHeight(getHeight());
	}
	
	public void shapeFitToSize(){
//		path = shape.toPath();
//		Matrix scaleMatrix = new Matrix();
//		RectF rectF = new RectF();
//		path.computeBounds(rectF, true);
//		if(rectF.width() != 0 && rectF.height() !=0){
//			scaleMatrix.setScale(this.getWidth()/rectF.width(), this.getHeight()/rectF.height(),rectF.centerX(),rectF.centerY());
//			path.transform(scaleMatrix);
//		}
		
//		float sx = 0, sy = 0;
//		boolean isNeedScale = false;
		
		RectF rectF = shape.getShapeBounds();
		if(rectF.width() != 0 && rectF.height() !=0)
			shape.sacle(this.getWidth()/rectF.width(), this.getHeight()/rectF.height());
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
//		shape.setCenter(getCenterX(), shape.getCenter().y);
	}
	
	@Override
	public void setY(float y) {
		// TODO Auto-generated method stub
		super.setY(y);
//		shape.setCenter(shape.getCenter().x, getCenterY());
	}
	
	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		super.setPosition(x, y);
//		shape.setCenter(getCenterX(), getCenterY());
	}
	
	@Override
	public void setWidth(int w) {
		changeShaderSize(w, getHeight());
		
		super.setWidth(w);	
	}
	
	boolean isEnableShape = true;
	Point tmpSize = new Point();
	
	@Override
	public void setHeight(int h) {
		changeShaderSize(getWidth(), h);
		
		super.setHeight(h);
	}
	
	@Override
	public void setSize(int w, int h) {
		changeShaderSize(w, h);
		
		super.setSize(w, h);
	}

	private void changeShaderSize(int w, int h) {
		float sx = 1, sy = 1;
		boolean isNeedScale = false;
		if(shape.getShapeParam().isEnabledPercentagePositionX()){
			shape.setCenter(w * shape.getShapeParam().getPercentageX(), shape.getCenter().y);
		}
		if(shape.getShapeParam().isEnabledPercentageSizeW()){
			if(w==0){
				if(isEnableShape){
					tmpSize = getSize();
				}
				
				isEnableShape = false;
//				shape.setEnable(false);
			}else{
				isNeedScale = true;
				
				if(!isEnableShape && h!=0){
					sx = w/tmpSize.x;
					isEnableShape = true;
//					shape.setEnable(isEnableShape);
				}else {
//					sx = (w * shape.getShapeParam().getPercentageW())/this.getWidth();
					sx = (w * shape.getShapeParam().getPercentageW())/shape.getShapeBounds().width();
					if(!isEnableShape){
						tmpSize.x = w;
					}
				}
			}
		}
		
		if(shape.getShapeParam().isEnabledPercentagePositionY()){
			shape.setCenter(shape.getCenter().x, h * shape.getShapeParam().getPercentageY());
		}
		if(shape.getShapeParam().isEnabledPercentageSizeH()){
			if(h==0){
				if(isEnableShape){
					tmpSize = getSize();
				}
				
				isEnableShape = false;
			}else{
				isNeedScale = true;
				
				if(!isEnableShape && w!=0){
					sy = h/tmpSize.y;
					isEnableShape = true;
//					shape.setEnable(isEnableShape);
				}else {
//					sy =  (h * shape.getShapeParam().getPercentageH())/this.getHeight();
					sy =  (h * shape.getShapeParam().getPercentageH())/shape.getShapeBounds().height();
					if(!isEnableShape){
						tmpSize.y = h;
					}
				}
			}
		}
		
		if(isNeedScale)
			shape.sacle(sx, sy);
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
//		getFrameInScene();
		if(isEnableShape)
		shape.draw(canvas, paint, getFrameInScene().left, getFrameInScene().top);
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
		
		boolean isTouched = true;
		
		if(isEnableShape){
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
		
		isTouched = region1.contains((int)touchedPointX, (int)touchedPointY);
		}
		
		return isTouched;
	}
	
	@Override
	public void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
		super.onTouched(event);
		
		if((event.getAction()==MotionEvent.ACTION_DOWN || (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_DOWN) && isPressed()){
			setBackgroundColor(Color.GREEN);
		}
	}
	
	public static abstract class Shape{
		Path path = new Path();
		Paint paint = new Paint();
		protected Paint drawPaint;
		private PointF center = new PointF();
		private ShapeParam shapeParam = new ShapeParam();
		private RectF shapeBounds = new RectF();
//		private boolean isEnable;
		
		public static class ShapeParam extends LayerParam{
			
			public ShapeParam() {
				super();
			}
			
			public ShapeParam(ShapeParam shapeParam) {
				super(shapeParam);
			}
		}
		
		public void setCenter(float cx, float cy){
			center.set(cx, cy);
			toPath();
			calculateShapeBounds();
		}
		
		public PointF getCenter(){
			return center;
		}
		
		public Paint getPaint(){
			return paint;
		}
		
		public void draw(Canvas canvas, Paint paint, float offsetX, float offsetY){
//			if(!isEnable())
//				return;
			
			if(paint == null){
				paint = getPaint();
			}
			
			drawPaint = paint;
		}
		
		public void draw(Canvas canvas, Paint paint){
			draw(canvas, paint, 0, 0);
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

		public RectF getShapeBounds() {
			return shapeBounds;
		}
		
		public void sacle(float sx, float xy){
//			path = shape.toPath();
			Matrix scaleMatrix = new Matrix();
//			RectF rectF = new RectF();
//			path.computeBounds(rectF, true);
			scaleMatrix.setScale(sx, xy, getShapeBounds().centerX(), getShapeBounds().centerY());
			path.transform(scaleMatrix);
			calculateShapeBounds();
		}
		
		protected void calculateShapeBounds(){
			RectF rectF = new RectF();
			path.computeBounds(rectF, true);
			getShapeBounds().set(rectF);
		}
		
//		void setEnable(boolean isEnable){
//			this.isEnable = isEnable;
//		}
//		
//		public boolean isEnable(){
//			return isEnable;
//		}
		
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
			
			if(getWidth() ==0 || getHeight() ==0){
				if(isEnableShape){
					tmpSize = getSize();
				}
				
				isEnableShape = false;
				
//				shape.setEnable(false);
			}
		}
	}

	class RectShape extends Shape{
		RectF rectF = new RectF();
		
		public void setRectF(RectF rectF){
			this.rectF = rectF;
			setCenter(rectF.centerX(), rectF.centerY());
		}
		
		@Override
		public void setCenter(float cx, float cy) {
			// TODO Auto-generated method stub
			rectF.offset(cx - rectF.centerX(), cy - rectF.centerY());
			super.setCenter(cx, cy);
//			rectF.offset(cx - rectF.centerX(), cy - rectF.centerY());
//			getShapeBounds().set(rectF);
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
		
		@Override
		protected void calculateShapeBounds() {
			// TODO Auto-generated method stub
			super.calculateShapeBounds();
			rectF.set(getShapeBounds());
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
			this.radius = radius;
			setCenter(cx, cy);
		}

		@Override
		public void draw(Canvas canvas, Paint paint, float offsetX,
				float offsetY) {
			// TODO Auto-generated method stub
			super.draw(canvas, paint, offsetX, offsetY);
			
			canvas.drawCircle(getCenter().x + offsetX, getCenter().y + offsetY, radius, drawPaint);
		}
		
		@Override
		public void draw(Canvas canvas, Paint paint) {
			// TODO Auto-generated method stub
			super.draw(canvas, paint);
		}
		
		@Override
		public Path toPath() {
			// TODO Auto-generated method stub
			super.toPath();
			
			path.addCircle(getCenter().x, getCenter().y, radius, Direction.CW);
			return path;
		}
		
		@Override
		protected void calculateShapeBounds() {
			// TODO Auto-generated method stub
			super.calculateShapeBounds();
			float minSide = getShapeBounds().width() < getShapeBounds().height() ? getShapeBounds().width(): getShapeBounds().height();
			radius = minSide/2;
		}
	}
	
	public static class PolygonShape extends Shape{
		private Path polygon = new Path();
		private Path drawPath = new Path();
		
		public void setPath(Path path) {
			// TODO Auto-generated method stub
			this.polygon.set(path);
			toPath();
			calculateShapeBounds();
		}
		
		@Override
		public void setCenter(float cx, float cy) {
			// TODO Auto-generated method stub
			super.setCenter(cx, cy);
		}
		
		@Override
		public void draw(Canvas canvas, Paint paint, float offsetX,
				float offsetY) {
			// TODO Auto-generated method stub
			super.draw(canvas, paint, offsetX, offsetY);
			drawPath.set(polygon);
			drawPath.offset(offsetX, offsetY);
			canvas.drawPath(drawPath, drawPaint);
		}
		
		@Override
		public void draw(Canvas canvas, Paint paint) {
			// TODO Auto-generated method stub
			super.draw(canvas, paint);
			
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

