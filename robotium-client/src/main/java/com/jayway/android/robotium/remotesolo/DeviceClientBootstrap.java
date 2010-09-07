package com.jayway.android.robotium.remotesolo;

import java.lang.reflect.Method;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.jayway.android.robotium.remotesolo.proxy.ClientProxyCreator;

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

}
