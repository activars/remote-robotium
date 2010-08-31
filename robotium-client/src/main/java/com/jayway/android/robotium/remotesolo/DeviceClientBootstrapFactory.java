package com.jayway.android.robotium.remotesolo;

import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.jayway.android.robotium.remotesolo.proxy.MessageSender;
import com.jayway.android.robotium.remotesolo.proxy.ProxyMessageSender;

public class DeviceClientBootstrapFactory {
	
	public static ClientBootstrap create(DeviceClient device) {
		
		ClientBootstrap bootstrap = new DeviceClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors
						.newCachedThreadPool()));
		
		//PiplineFactory dependency setup
		ClientPiplineFactory piplineFactory = new ClientPiplineFactory();
		piplineFactory.setDeviceClient(device);
		
		//Bootstrap dependency setup
		bootstrap.setPipelineFactory(piplineFactory);
		
		bootstrap.setOption("tcpNoDelay", true); 
		bootstrap.setOption("keepAlive", true);
		
		return bootstrap;
	}
}
