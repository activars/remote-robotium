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
			// check arguments are primitives
			Object[] checkedArgs = new Object[args.length];
			for(int i = 0; i < args.length; i++) {
				if(TypeUtils.isPrimitive(args[i].getClass()) || args[i].getClass().equals(Class.class)) {
					checkedArgs[i] = args[i];
				} else {
					int sysRef = System.identityHashCode(proxyObj);
					String objRemoteID = messageWorker.getProxyObjectRemoteID(sysRef);
					if(objRemoteID != null) 
						checkedArgs[i] = objRemoteID;
					else 
						throw new UnsupportedOperationException(
						"Arguemtn type is not supported.");
				}
			}
			
			if (isSolo(proxyObj)) {
				message = MessageFactory.createEventMessage(proxyObj
						.getClass(), UUID.randomUUID().toString(),
						method, method.getParameterTypes(),
						checkedArgs);
			} else {
				
				// TODO: the invoke executes method on single remote object.
				// The client should find the same objects on other devices and execute the invocation too!!
				// only for non-solo based invocation
				
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
							method.getParameterTypes(), checkedArgs);
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
