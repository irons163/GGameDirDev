package com.example.ggamedirdev;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import android.util.Log;

import com.example.try_gameengine.remotecontroller.custome.Custom4D2FCommandType;
import com.example.try_gameengine.remotecontroller.custome.Custom4D2FRemoteController;

public class Custom4D2FRemoteContollerListener implements Custom4D2FRemoteController.RemoteContollerListener{
	private LinkedHashSet<Integer> keySequence	= new LinkedHashSet<Integer>();
	public static final int NONE = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int DOWN = 4;
	private int move = NONE;
	boolean isPressLeftMoveBtn, isPressRightMoveBtn, isPressUpMoveBtn, isPressDownMoveBtn;
	private ArrayList<Custom4D2FCommandType> currentCommandType = new ArrayList<Custom4D2FCommandType>();
	
	private Custom4D2FRemoteController.RemoteContollerListener custom4d2fRemoteContollerListener = new Custom4D2FRemoteController.RemoteContollerListener() {
		
		@Override
		public void pressDown(List<Custom4D2FCommandType> commandTypes) {
			// TODO Auto-generated method stub
			
		}
	}; 
	
	public void setCustom4D2FRemoteContollerListener(Custom4D2FRemoteController.RemoteContollerListener custom4d2fRemoteContollerListener){
		this.custom4d2fRemoteContollerListener = custom4d2fRemoteContollerListener;
	}
	
	public Custom4D2FRemoteController.RemoteContollerListener getCustom4D2FRemoteContollerListener(){
		return custom4d2fRemoteContollerListener;
	}
	
	private int getLastMoveAfterRemoveMove(int curentMove){
		int lastMove = NONE;
		int tmpMove = NONE;
		Iterator<Integer> keySequenceIterator = keySequence.iterator();
		
		while(keySequenceIterator.hasNext()){
			tmpMove = keySequenceIterator.next();
			if(tmpMove==curentMove)
				keySequenceIterator.remove();	
		}
		
		keySequenceIterator = keySequence.iterator();
		while(keySequenceIterator.hasNext()){
			lastMove = keySequenceIterator.next();	
		}
		
		Log.e("lastMove", lastMove+"");
		
		return lastMove;
	}
	
	public int getCurrentMove(){
		return move;
	}
	
	@Override
	public void pressDown(List<Custom4D2FCommandType> commandTypes) {
		// TODO Auto-generated method stub
		for(Custom4D2FCommandType commandType : commandTypes){
			switch (commandType) {
			case RightKeyUpCommand:
				isPressRightMoveBtn = false;
				move = getLastMoveAfterRemoveMove(RIGHT);
				
				if(!isPressLeftMoveBtn && !isPressRightMoveBtn && !isPressUpMoveBtn && !isPressDownMoveBtn){
					currentCommandType.clear();
					currentCommandType.add(commandType);
					custom4d2fRemoteContollerListener.pressDown(currentCommandType);
				}
				break;
			case RightKeyDownCommand:
				isPressRightMoveBtn = true;
				move = RIGHT;	
				keySequence.remove(move);
				keySequence.add(move);
				break;
			case LeftKeyDownCommand:
				isPressLeftMoveBtn = true;
				move = LEFT;
				keySequence.remove(move);
				keySequence.add(move);
				break;
			case LeftKeyUpCommand:
				isPressLeftMoveBtn = false;
				move = getLastMoveAfterRemoveMove(LEFT);
				
				if(!isPressLeftMoveBtn && !isPressRightMoveBtn && !isPressUpMoveBtn && !isPressDownMoveBtn){
					currentCommandType.clear();
					currentCommandType.add(commandType);
					custom4d2fRemoteContollerListener.pressDown(currentCommandType);
				}
				break;
			case UPKeyDownCommand:
				isPressUpMoveBtn = true;
				move = UP;
				keySequence.remove(move);
				keySequence.add(move);
				break;
			case UPKeyUpCommand:
				isPressUpMoveBtn = false;
				move = getLastMoveAfterRemoveMove(UP);
				
				if(!isPressLeftMoveBtn && !isPressRightMoveBtn && !isPressUpMoveBtn && !isPressDownMoveBtn){
					currentCommandType.clear();
					currentCommandType.add(commandType);
					custom4d2fRemoteContollerListener.pressDown(currentCommandType);
				}
				break;
			case DownKeyDownCommand:
				isPressDownMoveBtn = true;
				move = DOWN;
				keySequence.remove(move);
				keySequence.add(move);
				break;
			case DownKeyUpCommand:
				isPressDownMoveBtn = false;
				move = getLastMoveAfterRemoveMove(DOWN);
				
				if(!isPressLeftMoveBtn && !isPressRightMoveBtn && !isPressUpMoveBtn && !isPressDownMoveBtn){
					currentCommandType.clear();
					currentCommandType.add(commandType);
					custom4d2fRemoteContollerListener.pressDown(currentCommandType);
				}
				break;
			default:
				break;
			}
		}
	}
}
