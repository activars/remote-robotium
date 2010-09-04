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
import com.jayway.android.robotium.remotesolo.proxy.ProxyManager;

public class ClientHandler extends SimpleChannelHandler {
	private ProxyManager proxyManager;

	private static final Logger logger = Logger.getLogger(ClientHandler.class
			.getName());

	/**
	 * Dependency injection.
	 * 
	 * @param container
	 */
	public void setMessageContainer(ProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}

	public ProxyManager getMessageContainer() {
		return this.proxyManager;
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
			proxyManager.getDeviceClient().disconnect();
		} catch (Exception e1) {
		}
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

		if (proxyManager != null) {

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
					proxyManager.addMessage(message);
				} else if (message instanceof TargetActivityRequestMessage) {
					// server requested a message about Instrumentation class
					Class activityClass = proxyManager.getDeviceClient()
							.getTargetClass();
					e.getChannel().write(
							MessageFactory.createTargetActivityMessage(
									activityClass).toString()
									+ "\r\n");
				} else if (message != null) {
					proxyManager.addMessage(message);
				}
			}

		} else {
			throw new NullPointerException(
					"Message container wasn't initialised.");
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		DeviceClient device = proxyManager.getDeviceClient();
		String failMsg = String.format("Device %s caught exception: \r\n %s",
				device.getDeviceSerial(), e.getCause().toString());
		Assert.fail(failMsg);
	}
}