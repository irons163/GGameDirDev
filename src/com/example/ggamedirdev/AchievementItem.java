package com.example.ggamedirdev;

import android.content.ContentValues;

import com.example.try_gameengine.center_notification.NSANotifiable;
import com.example.try_gameengine.center_notification.NSANotification;
import com.example.try_gameengine.center_notification.NSANotificationCenter;

public class AchievementItem implements NSANotifiable{
	public int condition = 100;
	private int currentScore;
	private boolean isArriveAchievement;
	
	public AchievementItem() {
		// TODO Auto-generated constructor stub
		NSANotificationCenter.defaultCenter().addObserver(this, AchievementSystem.KEY_SCORE_ACHIEVEMENT, null);
		
//		NSANotification nsaNotification = new NSANotification();
//		nsaNotification.setName("");
//		
//		NSANotificationCenter.defaultCenter().postNotification(nsaNotification);
	}
	
	public void getCondition(){
		
	}
	
	public void checkArriveAchievement(){
		if(currentScore >= condition)
			isArriveAchievement = true;
	}
	
	public boolean isArriveAchievement(){
		return isArriveAchievement;
	}

	@Override
	public void receiveNotification(NSANotification nsaNotification) {
		// TODO Auto-generated method stub
		String name = nsaNotification.getName();
		if(name.equals("KEY_SCORE_ACHIEVEMENT")){
			ContentValues c = nsaNotification.getUserInfo();
			currentScore = c.getAsInteger("score");
			
		}
		
		checkArriveAchievement();
	}
}
