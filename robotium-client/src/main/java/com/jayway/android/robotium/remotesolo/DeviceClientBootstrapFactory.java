package com.jayway.android.robotium.remotesolo;

import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.jayway.android.robotium.remotesolo.proxy.MessageContainer;
import com.jayway.android.robotium.remotesolo.proxy.ProxyMessageContainer;

public class DeviceClientBootstrapFactory {
	
	public static ClientBootstrap create(DeviceClient device) {
		
		ClientBootstrap bootstrap = new DeviceClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors
						.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new ClientPiplineFactory());
		
		//create message container
		MessageContainer msgContainer = new ProxyMessageContainer();
		msgContainer.setDeviceClient(device);
		
		((DeviceClientBootstrap) bootstrap).setMessageContainer(msgContainer);
		bootstrap.setOption("tcpNoDelay", true); 
		bootstrap.setOption("keepAlive", true);
		
		return bootstrap;
	}
}
