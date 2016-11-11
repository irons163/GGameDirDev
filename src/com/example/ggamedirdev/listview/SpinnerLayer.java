package com.example.ggamedirdev.listview;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.LiveFolders;
import android.support.v7.internal.view.menu.ListMenuItemView;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.ggamedirdev.BitmapUtil;
import com.example.ggamedirdev.listview.ListViewLayer.IndexPath;
import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ButtonLayer;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.LabelLayer;
import com.example.try_gameengine.framework.Layer;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.framework.ButtonLayer.OnClickListener;

public class SpinnerLayer extends ButtonLayer{
	private List<? extends ILayer> mlayers;
//	private float itemHeight;
	private boolean isChecked;
	ListViewLayer listViewLayer;
	private LayerAdapter adapter;
	
	public interface OnCheckStatusChangedListener{
		public void onChanged(ButtonLayer buttonLayer, boolean isChecked);
	}
	
	private OnCheckStatusChangedListener onCheckStatusChangedListener = new OnCheckStatusChangedListener() {
		
		@Override
		public void onChanged(ButtonLayer buttonLayer, boolean isChecked) {
			// TODO Auto-generated method stub
			
		}
	};	
	
	private String strings = new String();
	
	public SpinnerLayer() {super(0, 0, false);
		// TODO Auto-generated constructor stub
//		setPosition(70, 70);
//		setBackgroundColor(Color.YELLOW);
		
//		itemHeight = 100;
		
//		initButtons();
//		initSprites();
//		initClipSprites();
		
		setOnLayerClickListener(new ALayer.OnLayerClickListener() {
			
			@Override
			public void onClick(ILayer layer) {
				// TODO Auto-generated method stub
				setBackgroundColor(Color.CYAN);
			}
		});
		
		final Layer layer = new Layer();
		
		ButtonLayer buttonLayer = new ButtonLayer("XX", 100, 50, false);
		buttonLayer.setOnClickListener(new ButtonLayer.OnClickListener() {
			
			@Override
			public void onClick(ButtonLayer buttonLayer) {
				// TODO Auto-generated method stub
				layer.setBackgroundColor(Color.MAGENTA);
			}
		});
		
		ButtonLayer layer1 = new ButtonLayer();
		ButtonLayer layer2 = new ButtonLayer();
		ButtonLayer layer3 = new ButtonLayer();
		
		layer1.setText("1");
		layer2.setText("2");
		layer3.setText("3");
		
		this.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonLayer buttonLayer) {
				// TODO Auto-generated method stub
				if(adapter!=null){
					listViewLayer = new ListViewLayer();
					listViewLayer.setY(SpinnerLayer.this.getHeight());
					listViewLayer.setWidth(getWidth());
					
					
					listViewLayer.setBackgroundColor(Color.CYAN);
					
					class MyListViewLayerListene extends ListViewLayer.DefaultListViewLayerListener{
						public MyListViewLayerListene() {
							// TODO Auto-generated constructor stub
							listViewLayer.super();
						}

						@Override
						public int numberOfItemsInSection(int section) {
							// TODO Auto-generated method stub
							return adapter.getCount();
						}
						
						@Override
						public int numberOfSections() {
							return 1;
						}
						
						@Override
						public ALayer itemForPositionAtIndexPath(ALayer contentLayer, IndexPath indexPath) {
							return adapter.getLayer((indexPath.getSection()+1) * indexPath.getPosition(), contentLayer, listViewLayer);
						}
						
						@Override
						public ALayer sectionForPostionAtIndexPath(ALayer sectionContentLayer, IndexPath indexPath) {
							return null;
						}
					}
					
					listViewLayer.setListViewLayerListener(new MyListViewLayerListene());
					
//					listViewLayer.setItems(layers);
					
					
					listViewLayer.refresh();
					
					int height;
					if(minDropHeight>0)
						height = listViewLayer.getHeight()<minDropHeight ? minDropHeight:listViewLayer.getHeight();
					else
						height = (int) (listViewLayer.bottomY - listViewLayer.topY);
					
					if(maxDropHeight>0)
						height = height>maxDropHeight ? maxDropHeight:height;
					
					listViewLayer.setHeight(height);
					
					listViewLayer.setOnItemSelectedListener(new ListViewLayer.OnItemSelectedListener() {
						
						@Override
						public void onNothingSelected(ListViewLayer listViewLayer) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onItemSelected(ListViewLayer listViewLayer,
								ALayer seletedLayer, IndexPath indexPath, boolean isItem) {
							// TODO Auto-generated method stub
//							String s = ((ButtonLayer)seletedLayer.getChildAt(0)).getText();
							if(seletedLayer!=null){
								String s = ((LabelLayer)seletedLayer).getText();
								SpinnerLayer.this.setText(s);
							}
							
						}
					});
					
					addChild(listViewLayer);
				}else
				if(listViewLayer==null){
					listViewLayer = new ListViewLayer();
					listViewLayer.setY(SpinnerLayer.this.getHeight());
					listViewLayer.setWidth(getWidth());
					List<ButtonLayer> layers = new ArrayList<ButtonLayer>();
					ButtonLayer spinnerSelectionLayer = new ButtonLayer();
					spinnerSelectionLayer.setWidth(100);
					spinnerSelectionLayer.setHeight(listViewLayer.getItemContentLayerHeight());
					spinnerSelectionLayer.setText("123");
					layers.add(spinnerSelectionLayer);
					
					listViewLayer.setBackgroundColor(Color.CYAN);
					
					class MyListViewLayerListene extends ListViewLayer.DefaultListViewLayerListener{
						public MyListViewLayerListene() {
							// TODO Auto-generated constructor stub
							listViewLayer.super();
						}

						@Override
						public int numberOfItemsInSection(int section) {
							// TODO Auto-generated method stub
							return 2;
						}
					}
					
					listViewLayer.setListViewLayerListener(new MyListViewLayerListene());
					
					listViewLayer.setItems(layers);
					
					
					listViewLayer.refresh();
					
					int height;
					if(minDropHeight>0)
						height = listViewLayer.getHeight()<minDropHeight ? minDropHeight:listViewLayer.getHeight();
					else
						height = (int) (listViewLayer.bottomY - listViewLayer.topY);
					
					if(maxDropHeight>0)
						height = height>maxDropHeight ? maxDropHeight:height;
					
					listViewLayer.setHeight(height);
					
					listViewLayer.setOnItemSelectedListener(new ListViewLayer.OnItemSelectedListener() {
						
						@Override
						public void onNothingSelected(ListViewLayer listViewLayer) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onItemSelected(ListViewLayer listViewLayer,
								ALayer seletedLayer, IndexPath indexPath, boolean isItem) {
							// TODO Auto-generated method stub
//							String s = ((ButtonLayer)seletedLayer.getChildAt(0)).getText();
							if(seletedLayer!=null){
								String s = ((ButtonLayer)seletedLayer).getText();
								SpinnerLayer.this.setText(s);
							}
							
						}
					});
					
					addChild(listViewLayer);
				}
				else{
//					if(SpinnerLayer.this.getLayers().contains(listViewLayer)){
//						remove(listViewLayer);
//					}else{
//						addChild(listViewLayer);
//					}
					if(listViewLayer.isHidden()){
						listViewLayer.setHidden(false);
					}else{
						listViewLayer.setHidden(true);
					}
//					if(listViewLayer.isVisible()){
//						listViewLayer.setVisible(false);
//					}else{
//						listViewLayer.setVisible(true);
//					}
				}
			}
		});
	}
	
	private int minDropHeight;
	private int maxDropHeight;
	
	//if 0, auto.
	public void setMinDropHeight(int minDropHeight){
		this.minDropHeight = minDropHeight;
	}
	
	//if 0, auto.
	public void setMaxDropHeight(int maxDropHeight){
		this.maxDropHeight = maxDropHeight;
	}
	
	public void setAdapter(LayerAdapter adapter){
		this.adapter = adapter;
	}
	
	interface OnItemSelectedListener{
		public void onItemSelected();
		public void onNothingSelected();
	}
	
	private OnItemSelectedListener onItemSelectedListener;
	
	
//	public void setStrings
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.drawSelf(canvas, paint);
			
//		Paint mPaint = new Paint();
//		mPaint.setColor(Color.BLUE);
//		for(ILayer layer : mlayers){
//			canvas.drawRect(layer.getFrame(), mPaint);
//		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		
		
		return super.onTouchEvent(event);
	}

	@Override
	protected void onTouched(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
