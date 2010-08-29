package com.jayway.android.robotium.remotesolo;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.jayway.android.robotium.remotesolo.proxy.MessageContainer;
import com.jayway.android.robotium.remotesolo.proxy.ProxyMessageContainer;

public class DeviceClientBootstrap extends ClientBootstrap {
	
	public DeviceClientBootstrap(NioClientSocketChannelFactory socketChannelFacotry) {
		super(socketChannelFacotry);
	}
	
	private ChannelHandler getChannelHandler() {
		
		try {
			return getPipelineFactory().getPipeline().get("handler");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setMessageContainer(MessageContainer msgContainer) {
		
		ChannelHandler handler = getChannelHandler();
		
		if(handler != null && handler instanceof ClientHandler) {
			((ClientHandler)handler).setMessageContainer(msgContainer);
		} else {
			throw new NullPointerException("ClientHandler is null and it has not been setup.");
		}
	}
	
	public MessageContainer getMessageContainer() {
		ChannelHandler handler = getChannelHandler();
		
		if(handler != null && handler instanceof ClientHandler) {
			return ((ClientHandler)handler).getMessageContainer();
		} else {
			throw new NullPointerException("ClientHandler is null and it has not been setup.");
		}
	}
	
	public Object createObjectProxy(Class<?> targetClass) {
		MessageContainer container = getMessageContainer();
		if(container != null && container instanceof ProxyMessageContainer) {
			return ((ProxyMessageContainer)container).createProxy(targetClass);
		} else {
			throw new NullPointerException("Failed to create proxy for target class");
		}
	}

}
