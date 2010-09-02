package com.jayway.android.robotium.remotesolo;

import static org.jboss.netty.channel.Channels.*;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import com.jayway.android.robotium.remotesolo.proxy.ClientProxyManager;

public class ClientPiplineFactory implements ChannelPipelineFactory {
	
	private DeviceClient device;
	
	public void setDeviceClient(DeviceClient device) {
		this.device = device;
	}
	
	
	public ChannelPipeline getPipeline() throws Exception {
		// Create a default pipeline implementation.
		ChannelPipeline pipeline = pipeline();

		// Add the text line codec combination first,
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
				Delimiters.lineDelimiter()));
		
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());
		
		ClientHandler handler = new ClientHandler();
		ClientProxyManager container = new ClientProxyManager();
		container.setDeviceClient(device);
		handler.setMessageContainer(container);
		// and then business logic.
		pipeline.addLast("handler", handler);

		return pipeline;
	}
}
