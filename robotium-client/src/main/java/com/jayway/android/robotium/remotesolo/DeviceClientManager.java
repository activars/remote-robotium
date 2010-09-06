package com.jayway.android.robotium.remotesolo;

import java.awt.event.ActionListener;
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

import junit.framework.Assert;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.jayway.android.robotium.common.util.TypeUtils;

class DeviceClientManager {

	private Class<?> targetClass;

	DeviceClientManager() {
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
		DeviceClientImpl.newInstance(deviceSerial, pcPort, devicePort,
				targetClass);
	}

	void connectAll() {
		final Map<String, DeviceClient> devices = DeviceClientImpl
				.getCurrentDevices();
		Iterator<String> it = devices.keySet().iterator();
		ExecutorService pool = Executors.newFixedThreadPool(devices.size());
		final ArrayList<DeviceClient> failedToConnect = new ArrayList<DeviceClient>();
		final Object lock = new Object();
		while (it.hasNext()) {
			final DeviceClient dc = devices.get(it.next());
			pool.execute(new Runnable() {
				public void run() {
					// make device connection
					boolean connected = dc.connect();
					if (!connected) {
						synchronized (lock) {
							failedToConnect.add(dc);
							String errMsg = String
									.format(
											"Device %s failed connecting to Robotium server.",
											dc.getDeviceSerial());
							System.err.println(errMsg);
						}
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
	}

	Object invokeMethod(final String methodToExecute,
			final Class<?>[] argumentTypes, final Object... arguments)
			throws RemoteException, InconsistentResultException {
		final Map<String, DeviceClient> devices = DeviceClientImpl
				.getCurrentDevices();
		Iterator<String> it = devices.keySet().iterator();
		if (devices.size() == 0) {
			throw new RemoteException(
					"Server Error: Robotium test server is disconnected");
		}
		ExecutorService pool = Executors.newFixedThreadPool(devices.size());
		final Map<DeviceClient, Object> results = Collections
				.synchronizedMap(new HashMap<DeviceClient, Object>());
		while (it.hasNext()) {
			final DeviceClient dc = devices.get(it.next());
			pool.execute(new Runnable() {
				public void run() {
					try {
						Object obj = dc.invokeMethod(methodToExecute,
								argumentTypes, arguments);
						synchronized (results) {
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
		}
		// comparing values, only for primitives
		// collection check the collection size matches
		String methodDetails = methodToExecute + ":" +  TypeUtils.getArgumentsStringValue(arguments);
		return ResultChecker.checkConsistancy(results, methodDetails);
	}

	void disconnectAllDevices() throws RemoteException {
		final Map<String, DeviceClient> devices = DeviceClientImpl
				.getCurrentDevices();
		Iterator<String> it = devices.keySet().iterator();
		while (it.hasNext()) {
			devices.get(it.next()).disconnect();
		}
	}

}
