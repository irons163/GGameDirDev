package com.example.ggamedirdev;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.try_gameengine.framework.Config;
import com.example.try_gameengine.framework.LayerManager;
import com.example.try_gameengine.framework.Config.DestanceType;
import com.example.try_gameengine.scene.Scene;
import com.example.try_gameengine.scene.SceneManager;
import com.example.try_gameengine.stage.Stage;
import com.example.try_gameengine.stage.StageManager;


public class GameActivity extends Stage{
	SceneManager sceneManager;
	
	private String[] strs = new String[]{
			 "DetectArea", "MultiDetectArea", "SpriteDetectArea"
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strs));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = null;
				
				switch (position) {
				case 0:
					sceneManager.startScene(0);
					break;
				case 1:
					sceneManager.startScene(1);
					break;
				case 2:
//					intent = new Intent(GameActivity.this, org.ggamedirdemo.stage.MainActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
				
				
			}
		});
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		CommonUtil.screenHeight = dm.heightPixels;
		CommonUtil.screenWidth = dm.widthPixels;
		CommonUtil.statusBarHeight = CommonUtil.getStatusBarHeight(this);
		CommonUtil.screenHeight -= CommonUtil.statusBarHeight;
		
		Config.enableFPSInterval = true;
		Config.fps = 40;
		Config.showFPS = true;
		Config.destanceType = DestanceType.ScreenPersent;
		Config.currentScreenWidth = CommonUtil.screenWidth;
		Config.currentScreenHeight = CommonUtil.screenHeight;
		
		BitmapUtil.initBitmap(this);
		
		StageManager.init(this);
		initStage();
	}
	
	@Override
	public SceneManager initSceneManager() {
		// TODO Auto-generated method stub
		if(CommonUtil.screenHeight<=0){
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			
			CommonUtil.screenHeight = dm.heightPixels;
			CommonUtil.screenWidth = dm.widthPixels;
			CommonUtil.statusBarHeight = CommonUtil.getStatusBarHeight(this);
			CommonUtil.screenHeight -= CommonUtil.statusBarHeight;
		}
		BitmapUtil.initBitmap(this);
	
		LayerManager.setLayerBySenceIndex(0);
		Scene scene = new MyScene(this, "a", 1, Scene.RESUME);
//		LayerManager.setLayerBySenceIndex(1);
//		Scene scene2 = new MultiDetectAreaScene(this, "b", 2, Scene.RESTART);
		
		sceneManager = new SceneManager();
		sceneManager.addScene(scene);
//		sceneManager.addScene(scene2);
		
//		sceneManager.startScene(0);
		
		return sceneManager;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
		if(!sceneManager.previous())
			super.onBackPressed();
//		sceneManager.previousAndLeaveWhenNoPrevious();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}
}
