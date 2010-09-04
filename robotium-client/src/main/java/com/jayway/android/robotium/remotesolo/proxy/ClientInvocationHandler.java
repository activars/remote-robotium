package com.jayway.android.robotium.remotesolo.proxy;


import java.util.UUID;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;



import com.jayway.android.robotium.common.message.EventInvokeMethodMessage;
import com.jayway.android.robotium.common.message.Message;
import com.jayway.android.robotium.common.message.MessageFactory;
import com.jayway.android.robotium.common.util.TypeUtils;
import com.jayway.android.robotium.remotesolo.DeviceClient;
import com.jayway.android.robotium.remotesolo.MessageWorker;
import com.jayway.android.robotium.remotesolo.RemoteException;
import com.jayway.android.robotium.solo.Solo;

public class ClientInvocationHandler implements InvocationHandler {

	private static ProxyManager proxyManager;
	
	private DeviceClient device;
	private static MessageWorker messageWorker;
	public static final int TIMEOUT = 15000;
	public static final int SLEEP_TIME = 500;

	public ClientInvocationHandler() {
		messageWorker = new MessageWorker();
	}

	@SuppressWarnings("static-access")
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
						.getClass(), UUID.randomUUID().toString(),
						method, method.getParameterTypes(),
						args);
			} else {
				
				if(method.getName().equals("hashCode")) {
					return System.identityHashCode(proxyObj);
				}
				
				// Then it could be a proxy object
				int sysRef = System.identityHashCode(proxyObj);
				String objRemoteID = messageWorker.getProxyObjectRemoteID(sysRef);
				// remove them from memory
				// messageWorker.removeProxyObject(sysRef, proxyObj);
				if (objRemoteID != null) {
					// construct a message and request for action
					message = MessageFactory.createEventMessage(
							proxyObj.getClass(), objRemoteID, method,
							method.getParameterTypes(), args);
				} else {
					System.err.println("cannot find proxy object");
					return null;
				}
			}
			
			device.sendMessage(message.toString());
			System.out.println("Sent Msg: " + message.toString());
			
			//wait for response
			int slept = 0; 
			boolean timedOut = false;
			while(!messageWorker.hasResponseFor(message) ) {
				if(slept >= TIMEOUT) {
					break;
				}
				Thread.sleep(SLEEP_TIME);
				slept += SLEEP_TIME;
			}
			
			if(timedOut == true) throw new RemoteException("Server time out");
			
			Object returnValue = messageWorker.digestMessage(message.getMessageId().toString(), proxyManager);
			return returnValue;
				
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
