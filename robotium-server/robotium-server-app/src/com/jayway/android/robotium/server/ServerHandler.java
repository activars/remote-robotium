package com.jayway.android.robotium.server;

import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.util.Date;
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

import com.jayway.android.robotium.common.EventMessage;
import com.jayway.android.robotium.common.Message;
import com.jayway.android.robotium.common.MessageFactory;
import com.jayway.android.robotium.common.TargetActivityMessage;
import com.jayway.android.robotium.solo.ISolo;
import com.jayway.android.robotium.solo.Solo;

class ServerHandler extends SimpleChannelUpstreamHandler {

	private static final Logger logger = Logger.getLogger(ServerHandler.class
			.getName());
	private static final String TAG = "ServerHandler";

	private ISolo mSolo;
	private Instrumentation mInstrumentation;
	private Activity mActivity;
	
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
		e.getChannel().write(MessageFactory.createTargetActivityRequestMessage().toString() + "\r\n");
		
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

		// Cast to a String first.
		String request = (String) e.getMessage();
		String response = "";
		Message mMessage = null;
		try {
				mMessage =  MessageFactory.parseMessageString(request);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if( mMessage instanceof TargetActivityMessage) {
			// the message contain Instrumentation information for the Activity
			String activityClassName = ((TargetActivityMessage) mMessage).getTargetClassName();
			
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
		
		} else if (mMessage instanceof EventMessage) {
			// a event message contains object and method was invoked
			EventMessage eventMsg = (EventMessage) mMessage;
			Class<?> returnType = eventMsg.getMethodReceived().getReturnType();
			if(eventMsg.getTargetObjectClass().getName().equals(Solo.class.getName())) {
				try {
					Object returnValue = eventMsg.getMethodReceived().invoke(mSolo, eventMsg.getParameters());
					if(returnType.equals(void.class)) {
						//TODO: send success 
						Message responseMsg = MessageFactory.createSuccessMessage();
						responseMsg.setMessageId(mMessage.getMessageId());
						e.getChannel().write(responseMsg.toString() + "\r\n");
					} else {
						//TODO: get object reference
						//      
					}
					
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
