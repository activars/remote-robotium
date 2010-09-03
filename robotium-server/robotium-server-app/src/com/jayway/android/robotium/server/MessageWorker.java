package com.jayway.android.robotium.server;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.util.Log;

import com.jayway.android.robotium.common.message.EventInvokeMethodMessage;
import com.jayway.android.robotium.common.message.EventReturnValueMessage;
import com.jayway.android.robotium.common.message.Message;
import com.jayway.android.robotium.common.message.MessageFactory;
import com.jayway.android.robotium.common.message.UnsupportedMessage;
import com.jayway.android.robotium.solo.Solo;

public class MessageWorker {
	
	private static final String TAG = "MessageWorker";
	
	private Solo mSolo;
	private Activity mActiviy;
	private Instrumentation mInstrumentation;
	private Intent mIntent;
	private Map<String, Object> referencedObjects;
	
	
	public MessageWorker() {
		// stores weak reference of an object
		referencedObjects = Collections.synchronizedMap(new WeakHashMap());
	}
	
	public void setConfiguration(Solo solo, Activity activity, Instrumentation inst, Intent intent) {
		mSolo = solo;
		mActiviy = activity;
		mInstrumentation = inst;
		mIntent = intent;
	}
	
	private void checkConfiguration() {
		if(mSolo == null || mActiviy == null || mInstrumentation == null || mIntent == null) {
			throw new IllegalArgumentException("Instrumentation missing configuration");
		}
	}
	
	public void receivedChannelConnected(ChannelStateEvent e) {
		Message responseMsg = MessageFactory.createTargetActivityRequestMessage();
		e.getChannel().write(responseMsg.toString() + "\r\n");
	}
	
	public void receivedEventInvokeMethodMessage(EventInvokeMethodMessage mMessage, ChannelFuture future, MessageEvent e) {
		// a event message contains object and method was invoked
		EventInvokeMethodMessage eventMsg = mMessage;
		Log.d(TAG, (eventMsg).getMessageHeader());

		Method receivedMethod = eventMsg.getMethodReceived();
		// Log.d(TAG, "Calling on method:" + receivedMethod.toString());

		Class<?> returnType = receivedMethod.getReturnType();
		// Log.d(TAG, "Return type:" + returnType.getName());

		// check if this has a List Collection interface and
		Class<?>[] collectionInterfaces = returnType.getInterfaces();
		boolean hasListInterface = false;
		boolean hasCollectionInterface = false;
		for (Class<?> c : collectionInterfaces) {
			if (c.getName().equals(List.class.getName())) {
				hasListInterface = true;
			} else if (c.getName().equals(Collection.class.getName())) {
				hasCollectionInterface = true;
			}
			if (hasListInterface && hasCollectionInterface)
				break;
		}
		if (eventMsg.getTargetObjectClass().getName().equals(
				Solo.class.getName())) {
			Log.d(TAG, "calling on Solo base");
			try {

				Object returnValue = receivedMethod.invoke(mSolo, eventMsg
						.getParameters());
				Log.d(TAG, "solo.invoked.");
				if (returnType.equals(void.class)) {
					// send success
					Message responseMsg = MessageFactory
							.createSuccessMessage();
					responseMessage(future, e, responseMsg, mMessage);
				} else {
					// get object reference
					if (returnType.isPrimitive()) {

						// construct return value message, copy the original
						// message ID
						// and write to the client channel
						Message responseMsg = new EventReturnValueMessage(
								returnType, void.class,
								new Object[] { returnValue });
						responseMessage(future, e, responseMsg, mMessage);

					} else if (!returnType.isPrimitive()
							&& !hasListInterface && !hasCollectionInterface) {
						String key = UUID.randomUUID().toString();
						// store the object in WeakHashMap for later
						// use
						synchronized (referencedObjects) {
							referencedObjects.put(key,
									new WeakReference<Object>(returnValue));
						}
						Message responseMsg = new EventReturnValueMessage(
								returnType, void.class,
								new Object[] { key });
						responseMessage(future, e, responseMsg, mMessage);

					} else if (hasListInterface) {
						// if the top root is list, then cast it as a list
						// get the first element in the list to find out the
						// class type
						Object element = ((List<?>) returnValue).get(0);
						Class<?> innerClassType = element.getClass();
						// if the inner generic class is not primitive, we
						// have to constructs an object reference
						// and store it in the WeakHashMap
						Message responseMsg;
						if (!innerClassType.isPrimitive()) {
							List<String> shouldReturnValue = new ArrayList<String>();
							String key;
							for (Object ele : (List<?>) returnValue) {
								// use UUID as the object ID
								key = UUID.randomUUID().toString();
								shouldReturnValue.add(key);
								// store the object in WeakHashMap for later
								// use
								synchronized (referencedObjects) {
									referencedObjects.put(key,
											new WeakReference<Object>(ele));
								}
							}
							
							responseMsg = new EventReturnValueMessage(
									returnType, innerClassType,
									shouldReturnValue.toArray());
						} else {
							responseMsg = new EventReturnValueMessage(
									returnType, innerClassType,
									((List<?>) returnValue).toArray());
						}

						responseMessage(future, e, responseMsg, mMessage);
					} else {

						// response an unsupported message
						Message responseMsg = new UnsupportedMessage(
								"Returned value only can be List, primitives and other non-collection objects.");
						responseMessage(future, e, responseMsg, mMessage);
					}
				}

			} catch (Exception ex){}
		}
	}
	
	
	/**
	 * Returns a Message for given message JSONString
	 * @param msgString String message in JSON String
	 */
	public Message parseMessage(String msgString) {
		Message mMessage = null;
		try {
			mMessage = MessageFactory.parseMessageString(msgString);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Log.d(TAG, "Receiving MSG: " + mMessage.toString());
		return mMessage;
	}
	
	/**
	 * Start the current target Intent activity
	 */
	public void startTargetIntent() {
		checkConfiguration();
		mInstrumentation.startActivitySync(mIntent);
	}
	
	public ChannelFuture responseMessage(ChannelFuture future, MessageEvent e,
		Message responseMsg, Message incomingMsg) {
		responseMsg.setMessageId(incomingMsg.getMessageId());
		future = e.getChannel().write(responseMsg.toString() + "\r\n");
		 Log.d(TAG, "Server replied message");
		 Log.d(TAG, "MessageType: " + responseMsg.getMessageHeader());
		 Log.d(TAG, "MessageType: " + responseMsg.toString());
		return future;
	}

}
