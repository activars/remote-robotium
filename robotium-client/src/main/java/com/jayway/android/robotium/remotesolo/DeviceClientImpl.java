package com.jayway.android.robotium.remotesolo;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.powermock.reflect.Whitebox;

import com.jayway.android.robotium.solo.ISolo;
import com.jayway.android.robotium.solo.Solo;
import com.jayway.maven.plugins.android.CommandExecutor;
import com.jayway.maven.plugins.android.ExecutionException;

public class DeviceClientImpl implements DeviceClient {
	private int pcPort;
	private int devicePort;
	private String deviceSerial;
	private String host;
	private Channel channel;
	private ChannelFuture lastWriteFuture;
	private ClientBootstrap bootstrap;
	private ISolo mSolo;

	public DeviceClientImpl(String deviceSerial, int pcPort, int devicePort) {
		initialize("localhost", deviceSerial, pcPort, devicePort);
	}

	public DeviceClientImpl(int pcPort, int devicePort) {
		initialize("localhost", null, pcPort, devicePort);
	}

	// helper to setup the variables
	private void initialize(String host, String deviceSerial, int pcPort,
			int devicePort) {
		this.host = host;
		this.pcPort = pcPort;
		this.devicePort = devicePort;
		this.deviceSerial = deviceSerial;
	}
	
	/**
	 * Returns the PC port number
	 */
	public int getPcPort() {
		return this.pcPort;
	}
	
	/**
	 * Returns the device port number
	 */
	public int getDevicePort() {
		return this.devicePort;
	}
	
	/**
	 * Returns the device serial number
	 */
	public String getDeviceSerial() {
		return this.deviceSerial;
	}
	
	/**
	 * Tries to connect to the remote device
	 */
	public void connect() {

		// configure the client
		bootstrap = DeviceClientBootstrapFactory.create(this);
		
		// forwarding port using adb shell
		ShellCmdHelper.forwardingPort(this.pcPort, this.devicePort,
				this.deviceSerial);

		// Start the connection attempt.
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(
				this.host, this.pcPort));
		
		// create proxy object for solo class
		mSolo = (ISolo) ((DeviceClientBootstrap)bootstrap).createObjectProxy(Solo.class);
		
		// Wait until the connection attempt succeeds or fails.
		channel = future.awaitUninterruptibly().getChannel();
		if (!future.isSuccess()) {
			future.getCause().printStackTrace();
			bootstrap.releaseExternalResources();
			return;
		}
	}

	/**
	 * send a message to the remote device
	 * 
	 * @param msg
	 * @return
	 */
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
	
	
	public Object invokeMethod(String methodToExecute, Class<?>[] argumentTypes, Object... arguments) throws Exception {
		return Whitebox.invokeMethod(mSolo, methodToExecute, argumentTypes, arguments);
	}

	
	/**
	 * Close the connection
	 */
	public void disconnect() {

		if (channel.isConnected()) {
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
		}
		// Shut down all thread pools to exit.
		bootstrap.releaseExternalResources();
	}

}
