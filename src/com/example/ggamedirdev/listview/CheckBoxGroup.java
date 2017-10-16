package com.example.ggamedirdev.listview;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.MotionEvent;

import com.example.try_gameengine.center_notification.NSANotifiable;
import com.example.try_gameengine.center_notification.NSANotification;
import com.example.try_gameengine.center_notification.NSANotificationCenter;
import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.ILayer;
import com.example.try_gameengine.framework.ILayerDelegate;
import com.example.try_gameengine.framework.ILayerWithDelegate;
import com.example.try_gameengine.framework.Layer;

public class CheckBoxGroup extends Layer implements NSANotifiable{
	private List<ILayer> layers = new ArrayList<ILayer>();
	private int currentCheckedNum;
	private int minCheckedNum;
	private int maxCheckedNum = 1;
	
	private OnCheckedListener checkedListener = new OnCheckedListener() {
		
		@Override
		public void onCheckStatusChanged(ALayer layer, boolean checked) {
			// TODO Auto-generated method stub
			
		}
	};
	
	interface OnCheckedListener {
		public void onCheckStatusChanged(ALayer layer, boolean checked);
	}
	
	public void addCheckBox(CheckboxLayer checkboxLayer){
//		checkboxLayer.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(ButtonLayer buttonLayer) {
//				// TODO Auto-generated method stub
//				checkedListener.onCheckStatusChanged(buttonLayer, false);
//			}
//		});
//		checkboxLayer.addChild(checkboxLayer);
		
		
		MyHandler myHandler = new MyHandler();
		myHandler.setCheckboxLayer(checkboxLayer);
		 Class[] interfaces = checkboxLayer.getClass().getInterfaces();
	        for (Class c : interfaces) {
	            Log.e("Name",c.getCanonicalName());
	        }
	    ILayer proxyLayer = (ILayerWithDelegate)Proxy.newProxyInstance(checkboxLayer.getClass().getClassLoader(), new Class[]{ILayerWithDelegate.class}, myHandler);
			
		layers.add(proxyLayer);
//		addChild(checkboxLayer);
		
//		CheckboxLayer proxyLayer = (CheckboxLayer) (new MyHandler3().QQ(checkboxLayer));
		addChild(proxyLayer);		
		
		NSANotificationCenter.defaultCenter().addObserver(this, "EditClicked", null);
	}
	
//	public void removeCheckBox(CheckboxLayer checkboxLayer){
//		remove(checkboxLayer);
//	}
	
	@Override
	public void addChild(ILayer layer) {
		// TODO Auto-generated method stub
		if(layers.add(layer))
			super.addChild(layer);
	}

	@Override
	public List<ILayer> getLayers() {
		// TODO Auto-generated method stub
		return super.getLayers();
	}
	
	@Override
	public void remove(ILayer layer) {
		// TODO Auto-generated method stub
		if(layers.remove(layer)){
			boolean isRemovedProxyCheckboxLayer = false;
			for(ILayer child : super.getLayers()){
				if(child instanceof MyHandler && ((MyHandler)child).checkboxLayer.equals(layer)){
					super.remove(child);
					isRemovedProxyCheckboxLayer = true;
					break;
				}
			}
			
			if(!isRemovedProxyCheckboxLayer)
				super.remove(layer);
		}
	}
	
	@Override
	public List<ILayer> getLayersFromRootLayerToCurrentLayerInComposite() {
		// TODO Auto-generated method stub
		return super.getLayersFromRootLayerToCurrentLayerInComposite();
	}
	
	public void setOnCheckedListener(OnCheckedListener onCheckedListener){
		this.checkedListener = onCheckedListener;
	}
	
