package com.jayway.android.robotium.server;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class RemoteService extends Service {

	private static final String TAG = "Service";
	private ServerBootstrap bootstrap;
	private ChannelGroup channelGroup;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		if(bootstrap == null) {
			// Configure the server.
			bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
					                         Executors.newCachedThreadPool(),
					                          Executors.newCachedThreadPool()));
		}
		if (channelGroup == null) 
			channelGroup = new DefaultChannelGroup();
		// Configure the pipeline factory.
		bootstrap.setPipelineFactory(new TelnetServerPipelineFactory());
	} 

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onDestroy");
		// shut down with channel group
		channelGroup.close().awaitUninterruptibly();
		Log.d(TAG, "channel group count: " + channelGroup.size());
		channelGroup.clear();
		Log.d(TAG, "channel group cleared");
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "Server Started", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onStart");
		// parse the EditText value to integer
		int portNumber = getSharedPreferences(RemoteControlActivity.PREFS, 0).getInt("portNumber", 8080);
		// prevent the bad address error
		//System.setProperty("java.net.preferIPv6Addresses", "false");
		// Bind and start to accept incoming connections.
		Channel channel = bootstrap.bind(new InetSocketAddress(portNumber));
		channelGroup.add(channel);
	}
	
	
	
	

}
