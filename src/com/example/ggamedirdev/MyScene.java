package com.example.ggamedirdev;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.ggamedirdev.listview.AchievementSystemLayer;
import com.example.ggamedirdev.listview.CheckboxLayer;
import com.example.ggamedirdev.listview.EditTextLayer;
import com.example.ggamedirdev.listview.ListViewLayer;
import com.example.ggamedirdev.listview.ScaleGuestureViewLayer;
import com.example.ggamedirdev.listview.ScrollViewLayer;
import com.example.ggamedirdev.listview.SelectViewLayer;
import com.example.try_gameengine.Camera.Camera;
import com.example.try_gameengine.avg.SelectView;
import com.example.try_gameengine.framework.GameView;
import com.example.try_gameengine.framework.IGameController;
import com.example.try_gameengine.framework.IGameModel;
import com.example.try_gameengine.framework.LabelLayer;
import com.example.try_gameengine.framework.LabelLayer.LabelBaseLine;
import com.example.try_gameengine.framework.LayerManager;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.remotecontroller.custome.Custome4D2FCommandType;
import com.example.try_gameengine.remotecontroller.custome.Custome4D2FRemoteController;
import com.example.try_gameengine.scene.EasyScene;
import com.example.try_gameengine.utils.DetectArea;
import com.example.try_gameengine.utils.DetectAreaPoint;
import com.example.try_gameengine.utils.DetectAreaRect;
import com.example.try_gameengine.utils.DetectAreaRound;
import com.example.try_gameengine.utils.DetectAreaSpriteRect;
import com.example.try_gameengine.utils.GameTimeUtil;
import com.example.try_gameengine.utils.SpriteDetectAreaHandler;

public class MyScene extends EasyScene{
	private int gameTime;
	
	private GameTimeUtil gameTimeUtil;
	
	private Custom4D2FRemoteContollerListener custom4d2fRemoteContollerListener = new Custom4D2FRemoteContollerListener();
	
	private RectF userRectF = new RectF(100, 200, 200, 300);
	private RectF rectF = new RectF(100, 500, 200, 600);
	private float circleRadius = 50;
	private PointF circleCenter = new PointF(550, 250);
	private PointF pointF = new PointF(500, 500);
	private Sprite sprite = new Sprite();
	private LabelLayer dirMsgLayer = new LabelLayer(0.0f, 50.0f, false);
	private LabelLayer collisionMsgLayer = new LabelLayer(0.0f, 70.0f, false);
	private LabelLayer userRectMsgLayer = new LabelLayer(0.0f, 50.0f, false);
	private LabelLayer rectMsgLayer = new LabelLayer(0.0f, 50.0f, false);
	private LabelLayer circleMsgLayer = new LabelLayer(0.0f, 50.0f, false);
	private LabelLayer pointMsgLayer = new LabelLayer(0.0f, 50.0f, false);
	private DetectArea userRectDetectArea;
	private DetectArea rectDetectArea;
	private DetectArea circleDetectArea;
	private DetectArea pointDetectArea;
	private ListViewLayer listViewLayer;
	private SelectViewLayer selectViewLayer;
	private ScaleGuestureViewLayer scaleGuestureViewLayer;
	private ScrollViewLayer scrollViewLayer;
	private CheckboxLayer checkboxLayer;
	private EditTextLayer editTextLayer;
	private AchievementSystemLayer achievementSystemLayer;
	
	private void setDectecAreas(){
		userRectDetectArea = new DetectAreaRect(userRectF);
		rectDetectArea = new DetectAreaRect(rectF);
		circleDetectArea = new DetectAreaRound(circleCenter, circleRadius);
		pointDetectArea = new DetectAreaPoint(pointF);
		SpriteDetectAreaHandler spriteDetectAreaHandler = new SpriteDetectAreaHandler();
		DetectArea a = new DetectAreaSpriteRect(new RectF(), new DetectAreaSpriteRect.SpriteRectListener() {
			
			@Override
			public RectF caculateSpriteRect() {
				// TODO Auto-generated method stub
				RectF rectF;
				if(sprite.getLocationInScene()!=null)
					rectF = new RectF(sprite.getLocationInScene().x, sprite.getLocationInScene().y, sprite.getLocationInScene().x + sprite.w, sprite.getLocationInScene().y + sprite.h);
				else
					rectF = sprite.getFrame();
				return rectF;
			}
			
			@Override
			public PointF caculateSpriteCenter() {
				// TODO Auto-generated method stub;
				PointF pointF;
				if(sprite.getLocationInScene()!=null)
					pointF = new PointF(sprite.getLocationInScene().x + sprite.w/2, sprite.getLocationInScene().y + sprite.h/2);
				else
					pointF = new PointF(sprite.getFrame().centerX(), sprite.getFrame().centerY());
				return pointF;
			}
		});
	
		sprite.setSpriteDetectAreaHandler(spriteDetectAreaHandler);
		spriteDetectAreaHandler.addSuccessorDetectArea(a);
	}	
	
