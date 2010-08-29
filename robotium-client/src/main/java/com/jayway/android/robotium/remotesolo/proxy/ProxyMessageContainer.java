package com.jayway.android.robotium.remotesolo.proxy;

import java.lang.reflect.Method;

import org.powermock.reflect.Whitebox;

import com.jayway.android.robotium.common.Message;
import com.jayway.android.robotium.remotesolo.DeviceClient;
import com.jayway.awaitility.proxy.ProxyCreator;
import com.jayway.awaitility.proxy.TypeUtils;

public class ProxyMessageContainer implements MessageContainer {
	
	private DeviceClient deviceClient;
	
	
	//queue
	private  ProxyCreator proxyCreator = new ProxyCreator() {
        @Override
        protected Object callReceived(Method method, Object[] args) {
        	  //TODO: 
              //  lastMethod = method;
              //  lastArgs = args;
        		Class cl = method.getReturnType();
        		Object obj = Whitebox.newInstance(cl);
        		Object objpr = cl.cast( createProxy(obj));
                return objpr; //TypeUtils.getDefaultValue(method.getReturnType());
        }
	};
	
	public  Object createProxy(Object target) {
        Object proxy = proxyCreator.create(target);
        //lastTarget = target;
        return proxy;
	}
	
	public  Object createProxy(Class<?> targetClass) {
		Object target = Whitebox.newInstance(targetClass);
        Object proxy = proxyCreator.create(target);
        //lastTarget = target;
        return proxy;
	}
	
	

	public void addMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

	public void setDeviceClient(DeviceClient device) {
		this.deviceClient = device;
	}


}
