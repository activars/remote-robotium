package com.jayway.android.robotium.server;


import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

public class InstrumentationRunner extends Instrumentation {
	
	private static final String TAG = "InstrumentationRunner";
	public static boolean isClosed = true;
	private ServerBootstrap bootstrap;
	private ChannelGroup channelGroup;
	
	
	@Override
	public void onCreate(Bundle arguments) {
		super.onCreate(arguments);
		Log.d(TAG, "Intrumentation calling onCreate");
		
		//default port number for running server
		int portNum = 8080;
		if(arguments.containsKey("port")) {
			String portString = arguments.getString("port");
			portNum = Integer.parseInt(portString);
		}
		
		Log.d(TAG, "Obtained port number from shared pref:" + portNum);
		
		
		bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors
				.newCachedThreadPool()));
		
		bootstrap.setOption("keepAlive", true);
		
		Log.d(TAG, "Server bootstrap initialized");
		
		// channel group is used for closing connection properly
		channelGroup = new DefaultChannelGroup();
		
		// Configure the pipeline factory.
		// pipline created internally, the ServerHandler can be obtained by: 
		// ((ServerHandler)bootstrap.getPipeline().get(ServerPipelineFactory.SERVER_HANDLER))
		// or less safely:
		// ((ServerHandler)bootstrap.getPipeline().getLast())
		ServerPipelineFactory pipelineFactory = new ServerPipelineFactory();
		pipelineFactory.setInstrumentation(this);
		bootstrap.setPipelineFactory(pipelineFactory);
		Log.d(TAG, "Configured server pipline factory");
		
		// NOTE: This is a work around to prevent the bad address error.
		// This was a bug exposed on Android 2.2
		// http://code.google.com/p/android/issues/detail?id=9431
		System.setProperty("java.net.preferIPv6Addresses", "false");
		
		// Bind and start to accept incoming connections.
		Channel channel = bootstrap.bind(new InetSocketAddress(portNum));
		channelGroup.add(channel);
		Log.d(TAG, "Server is now running");
		
		start();
	}

	
	/**
	 * Starts Instrumentation and pass itself to server handler
	 */
	@Override
	public void onStart() {
		Looper.prepare();
		
		Log.d(TAG, "Intrumentation started");
	}
	
	public void onDestroy() {
		Log.d(TAG, "Intrumentation Destroyed");
	}
	
	
	
}
