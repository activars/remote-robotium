package com.jayway.android.robotium.remotesolo;

import java.lang.reflect.Method;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.jayway.android.robotium.remotesolo.proxy.ProxyManager;
import com.jayway.android.robotium.remotesolo.proxy.ClientProxyManager;

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
	
	public void setMessageContainer(ProxyManager msgContainer) {
		
		ChannelHandler handler = getChannelHandler();
		
		if(handler != null && handler instanceof ClientHandler) {
			((ClientHandler)handler).setMessageContainer(msgContainer);
		} else {
			throw new NullPointerException("ClientHandler is null and it has not been setup.");
		}
	}
	
	public ProxyManager getMessageContainer() {
		ChannelHandler handler = getChannelHandler();
		
		if(handler != null && handler instanceof ClientHandler) {
			return ((ClientHandler)handler).getMessageContainer();
		} else {
			throw new NullPointerException("ClientHandler is null and it has not been setup.");
		}
	}
	
	public Object createObjectProxy(Class<?> targetClass) {
		ProxyManager container = getMessageContainer();
		if(container != null && container instanceof ClientProxyManager) {
			return ((ClientProxyManager)container).createProxy(targetClass);
		} else {
			throw new NullPointerException("Failed to create proxy for target class");
		}
	}

}
