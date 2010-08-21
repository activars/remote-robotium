package com.jayway.android.robotium.remotesolo;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;


import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.jayway.maven.plugins.android.CommandExecutor;
import com.jayway.maven.plugins.android.ExecutionException;

public class SoloClient {
	private ClientBootstrap bootstrap;
	private int pcPort;
	private int devicePort;
	private String deviceSerial;
	private String host;
	private Channel channel;
	private ChannelFuture lastWriteFuture;
	
	
	public SoloClient(int pcPort, int devicePort, String deviceSerial) {
		initialize("localhost", pcPort, devicePort, deviceSerial);
	}

	public SoloClient(int pcPort, int devicePort) {
		initialize("localhost", pcPort, devicePort, "");
	}

	private void initialize(String host, int pcPort, int devicePort, String deviceSerial) {
		this.host = host;
		this.pcPort = pcPort;
		this.devicePort = devicePort;
		this.deviceSerial = deviceSerial;
		
		// configure the client
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors
						.newCachedThreadPool()));

		bootstrap.setPipelineFactory(new ClientPiplineFactory());
		
		forwardingPort(this.pcPort, this.devicePort, this.deviceSerial);

		// Start the connection attempt.
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(
				this.host, this.pcPort));

		// Wait until the connection attempt succeeds or fails.
		channel = future.awaitUninterruptibly().getChannel();
		if (!future.isSuccess()) {
			future.getCause().printStackTrace();
			bootstrap.releaseExternalResources();
			return;
		}
	}

	public boolean sendMessage(String msg) {
		if (this.channel.isConnected()) {
			if (!msg.endsWith("\n")) {
				this.channel.write(msg + '\n');
			} else {
				this.channel.write(msg);
			}
			
			return true;
		}
		return false;
	}

	public void close() {

		String msg = "bye";
		lastWriteFuture = channel.write(msg + "\r\n");

		// wait until the server closes the connection.
		channel.getCloseFuture().awaitUninterruptibly();

		// Wait until all messages are flushed before closing the channel.
		if (lastWriteFuture != null) {
			lastWriteFuture.awaitUninterruptibly();
		}

		// Close the connection. Make sure the close operation ends because
		// all I/O operations are asynchronous in Netty.
		channel.close().awaitUninterruptibly();

		// Shut down all thread pools to exit.
		bootstrap.releaseExternalResources();
	}

	private void forwardingPort(int pcPort, int devicePort, String deviceSerial) {
		CommandExecutor executor = CommandExecutor.Factory
				.createDefaultCommmandExecutor();
		List<String> commands = new ArrayList<String>();
		if(!deviceSerial.equals("") && deviceSerial != null) {
			commands.add("-s");
			commands.add(deviceSerial);
		}
		commands.add("forward");
		commands.add("tcp:" + pcPort);
		commands.add("tcp:" + devicePort);

		try {
			executor.executeCommand("adb", commands, false);
		} catch (ExecutionException e) {
			// this happens when multiple devices connected
			// or the envirment varialbe wasn't setup property
			// need to have ANDROID_HOME setup
			e.printStackTrace();
		}

	}

}
