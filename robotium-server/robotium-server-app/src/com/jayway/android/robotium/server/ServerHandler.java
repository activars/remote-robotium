package com.jayway.android.robotium.server;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.jayway.android.robotium.common.EventInvokeMethodMessage;
import com.jayway.android.robotium.common.EventReturnValueMessage;
import com.jayway.android.robotium.common.Message;
import com.jayway.android.robotium.common.MessageFactory;
import com.jayway.android.robotium.common.TargetActivityMessage;
import com.jayway.android.robotium.common.TypeUtility;
import com.jayway.android.robotium.common.UnsupportedMessage;
import com.jayway.android.robotium.solo.ISolo;
import com.jayway.android.robotium.solo.Solo;

class ServerHandler extends SimpleChannelUpstreamHandler {

	private static final Logger logger = Logger.getLogger(ServerHandler.class
			.getName());
	private static final String TAG = "ServerHandler";

	private ISolo mSolo;
	private Instrumentation mInstrumentation;
	private Activity mActivity;
	private Map<String, Object> referencedObjects;

	public ServerHandler() {
		super();
		// stores weak reference of an object
		referencedObjects = Collections.synchronizedMap(new WeakHashMap());
	}

	public void setInstrumentation(Instrumentation instrumentation) {
		mInstrumentation = instrumentation;
	}

	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		if (e instanceof ChannelStateEvent) {
			logger.info(e.toString());
		}
		super.handleUpstream(ctx, e);
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		// first connection send out request for instrumentation class
		e.getChannel().write(
				MessageFactory.createTargetActivityRequestMessage().toString()
						+ "\r\n");

	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

		// Cast to a String first.
		String request = (String) e.getMessage();
		String response = "";
		Message mMessage = null;
		try {
			mMessage = MessageFactory.parseMessageString(request);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (mMessage instanceof TargetActivityMessage) {
			// the message contain Instrumentation information for the Activity
			String activityClassName = ((TargetActivityMessage) mMessage)
					.getTargetClassName();

			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setClassName(mInstrumentation.getTargetContext()
					.getPackageName(), activityClassName);
			mActivity = mInstrumentation.startActivitySync(intent);
			// initialize the solo tool
			mSolo = new Solo(mInstrumentation, mActivity);

			// create response message and copy the old message uuid
			Message responseMsg = MessageFactory.createSuccessMessage();
			responseMsg.setMessageId(mMessage.getMessageId());
			e.getChannel().write(responseMsg.toString() + "\r\n");

		} else if (mMessage instanceof EventInvokeMethodMessage) {
			// a event message contains object and method was invoked
			EventInvokeMethodMessage eventMsg = (EventInvokeMethodMessage) mMessage;
			Method receivedMethod = eventMsg.getMethodReceived();
			Class<?> returnType = receivedMethod.getReturnType();
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
				try {

					Object returnValue = receivedMethod.invoke(mSolo, eventMsg
							.getParameters());
					if (returnType.equals(void.class)) {
						// send success
						Message responseMsg = MessageFactory
								.createSuccessMessage();
						responseMsg.setMessageId(mMessage.getMessageId());
						e.getChannel().write(responseMsg.toString() + "\r\n");
					} else {
						// get object reference
						if (returnType.isPrimitive()
								|| (!returnType.isPrimitive()
										&& !hasListInterface && !hasCollectionInterface)) {

							// construct return value message, copy the original
							// message ID
							// and write to the client channel
							Message responseMsg = new EventReturnValueMessage(
									returnType, void.class,
									new Object[] { returnValue });
							responseMsg.setMessageId(mMessage.getMessageId());
							e.getChannel().write(
									responseMsg.toString() + "\r\n");

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
									synchronized(referencedObjects) {
										referencedObjects.put(key, new WeakReference<Object>(ele));
									}
								}
								Log.d(TAG, "should return " + innerClassType.getName() + " " + shouldReturnValue.size());
								responseMsg = new EventReturnValueMessage(
										returnType, innerClassType,
										shouldReturnValue.toArray());
							} else {
								responseMsg = new EventReturnValueMessage(
										returnType, innerClassType,
										((List<?>) returnValue).toArray());
							}

							responseMsg.setMessageId(mMessage.getMessageId());
							e.getChannel().write(
									responseMsg.toString() + "\r\n");
						} else {

							// response an unsupported message
							Message responseMsg = new UnsupportedMessage(
									"Returned value only can be List, primitives and other non-collection objects.");
							responseMsg.setMessageId(mMessage.getMessageId());
							e.getChannel().write(
									responseMsg.toString() + "\r\n");
						}
					}

				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
				}
			} else {
				// the event is calling on other object
				// server should have a weak reference to the object that is
				// referenced.
				// TODO: event is calling on other object
				// use UUID from message to find the object and cast it to the
				// type.
				Log
						.d(TAG,
								"Calling on other object and need to look up in the WeakHashMap");
			}

		}

		// never close for now
		boolean close = false;

		// We do not need to write a ChannelBuffer here.
		// We know the encoder inserted at ServerPipelineFactory will do the
		// conversion.
		ChannelFuture future = e.getChannel().write(response);

		// Close the connection after sending 'Have a good day!'
		// if the client has sent 'bye'.
		if (close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {

		logger.log(Level.WARNING, "Unexpected exception from downstream.", e
				.getCause());
		e.getChannel().close();
	}

}
