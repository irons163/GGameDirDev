package com.example.ggamedirdev.listview;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.widget.AdapterView;

import com.example.ggamedirdev.BitmapUtil;
import com.example.ggamedirdev.listview.SpinnerLayer.OnItemSelectedListener;
import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.Sprite;

public class ListViewLayer extends ScrollViewLayer{
	private List<ALayer> sectionContentLayers = new ArrayList<ALayer>();
	private List<ALayer> contentLayers = new ArrayList<ALayer>();
	private List<? extends ALayer> sectionLayers;
	private List<? extends ALayer> itemLayers;
	private List<Integer> nullSectionsPosition = new ArrayList<Integer>();
	private List<Integer> nullItemsPosition = new ArrayList<Integer>();
	private int sectionContentLayerHeight = 60; //default 60
	private int itemContentLayerHeight = 60; //default 60
	private int heightForSectionHeaderSpace = 50; //default 50
	private int heightForSectionFooterSpace = 50; //default 50
	
	interface OnItemSelectedListener{
		public void onItemSelected(ListViewLayer listViewLayer, ALayer seletedLayer, IndexPath indexPath, boolean isItem);
		public void onNothingSelected(ListViewLayer listViewLayer);
	}
	
	private OnItemSelectedListener onItemSelectedListener;
	
