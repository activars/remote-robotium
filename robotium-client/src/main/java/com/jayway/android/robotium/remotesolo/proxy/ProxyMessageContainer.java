package com.jayway.android.robotium.remotesolo.proxy;

import java.lang.reflect.Method;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang.NotImplementedException;
import org.powermock.reflect.Whitebox;

import com.jayway.android.robotium.common.Message;
import com.jayway.android.robotium.remotesolo.DeviceClient;
import com.jayway.awaitility.proxy.ProxyCreator;
import com.jayway.awaitility.proxy.TypeUtils;

import edu.emory.mathcs.backport.java.util.AbstractQueue;

public class ProxyMessageContainer implements MessageContainer {
	
	private DeviceClient deviceClient;
	private Queue<Message> receivedMessage;
	
	
	public ProxyMessageContainer() {
		receivedMessage = new ConcurrentLinkedQueue<Message>();
	}
	
	/**
	 * The ProxyMessageContainer requires a DeviceClient to be set
	 * after its creation.
	 */
	public void setDeviceClient(DeviceClient device) {
		this.deviceClient = device;
	}
	
	private  ProxyCreator proxyCreator = new ProxyCreator() {
        @Override
        protected Object callReceived(Method method, Object[] args) {
        	
        	throw new UnsupportedOperationException("Not Implemented yet");
        	  //TODO: 
              //  lastMethod = method;
              //  lastArgs = args;
        	//	Class cl = method.getReturnType();
        	//	Object obj = Whitebox.newInstance(cl);
        	//	Object objpr = cl.cast( createProxy(obj));
             //   return objpr; //TypeUtils.getDefaultValue(method.getReturnType());
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
	
	
	/**
	 * Add a message to the end of the message queue
	 */
	public void addMessage(Message message) {
		receivedMessage.offer(message);
	}

	public DeviceClient getDeviceClient() {
		return deviceClient;
		
	}
	
	


}
