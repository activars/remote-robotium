package com.jayway.android.robotium.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class RemoteService extends Service {

	private static final String TAG = "RobotiumService";
	private ServerBootstrap bootstrap;
	private ChannelGroup channelGroup;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		if (bootstrap == null) {
			// Configure the server.
			bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
					Executors.newCachedThreadPool(), Executors
							.newCachedThreadPool()));
		}
		if (channelGroup == null)
			channelGroup = new DefaultChannelGroup();
		// Configure the pipeline factory.
		bootstrap.setPipelineFactory(new ServerPipelineFactory());
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		// shut down with channel group
		channelGroup.close().awaitUninterruptibly();
		Log.d(TAG, "channel group count: " + channelGroup.size());
		channelGroup.clear();
		Log.d(TAG, "channel group cleared");
		Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStart(Intent intent, int startid) {
		Log.d(TAG, "onStart");
		// parse the EditText value to integer
		int portNumber = getSharedPreferences(RemoteControlActivity.PREFS, 0)
				.getInt("portNum", 8080);

		// NOTE: This is a work around to prevent the bad address error.
		// This was a bug exposed on Android 2.2
		// http://code.google.com/p/android/issues/detail?id=9431
		System.setProperty("java.net.preferIPv6Addresses", "false");

		// Bind and start to accept incoming connections.
		Channel channel = bootstrap.bind(new InetSocketAddress(portNumber));
		channelGroup.add(channel);
		
		Toast.makeText(this, "Server Started", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "server listening on port " + portNumber);
	}

	

}
