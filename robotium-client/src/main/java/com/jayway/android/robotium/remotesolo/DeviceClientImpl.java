package com.jayway.android.robotium.remotesolo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import junit.framework.Assert;


import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

import com.jayway.android.robotium.remotesolo.proxy.ClientInvocationHandler;
import com.jayway.android.robotium.remotesolo.proxy.ProxyCreator;
import com.jayway.android.robotium.solo.Solo;


public class DeviceClientImpl implements DeviceClient {
	
	private static Map<String, DeviceClient> devicesRepostory =  new ConcurrentHashMap<String, DeviceClient>();
	private int pcPort;
	private int devicePort;
	private Class<?> targetClass;
	private String deviceSerial;
	private String host;
	private Channel channel;
	private ChannelFuture lastWriteFuture;
	private ClientBootstrap bootstrap;
	private Solo mSoloProxy;
	
	public static DeviceClient newInstance(String deviceSerial, int pcPort, int devicePort, Class<?> targetClass) {
		// the key is => pcPort:devicePort
		// first need to check if the channel has been used
		//  by another device
		String key = pcPort + ":" + devicePort;
		DeviceClient device = devicesRepostory.get(key);
		
		if(device == null) {
			device = new DeviceClientImpl(deviceSerial, pcPort, devicePort);
			if (targetClass == null)
				throw new NullPointerException("Missing target Class for intrumentation.");
			else
				device.setTargetClass(targetClass);
			
			// store the device reference
			devicesRepostory.put(key, device);
		}
		
		return device;
	}
	
	public synchronized static Map<String, DeviceClient> getCurrentDevices() {
		return devicesRepostory;
	}

	protected DeviceClientImpl(String deviceSerial, int pcPort, int devicePort) {
		initialize("localhost", deviceSerial, pcPort, devicePort);
	}

	protected DeviceClientImpl(int pcPort, int devicePort) {
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
	public boolean connect() {

		// configure the client
		bootstrap = DeviceClientBootstrapFactory.create(this);
		
		// forwarding port using adb shell
		ShellCmdHelper.forwardingPort(this.pcPort, this.devicePort,
				this.deviceSerial);
		
		// starts the instrumentation server for this app
		ShellCmdHelper.startInstrumentationServer(pcPort, deviceSerial);
		
		// Start the connection attempt.
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(
				this.host, this.pcPort)); 
		
		
		// Wait until the connection attempt succeeds or fails.
		channel = future.awaitUninterruptibly().getChannel();
		if (!future.isSuccess()) {
			bootstrap.releaseExternalResources();
			return false;
		}
		return true;
	}

	/**
	 * send a message to the remote device
	 * 
	 * @param msg
	 * @return
	 * @throws RemoteException 
	 */
	public void sendMessage(String msg) throws RemoteException {
		if (this.channel.isConnected()) {
			if (!msg.endsWith("\r\n")) {
				this.channel.write(msg + "\r\n");
			} else {
				this.channel.write(msg);
			}
		}else{
			this.disconnect();
		}
	}
	
	
	public Object invokeMethod(String methodToExecute, Class<?>[] argumentTypes, Object... arguments) throws Throwable {

		// create Proxy object for solo class when needed
		mSoloProxy = (Solo) ((DeviceClientBootstrap)bootstrap).createObjectProxy(Solo.class);
		// the invoked method
		Method receivedMethod = mSoloProxy.getClass().getMethod(methodToExecute, argumentTypes);
		// invoke, should bubble up to the ClientInvocationHandler
		return receivedMethod.invoke(mSoloProxy, arguments);
	}

	/**
	 * Close the connection
	 * @throws RemoteException 
	 */
	public void disconnect() throws RemoteException {

		if (channel != null && channel.isConnected()) {
			String msg = "disconnect";
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
			devicesRepostory.remove(getKey());
		}
	}
	
	public String getKey() {
		return this.pcPort + ":" + this.devicePort;
	}

	public Class<?> getTargetClass() {
		return this.targetClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

}
