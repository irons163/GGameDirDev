package com.example.ggamedirdev;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import com.example.ggamedirdev.calendar.CalanderLayer;
import com.example.ggamedirdev.listview.AchievementSystemLayer;
import com.example.ggamedirdev.listview.BaseLayerAdapter;
import com.example.ggamedirdev.listview.CheckBoxGroup;
import com.example.ggamedirdev.listview.CheckboxLayer;
import com.example.ggamedirdev.listview.CollectionLayer;
import com.example.ggamedirdev.listview.ControllerBarLayer;
import com.example.ggamedirdev.listview.EditTextLayer;
import com.example.ggamedirdev.listview.EditTextLayer.MyInputConnection;
import com.example.ggamedirdev.listview.GameLevelLayer;
import com.example.ggamedirdev.listview.ITouchStatusListener;
import com.example.ggamedirdev.listview.ListViewLayer;
import com.example.ggamedirdev.listview.NumberPickerLayer;
import com.example.ggamedirdev.listview.ProgressLayer;
import com.example.ggamedirdev.listview.ScaleGuestureViewLayer;
import com.example.ggamedirdev.listview.ScrollViewLayer;
import com.example.ggamedirdev.listview.SelectViewLayer;
import com.example.ggamedirdev.listview.SpinnerLayer;
import com.example.ggamedirdev.listview.SwitchLayer;
import com.example.ggamedirdev.listview.TabViewLayer;
import com.example.ggamedirdev.listview.TabbarLayer;
import com.example.ggamedirdev.listview.VideoLayer;
import com.example.ggamedirdev.listview.ViewPagerAdapter;
import com.example.ggamedirdev.listview.ViewPagerLayer;
import com.example.ggamedirdev.listview.WebViewLayer;
import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.GameView;
import com.example.try_gameengine.framework.IGameController;
import com.example.try_gameengine.framework.IGameModel;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.LabelLayer;
import com.example.try_gameengine.framework.ShapeLayer;
import com.example.try_gameengine.framework.LabelLayer.AlignmentVertical;
import com.example.try_gameengine.framework.ShapeLayer.CircleShape;
import com.example.try_gameengine.framework.ShapeLayer.PolygonShape;
import com.example.try_gameengine.framework.ShapeLayer.Shape.ShapeParam;
import com.example.try_gameengine.framework.Layer;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.remotecontroller.custome.Custom4D2FCommandType;
import com.example.try_gameengine.remotecontroller.custome.Custom4D2FRemoteController;
import com.example.try_gameengine.scene.EasyScene;
import com.example.try_gameengine.stage.StageManager;
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
	private TabViewLayer tabViewLayer;
	private ViewPagerLayer viewPagerLayer;
	private SpinnerLayer spinnerLayer;
	
	InputMethodManager input;
	
	MyInputConnection myInputConnection;
	
	private void setDectecAreas(){
		userRectDetectArea = new DetectAreaRect(userRectF);
		rectDetectArea = new DetectAreaRect(rectF);
		circleDetectArea = new DetectAreaRound(circleCenter, circleRadius);
		pointDetectArea = new DetectAreaPoint(pointF);
		SpriteDetectAreaHandler spriteDetectAreaHandler = new SpriteDetectAreaHandler();
		DetectArea a = new DetectAreaSpriteRect(new RectF(), new DetectAreaSpriteRect.SpriteRectListener() {
			
			@Override
			public RectF calculateSpriteRect() {
				// TODO Auto-generated method stub
				RectF rectF;
				if(sprite.getLocationInScene()!=null)
					rectF = new RectF(sprite.getLocationInScene().x, sprite.getLocationInScene().y, sprite.getLocationInScene().x + sprite.getWidth(), sprite.getLocationInScene().y + sprite.getHeight());
				else
					rectF = sprite.getFrame();
				return rectF;
			}
			
			@Override
			public PointF calculateSpriteCenter() {
				// TODO Auto-generated method stub;
				PointF pointF;
				if(sprite.getLocationInScene()!=null)
					pointF = new PointF(sprite.getLocationInScene().x + sprite.getWidth()/2, sprite.getLocationInScene().y + sprite.getHeight()/2);
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
		Custom4D2FRemoteController remoteController = Custom4D2FRemoteController.createRemoteController();
		setRemoteController(remoteController);
		custom4d2fRemoteContollerListener.setCustom4D2FRemoteContollerListener(new Custom4D2FRemoteController.RemoteContollerListener() {
			
			@Override
			public void pressDown(List<Custom4D2FCommandType> commandTypes) {
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
		
		userRectMsgLayer.setAlignmentVertical(AlignmentVertical.ALIGNMENT_TOP);
		rectMsgLayer.setAlignmentVertical(AlignmentVertical.ALIGNMENT_TOP);
		circleMsgLayer.setAlignmentVertical(AlignmentVertical.ALIGNMENT_TOP);
		pointMsgLayer.setAlignmentVertical(AlignmentVertical.ALIGNMENT_TOP);
		
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
		
		selectViewLayer = new SelectViewLayer();
		selectViewLayer.setWidth(200);
		selectViewLayer.setHeight(1850);
		selectViewLayer.setAutoAdd(true);
		
		scaleGuestureViewLayer = new ScaleGuestureViewLayer();
		scaleGuestureViewLayer.setPosition(500, 200);
		scaleGuestureViewLayer.setWidth(200);
		scaleGuestureViewLayer.setHeight(250);
		scaleGuestureViewLayer.setAutoAdd(true);
		
		scrollViewLayer = new ScrollViewLayer();
		scrollViewLayer.setPosition(650, 200);
		scrollViewLayer.setWidth(150);
		scrollViewLayer.setHeight(350);
		scrollViewLayer.setBackgroundColor(Color.CYAN);
		((ITouchStatusListener)scrollViewLayer).setTouchedColors(new int[]{Color.RED, Color.YELLOW, Color.BLUE});

		Sprite layer = new Sprite(BitmapUtil.hamster, 100, 100, false);
//		ButtonLayer layer = new ButtonLayer(100, 100, false);
		
//		setIsClipOutside(true);
		

			layer.setBitmapAndFrameColAndRowNumAndAutoWH(layer.getBitmap(), 7, 2);
			layer.setY(y);
			layer.setAnchorPoint(-0.55f, 0.15f);
//			layer.setAnchorPoint(0.0f, 0.0f);
			layer.setXscale(1.5f);
			layer.setYscale(1.5f);
			layer.setRotation(45);
			layer.setBackgroundColor(Color.RED);
//			layer.setButtonColors(Color.RED, Color.BLUE, Color.YELLOW);
//			addChild(layer);
//			layer.setIsClipOutside(true);
//			layer.setOnLayerClickListener(new OnLayerClickListener() {
//				
//				@Override
//				public void onClick(ILayer layer) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
			
		scrollViewLayer.addChild(layer);
		scrollViewLayer.setAutoAdd(true);
		
		final List<ALayer> pagers = new ArrayList<ALayer>();
		pagers.add(new ButtonLayer("1", 100, (int) 100, false));
		pagers.add(new ButtonLayer("2", 100, (int) 100, false));
		pagers.add(new ButtonLayer("3", 100, (int) 100, false));
		
		viewPagerLayer = new ViewPagerLayer(StageManager.getCurrentStage());
		viewPagerLayer.setWidth(200);
		viewPagerLayer.setHeight(600);
		viewPagerLayer.setBackgroundColor(Color.YELLOW);
		viewPagerLayer.setAutoAdd(true);
		viewPagerLayer.setIsClipOutside(true);
		
		ALayer backgroundButtonLayer = new ButtonLayer();
		backgroundButtonLayer.setWidth(600);
		backgroundButtonLayer.setHeight(400);
		backgroundButtonLayer.setBackgroundColor(Color.MAGENTA);
		viewPagerLayer.addChild(backgroundButtonLayer);
		
		viewPagerLayer.setAdapter(new ViewPagerAdapter() {
			
			@Override
			public boolean isViewFromObject(ALayer view, Object object) {
				// TODO Auto-generated method stub
				return view == object;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 3;
			}
			
			@Override
			public Object instantiateItem(ALayer container, int position) {
				// TODO Auto-generated method stub
				container.addChild(pagers.get(position));
				return pagers.get(position);
			}
			
			@Override
			public void destroyItem(ALayer container, int position,
					Object object) {
				// TODO Auto-generated method stub
				container.remove(pagers.get(position));
			}
		});
		

		
		ALayer viewPagerDecorLayer = new Sprite();
		viewPagerDecorLayer.setBitmapAndAutoChangeWH(BitmapUtil.icon);
		viewPagerDecorLayer.setPosition(viewPagerLayer.getFrame().right, viewPagerLayer.getFrame().bottom);
		viewPagerDecorLayer.setAnchorPoint(1.0f, 1.0f);
		viewPagerLayer.addChildDecor(viewPagerDecorLayer);
		
		listViewLayer = new ListViewLayer();
		listViewLayer.addScrollFlag(ScrollViewLayer.SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_CONTENTS_HEIGHT_LESS_THAN_VIEW_HEIGHT);
//		listViewLayer.addListViewFlag(ListViewLayer.SCROLL_LIMIT_FOR_CAN_SCOLL_DOWN_WHEN_FIRST_ITEM_IN_THE_TOP);
//		listViewLayer.addListViewFlag(ListViewLayer.SCROLL_LIMIT_FOR_CAN_SCOLL_UP_WHEN_LAST_ITEM_IN_THE_BOTTOM);
		listViewLayer.setWidth(200);
//		listViewLayer.setHeight(850);
		listViewLayer.setHeight(500);
		listViewLayer.setAutoAdd(true);
		listViewLayer.setBackgroundColor(Color.CYAN);
		listViewLayer.setPosition(70, 70);
		
		List<ButtonLayer> layers = new ArrayList<ButtonLayer>();
//		itemLayers = layers;
		layers.add(new ButtonLayer("1", 100, (int) listViewLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("2", 100, (int) listViewLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("3", 100, (int) listViewLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("4", 100, (int) listViewLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("5", 100, (int) listViewLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("6", 100, (int) listViewLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("7", 100, (int) listViewLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("8", 100, (int) listViewLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("9", 100, (int) listViewLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("100", 100, (int) listViewLayer.getItemContentLayerHeight(), false));
		
		int y = 0;
		for(ButtonLayer itemlayer : layers){
			itemlayer.setY(y);
			itemlayer.setBackgroundColor(Color.RED);
			itemlayer.setTextColor(Color.WHITE);
			itemlayer.setButtonColors(Color.RED, Color.BLUE, Color.YELLOW);
//			addChild(layer);
//			layer.setIsClipOutside(true);
//			y += itemHeight;
			itemlayer.setOnClickListener(new ButtonLayer.OnClickListener() {
				
				@Override
				public void onClick(ButtonLayer buttonLayer) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		listViewLayer.setItems(layers);
		
		((ITouchStatusListener)listViewLayer).setTouchedColors(new int[]{Color.RED, Color.YELLOW, Color.BLUE});
		
		spinnerLayer = new SpinnerLayer();
		spinnerLayer.setWidth(150);
		spinnerLayer.setHeight(50);
		spinnerLayer.setPosition(250, 600);
		spinnerLayer.setAutoAdd(true);
		spinnerLayer.setAdapter(new BaseLayerAdapter() {
			String[] strs = new String[]{"223","323","423"};
			@Override
			public ALayer getLayer(int position, ALayer layer, ALayer parent) {
				// TODO Auto-generated method stub
				LabelLayer spinnerSelectionLayer = null;
				if(layer==null){
					spinnerSelectionLayer = new LabelLayer(0,0,false);
					spinnerSelectionLayer.setWidth(100);
					spinnerSelectionLayer.setHeight(60);
					spinnerSelectionLayer.setText((String)getItem(position));
					spinnerSelectionLayer.setAlignmentVertical(AlignmentVertical.ALIGNMENT_CENTER);
				}else{
					spinnerSelectionLayer = (LabelLayer) layer;
//					spinnerSelectionLayer.setText((String)getItem(position));
				}
				
				return spinnerSelectionLayer;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return strs[position];
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return strs.length;
			}
		});
		
		SwitchLayer switchLayer = new SwitchLayer();
		switchLayer.setPosition(500, 600);
		addChild(switchLayer);
		
		checkboxLayer = new CheckboxLayer();
		checkboxLayer.setPosition(650, 700);
		checkboxLayer.setWidth(150);
		checkboxLayer.setHeight(350);
		checkboxLayer.setAutoAdd(true);
		
		CheckboxLayer checkboxLayer = new CheckboxLayer();
		checkboxLayer.setSize(150, 100);
		checkboxLayer.setPosition(400, 400);
		addChild(checkboxLayer);
		
		CheckBoxGroup checkBoxGroup = new CheckBoxGroup();
		checkBoxGroup.setSize(150, 300);
		checkBoxGroup.setPosition(400, 550);
		checkBoxGroup.setBackgroundColor(Color.CYAN);
		addChild(checkBoxGroup);
		
		CheckboxLayer checkboxLayer1 = new CheckboxLayer();
		checkboxLayer1.setSize(150, 100);
		checkboxLayer1.setPosition(0, 0);
		checkBoxGroup.addCheckBox(checkboxLayer1);
		
		CheckboxLayer checkboxLayer2 = new CheckboxLayer();
		checkboxLayer2.setSize(150, 100);
		checkboxLayer2.setPosition(0, 100);
		checkBoxGroup.addCheckBox(checkboxLayer2);
//		
		ShapeLayer shapeLayer = new ShapeLayer();
		shapeLayer.setSize(100, 100);
		shapeLayer.setPosition(550, 500);
		CircleShape circleShape = new ShapeLayer.CircleShape();
		circleShape.setCenter(50, 50, 10);
		circleShape.getPaint().setColor(Color.RED);
		circleShape.getPaint().setStyle(Style.FILL);
		shapeLayer.setShape(circleShape);
		shapeLayer.setBackgroundColor(Color.YELLOW);
		addChild(shapeLayer);
		
		ShapeLayer shapeLayer2 = new ShapeLayer();
		shapeLayer2.setSize(100, 100);
		shapeLayer2.setPosition(550, 600);
		CircleShape circleShape2 = new ShapeLayer.CircleShape();
		circleShape2.setCenter(50, 50, 10);
		circleShape2.getPaint().setColor(Color.RED);
		circleShape2.getPaint().setStyle(Style.FILL);
		ShapeParam shapeParam = new ShapeParam();
		shapeParam.setEnabledPercentageSizeW(true);
		shapeParam.setPercentageW(1f);
		shapeParam.setEnabledPercentageSizeH(true);
		shapeParam.setPercentageH(1f);
		circleShape2.setShapeParam(shapeParam);
		shapeLayer2.setShape(circleShape2);
		shapeLayer2.shapeFitToSize();
		shapeLayer2.setBackgroundColor(Color.YELLOW);
		shapeLayer2.setWidth(200);
		shapeLayer2.setHeight(150);
		addChild(shapeLayer2);
		
		ShapeLayer shapeLayer3 = new ShapeLayer();
		shapeLayer3.setPosition(550, 800);
		CircleShape circleShape3 = new ShapeLayer.CircleShape();
		circleShape3.setCenter(50, 50, 10);
		circleShape3.getPaint().setColor(Color.RED);
		circleShape3.getPaint().setStyle(Style.FILL);
		shapeParam = new ShapeParam();
		shapeParam.setEnabledPercentageSizeW(true);
		shapeParam.setPercentageW(1f);
		shapeParam.setEnabledPercentagePositionX(true);
		shapeParam.setPercentageX(0.5f);
		shapeParam.setEnabledPercentagePositionY(true);
		shapeParam.setPercentageY(0.5f);
		circleShape3.setShapeParam(shapeParam);
		shapeLayer3.setShape(circleShape3);
		shapeLayer3.shapeFitToSize();
		shapeLayer3.setBackgroundColor(Color.YELLOW);
		addChild(shapeLayer3);
		
		ShapeLayer shapeLayer4 = new ShapeLayer();
		shapeLayer4.setPosition(550, 900);
		
		CircleShape circleShape4 = new ShapeLayer.CircleShape();
		circleShape4.setCenter(50, 50, 10);
		circleShape4.getPaint().setColor(Color.RED);
		circleShape4.getPaint().setStyle(Style.FILL);
		shapeParam = new ShapeParam();
		shapeParam.setEnabledPercentageSizeW(true);
		shapeParam.setPercentageW(1f);
		shapeParam.setEnabledPercentageSizeH(true);
		shapeParam.setPercentageH(1f);
		shapeParam.setEnabledPercentagePositionX(true);
		shapeParam.setPercentageX(0.5f);
		shapeParam.setEnabledPercentagePositionY(true);
		shapeParam.setPercentageY(0.5f);
		circleShape4.setShapeParam(shapeParam);
		
		shapeLayer4.setShape(circleShape4);
		shapeLayer4.shapeFitToSize();
		shapeLayer4.setBackgroundColor(Color.YELLOW);
		shapeLayer4.setWidth(100);
		shapeLayer4.setHeight(100);
		addChild(shapeLayer4);
		
		ShapeLayer shapeLayer5 = new ShapeLayer();
		shapeLayer5.setPosition(550, 1000);
		PolygonShape polygonShape = new ShapeLayer.PolygonShape();
		polygonShape.setCenter(50, 50);
		Path path  = new Path();
		path.moveTo(20, 20);
		path.lineTo(80, 40);
		path.lineTo(120, 100);
		path.lineTo(10, 80);
		path.close();
		polygonShape.setPath(path);
		polygonShape.getPaint().setColor(Color.RED);
		polygonShape.getPaint().setStyle(Style.FILL);
		shapeParam = new ShapeParam();
		shapeParam.setEnabledPercentageSizeW(true);
		shapeParam.setPercentageW(1f);
		shapeParam.setEnabledPercentageSizeH(true);
		shapeParam.setPercentageH(1f);
		shapeParam.setEnabledPercentagePositionX(true);
		shapeParam.setPercentageX(0.5f);
		shapeParam.setEnabledPercentagePositionY(true);
		shapeParam.setPercentageY(0.5f);
		polygonShape.setShapeParam(shapeParam);
		shapeLayer5.setShape(polygonShape);
		shapeLayer5.shapeFitToSize();
		shapeLayer5.setBackgroundColor(Color.YELLOW);
		shapeLayer5.setWidth(200);
		shapeLayer5.setHeight(150);
		addChild(shapeLayer5);
		
		Layer layer2 = new Layer();
		
		TabbarLayer tabbarLayer = new TabbarLayer();
		tabbarLayer.setSize(150, 50);
		tabbarLayer.setPosition(70, 670);
//		tabbarLayer.setTabs(new ButtonLayer());
		addChild(tabbarLayer);
//		tabbarLayer.setTabs(new ButtonLayer());
		
		tabViewLayer = new TabViewLayer();
		tabViewLayer.setWidth(200);
		tabViewLayer.setHeight(600);
		tabViewLayer.setPosition(0, 650);
//		tabViewLayer.setAutoAdd(true);
//		tabViewLayer.addp
		
		CalanderLayer calanderLayer = new CalanderLayer();
		VideoLayer videoLayer = new VideoLayer();
		
		ControllerBarLayer controllerBarLayer = new ControllerBarLayer();
		controllerBarLayer.setSize(100, 50);
		controllerBarLayer.setPosition(50, 800);
		addChild(controllerBarLayer);
		
	    
		GameLevelLayer gameLevelLayer = new GameLevelLayer();
		
		WebViewLayer webViewLayer = new WebViewLayer();
		
		NumberPickerLayer numberPickerLayer = new NumberPickerLayer();
//		numberPickerLayer.setSize(50, 30);
		numberPickerLayer.setPosition(50, 600);
		numberPickerLayer.setType(com.example.ggamedirdev.listview.NumberPickerLayer.Type.NoneSquencial);
		numberPickerLayer.setPickerRange(new int[]{1,2,3,4,5,6,7,8,9,10,11,12});
		numberPickerLayer.setBackgroundColor(Color.MAGENTA);
		addChild(numberPickerLayer);
		
		NumberPickerLayer dayPickerLayer = new NumberPickerLayer();
		dayPickerLayer.setPosition(100, 600);
		dayPickerLayer.setType(com.example.ggamedirdev.listview.NumberPickerLayer.Type.Squencial);
		dayPickerLayer.setPickerRange(1, 31);
		dayPickerLayer.setBackgroundColor(Color.MAGENTA);
		addChild(dayPickerLayer);
		
		CollectionLayer collectionLayer = new CollectionLayer();
		collectionLayer.addScrollFlag(ScrollViewLayer.SCROLL_LIMIT_FOR_CAN_SCOLL_WHEN_CONTENTS_HEIGHT_LESS_THAN_VIEW_HEIGHT);
//		listViewLayer.addListViewFlag(ListViewLayer.SCROLL_LIMIT_FOR_CAN_SCOLL_DOWN_WHEN_FIRST_ITEM_IN_THE_TOP);
//		listViewLayer.addListViewFlag(ListViewLayer.SCROLL_LIMIT_FOR_CAN_SCOLL_UP_WHEN_LAST_ITEM_IN_THE_BOTTOM);
		collectionLayer.setWidth(100);
//		listViewLayer.setHeight(850);
		collectionLayer.setHeight(500);
		collectionLayer.setAutoAdd(true);
		collectionLayer.setBackgroundColor(Color.CYAN);
		collectionLayer.setPosition(280, 70);
		
		layers = new ArrayList<ButtonLayer>();
//		itemLayers = layers;
		layers.add(new ButtonLayer("1", 100, (int) collectionLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("2", 100, (int) collectionLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("3", 100, (int) collectionLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("4", 100, (int) collectionLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("5", 100, (int) collectionLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("6", 100, (int) collectionLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("7", 100, (int) collectionLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("8", 100, (int) collectionLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("9", 100, (int) collectionLayer.getItemContentLayerHeight(), false));
		layers.add(new ButtonLayer("100", 100, (int) collectionLayer.getItemContentLayerHeight(), false));
		

		y = 0;
		for(ButtonLayer itemlayer : layers){
			itemlayer.setY(y);
			itemlayer.setBackgroundColor(Color.RED);
			itemlayer.setTextColor(Color.WHITE);
			itemlayer.setButtonColors(Color.RED, Color.BLUE, Color.YELLOW);
//			addChild(layer);
//			layer.setIsClipOutside(true);
//			y += itemHeight;
			itemlayer.setOnClickListener(new ButtonLayer.OnClickListener() {
				
				@Override
				public void onClick(ButtonLayer buttonLayer) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		collectionLayer.setItems(layers);
		
		
		
		final ProgressLayer progressLayer = new ProgressLayer();
		progressLayer.setSize(100, 100);
		progressLayer.setPosition(500, 900);
		addChild(progressLayer);
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (progressLayer.getCurrentProgress() < 100) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					progressLayer.setCurrentProgress(progressLayer.getCurrentProgress()+1);
				}
				
			}
		});
		
		thread.start();
		
		final ProgressLayer progressLayerLine = new ProgressLayer();
		progressLayerLine.setSize(100, 100);
		progressLayerLine.setPosition(500, 1000);
		addChild(progressLayerLine);
		
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (progressLayerLine.getCurrentProgress() < 100) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					progressLayerLine.setCurrentProgress(progressLayerLine.getCurrentProgress()+1);
				}
				
			}
		});
		
		thread.start();
		
		
	}

	GameView gameview;
	
	@Override
	public GameView initGameView(Activity activity, IGameController gameController,
			IGameModel gameModel) {
		// TODO Auto-generated method stub
		class MyGameView extends GameView{
			public MyGameView(Context context, IGameController gameController,
					IGameModel gameModel) {
				super(context, gameController, gameModel);
				// TODO Auto-generated constructor stub
//				input=(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			}			
			
		    @Override 
		    public InputConnection onCreateInputConnection(EditorInfo outAttrs) { 
		        // TODO Auto-generated method stub 
		        return myInputConnection;//super.onCreateInputConnection(outAttrs); 
		    }
		}		
		
		gameview = new MyGameView(activity, gameController, gameModel);
		
		EditTextLayer editTextLayer = new EditTextLayer(gameview);
		editTextLayer.setPosition(200, 800);
		editTextLayer.setSize(250, 100);
		editTextLayer.setBackgroundColor(Color.RED);
		
		addChild(editTextLayer);
		
		myInputConnection = editTextLayer.getMyInputConnection();
		
//		editTextLayer.setOnLayerClickListener(new OnLayerClickListener() {
//			
//			@Override
//			public void onClick(ILayer layer) {
//				input.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//			}
//		});
		
		return gameview;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		listViewLayer.frameTrig();
		scrollViewLayer.frameTrig();
		viewPagerLayer.frameTrig();
		tabViewLayer.frameTrig();
		spinnerLayer.frameTrig();
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
//		LayerManager.getInstance().drawLayers(canvas, null);
//		LayerManager.getInstance().drawSceneLayers(canvas, null, sceneLayerLevel);
		
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
		
//		listViewLayer.drawSelf(canvas, null);
		
//		selectViewLayer.drawSelf(canvas, null);
		
//		scaleGuestureViewLayer.drawSelf(canvas, null);
		
//		scrollViewLayer.drawSelf(canvas, null);
		
//		checkboxLayer.drawSelf(canvas, null);
		
//		tabViewLayer.drawSelf(canvas, null);
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
		/*
		Camera camera = new Camera(0, 0, gameview.getWidth()+500, gameview.getHeight());
		setCamera(camera);
//		getCamera().translate(1500, 1500);
		getCamera().translate(540, 0);
		getCamera().zoom(-1.0f);
		getCamera().rotation(45); //viewport, touchrange, spinner,edittext,save    
		getCamera().setViewPort(0, 0, gameview.getWidth()/2, gameview.getHeight()/2);
		getCamera().getViewPortRectF();
		
		getCamera().applyCameraSpaceToViewPort();
		*/
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
