package com.jayway.android.robotium.remotesolo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

import junit.framework.Assert;

import com.jayway.android.robotium.common.message.EventReturnValueMessage;
import com.jayway.android.robotium.common.message.ExceptionMessage;
import com.jayway.android.robotium.common.message.FailureMessage;
import com.jayway.android.robotium.common.message.Message;
import com.jayway.android.robotium.common.message.UnsupportedMessage;
import com.jayway.android.robotium.remotesolo.proxy.ProxyManager;

public class MessageWorker {

	private static Map<String, Message> receivedMessages;
	private static Map<Integer, String> proxyObjectsRemoteID;
	private static Map<Object, Object> proxyObjs;
	private static final Object lock = new Object();

	public MessageWorker() {
		receivedMessages = Collections
				.synchronizedMap(new HashMap<String, Message>());
		proxyObjectsRemoteID = Collections
				.synchronizedMap(new HashMap<Integer, String>());
		proxyObjs = new HashMap<Object, Object>();
	}

	public Object digestMessage(String messageID, ProxyManager proxyManager) {

		Message message = receivedMessages.get(messageID);
		if (message == null)
			return null;

		if (message instanceof FailureMessage) {
			receivedFailureMessage((FailureMessage) message);

		} else if (message instanceof ExceptionMessage) {
			receivedExceptionMessage((ExceptionMessage) message);

		} else if (message instanceof UnsupportedMessage) {
			receivedUnsupportedMessage((UnsupportedMessage) message);

		} else if (message instanceof EventReturnValueMessage) {
			return receivedEventReturnValueMessage(
					(EventReturnValueMessage) message, proxyManager);
		} else {
			removeMessage(message);

		}

		return null;
	}

	public String getProxyObjectRemoteID(int systemRefId) {
		synchronized (lock) {
				return proxyObjectsRemoteID.get(systemRefId);
		}
	}

	public void removeProxyObject(int systemObjRef, Object proxyObj) {
		synchronized (lock) {
			proxyObjectsRemoteID.remove(systemObjRef);
			proxyObjs.remove(proxyObj);
		}
	}

	public void addMessge(Message message) {
		System.out.println("Msg Added: " + message.getMessageId().toString());
		receivedMessages.put(message.getMessageId().toString(), message);
	}

	public boolean hasResponseFor(Message message) {
		return receivedMessages.containsKey(message.getMessageId().toString());
	}

	private void addProxyObject(Object proxyObj, String remoteRef) {
		synchronized (lock) {
		
		int sysRef = System.identityHashCode(proxyObj);
		proxyObjs.put(proxyObj, proxyObj);
		proxyObjectsRemoteID.put(sysRef, remoteRef);
		}
	}

	private void removeMessage(Message message) {
		System.out.println("Msg Removed: " + message.toString());
		receivedMessages.remove(message.getMessageId().toString());
	}

	private void receivedFailureMessage(FailureMessage message) {
		removeMessage(message);
		Assert.fail("Robotium::FailureMessage: \r\n" + message.getMessage());
	}

	private void receivedExceptionMessage(ExceptionMessage message) {
		removeMessage(message);
		Assert.fail("Robotium::ExceptionMessage: \r\n"
				+ message.getExceptionName() + "\r\n"
				+ message.getExceptionMessage());
	}

	private void receivedUnsupportedMessage(UnsupportedMessage message) {
		removeMessage(message);
		Assert.fail("Robotium::UnsupportedMessage: " + message.getMessage());
	}

	private Object receivedEventReturnValueMessage(
			EventReturnValueMessage message, ProxyManager proxyManager) {

		EventReturnValueMessage returnValueMsg = message;

		boolean isInnerClassVoidType = returnValueMsg.getInnerClassType()
				.getName().equals(void.class.getName());

		if ((returnValueMsg.isPrimitive() && isInnerClassVoidType) || returnValueMsg.getClassType().equals(String.class)) {
			// primitive message's innerClass type is void, take String into account
			removeMessage(message);
			return returnValueMsg.getReturnValue()[0];
			
		} else if (returnValueMsg.isCollection() && !isInnerClassVoidType) {
		
			// then must be List collection for now
			// as other collection types are not supported yet
			List returnVal = null;
			try {
				returnVal = (List) returnValueMsg.getClassType().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			for (Object obj : returnValueMsg.getReturnValue()) {
				Object shouldAdd;
				if (!returnValueMsg.isInnerPrimitive()) {
					// class in collection are not primitives
					// we need to construct an object proxy for the
					// object

					shouldAdd = proxyManager.createProxy(returnValueMsg
							.getInnerClassType());

					System.out.println("proxy created for: "
							+ returnValueMsg.getInnerClassType().getName());

					addProxyObject(shouldAdd, obj.toString());

				} else {
					// collection of primitive, so just add to
					shouldAdd = returnValueMsg.getInnerClassType().cast(obj);
				}
				returnVal.add(shouldAdd);
			}

			// returns the list
			removeMessage(message);
			return returnVal;
			
		}  else if (!returnValueMsg.isPrimitive() && isInnerClassVoidType) {
			// the return object should be proxied
			Object returnVal = proxyManager.createProxy(returnValueMsg
					.getClassType());
			Object[] ojbs = returnValueMsg.getReturnValue();
			addProxyObject(returnVal, ojbs[0].toString());
			
			removeMessage(message);
			return returnVal;
		}
		return null;
	}

}