	public void checked(){
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event, int touchEventFlag) {
		// TODO Auto-generated method stub
//		for(CheckboxLayer checkboxLayer : checkboxLayers){
//			if(checkboxLayer.onTouchEvent(event, touchEventFlag)){
//				if((event.getAction()==MotionEvent.ACTION_UP 
//						|| (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP)
//						&& checkboxLayer.isPressed()){
//					checkedListener.onCheckStatusChanged(checkboxLayer, checkboxLayer.isChecked());
//				}
//			}
//		}
		//改用動態代理？
		/*
		 * onTouch{
		 * 		check Min & Max
		 * 		ontouch
		 * if((event.getAction()==MotionEvent.ACTION_UP 
						|| (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP)
						&& checkboxLayer.isPressed()){
					checkedListener.onCheckStatusChanged(checkboxLayer, checkboxLayer.isChecked());
				}
		 * }
		 */
		
//		for(CheckboxLayer checkboxLayer : checkboxLayers){
//			if(checkboxLayer.onTouchEvent(event, touchEventFlag)){
//				if((event.getAction()==MotionEvent.ACTION_UP 
//						|| (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP)
//						&& checkboxLayer.isPressed()){
//					checkedListener.onCheckStatusChanged(checkboxLayer, checkboxLayer.isChecked());
//				}
//			}
//			MyHandler myHandler = new MyHandler();
//			myHandler.setCheckboxLayer(checkboxLayer);
//			CheckboxLayer layer = (CheckboxLayer)Proxy.newProxyInstance(CheckboxLayer.class.getClassLoader(), checkboxLayer.getClass().getInterfaces(), myHandler);
//			layer.onTouchEvent(event, touchEventFlag);
//		}
		
		
		return super.onTouchEvent(event, touchEventFlag);
	}
	
	class MyHandler implements InvocationHandler {
		private CheckboxLayer checkboxLayer;
		
		public CheckboxLayer getCheckboxLayer() {
			return checkboxLayer;
		}

		public void setCheckboxLayer(CheckboxLayer checkboxLayer) {
			this.checkboxLayer = checkboxLayer;
		}

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			Object result;
			
			if(method.getName().equals("onTouchEvent")){
				result = false;
				MotionEvent event = (MotionEvent) args[0];
				if((checkboxLayer.isChecked() && currentCheckedNum > minCheckedNum)
					|| (!checkboxLayer.isChecked() && currentCheckedNum < maxCheckedNum)){
					
					result = method.invoke(checkboxLayer, args); 
					
					if((Boolean)result && ((event.getAction()==MotionEvent.ACTION_UP 
							|| (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP))
						){
//							&& checkboxLayer.isPressed()){
						if(checkboxLayer.isChecked())
							currentCheckedNum++;
						else
							currentCheckedNum--;
						checkedListener.onCheckStatusChanged(checkboxLayer, checkboxLayer.isChecked());
					}
					
				}
				
				return result;
			}
			
			result = method.invoke(checkboxLayer, args);
			
			return result;
		}
		
		public void dd(){
			System.out.println("dd");
		}
	}

	@Override
	public void receiveNotification(NSANotification nsaNotification) {
		// TODO Auto-generated method stub
		
	}
	

}

