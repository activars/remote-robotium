package com.jayway.android.robotium.remotesolo;

import java.util.logging.Logger;

import junit.framework.Assert;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.jayway.android.robotium.common.message.Message;
import com.jayway.android.robotium.common.message.MessageFactory;
import com.jayway.android.robotium.common.message.SuccessMessage;
import com.jayway.android.robotium.common.message.TargetActivityRequestMessage;


public class ClientHandler extends SimpleChannelHandler {
	
	private DeviceClient device;
	
	private static final Logger logger = Logger.getLogger(ClientHandler.class
			.getName());
	
	
	public void setDeviceClient(DeviceClient device) {
		this.device = device;
	}


	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		if (e instanceof ChannelStateEvent) {
			logger.info(e.toString());
		}
		super.handleUpstream(ctx, e);

	}

	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
		try {
			super.channelClosed(ctx, e);
			device.disconnect();
		} catch (Exception e1) {
		}
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

		if (device != null) {

			String messageString = (String) e.getMessage();
			Message message = null;
			if (messageString.equals("Test End.")) {

			} else {

				try {
					message = MessageFactory.parseMessageString(messageString);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				if (message instanceof SuccessMessage) {
					MessageWorker.addMessage(message);
				} else if (message instanceof TargetActivityRequestMessage) {
					// server requested a message about Instrumentation class
					Class<?> activityClass = device.getTargetClass();
					e.getChannel().write(
							MessageFactory.createTargetActivityMessage(
									activityClass).toString()
									+ "\r\n");
				} else if (message != null) {
					MessageWorker.addMessage(message);
				}
			}

		} else {
			throw new NullPointerException(
					"Message container wasn't initialised.");
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		String failMsg = String.format("Device %s caught exception: \r\n %s",
				device.getDeviceSerial(), e.getCause().toString());
		
	}
}