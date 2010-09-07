package com.jayway.android.robotium.server;

import java.lang.reflect.InvocationTargetException;
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
import android.content.Intent;
import android.util.Log;

import com.jayway.android.robotium.common.message.EventInvokeMethodMessage;
import com.jayway.android.robotium.common.message.Message;
import com.jayway.android.robotium.common.message.MessageFactory;
import com.jayway.android.robotium.common.message.TargetActivityMessage;
import com.jayway.android.robotium.solo.Solo;

class ServerHandler extends SimpleChannelUpstreamHandler {

	private static final Logger logger = Logger.getLogger(ServerHandler.class
			.getName());
	private static final String TAG = "ServerHandler";

	private Solo mSolo;
	private Instrumentation mInstrumentation;
	private Intent mTargetIntent;
	private Activity mActivity;
	private MessageWorker messageWorker;

	public ServerHandler() {
		super();
		messageWorker = new MessageWorker();
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
		messageWorker.receivedChannelConnected(e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

		// We do not need to write a ChannelBuffer here.
		// We know the encoder inserted at ServerPipelineFactory will do the
		// conversion.
		ChannelFuture future = null;
		// never close for now
		boolean close = false;
		
		String messageString = (String) e.getMessage();
		// Cast to a String first.

		if (messageString.equals("disconnect")) {
			future = e.getChannel().write("Test End.\r\n");
			close = true;
		} else {

			Message mMessage = messageWorker.parseMessage((String) e
					.getMessage());
			
			if (mMessage instanceof TargetActivityMessage) {
				Log.d(TAG, ((TargetActivityMessage) mMessage)
						.getMessageHeader());
				// the message contain Instrumentation information for the
				// Activity
				String activityClassName = ((TargetActivityMessage) mMessage)
						.getTargetClassName();

				mTargetIntent = new Intent(Intent.ACTION_MAIN);
				mTargetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mTargetIntent.setClassName(mInstrumentation.getTargetContext()
						.getPackageName(), activityClassName);
				mActivity = mInstrumentation.startActivitySync(mTargetIntent);

				// initialize the solo tool
				mSolo = new Solo(mInstrumentation, mActivity);
				// set configuration for message worker
				messageWorker.setConfiguration(mSolo, mActivity,
						mInstrumentation, mTargetIntent);

				// create response message and copy the old message UUID
				Message responseMsg = MessageFactory.createSuccessMessage();
				messageWorker.responseMessage(future, e, responseMsg, mMessage);

			} else if (mMessage instanceof EventInvokeMethodMessage) {
				EventInvokeMethodMessage msg = (EventInvokeMethodMessage) mMessage;
				try {
					messageWorker.receivedEventInvokeMethodMessage(msg, future, e);
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
				}
			} 
		}
		
		if (close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		Message msg = MessageFactory.createExceptionMessage(new Exception(), e
				.getCause().getMessage());
		e.getChannel().write(msg.toString() + "\r\n");
		Log.d(TAG, "Server received Exception", e.getCause());
		// exception may turn the activity off and prevent further testing.
		//messageWorker.startTargetIntent();
     	//e.getChannel().close();
	}

}
