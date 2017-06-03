package com.example.ggamedirdev;

import android.content.ContentValues;

import com.example.try_gameengine.center_notification.NSANotification;
import com.example.try_gameengine.center_notification.NSANotificationCenter;

public class Score implements IAchieveable{
	int score;
	
	@Override
	public void send() {
		// TODO Auto-generated method stub
		NSANotification nsaNotification = new NSANotification();
		nsaNotification.setName("Score");
		
		ContentValues contentValues = new ContentValues();
//		contentValues.put(keys[0], score);
		contentValues.put("score", score);
		nsaNotification.setUserInfo(contentValues);
		NSANotificationCenter.defaultCenter().postNotification(nsaNotification);
	}
}
