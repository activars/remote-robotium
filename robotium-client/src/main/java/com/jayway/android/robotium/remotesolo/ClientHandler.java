package com.jayway.android.robotium.remotesolo;

import java.awt.Container;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.jayway.android.robotium.common.Message;
import com.jayway.android.robotium.common.MessageFactory;
import com.jayway.android.robotium.common.SuccessMessage;
import com.jayway.android.robotium.common.TargetActivityRequestMessage;
import com.jayway.android.robotium.remotesolo.proxy.MessageSender;

public class ClientHandler extends SimpleChannelHandler {
	private MessageSender msgContainer;

	private static final Logger logger = Logger.getLogger(ClientHandler.class
			.getName());

	/**
	 * Dependency injection.
	 * 
	 * @param container
	 */
	public void setMessageContainer(MessageSender container) {
		this.msgContainer = container;
	}

	public MessageSender getMessageContainer() {
		return this.msgContainer;
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
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		
		if (msgContainer != null) {
		    
			String messageString = (String) e.getMessage();
			Message message = null;
			try {
				message = MessageFactory.parseMessageString(messageString);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			

			if (message instanceof SuccessMessage) {
				System.out.println("Server is happy");
				msgContainer.addMessage(message);
			} else if (message instanceof TargetActivityRequestMessage) {
				// server requested a message about Instrumentation class
				Class activityClass = msgContainer.getDeviceClient()
						.getTargetClass();
				e.getChannel().write(
						MessageFactory.createTargetActivityMessage(
								activityClass).toString()
								+ "\r\n");
			} else if (message != null){
				msgContainer.addMessage(message);
			}

		} else {
			throw new NullPointerException(
					"Message container wasn't initialised.");
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		logger.log(Level.WARNING, "Unexpected exception from downstream.", e
				.getCause());
		e.getChannel().close();
	}
}