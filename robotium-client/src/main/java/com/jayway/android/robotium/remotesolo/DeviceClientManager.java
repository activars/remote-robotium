package com.jayway.android.robotium.remotesolo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

class DeviceClientManager {
	
	private final Map<String, DeviceClientImpl> devices;
	private Class<?> targetClass;
	DeviceClientManager() {
		// init hashmap storing references to device client
		devices = new ConcurrentHashMap<String, DeviceClientImpl>();
	}
	
	void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}
	
	/**
	 * Connects to a device that will be managed by the DeviceClientManager
	 * 
	 * @param deviceSerial
	 * @param pcPort
	 * @param devicePort
	 */
	void addDevice(String deviceSerial, int pcPort, int devicePort) {
		// the key is => pcPort:devicePort
		// first need to check if the channel has been used
		//  by another device
		String key = pcPort + ":" + devicePort;
		if (devices.containsKey(key))
			throw new IllegalArgumentException(
					"forwarding port "
							+ pcPort
							+ " to "
							+ devicePort
							+ " has already been used.\n\r"
							+ "please use another port forwarding option if you have multiple devices");
		
		// TODO: check if all the parameters are supplied
		DeviceClientImpl device = new DeviceClientImpl(deviceSerial, pcPort, devicePort);
		if (targetClass == null)
			throw new NullPointerException("Missing target Class for intrumentation.");
		else
			device.setTargetClass(targetClass);
		
		// store the device reference
		devices.put(key, device);
	}
	
	
	
	void connectAll() {
		
		Iterator<String> it = devices.keySet().iterator();
		ExecutorService pool = Executors.newFixedThreadPool(devices.size());

		while (it.hasNext()) {
			final DeviceClient dc = devices.get(it.next());
			pool.execute(new Runnable() {
				public void run() {
					// starts the instrumentation server for this app
					ShellCmdHelper.startInstrumentationServer(dc.getDevicePort(), dc.getDeviceSerial());	
					// make device connection
					dc.connect();	
				}	
			});
		}
		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			 e.printStackTrace();
		}
	}

	/**
	 * Sends messages to all devices
	 * @param message
	 */
	void sendMessage(String message) {
		Iterator<String> it = devices.keySet().iterator();
		while (it.hasNext()) {
			devices.get(it.next()).sendMessage(message);
		}
	}
	
	Object invokeMethod(final String methodToExecute, final Class<?>[] argumentTypes, final Object... arguments)  {
		final Iterator<String> it = devices.keySet().iterator();
		ExecutorService pool = Executors.newFixedThreadPool(devices.size());
		final Map<DeviceClient, Object> results = Collections.synchronizedMap(new HashMap<DeviceClient, Object>());
		while (it.hasNext()) {
			final DeviceClient dc = devices.get(it.next());
			pool.execute(new Runnable() {
				public void run() {
					try {
						Object obj = dc.invokeMethod(methodToExecute, argumentTypes, arguments);
						synchronized(results) {
							results.put(dc, obj);
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}	
				}
				
			});
		}
		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			 e.printStackTrace();
		}
		// comparing values, only for primitives
		// collection check the collection size matches
		return results.get(results.keySet().toArray()[0]);
		
	//	return null;
	}

	void disconnectAllDevices() {
		Iterator<String> it = devices.keySet().iterator();
		while (it.hasNext()) {
			devices.get(it.next()).disconnect();
		}
	}

}