	private void checkDetectAreasCollision(){
		if(DetectArea.detectConditionWithTwoArea(userRectDetectArea, rectDetectArea)){
			collisionMsgLayer.setText("Collision RECT");
		}else if(DetectArea.detectConditionWithTwoArea(userRectDetectArea, circleDetectArea)){
			collisionMsgLayer.setText("Collision CIRCLE");
		}else if(DetectArea.detectConditionWithTwoArea(userRectDetectArea, pointDetectArea)){
			collisionMsgLayer.setText("Collision POINT");
		}else{
			collisionMsgLayer.setText("");
		}
	}
	
	public MyScene(final Context context, String id, int level, int mode) {
		super(context, id, level, mode);
		// TODO Auto-generated constructor stub
		isEnableRemoteController(true);
		Custome4D2FRemoteController remoteController = Custome4D2FRemoteController.createRemoteController();
		setRemoteController(remoteController);
		custom4d2fRemoteContollerListener.setCustom4D2FRemoteContollerListener(new Custome4D2FRemoteController.RemoteContollerListener() {
			
			@Override
			public void pressDown(List<Custome4D2FCommandType> commandTypes) {
				// TODO Auto-generated method stub
				
			}
		});
		remoteController.setRemoteContollerListener(custom4d2fRemoteContollerListener);
		
		setDectecAreas();
		
		userRectMsgLayer.setAnchorPoint(0.5f, 1.0f);
		rectMsgLayer.setAnchorPoint(0.5f, 1.0f);
		circleMsgLayer.setAnchorPoint(0.5f, 1.0f);
		pointMsgLayer.setAnchorPoint(0.5f, 1.0f);
//		userRectMsgLayer.setAnchorPoint(0.5f, 0.0f);
//		rectMsgLayer.setAnchorPoint(0.5f, 0.0f);
//		circleMsgLayer.setAnchorPoint(0.5f, 0.0f);
//		pointMsgLayer.setAnchorPoint(0.5f, 0.0f);
		
		userRectMsgLayer.setLabelBaseLine(LabelBaseLine.BASELINE_FOR_TEXT_TOP);
		rectMsgLayer.setLabelBaseLine(LabelBaseLine.BASELINE_FOR_TEXT_TOP);
		circleMsgLayer.setLabelBaseLine(LabelBaseLine.BASELINE_FOR_TEXT_TOP);
		pointMsgLayer.setLabelBaseLine(LabelBaseLine.BASELINE_FOR_TEXT_TOP);
		
		userRectMsgLayer.setText("USER RECTj");
		rectMsgLayer.setText("RECTj");
		circleMsgLayer.setText("CIRCLE");
		pointMsgLayer.setText("POINT");
		
		userRectMsgLayer.setPosition(userRectF.centerX(), userRectF.bottom);
		rectMsgLayer.setPosition(rectF.centerX(), rectF.bottom);
		circleMsgLayer.setPosition(circleCenter.x, circleCenter.y+circleRadius);
		pointMsgLayer.setPosition(pointF.x, pointF.y + 50);
		
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		
		userRectMsgLayer.setPaint(paint);
		rectMsgLayer.setPaint(paint);
		circleMsgLayer.setPaint(paint);
		pointMsgLayer.setPaint(paint);
		
		listViewLayer = new ListViewLayer();
		listViewLayer.setWidth(200);
		listViewLayer.setHeight(1850);
		listViewLayer.setAutoAdd(true);
		
		selectViewLayer = new SelectViewLayer();
		selectViewLayer.setWidth(200);
		selectViewLayer.setHeight(1850);
		selectViewLayer.setAutoAdd(true);
		
//		scaleGuestureViewLayer = new ScaleGuestureViewLayer();
//		scaleGuestureViewLayer.setPosition(500, 200);
//		scaleGuestureViewLayer.setWidth(200);
//		scaleGuestureViewLayer.setHeight(250);
//		scaleGuestureViewLayer.setAutoAdd(true);
		
		scrollViewLayer = new ScrollViewLayer();
		scrollViewLayer.setPosition(650, 300);
		scrollViewLayer.setWidth(150);
		scrollViewLayer.setHeight(350);
		scrollViewLayer.setAutoAdd(true);
		
		checkboxLayer = new CheckboxLayer();
		checkboxLayer.setPosition(650, 700);
		checkboxLayer.setWidth(150);
		checkboxLayer.setHeight(350);
		checkboxLayer.setAutoAdd(true);
		
	}

	GameView gameview;
	
	@Override
	public void initGameView(Activity activity, IGameController gameController,
			IGameModel gameModel) {
		// TODO Auto-generated method stub
		class MyGameView extends GameView{
			public MyGameView(Context context, IGameController gameController,
					IGameModel gameModel) {
				super(context, gameController, gameModel);
				// TODO Auto-generated constructor stub
			}			
		}		
		gameview = new MyGameView(activity, gameController, gameModel);
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		checkPlayerMoved();
		checkDetectAreasCollision();
		tickTime();
	}
	
