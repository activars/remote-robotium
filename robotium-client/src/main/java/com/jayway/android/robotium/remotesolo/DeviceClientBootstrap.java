package com.jayway.android.robotium.remotesolo;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;


public class DeviceClientBootstrap extends ClientBootstrap {
	
	public DeviceClientBootstrap(NioClientSocketChannelFactory socketChannelFacotry) {
		super(socketChannelFacotry);
	}

}