/*
class MyHandler2 implements MethodInterceptor {
	private CheckboxLayer checkboxLayer;
	
	public Object getInstance(CheckboxLayer checkboxLayer){
        this.checkboxLayer = checkboxLayer;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.checkboxLayer.getClass());
        // 设置回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }
	
	public Object getCglibProxy(Class<?> clazz, Class<?>[] argClazz, Object[] args) {
		return args;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		Object result;
		
//		if(method.getName().equals("onTouchEvent")){
//			result = false;
//			MotionEvent event = (MotionEvent) args[0];
//			if((checkboxLayer.isChecked() && currentCheckedNum > minCheckedNum)
//				&& (!checkboxLayer.isChecked() && currentCheckedNum < maxCheckedNum)){
////				result = method.invoke(checkboxLayer, args); 
//				result = proxy.invokeSuper(obj, args);
//				if((event.getAction()==MotionEvent.ACTION_UP 
//						|| (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP)
//						&& checkboxLayer.isPressed()){
//					if(checkboxLayer.isChecked())
//						currentCheckedNum++;
//					else
//						currentCheckedNum--;
//					checkedListener.onCheckStatusChanged(checkboxLayer, checkboxLayer.isChecked());
//				}
//			}
//			
//			return result;
//		}
		
//		result = method.invoke(checkboxLayer, args);
		result = proxy.invokeSuper(obj, args);
		
		return result;
	}
}
*/
/*
class MyHandler3 implements MethodInterceptor {
	private CheckboxLayer checkboxLayer;
	
	public Object getInstance(CheckboxLayer checkboxLayer){
        this.checkboxLayer = checkboxLayer;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.checkboxLayer.getClass());
        // 设置回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }
	
	public Object getCglibProxy(Class<?> clazz, Class<?>[] argClazz, Object[] args) {
		return args;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		Object result;
		
//		if(method.getName().equals("onTouchEvent")){
//			result = false;
//			MotionEvent event = (MotionEvent) args[0];
//			if((checkboxLayer.isChecked() && currentCheckedNum > minCheckedNum)
//				&& (!checkboxLayer.isChecked() && currentCheckedNum < maxCheckedNum)){
////				result = method.invoke(checkboxLayer, args); 
//				result = proxy.invokeSuper(obj, args);
//				if((event.getAction()==MotionEvent.ACTION_UP 
//						|| (event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_POINTER_UP)
//						&& checkboxLayer.isPressed()){
//					if(checkboxLayer.isChecked())
//						currentCheckedNum++;
//					else
//						currentCheckedNum--;
//					checkedListener.onCheckStatusChanged(checkboxLayer, checkboxLayer.isChecked());
//				}
//			}
//			
//			return result;
//		}
		
//		result = method.invoke(checkboxLayer, args);
		result = proxy.invokeSuper(obj, args);
		return result;
	}
		
	public Object QQ(CheckboxLayer checkboxLayer){
		 ByteBuddy byteBuddy;
         try {
             byteBuddy = new ByteBuddy();
         } catch (Throwable e) {
             return null;
         }
         try {
             File file = StageManager.getCurrentStage().getDir(RandomString.make(), Context.MODE_PRIVATE);
             if (!file.isDirectory()) {
                 throw new IOException("Not a directory: " + file);
             }
             DynamicType.Loaded<?> dynamicType;
             try {
//                 dynamicType = byteBuddy.subclass(checkboxLayer.getClass())
//                         .method(named("onTouchEvent")).intercept(MethodDelegation.to(Interceptor.class))
//                         .make()
//                         .load(checkboxLayer.getClass().getClassLoader(), new AndroidClassLoadingStrategy(file));
            	 dynamicType = byteBuddy.subclass(checkboxLayer.getClass())
                         .method(named("onTouchEvent")).intercept(MethodDelegation.to(Interceptor.class))
                         .make()
                         .load(CheckBoxGroup.class.getClassLoader(), new AndroidClassLoadingStrategy(file));
             } catch (Throwable e) {
                 return null;
             }
             try {
                 CheckboxLayer value = (CheckboxLayer) dynamicType.getLoaded().newInstance();
                 return value;
             } catch (Throwable e) {
             }
         } catch (Throwable e) {
         }
         
		return null;
	}
	
	public static class Interceptor {

//        
//         * The interception method to be applied.
//         *
//         * @param zuper A proxy to call the super method to validate the functioning og creating an auxiliary type.
//         * @return The value to be returned by the instrumented {@link Object#toString()} method.
//         * @throws Exception If an exception occurs.
//         
        public static boolean intercept(@SuperCall Callable<Boolean> zuper) throws Exception {
            boolean result = false;
//            if (toString.equals("onTouchEvent")) {
            	result = zuper.call();
//            }
            return result;
        }
    }
}
*/
