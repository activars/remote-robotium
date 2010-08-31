package com.jayway.android.robotium.remotesolo.proxy;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.sf.cglib.proxy.Enhancer;

import com.jayway.android.robotium.common.Message;
import com.jayway.android.robotium.common.MessageFactory;
import com.jayway.android.robotium.common.TypeUtility;
import com.jayway.android.robotium.remotesolo.DeviceClient;
import com.jayway.android.robotium.solo.Solo;


public class ClientInvocationHandler implements java.lang.reflect.InvocationHandler {
	
	private ConcurrentLinkedQueue<Message> receivedMessages;
	private DeviceClient device;
	
	public ClientInvocationHandler() {
		receivedMessages = new ConcurrentLinkedQueue<Message>();
	}
	
	public void setDeviceClient(DeviceClient device) {
		this.device = device;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		if (shouldBeRecorded(method)) {
			if(isSolo(proxy)) {
				Message message = MessageFactory.createEventMessage(proxy.getClass(), 
						String.valueOf(proxy.hashCode()), method, method.getParameterTypes(), args);
				
				device.sendMessage(message.toString());
				
				// TODO: check if the  method has return value, should wait for value return;
			}
		}
		return null;
		//throw new UnsupportedOperationException("Not Implemented yet");
		
	}
	
	private boolean isSolo(Object obj) {
		return TypeUtility.getStringValue(obj.getClass()).equals(TypeUtility.getStringValue(Solo.class));
	}

	/**
	 * ignore calls on object and finalize method
	 */
	private boolean shouldBeRecorded(Method method) {
		return !(method.getDeclaringClass().equals(Object.class) && method
				.getName().equals("finalize"));
	}

}
