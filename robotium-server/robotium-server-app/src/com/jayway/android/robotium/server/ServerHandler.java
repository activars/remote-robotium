package com.jayway.android.robotium.server;

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
			// testing code: can use injected instrumentation to run launch events
			// as usual.
			
			while(mInstrumentation == null) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Log.d(TAG, "Instrumentation is null");
			}
			
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setClassName(mInstrumentation.getTargetContext()
					.getPackageName(), activityClassName);
			mActivity = mInstrumentation.startActivitySync(intent);

			mSolo = new Solo(mInstrumentation, mActivity);

			mSolo.clickOnMenuItem("Add note");
			mSolo.assertCurrentActivity("Expected NoteEditor activity",
					"NoteEditor"); // Assert that NoteEditor activity is opened
			mSolo.enterText(0, "Note 1"); // Add note
			mSolo.goBack();
			mSolo.clickOnMenuItem("Add note"); // Clicks on menu item
			mSolo.enterText(0, "Note 2"); // Add note
			mSolo.goBack();
			
			response = MessageFactory.createSuccessMessage().toString() + "\r\n";
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
