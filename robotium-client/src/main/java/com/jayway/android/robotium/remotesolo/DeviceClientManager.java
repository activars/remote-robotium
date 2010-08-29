package com.jayway.android.robotium.remotesolo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

class DeviceClientManager {
	
	private Map<String, DeviceClientImpl> devices;
	private Class<?> targetClass;
	DeviceClientManager() {
		// init hashmap storing references to device client
		devices = new HashMap<String, DeviceClientImpl>();
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
		
		//TODO: Adding timing
		Iterator<String> it = devices.keySet().iterator();
		DeviceClient dc = null;
		while (it.hasNext()) {
			dc = devices.get(it.next());
			
			// starts the server on device/emulator
			ShellCmdHelper.startRobotiumServer(dc.getDevicePort(), dc.getDeviceSerial());
			// make device connection
			dc.connect();
			
			// TODO: first connection sending Activity class info? 
			// attempt
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
	
	Object invokeMethod(String methodToExecute, Class<?>[] argumentTypes, Object... arguments) throws Exception {
		Iterator<String> it = devices.keySet().iterator();
		while (it.hasNext()) {
			return devices.get(it.next()).invokeMethod(methodToExecute, argumentTypes, arguments);
		}
		
		return null;
	}

	void disconnectAllDevices() {
		Iterator<String> it = devices.keySet().iterator();
		while (it.hasNext()) {
			devices.get(it.next()).disconnect();
		}
	}

}
