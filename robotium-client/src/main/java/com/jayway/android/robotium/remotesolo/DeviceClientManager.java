package com.jayway.android.robotium.remotesolo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

class DeviceClientManager {
	
	private Map<String, DeviceClient> devices;

	DeviceClientManager() {
		// init hashmap storing references to device client
		devices = new HashMap<String, DeviceClient>();
	}
	
	/**
	 * Connects to a device that will be managed by the DeviceClientManager
	 * 
	 * @param deviceSerial
	 * @param pcPort
	 * @param devicePort
	 */
	void connectDevice(String deviceSerial, int pcPort, int devicePort) {
		// TODO: check if all the parameters are supplied
		DeviceClient device = new DeviceClient(deviceSerial, pcPort, devicePort);
		device.connect();
		
		// the key is => pcPort:devicePort
		String key = pcPort + ":" + devicePort;
		if (devices.containsKey(key))
			throw new IllegalArgumentException(
					"forwarding port "
							+ pcPort
							+ " to "
							+ devicePort
							+ " has already been used.\n\r"
							+ "please use another port forwarding option if you have multiple devices");
		devices.put(key, device);
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

	void disconnectAllDevices() {
		Iterator<String> it = devices.keySet().iterator();
		while (it.hasNext()) {
			devices.get(it.next()).disconnect();
		}
	}

}
