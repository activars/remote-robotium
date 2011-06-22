package com.jayway.android.robotium.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.jayway.android.robotium.common.util.TypeUtils;
import com.jayway.android.robotium.solo.Solo;

public class MessageWorker {
	
	private static final String TAG = "MessageWorker";
	
	private Solo mSolo;
	private Activity mActiviy;
	private Instrumentation mInstrumentation;
	private Intent mIntent;
	private static Map<String, Object> referencedObjects;
	
	
	public MessageWorker() {
		// stores weak reference of an object
		referencedObjects = Collections.synchronizedMap(new HashMap<String, Object>());
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
	
	public void receivedEventInvokeMethodMessage(EventInvokeMethodMessage mMessage, ChannelFuture future, MessageEvent e) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		// a event message contains object and method was invoked
		EventInvokeMethodMessage eventMsg = mMessage;
		Log.d(TAG, (eventMsg).getMessageHeader());

		Method receivedMethod = eventMsg.getMethodReceived();
		// Log.d(TAG, "Calling on method:" + receivedMethod.toString());

		Class<?> returnType = receivedMethod.getReturnType();
		// Log.d(TAG, "Return type:" + returnType.getName());

		// check if this has a List Collection interface and
		boolean hasListInterface = TypeUtils.hasListInterfaceType(returnType);
		boolean hasCollectionInterface = TypeUtils.hasCollectionInterfaceType(returnType);

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
					// response to non void return type
					responseToNonVoidReturnType(future, e, mMessage, hasListInterface, hasCollectionInterface, returnValue);
				}

			} catch (Exception ex){}
		} else {
			Log.d(TAG, "Calling on non-solo object");
			String objID = eventMsg.getTargetObjectId();
			Object realObj = null;
			synchronized (referencedObjects) {
				Log.d(TAG, "referencdObjects size: " + referencedObjects.size());
				Log.d(TAG, "Need find: " + objID);
				Log.d(TAG, "Has: " + referencedObjects.keySet().toString());
				if(referencedObjects.containsKey(objID)) {
					realObj = referencedObjects.get(objID);
				}
			}
			
			if(realObj != null) {
				
				// check parameters: they could be remote references.
				Class<?>[] tmpTypes = eventMsg.getParameterTypes();
				Object[] tmpParams = eventMsg.getParameters();
				Object[] checkedParams = new Object[tmpParams.length];
				for(int i = 0; i < tmpTypes.length; i++) {
					if(TypeUtils.isPrimitive(tmpTypes[i]) || tmpTypes[i].equals(Class.class)) {
						checkedParams[i] = tmpParams[i];
					} else {
						String objRemoteID = tmpParams[i].toString();
						Object refObj = referencedObjects.get(objRemoteID);
						Log.d(TAG, "Object in parameter found");
						if(refObj != null) 
							checkedParams[i] = refObj;
						else 
							throw new UnsupportedOperationException(
							"This may not be a remote object or the server lost reference.");
					}
				}
			
				
				Log.d(TAG, "Found remote object in hashmap");
				Object returnValue = eventMsg.getMethodReceived().invoke(realObj, checkedParams);
				Log.d(TAG, "Return value:" + returnValue.toString());
				if(eventMsg.getMethodReceived().getReturnType().getClass().equals(void.class)) {
					// no need to return value
					// send success
					Message responseMsg = MessageFactory
							.createSuccessMessage();
					responseMessage(future, e, responseMsg, mMessage);
				} else {
					// response to non void return type request
					responseToNonVoidReturnType(future, e, mMessage, hasListInterface, hasCollectionInterface, returnValue);
				}
			}
		}
	}
	
	
	private void responseToNonVoidReturnType(ChannelFuture future, MessageEvent e, EventInvokeMethodMessage mMessage, boolean hasListInterface, boolean hasCollectionInterface, Object returnValue) {
		Class<?> returnType = mMessage.getMethodReceived().getReturnType();
		// get object reference
		if (returnType.isPrimitive() || returnType.equals(String.class)) {

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
				referencedObjects.put(key, returnValue);
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
					key = String.valueOf(UUID.randomUUID());
					shouldReturnValue.add(key);
					// store the object in WeakHashMap for later
					// use
					synchronized (referencedObjects) {
						referencedObjects.put(key, ele);
					}
					Log.d(TAG, "Added new, now referencdObjects size: " + referencedObjects.size());
					Log.d(TAG, "Has: " + referencedObjects.keySet().toString());
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
	
	
	/**
	 * Returns a Message for given message JSONString
	 * @param msgString String message in JSON String
	 */
	public Message parseMessage(String msgString) {
		Message mMessage = null;
		try {
			Log.d(TAG+":Parsing", msgString);
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
