package com.example.ggamedirdev.achievement;

import com.example.try_gameengine.framework.Layer;

public class AchievementItemLayer extends Layer{
	private AchievementItem achievementItem;
	
	public AchievementItemLayer(AchievementItem achievementItem) {
		// TODO Auto-generated constructor stub
		this.achievementItem = achievementItem;
		if(this.achievementItem.isArriveAchievement()){
			setEnable(false);
			setAlpha(100);
		}else{
			setEnable(true);
		}
	}
	
}
