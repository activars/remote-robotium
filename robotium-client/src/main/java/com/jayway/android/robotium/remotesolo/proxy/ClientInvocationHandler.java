package com.jayway.android.robotium.remotesolo.proxy;


import java.util.UUID;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


import com.jayway.android.robotium.common.message.Message;
import com.jayway.android.robotium.common.message.MessageFactory;
import com.jayway.android.robotium.common.util.TypeUtils;
import com.jayway.android.robotium.remotesolo.DeviceClient;
import com.jayway.android.robotium.remotesolo.MessageWorker;
import com.jayway.android.robotium.solo.Solo;

public class ClientInvocationHandler implements InvocationHandler {

	private ProxyManager proxyManager;
	
	private DeviceClient device;
	private MessageWorker messageWorker;
	public static final int TIMEOUT = 15000;
	public static final int SLEEP_TIME = 700;

	public ClientInvocationHandler() {
		messageWorker = new MessageWorker();
	}

	public void setMessageSender(ProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}

	public void setDeviceClient(DeviceClient device) {
		this.device = device;
	}
	
	
	public Object invoke(Object proxyObj, Method method, Object[] args) throws Throwable {

		if (shouldBeRecorded(method)) {
			
			Message message = null;
			if (isSolo(proxyObj)) {
				message = MessageFactory.createEventMessage(proxyObj
						.getClass(), String.valueOf(UUID.randomUUID()),
						method, method.getParameterTypes(),
						args);
			} else {
				// Then it could be a proxy object
				String objRemoteID = messageWorker.getProxyObjectRemoteID(proxyObj);
				if (objRemoteID != null) {
					message = MessageFactory.createEventMessage(
							proxyObj.getClass(), objRemoteID, method,
							method.getParameterTypes(), args);
				} else {
					throw new NullPointerException("the proxy object cannot be found");
				}
			}
			
			device.sendMessage(message.toString());
			
			//wait for response
			while(!messageWorker.hasResponseFor(message))
				Thread.sleep(1000);
					
			return messageWorker.digestMessage(message.getMessageId().toString(), proxyManager);
				
		}

		return null;
	}
	
	
	public void addMessage(Message message) {
		messageWorker.addMessge(message);
	}
	
	
	private boolean isSolo(Object obj) {
		return TypeUtils.getClassName(obj.getClass()).equals(
				TypeUtils.getClassName(Solo.class));
	}

	
	/**
	 * ignore calls on object and finalize method
	 */
	private boolean shouldBeRecorded(Method method) {
		return !((method.getDeclaringClass().equals(Object.class) || method.getDeclaringClass().equals(Solo.class))
				&& method.getName().equals("finalize"));
	}

	

}