	public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener){
		this.onItemSelectedListener = onItemSelectedListener;
	}
	
	public void setListViewLayerListener(ListViewLayerListener listViewLayerListener){
		this.listViewLayerListener = listViewLayerListener;
	}
	
	private ListViewLayerListener listViewLayerListener = new DefaultListViewLayerListener() {
		@Override
		public int numberOfItemsInSection(int section) {
			// TODO Auto-generated method stub
			return contentLayers.size();
		}
	};
	
	public abstract class DefaultListViewLayerListener implements ListViewLayerListener{
		@Override
		public ALayer itemForPositionAtIndexPath(ALayer contentLayer, IndexPath indexPath) {
			return contentLayer;
		}
		@Override
		public ALayer sectionForPostionAtIndexPath(ALayer sectionContentLayer,IndexPath indexPath) {
			return sectionContentLayer;
		}
		
		@Override
		public int numberOfSections(){
			return sectionContentLayers.size();
		}
		@Override
		public int heightForItemAtIndexPath(IndexPath indexPath){
//			return itemForPositionAtIndexPath(getItemAtIndexPath(indexPath), indexPath).getHeight();
			return itemContentLayerHeight;
		}
		@Override
		public int heightForSectionAtIndexPath(IndexPath indexPath){
//			return sectionForPostionAtIndexPath(getSectionAtIndexPath(indexPath), indexPath).getHeight();
			return sectionContentLayerHeight;
		}
		@Override
		public int heightForSectionHeaderSpaceAtIndexPath(IndexPath indexPath){
			return heightForSectionHeaderSpace;
		}
		@Override
		public int heightForSectionFooterSpaceAtIndexPath(IndexPath indexPath){
			return heightForSectionFooterSpace;
		}
		@Override
		public void didSelected(ALayer selectedLayer, IndexPath indexPath, boolean isItem) {
			// TODO Auto-generated method stub
			if(onItemSelectedListener!=null)
				onItemSelectedListener.onItemSelected(ListViewLayer.this, selectedLayer, indexPath, isItem);
		}
		@Override
		public void didUnSelected(ALayer selectedLayer, IndexPath indexPath, boolean isItem) {
			// TODO Auto-generated method stub
			if(onItemSelectedListener!=null)
				onItemSelectedListener.onNothingSelected(ListViewLayer.this);
		}
	}
	
	public void scrollToItemPosition(IndexPath indexPath){
		ALayer layer = listViewLayerListener.itemForPositionAtIndexPath(getItemContentLayerAtIndexPath(indexPath), indexPath);
		float dy = 0 - layer.getTop();
		scroll(dy);
	}
	
	public void scrollToSectionPosition(IndexPath indexPath){
		ALayer layer = listViewLayerListener.sectionForPostionAtIndexPath(getSectionContentLayerAtIndexPath(indexPath), indexPath);
		float dy = 0 - layer.getTop();
		scroll(dy);
	}
	
	public void scroll(float dy){
		for(ALayer layer : sectionContentLayers){
			layer.setY(layer.getY() + dy);
		}
		for(ALayer layer : contentLayers){
			layer.setY(layer.getY() + dy);
		}
	}
	
	public class IndexPath{
		private int section;
		private int position;
		
		public int getSection() {
			return section;
		}
		public int getPosition() {
			return position;
		}
		public void setSection(int section) {
			this.section = section;
		}
		public void setPosition(int position) {
			this.position = position;
		}
	}
	
	public interface OnSelectListener{
		public void didSelected(ALayer selectedLayer, IndexPath indexPath, boolean isItem);
		public void didUnSelected(ALayer selectedLayer, IndexPath indexPath, boolean isItem);
	}
	
	public interface ListViewLayerListener extends OnSelectListener{
		public ALayer itemForPositionAtIndexPath(ALayer contentLayer,
				IndexPath indexPath);

		public ALayer sectionForPostionAtIndexPath(ALayer sectionContentLayer,
				IndexPath indexPath);

		public int numberOfSections();

		public int numberOfItemsInSection(int section);
		
		public int heightForSectionAtIndexPath(IndexPath indexPath);

		public int heightForItemAtIndexPath(IndexPath indexPath);
		
		public int heightForSectionHeaderSpaceAtIndexPath(IndexPath indexPath);
		
		public int heightForSectionFooterSpaceAtIndexPath(IndexPath indexPath);
	}
	
	public ListViewLayer() {
		// TODO Auto-generated constructor stub
		addFlag(TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN);
		
		setIsClipOutside(true);
//		initButtons();
//		initSprites();
//		initClipSprites();
	}
	
	@Override
	protected boolean checkScrollable(MotionEvent e1, MotionEvent e2,
			float distanceX, float distanceY) {
		return listViewLayerListener.numberOfSections()!=0;
	}
	
	@Override
	protected void willStartScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		MotionEvent cancelEvent = MotionEvent.obtain(e2);
		cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
		
		for(ILayer layer : sectionContentLayers){
			layer.onTouchEvent(cancelEvent);
		}
		for(ILayer layer : contentLayers){
			layer.onTouchEvent(cancelEvent);
		}
	}
	
	@Override
	protected void scrollContents(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		for(ILayer layer : sectionContentLayers){
			layer.setY(layer.getY() - distanceY);
		}
		for(ILayer layer : contentLayers){
			layer.setY(layer.getY() - distanceY);
		}
	}

	@Override
	public void refresh() {
		nullSectionsPosition.clear();
		nullItemsPosition.clear();
		
		for(ALayer sectionContentLayer : sectionContentLayers){
			sectionContentLayer.setHidden(true);
		}
		
		for(ALayer contentLayer : contentLayers){
			contentLayer.setHidden(true);
		}
		
		IndexPath indexPath = new IndexPath();
		int numberOfSections = listViewLayerListener.numberOfSections();
		float newY = topY;
		int currentPosition = 0;
		for(int iSec = 0; iSec < numberOfSections; iSec++){
			indexPath.setSection(iSec);
			
			int sectionHeaderHeight = listViewLayerListener.heightForSectionHeaderSpaceAtIndexPath(indexPath);
			newY += sectionHeaderHeight;
			ALayer sectionContentLayer = getSectionContentLayerAtIndexPath(indexPath);
			/*
			sectionContentLayer.setHidden(false);
			ALayer sectionLayer = listViewLayerListener.sectionForPostionAtIndexPath(sectionContentLayer, indexPath);
			
			boolean isSectionLayerNull = false;
			if(sectionLayer!=null){
				int sectionHeight = listViewLayerListener.heightForSectionAtIndexPath(indexPath);
				
				if(sectionLayer.getHeight() != sectionHeight){
					sectionLayer.setHeight(sectionHeight);
				}
				if(sectionLayer.getY() != newY){
					sectionLayer.setY(newY);
				}
				newY += sectionHeight;
			}else{
				isSectionLayerNull = true;
//				nullSectionsPosition.add(currentPosition);
				nullSectionsPosition.add(iSec);
			}
			*/
			
			ALayer sectionLayer = null;
			if(sectionContentLayer==null){
				sectionContentLayer = createSectionAndItemContentLayers((int)sectionContentLayerHeight);
				sectionContentLayer.setHidden(true);
				addChild(sectionContentLayer);
				sectionContentLayers.add(iSec, sectionContentLayer);
				sectionLayer = listViewLayerListener.sectionForPostionAtIndexPath(null, indexPath);
				if(sectionLayer!=null)
					sectionContentLayer.addChild(sectionLayer);
			}else{
				sectionLayer = listViewLayerListener.sectionForPostionAtIndexPath(getSectionAtIndexPath(indexPath), indexPath);
			}
			
			boolean isSectionLayerNull = false;
			/*
			if(sectionLayer!=null){
				sectionContentLayer.setHidden(false);
				
				int sectionHeight = listViewLayerListener.heightForSectionAtIndexPath(indexPath);
				
				if(sectionContentLayer.getHeight() != sectionHeight){
					sectionContentLayer.setHeight(sectionHeight);
				}
				if(sectionContentLayer.getY() != newY){
					sectionContentLayer.setY(newY);
				}
				newY += sectionHeight;
			}else{
				isSectionLayerNull = true;
				nullSectionsPosition.add(iSec);
			}*/
			
			if(sectionLayer!=null){
				
			}else{
//				isSectionLayerNull = true;
				ALayer sectionLayerInSctionContent = getSectionAtIndexPath(indexPath);
				if(sectionLayerInSctionContent!=null)
					sectionLayerInSctionContent.removeFromParent();
			}
			
			sectionContentLayer.setHidden(false);
			
			int sectionHeight = listViewLayerListener.heightForSectionAtIndexPath(indexPath);
			
			if(sectionContentLayer.getHeight() != sectionHeight){
				sectionContentLayer.setHeight(sectionHeight);
			}
			if(sectionContentLayer.getY() != newY){
				sectionContentLayer.setY(newY);
			}
			newY += sectionHeight;
			
			int numberOfItems = listViewLayerListener.numberOfItemsInSection(iSec);
			
//			if(isSectionLayerNull){
//				currentPosition += numberOfItems;
//				continue;
//			}
			
			for(int iItem = 0; iItem < numberOfItems; iItem++){
				indexPath.setPosition(iItem);
				
				ALayer contentLayer = getItemContentLayerAtIndexPath(indexPath);
				/*
				contentLayer.setHidden(false);
				ALayer itemLayer = listViewLayerListener.itemForPositionAtIndexPath(contentLayer, indexPath);
				
				if(itemLayer!=null){
					int itemHeight = listViewLayerListener.heightForItemAtIndexPath(indexPath);
					
					if(itemLayer.getHeight() != itemHeight){
						itemLayer.setHeight(itemHeight);
					}
					if(itemLayer.getY() != newY){
						itemLayer.setY(newY);
					}
					newY += itemHeight;
				}else{
					nullItemsPosition.add(currentPosition);
				}
				*/
				
				ALayer itemLayer = null;
				if(contentLayer==null){
					contentLayer = createSectionAndItemContentLayers((int)itemContentLayerHeight);
					contentLayers.add(iItem, contentLayer);
					contentLayer.setHidden(true);
					addChild(contentLayer);
					itemLayer = listViewLayerListener.itemForPositionAtIndexPath(null, indexPath);
				}else{
					itemLayer = listViewLayerListener.itemForPositionAtIndexPath(getItemAtIndexPath(indexPath), indexPath);;
				}
				
				/*
				if(itemLayer!=null){
					contentLayer.setHidden(false);
					
					int itemHeight = listViewLayerListener.heightForItemAtIndexPath(indexPath);
					
					if(contentLayer.getHeight() != itemHeight){
						contentLayer.setHeight(itemHeight);
					}
					if(contentLayer.getY() != newY){
						contentLayer.setY(newY);
					}
					newY += itemHeight;
				}else{
					nullItemsPosition.add(currentPosition);
				}*/
				
				if(itemLayer!=null){
					if(!contentLayer.getLayers().contains(itemLayer)){
						contentLayer.removeAllChildren();
						contentLayer.addChild(itemLayer);
					}
				}else{
//					isSectionLayerNull = true;
					ALayer itemLayerInItemContent = getItemAtIndexPath(indexPath);
					if(itemLayerInItemContent!=null)
						itemLayerInItemContent.removeFromParent();
				}
				
				contentLayer.setHidden(false);
				
				int itemHeight = listViewLayerListener.heightForItemAtIndexPath(indexPath);
				
				if(contentLayer.getHeight() != itemHeight){
					contentLayer.setHeight(itemHeight);
				}
				if(contentLayer.getY() != newY){
					contentLayer.setY(newY);
				}
				newY += itemHeight;
				
				currentPosition++;
			}
			
			int sectionFooterHeight = listViewLayerListener.heightForSectionFooterSpaceAtIndexPath(indexPath);
			newY += sectionFooterHeight;
			currentPosition++;
		}
		
		bottomY = newY;
	}
	
	private ALayer createSectionAndItemContentLayers(int height){
		ButtonLayer defaultSectionLayer = new ButtonLayer();
		defaultSectionLayer.setHeight(height);
//		defaultSectionLayer.setWidth(width);
		LayerParam layerParam = new LayerParam();
		layerParam.setEnabledPercentageSizeW(true);
		layerParam.setPercentageW(1.0f);
		defaultSectionLayer.setLayerParam(layerParam);
		defaultSectionLayer.setOnClickListener(new ButtonLayer.OnClickListener() {
			
			@Override
			public void onClick(ButtonLayer buttonLayer) {
				// TODO Auto-generated method stub
				ALayer layer = null;
				try {
					layer = (ALayer) buttonLayer.getChildAt(0);
				} catch (IndexOutOfBoundsException e) {
					// TODO: handle exception
				}
				if(checkIsItemContenLayer(buttonLayer))
					listViewLayerListener.didSelected(layer, getItemPosition(buttonLayer), true);
				else
					listViewLayerListener.didSelected(layer, getSctionPosition(buttonLayer), false);
			}
		});
		return defaultSectionLayer;
	}
	
	/**
	 * @param layers 
	 * 			the layers you set to {@code ListViewLayer} as items. They be added to {@code ListViewLayer} as children, so need to use {@link #removeItems()}.
	 */
	public void setItems(List<? extends ALayer> layers){
		removeItems();
		itemLayers = layers;
		
		if(itemLayers==null){
			return;
		}
		
		if(sectionContentLayers.size()==0){
			ALayer sectionContentLayer = createSectionAndItemContentLayers((int)sectionContentLayerHeight);
			addChild(sectionContentLayer);
			sectionContentLayers.add(sectionContentLayer);
		}
		
		int y = 0;
		for(ILayer layer : itemLayers){
			ALayer contentLayer = createSectionAndItemContentLayers((int)itemContentLayerHeight);
			contentLayers.add(contentLayer);
			contentLayer.addChild(layer);
			addChild(contentLayer);
			if(isClipOutside())
				contentLayer.setIsClipOutside(isClipOutside());
			y += (int)itemContentLayerHeight;
		}
		
		invalidate();
	}
	
	public void setSections(List<? extends ALayer> layers){
		removeSections();
		
		sectionLayers = layers;
		
		if(sectionLayers==null){
			return;
		}
		
		int y = 0;
		for(ILayer layer : sectionLayers){
			ALayer sectionContentLayer = createSectionAndItemContentLayers((int)sectionContentLayerHeight);
			sectionContentLayers.add(sectionContentLayer);
			sectionContentLayer.addChild(layer);
			addChild(sectionContentLayer);
			if(isClipOutside())
				sectionContentLayer.setIsClipOutside(isClipOutside());
			y += (int)sectionContentLayerHeight;
		}
		
		invalidate();
	}
	
	/**
	 * Remove items from {@code ListViewLayer}, it make the items do {@link #removeFromParent()};
	 */
	public void removeItems(){
		for(ALayer layer : contentLayers){
			layer.removeFromParent();
		}
		
		contentLayers.clear();
		nullItemsPosition.clear();
		
		if(itemLayers==null){
			invalidate();
			return;
		}
		
		for(ILayer layer : itemLayers){
			layer.removeFromParent();
		}
		
		itemLayers.clear();
		
		invalidate();
	}
	
	public void removeSections(){
		for(ALayer layer : sectionContentLayers){
			layer.removeFromParent();
		}
		
		sectionContentLayers.clear();
		nullSectionsPosition.clear();
		
		if(sectionLayers==null){
			invalidate();
			return;
		}
		
		for(ILayer layer : sectionLayers){
			layer.removeFromParent();
		}
		
		sectionLayers.clear();
		
		invalidate();
	}
	
	public void setSectionContentLayerHeight(int sectionContentLayerHeight){
		this.sectionContentLayerHeight = sectionContentLayerHeight;
	}
	
	public void setItemContentLayerHeight(int itemContentLayerHeight){
		this.itemContentLayerHeight = itemContentLayerHeight;
	}
	
	public void setHeightForSectionHeaderSpace(int heightForSectionHeaderSpace){
		this.heightForSectionHeaderSpace = heightForSectionHeaderSpace;
	}
	
	public void setHeightForSectionFooterSpace(int heightForSectionFooterSpace){
		this.heightForSectionFooterSpace = heightForSectionFooterSpace;
	}
	
	public int getSectionContentLayerHeight(){
		return this.sectionContentLayerHeight;
	}
	
	public int getItemContentLayerHeight(){
		return this.itemContentLayerHeight;
	}
	
	public int getHeightForSectionHeaderSpace(){
		return this.heightForSectionHeaderSpace;
	}
	
	public int getHeightForSectionFooterSpace(){
		return this.heightForSectionFooterSpace;
	}
	
	public int getSectionContentLayerHeight(IndexPath indexPath){
		return listViewLayerListener.heightForSectionAtIndexPath(indexPath);
	}
	
	public int getItemContentLayerHeight(IndexPath indexPath){
		return listViewLayerListener.heightForItemAtIndexPath(indexPath);
	}
	
	public ALayer getItemContentLayerAtIndexPath(IndexPath indexPath){
//		return contentLayers.get((indexPath.getSection()+1) * indexPath.getPosition());
		int index = (indexPath.getSection()+1) * indexPath.getPosition();
		if(index >=0 && index < contentLayers.size())
			return contentLayers.get(index);
		else
			return null;
	}
	
	public ALayer getSectionContentLayerAtIndexPath(IndexPath indexPath){
//		return sectionContentLayers.get(indexPath.getSection());
		int index = indexPath.getSection();
		if(index >=0 && index < sectionContentLayers.size())
			return sectionContentLayers.get(index);
		else
			return null;
	}
	
	public ALayer getItemAtIndexPath(IndexPath indexPath){
//		return (ALayer) contentLayers.get((indexPath.getSection()+1) * indexPath.getPosition()).getChildAt(0);
		try {
			return (ALayer) contentLayers.get((indexPath.getSection()+1) * indexPath.getPosition()).getChildAt(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public ALayer getSectionAtIndexPath(IndexPath indexPath){
//		return (ALayer) sectionContentLayers.get(indexPath.getSection()).getChildAt(0);
		try {
			return (ALayer) sectionContentLayers.get(indexPath.getSection()).getChildAt(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	@Override
	public void setWidth(int w) {
		// TODO Auto-generated method stub
		super.setWidth(w);
		for(ILayer layer : contentLayers){
			layer.setWidth(getWidth());
		}
	}
	
	@Override
	public void setIsClipOutside(boolean isClipOutside) {
		// TODO Auto-generated method stub
		super.setIsClipOutside(isClipOutside);
		for(ALayer layer : contentLayers){
			layer.setIsClipOutside(isClipOutside());
		}
	}
	
	public int getSectionsCount(){
		for(ALayer layer : sectionContentLayers){
			
		}
		return itemLayers.size();
	}
	
//	private int getItemsCount(){
//		
//	}
	
	public boolean checkIsItemContenLayer(ALayer layer){
		return contentLayers.indexOf(layer)!=-1;
	}
	
	public IndexPath getSctionPosition(ALayer sectionContentLayer){
		IndexPath indexPath = null;
		int position = sectionContentLayers.indexOf(sectionContentLayer);
		int sectionNum = listViewLayerListener.numberOfSections();
		for(int iSec = 0; iSec < sectionNum; iSec++){
			
			if(nullSectionsPosition.contains(iSec)){
				continue;
			}
			
			if(position == iSec){
				indexPath = new IndexPath();
				indexPath.setSection(iSec);
				indexPath.setPosition(0);
				break;
			}
		}
		return indexPath;
	}
	
	public IndexPath getItemPosition(ALayer contentLayer){
		IndexPath indexPath = null;
		int position = contentLayers.indexOf(contentLayer);
		int sectionNum = listViewLayerListener.numberOfSections();
		int currentPosition = 0;
		for(int iSec = 0; iSec < sectionNum; iSec++){
			int numberOfItemsInSection = listViewLayerListener.numberOfItemsInSection(iSec);
			
			if(nullSectionsPosition.contains(iSec)){
				currentPosition += numberOfItemsInSection;
				continue;
			}
			
			
			for(int iItem = 0; iItem < numberOfItemsInSection; iItem++){
//				if(nullItemsPosition.contains(iItem)){
				if(nullItemsPosition.contains(currentPosition)){
					currentPosition++;
					continue;
				}
				
				if(position == currentPosition){
					indexPath = new IndexPath();
					indexPath.setSection(iSec);
					indexPath.setPosition(position);
					break;
				}
				
				currentPosition++;
			}
			
			currentPosition++;
		}
		return indexPath;
	}
	
//	private int numberOfSections
	
	private void initClipSprites(){
		List<Sprite> layers = new ArrayList<Sprite>();
//		itemLayers = layers;
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.hamster, 100, (int) itemContentLayerHeight, false));
		
		setIsClipOutside(true);
		
		int y = 0;
		for(Sprite layer : layers){ 
//			need work:this issue, camera, view port, canvas concat matrix, spinner view
			layer.setBitmapAndFrameColAndRowNumAndAutoWH(layer.getBitmap(), 7, 2);
			layer.setY(y);
//			layer.setX(getWidth()/2);
			layer.setAnchorPoint(-0.35f, -0.15f);
//			layer.setAnchorPoint(0.5f, 0.5f);
//			layer.setXscale(1.5f);
//			layer.setYscale(1.5f);
			layer.setXscale(0.5f);
			layer.setYscale(0.5f);
			layer.setRotation(45);
			layer.setBackgroundColor(Color.RED);
//			addChild(layer);
//			y += itemHeight;
			layer.setOnLayerClickListener(new OnLayerClickListener() {
				
				@Override
				public void onClick(ILayer layer) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		setItems(layers);
	}
	
	private void initSprites(){
		List<Sprite> layers = new ArrayList<Sprite>();
		itemLayers = layers;
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemContentLayerHeight, false));
		layers.add(new Sprite(BitmapUtil.icon, 100, (int) itemContentLayerHeight, false));
		
		setIsClipOutside(true);
		
		int y = 0;
		for(Sprite layer : layers){			
			layer.setY(y);
			layer.setAnchorPoint(-0.65f, -0.15f);
			layer.setXscale(2.0f);
			layer.setYscale(2.0f);
			layer.setRotation(45);
			layer.setBackgroundColor(Color.RED);
//			layer.setButtonColors(Color.RED, Color.BLUE, Color.YELLOW);
			addChild(layer);
//			layer.setIsClipOutside(true);
			y += itemContentLayerHeight;
			layer.setOnLayerClickListener(new OnLayerClickListener() {
				
				@Override
				public void onClick(ILayer layer) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	
//	private void initButtons(){
//		List<ButtonLayer> layers = new ArrayList<ButtonLayer>();
////		itemLayers = layers;
//		layers.add(new ButtonLayer("1", 100, (int) itemContentLayerHeight, false));
//		layers.add(new ButtonLayer("2", 100, (int) itemContentLayerHeight, false));
//		layers.add(new ButtonLayer("3", 100, (int) itemContentLayerHeight, false));
//		layers.add(new ButtonLayer("4", 100, (int) itemContentLayerHeight, false));
//		layers.add(new ButtonLayer("5", 100, (int) itemContentLayerHeight, false));
//		layers.add(new ButtonLayer("6", 100, (int) itemContentLayerHeight, false));
//		layers.add(new ButtonLayer("7", 100, (int) itemContentLayerHeight, false));
//		layers.add(new ButtonLayer("8", 100, (int) itemContentLayerHeight, false));
//		layers.add(new ButtonLayer("9", 100, (int) itemContentLayerHeight, false));
//		layers.add(new ButtonLayer("100", 100, (int) itemContentLayerHeight, false));
//		
//		setIsClipOutside(true);
//		
//		int y = 0;
//		for(ButtonLayer layer : layers){
//			layer.setY(y);
//			layer.setBackgroundColor(Color.RED);
//			layer.setTextColor(Color.WHITE);
//			layer.setButtonColors(Color.RED, Color.BLUE, Color.YELLOW);
////			addChild(layer);
////			layer.setIsClipOutside(true);
////			y += itemHeight;
//			layer.setOnClickListener(new ButtonLayer.OnClickListener() {
//				
//				@Override
//				public void onClick(ButtonLayer buttonLayer) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
//		}
//		
//		setItems(layers);
//	}
	
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
	}
	

	
	@Override
	public void setEnableMultiTouch(boolean isEnableMultiTouch) {
		// TODO Auto-generated method stub
		super.setEnableMultiTouch(isEnableMultiTouch);
		if(gestureDetector!=null)
			gestureDetector.setEnableMultiTouch(isEnableMultiTouch);
	}

}
