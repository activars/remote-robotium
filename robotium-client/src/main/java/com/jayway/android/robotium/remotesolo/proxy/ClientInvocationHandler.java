package com.jayway.android.robotium.remotesolo.proxy;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import junit.framework.Assert;

import com.jayway.android.robotium.common.EventReturnValueMessage;
import com.jayway.android.robotium.common.ExceptionMessage;
import com.jayway.android.robotium.common.FailureMessage;
import com.jayway.android.robotium.common.Message;
import com.jayway.android.robotium.common.MessageFactory;
import com.jayway.android.robotium.common.TypeUtility;
import com.jayway.android.robotium.common.UnsupportedMessage;
import com.jayway.android.robotium.remotesolo.DeviceClient;
import com.jayway.android.robotium.solo.Solo;

public class ClientInvocationHandler implements
		java.lang.reflect.InvocationHandler {

	private MessageSender messageSender;
	private static Map<String, Message> receivedMessages;
	private static Map<String, String> proxyObjectsID;
	private static Map<String, WeakReference> proxyObjects;
	private DeviceClient device;
	private static final int TIMEOUT = 10000;
	private static final int SLEEP_TIME = 500;

	public ClientInvocationHandler() {
		receivedMessages = Collections
				.synchronizedMap(new HashMap<String, Message>());
		proxyObjectsID = Collections
				.synchronizedMap(new HashMap<String, String>());
		proxyObjects = Collections
		.synchronizedMap(new WeakHashMap<String, WeakReference>());
	}
	
	public void setMessageSender(MessageSender sender) {
		messageSender = sender;
	}

	public void setDeviceClient(DeviceClient device) {
		this.device = device;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		Class<?> returnType = method.getReturnType();

		if (shouldBeRecorded(method)) {
			Message message = null;
			if (isSolo(proxy)) {
				message = MessageFactory.createEventMessage(proxy.getClass(),
						String.valueOf(proxy.hashCode()), method, method
								.getParameterTypes(), args);
			} else {
				// the calling object is not a Proxy for solo but maybe a remote
				// obj
				synchronized (proxyObjects) {
					if (proxyObjects.containsKey(proxy.getClass().getName()))
						message = MessageFactory.createEventMessage(proxy
								.getClass(), proxyObjectsID.get(proxy.getClass().getName()), method,
								method.getParameterTypes(), args);
					else
						System.err.println("Could not find the proxy object!");
				}
			}

			device.sendMessage(message.toString());

			// wait if the server has not responded
			int waitedTime = 0;
			while (!containsMessage(message)) {
				if (waitedTime >= TIMEOUT) {
					Assert.fail("Robotium::Timeout: \r\n"
							+ "Server has no response.");
					return void.class.newInstance();
				}
				Thread.sleep(SLEEP_TIME);
				waitedTime += SLEEP_TIME;
			}

			// no need to get the value
			if (returnType.getName().equals(void.class)) {
				// void, then simply discard the echo reference
				removeMessage(message);
				return void.class.newInstance();
			}
			
			Message msgFromServer;
			synchronized (receivedMessages) {
				msgFromServer = receivedMessages.get(message.getMessageId()
					.toString());
			}

			if (msgFromServer instanceof FailureMessage) {
				removeMessage(message);
				Assert.fail("Robotium::FailureMessage: \r\n"
						+ ((FailureMessage) msgFromServer).getMessage());

			} else if (msgFromServer instanceof ExceptionMessage) {
				removeMessage(message);
				Assert.fail("Robotium::ExceptionMessage: \r\n"
						+ ((ExceptionMessage) msgFromServer).getExceptionName()
						+ "\r\n"
						+ ((ExceptionMessage) msgFromServer)
								.getExceptionMessage());

			} else if (msgFromServer instanceof UnsupportedMessage) {
				removeMessage(message);
				Assert.fail("Robotium::UnsupportedMessage: \r\n"
						+ ((UnsupportedMessage) msgFromServer).getMessage());

			} else if (msgFromServer instanceof EventReturnValueMessage) {
				EventReturnValueMessage returnValueMsg = (EventReturnValueMessage) msgFromServer;
				boolean isInnerClassVoidType = returnValueMsg
						.getInnerClassType().getName().equals(
								void.class.getName());

				if (returnValueMsg.isPrimitive() && isInnerClassVoidType) {
					// primitive message's innerClass type is void
					removeMessage(message);
					System.out
							.println(returnValueMsg.getClassType().toString());

					System.out.println(returnValueMsg.getReturnValue()[0]
							.toString());
					return returnValueMsg.getReturnValue()[0];

				} else if (returnValueMsg.isCollection()
						&& !isInnerClassVoidType) {
					// then must be List collection for now
					// as other collection types are not supported yet
					List returnVal = (List) returnValueMsg.getClassType()
							.newInstance();
					for (Object obj : returnValueMsg.getReturnValue()) {
						Object shouldAdd;
						if (!returnValueMsg.isInnerPrimitive()) {
							// class in collection are not primitives
							// we need to construct an object proxy for the
							// object
							shouldAdd = ((ProxyMessageSender)messageSender)
									.createProxy(returnValueMsg
											.getInnerClassType());
							
							System.out.println("proxy created for: " + returnValueMsg
									.getInnerClassType().getName());
							synchronized(proxyObjects) {
								proxyObjects.put(shouldAdd.getClass().getName(), new WeakReference(obj));
								proxyObjectsID.put(shouldAdd.getClass().getName(), obj.toString());
							}
						} else {
							// collection of primitive, so just add to return
							// list
							shouldAdd = returnValueMsg.getInnerClassType()
									.cast(obj);
						}
						returnVal.add(shouldAdd);
					}

					// returns the list
					removeMessage(message);
					return returnVal;
				}

			}
			
			removeMessage(message);
			System.err.println("some other message were not catched!: " + message.toString());
		}
		System.err.println("some other message were not catched!: " + method.getName() + "/" + proxy.getClass().getName());
		return null;
		// throw new UnsupportedOperationException("Not Implemented yet");
	}

	public void removeMessage(Message message) {
		synchronized (receivedMessages) {
			receivedMessages.remove(message.getMessageId().toString());
		}
	}

	public void addMessage(Message message) {
		synchronized (receivedMessages) {
			receivedMessages.put(message.getMessageId().toString(), message);
		}
	}

	private boolean containsMessage(Message message) {
		synchronized (receivedMessages) {
			return receivedMessages.containsKey(message.getMessageId().toString());
		}
	}

	private boolean isSolo(Object obj) {
		return TypeUtility.getClassName(obj.getClass()).equals(
				TypeUtility.getClassName(Solo.class));
	}

	/**
	 * ignore calls on object and finalize method
	 */
	private boolean shouldBeRecorded(Method method) {
		return !(method.getDeclaringClass().equals(Object.class) && method
				.getName().equals("finalize"));
	}

}
