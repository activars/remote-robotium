package com.jayway.android.robotium.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class RemoteService extends Service {

	private static final String TAG = "Service";
	MediaPlayer player;
	private ServerBootstrap bootstrap;
	
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
		// Configure the pipeline factory.
		bootstrap.setPipelineFactory(new TelnetServerPipelineFactory());
	} 

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onDestroy");
		bootstrap.releaseExternalResources();
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "Server Started", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onStart");
		// Bind and start to accept incoming connections.
		bootstrap.bind(new InetSocketAddress(8080));
	}
	
	

}