	private void tickTime(){
		if(gameTimeUtil.isArriveExecuteTime()){
			gameTime++;
		}
	}
	
	boolean isMoveing = false;
	private void checkPlayerMoved(){
		
		if(custom4d2fRemoteContollerListener.getCurrentMove() == Custom4D2FRemoteContollerListener.LEFT){
			userRectF.offset(-5, 0);
			dirMsgLayer.setText("LEFT");
		}else if(custom4d2fRemoteContollerListener.getCurrentMove() == Custom4D2FRemoteContollerListener.RIGHT){
			userRectF.offset(5, 0);
			dirMsgLayer.setText("RIGHT");
		}else if(custom4d2fRemoteContollerListener.getCurrentMove() == Custom4D2FRemoteContollerListener.UP){
			userRectF.offset(0, -5);
			dirMsgLayer.setText("UP");
		}else if(custom4d2fRemoteContollerListener.getCurrentMove() == Custom4D2FRemoteContollerListener.DOWN){
			userRectF.offset(0, 5);
			dirMsgLayer.setText("DOWN");
		}else{
			dirMsgLayer.setText("");
		}
		
		userRectMsgLayer.setPosition(userRectF.centerX(), userRectF.bottom);
//		player.frameTrig();
	}

	@Override
	public void doDraw(Canvas canvas) {
		// TODO Auto-generated method stub
//		sprite.drawSelf(canvas, null);
//		LayerManager.drawLayers(canvas, null);
		
		boolean isHW = canvas.isHardwareAccelerated();
		
//		LayerManager.drawSceneLayers(canvas, null, sceneLayerLevel);
		
		Paint paint = new Paint();
		paint.setTextSize(50);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setColor(Color.WHITE);
		canvas.drawText(gameTime+"", 300, 70, paint);
		dirMsgLayer.drawSelf(canvas, paint);
		collisionMsgLayer.drawSelf(canvas, paint);
		

		
		canvas.drawRect(userRectF, paint);
		canvas.drawRect(rectF, paint);
		canvas.drawCircle(circleCenter.x, circleCenter.y, circleRadius, paint);
		canvas.drawPoint(pointF.x, pointF.y, paint);
		
		userRectMsgLayer.drawSelf(canvas, null);
		rectMsgLayer.drawSelf(canvas, null);
		circleMsgLayer.drawSelf(canvas, null);
		pointMsgLayer.drawSelf(canvas, null);
		
		super.doDraw(canvas);
		
		listViewLayer.drawSelf(canvas, null);
		
		selectViewLayer.drawSelf(canvas, null);
		
//		scaleGuestureViewLayer.drawSelf(canvas, null);
		
		scrollViewLayer.drawSelf(canvas, null);
		
		checkboxLayer.drawSelf(canvas, null);
	}

	int count =0;
	float x = 0;
	float y = 0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			x = event.getX();
			y = event.getY();
		}else if(event.getAction() == MotionEvent.ACTION_MOVE){
			float dx = event.getX() - x;
			float dy = event.getY() - y;
			
			x = event.getX();
			y = event.getY();
		}
		
//		listViewLayer.onTouchEvent(event);
//		
//		selectViewLayer.onTouchEvent(event);
//		
//		scaleGuestureViewLayer.onTouchEvent(event);
//		
//		scrollViewLayer.onTouchEvent(event);
//		
//		checkboxLayer.onTouchEvent(event);
		
		return super.onTouchEvent(event);
	}
	
	@Override
	public void beforeGameStart() {
		// TODO Auto-generated method stub
		gameTimeUtil = new GameTimeUtil(1000);
//		getCamera().setLocation(gameview.getWidth()/2, gameview.getHeight()/2);
		Camera camera = new Camera(0, 0, gameview.getWidth()+500, gameview.getHeight());
		setCamera(camera);
//		getCamera().translate(1500, 1500);
		getCamera().translate(540, 0);
		getCamera().zoom(-1.0f);
		getCamera().rotation(45); //viewport, touchrange, spinner,edittext,save    
		getCamera().setViewPort(0, 0, gameview.getWidth()/2, gameview.getHeight()/2);
		getCamera().getViewPortRectF();
		
		getCamera().applyCameraSpaceToViewPort();
		
//		getCamera().applyCameraScale();
//		getCamera().applyCameraRotate();
//		getCamera().applyCameraTranslate();
//		getCamera().apply(canvas);
	}

	@Override
	public void arrangeView(Activity activity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setActivityContentView(Activity activity) {
		// TODO Auto-generated method stub
		activity.setContentView(gameview);
	}

	@Override
	public void afterGameStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

}
