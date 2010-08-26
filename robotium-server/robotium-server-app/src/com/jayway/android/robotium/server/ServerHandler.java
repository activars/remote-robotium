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
import android.content.Intent;

import com.jayway.android.robotium.solo.ISolo;
import com.jayway.android.robotium.solo.Solo;

class ServerHandler extends SimpleChannelUpstreamHandler {

	private static final Logger logger = Logger.getLogger(ServerHandler.class
			.getName());

	private static ISolo mSolo;
	private static Instrumentation mInstrumentation;
	private static Activity mActivity;
	
    static void injectInstrumentation(Instrumentation inst) {
    	mInstrumentation = inst;
//		testing code: can use injected instrumentation to run launch events as usual.
//
//    	Intent intent = new Intent(Intent.ACTION_MAIN);
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);	
//		intent.setClassName(mInstrumentation.getTargetContext().getPackageName(), 
//				mInstrumentation.getTargetContext().getPackageName() + ".NotesList");
//		mActivity = mInstrumentation.startActivitySync(intent);
		
//		mSolo = new Solo(mInstrumentation, mActivity);
		
//		mSolo.clickOnMenuItem("Add note");
//		mSolo.assertCurrentActivity("Expected NoteEditor activity", "NoteEditor"); //Assert that NoteEditor activity is opened
//		mSolo.enterText(0, "Note 1"); //Add note
//		mSolo.goBack();
//		mSolo.clickOnMenuItem("Add note"); //Clicks on menu item 
//		mSolo.enterText(0, "Note 2"); //Add note
//		mSolo.goBack();
    }
    
    static void injectActivity(Activity act) {
    	mActivity = act;
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
		// Send greeting for a new connection.
		 e.getChannel().write(
		 "Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
		 e.getChannel().write("It is " + new Date() + " now.\r\n");
		
		
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

		// Cast to a String first.
		// We know it is a String because we put some codec in
		// TelnetPipelineFactory.
		String request = (String) e.getMessage();

		// Generate and write a response.
		String response;
		boolean close = false;
		if (request.length() == 0) {
			response = "Please type something.\r\n";
		} else if (request.toLowerCase().equals("bye")) {
			response = "Have a good day!\r\n";
			close = true;
		} else {
			response = "Did you say '" + request + "'?\r\n";
		}

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
