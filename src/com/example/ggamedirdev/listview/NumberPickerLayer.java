package com.example.ggamedirdev.listview;

import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.LabelLayer;
import com.example.try_gameengine.framework.Layer;

public class NumberPickerLayer extends Layer{
	enum Type{
		Squencial,
		NoneSquencial
	}
	
	private Type type;
	private int[] range;
	private int index;
	private int firstNumber;
	private int lastNumber;
	private int currentNumber;
	
	private ButtonLayer increaseButton, decreaseButton;
	private LabelLayer labelLayer;
	
	public NumberPickerLayer() {
		// TODO Auto-generated constructor stub
		setWidth(50);
		setHeight(150);
		increaseButton = new ButtonLayer();
		increaseButton.setWidth(50);
		increaseButton.setHeight(50);
		increaseButton.setPosition(0, 0);
		increaseButton.setOnClickListener(new ButtonLayer.OnClickListener() {
			
			@Override
			public void onClick(ButtonLayer buttonLayer) {
				// TODO Auto-generated method stub
				if(increaseIndex())
					changePickerValue();
			}
		});
		decreaseButton = new ButtonLayer();
		decreaseButton.setWidth(50);
		decreaseButton.setHeight(50);
		decreaseButton.setPosition(0, 100);
		decreaseButton.setOnClickListener(new ButtonLayer.OnClickListener() {
			
			@Override
			public void onClick(ButtonLayer buttonLayer) {
				// TODO Auto-generated method stub
				if(decreaseIndex())
					changePickerValue();
			}
		});
		labelLayer = new LabelLayer(0, 50, false);
		
		this.addChild(labelLayer);
		this.addChild(increaseButton);
		this.addChild(decreaseButton);
	}
	
	public void setPickerRange(int[] range){
		this.range = range;
	}
	
	public void setPickerRange(int firstNumber, int lastNumber){
		this.firstNumber = firstNumber;
		this.lastNumber = lastNumber;
	}
	
	public int getCurrentNumber(){
		return currentNumber;
	}
	
	private boolean increaseIndex(){
		boolean leagal = false;
		if(type==Type.Squencial){
			leagal = index + firstNumber < lastNumber;
		}else if(type==Type.NoneSquencial){
			leagal = index < (range.length - 1);
		}
		
		if(leagal)
			index++;
		
		return leagal;
	}
	
	private boolean decreaseIndex(){
		boolean leagal = false;
		if(type==Type.Squencial){
			leagal = index > firstNumber;
		}else if(type==Type.NoneSquencial){
			leagal = index > 0;
		}
		
		if(leagal)
			index--;
		
		return leagal;
	}
	
	private void changePickerValue(){
		if(type==Type.Squencial){
			currentNumber = (firstNumber+index);
		}else if(type==Type.NoneSquencial){
			currentNumber = range[index];
		}
		
		labelLayer.setText(currentNumber+"");
	}
}
