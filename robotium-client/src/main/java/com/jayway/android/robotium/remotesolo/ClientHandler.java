package com.jayway.android.robotium.remotesolo;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.jayway.android.robotium.remotesolo.proxy.MessageContainer;

public class ClientHandler extends SimpleChannelHandler {
	private MessageContainer msgContainer;
	
	private static final Logger logger = Logger.getLogger(ClientHandler.class
			.getName());
	
	/**
	 * Dependency injection.
	 * 
	 * @param container
	 */
	public void setMessageContainer(MessageContainer container) {
		this.msgContainer = container;
	}
	
	public MessageContainer getMessageContainer() {
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
		// Print out the line received from the server.
		System.out.println(e.getMessage());
		if (msgContainer != null)
			//msgContainer.addMessage(message)
			;
		else
			throw new NullPointerException("Message container wasn't initialised.");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		logger.log(Level.WARNING, "Unexpected exception from downstream.", e
				.getCause());
		e.getChannel().close();
	}
}