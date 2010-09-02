package com.jayway.android.robotium.server;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
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
import com.jayway.android.robotium.solo.ISolo;
import com.jayway.android.robotium.solo.Solo;

class ServerHandler extends SimpleChannelUpstreamHandler {

	private static final Logger logger = Logger.getLogger(ServerHandler.class
			.getName());
	private static final String TAG = "ServerHandler";

	private ISolo mSolo;
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

		// Cast to a String first.
		Message mMessage = messageWorker.parseMessage((String) e.getMessage());
		String response = "";

		if (mMessage instanceof TargetActivityMessage) {
			Log.d(TAG, ((TargetActivityMessage) mMessage).getMessageHeader());
			// the message contain Instrumentation information for the Activity
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
			messageWorker.setConfiguration(mSolo, mActivity, mInstrumentation,
					mTargetIntent);

			// create response message and copy the old message UUID
			Message responseMsg = MessageFactory.createSuccessMessage();
			messageWorker.responseMessage(future, e, responseMsg, mMessage);

		} else if (mMessage instanceof EventInvokeMethodMessage) {
			EventInvokeMethodMessage msg = (EventInvokeMethodMessage) mMessage;
			messageWorker.receivedEventInvokeMethodMessage(msg, future, e);
		} else {
			// the event is calling on other object
			// server should have a weak reference to the object that is
			// referenced.
			// TODO: event is calling on other object
			// use UUID from message to find the object and cast it to the
			// type.
			Log.d(TAG, "Calling on other object and need"
					+ " to look up in the WeakHashMap");
		}

		// never close for now
		boolean close = false;

		// Close the connection after sending 'Have a good day!'
		// if the client has sent 'bye'.
		if (close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		Message msg = MessageFactory.createExceptionMessage(new Exception(), e
				.getCause().getMessage());
		e.getChannel().write(msg.toString() + "\r\n");

		// exception may turn the activity off and prevent further testing.
		messageWorker.startTargetIntent();

		// e.getChannel().close();
	}

}
