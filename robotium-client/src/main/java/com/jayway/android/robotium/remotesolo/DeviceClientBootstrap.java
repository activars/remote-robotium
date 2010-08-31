package com.jayway.android.robotium.remotesolo;

import java.lang.reflect.Method;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.jayway.android.robotium.remotesolo.proxy.MessageSender;
import com.jayway.android.robotium.remotesolo.proxy.ProxyMessageSender;

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
	
	public void setMessageContainer(MessageSender msgContainer) {
		
		ChannelHandler handler = getChannelHandler();
		
		if(handler != null && handler instanceof ClientHandler) {
			((ClientHandler)handler).setMessageContainer(msgContainer);
		} else {
			throw new NullPointerException("ClientHandler is null and it has not been setup.");
		}
	}
	
	public MessageSender getMessageContainer() {
		ChannelHandler handler = getChannelHandler();
		
		if(handler != null && handler instanceof ClientHandler) {
			return ((ClientHandler)handler).getMessageContainer();
		} else {
			throw new NullPointerException("ClientHandler is null and it has not been setup.");
		}
	}
	
	public Object createObjectProxy(Class<?> targetClass) {
		MessageSender container = getMessageContainer();
		if(container != null && container instanceof ProxyMessageSender) {
			return ((ProxyMessageSender)container).createProxy(targetClass);
		} else {
			throw new NullPointerException("Failed to create proxy for target class");
		}
	}
	
	public Object invokeProxy(Object proxy, Method method, Object[] args) throws Throwable {
		MessageSender container = getMessageContainer();
		if(container != null && container instanceof ProxyMessageSender) {
			return ((ProxyMessageSender)container).invokeProxy(proxy, method, args);
		} else {
			throw new NullPointerException("Failed to create proxy for target class");
		}
	}

}
