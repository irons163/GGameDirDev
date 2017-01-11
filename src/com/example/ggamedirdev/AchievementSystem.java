package com.example.ggamedirdev;

import android.content.ContentValues;

import com.example.try_gameengine.center_notification.NSANotifiable;
import com.example.try_gameengine.center_notification.NSANotification;
import com.example.try_gameengine.center_notification.NSANotificationCenter;

public class AchievementSystem implements NSANotifiable{
	public final static String KEY_SCORE_ACHIEVEMENT = "ScoreAchievement";
	public final static String KEY_DISTANCE_ACHIEVEMENT = "DistanceAchievement";
	
	public AchievementSystem() {
		// TODO Auto-generated constructor stub
		NSANotificationCenter.defaultCenter().addObserver(this, "AchievementSystem", null);
	}

	@Override
	public void receiveNotification(NSANotification nsaNotification) {
		// TODO Auto-generated method stub
		
	}
	
	
}
