package com.example.ggamedirdev;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.ImageView;

import com.example.ggamedirdev.achievement.AchievementItem;
import com.example.ggamedirdev.listview.ListViewLayer;
import com.example.ggamedirdev.listview.ListViewLayer.IndexPath;
import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.GameView;
import com.example.try_gameengine.framework.IGameController;
import com.example.try_gameengine.framework.IGameModel;
import com.example.try_gameengine.framework.LabelLayer;
import com.example.try_gameengine.framework.Layer;
import com.example.try_gameengine.framework.LayerManager;
import com.example.try_gameengine.scene.EasyScene;
import com.example.try_gameengine.utils.GameTimeUtil;

public class AchievementScene extends EasyScene{
	private int gameTime;
	
	private GameTimeUtil gameTimeUtil;
	
	private Custom4D2FRemoteContollerListener custom4d2fRemoteContollerListener = new Custom4D2FRemoteContollerListener();
	
	ListViewLayer listViewLayer;
	List<AchievementItem> achievementItems = new ArrayList<AchievementItem>();
	
	class AchievementItemLayer extends Layer{
		
		public AchievementItemLayer() {
			// TODO Auto-generated constructor stub
			
			setPosition(0, 0);
			setSize(CommonUtil.screenWidth, 150);
		}
		
		private void setUI(){
			Layer imageLayer = new Layer();
			imageLayer.setSize(100, 100);
			imageLayer.setPosition(20, this.getHeight()/2);
			imageLayer.setAnchorPoint(0, 0.5f);
			
//			LabelLayer titleLabel = new LabelLayer(drawOffsetX, sceneLayerLevel, autoAdd)
			
			addChild(imageLayer);
			
		}
	}
	
	public AchievementScene(final Context context, String id, int level, int mode) {
		super(context, id, level, mode);
		
		class MyListViewLayerListener extends ListViewLayer.DefaultListViewLayerListener{
			public MyListViewLayerListener() {
				// TODO Auto-generated constructor stub
				listViewLayer.super();
			}

			@Override
			public int numberOfItemsInSection(int section) {
				// TODO Auto-generated method stub
				return achievementItems.size();
			}
			
			@Override
			public int numberOfSections() {
				return 1;
			}
			
			@Override
			public ALayer itemForPositionAtIndexPath(ALayer cell, IndexPath indexPath) {
				AchievementLayer layer = null;
				if(cell==null){
					layer = new AchievementLayer(200, heightForItemAtIndexPath(indexPath), false);
					cell = layer;
				}
				
//				cell.
				AchievementItem item = achievementItems.get(indexPath.getPosition());
//				cell.setBitmap();
				layer.setTitle(indexPath.getPosition()+"");
				layer.setInfo("QQ");
				
				return layer;
			}
			
			@Override
			public ALayer sectionForPostionAtIndexPath(ALayer sectionContentLayer, IndexPath indexPath) {
				return null;
			}
		}
		
		ListViewLayer listViewLayer = new ListViewLayer();
		listViewLayer.setWidth(CommonUtil.screenWidth);
		listViewLayer.setListViewLayerListener(new MyListViewLayerListener());
		
		addChild(listViewLayer);
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
			}			
		}		
		return gameview = new MyGameView(activity, gameController, gameModel);
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		tickTime();
	}
	
	private void tickTime(){
		if(gameTimeUtil.isArriveExecuteTime()){
			gameTime++;
		}
	}
	
	boolean isMoveing = false;

	@Override
	public void doDraw(Canvas canvas) {
		// TODO Auto-generated method stub
//		sprite.drawSelf(canvas, null);
//		LayerManager.getInstance().drawLayers(canvas, null);
		LayerManager.getInstance().drawSceneLayers(canvas, null, sceneLayerLevel);
		
		Paint paint = new Paint();
		paint.setTextSize(50);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		canvas.drawText(gameTime+"", 100, 100, paint);
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
		return super.onTouchEvent(event);
	}
	
	@Override
	public void beforeGameStart() {
		// TODO Auto-generated method stub
		gameTimeUtil = new GameTimeUtil(1000);
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

class AchievementLayer extends Layer{
	private String title;
	private String info;
	
	public AchievementLayer(int w, int h, boolean isAuto) {
		// TODO Auto-generated constructor stub
		super(w, h, isAuto);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
