package com.jayway.android.robotium.remotesolo.proxy;

import java.lang.reflect.Method;
import org.powermock.reflect.Whitebox;

import com.jayway.android.robotium.common.message.Message;
import com.jayway.android.robotium.remotesolo.DeviceClient;

public class ClientProxyManager implements ProxyManager {
	
	private DeviceClient deviceClient;
	private ClientInvocationHandler invocationHandler;
	
	public ClientProxyManager() {
		invocationHandler = new ClientInvocationHandler();
		invocationHandler.setMessageSender(this);
	}

	/**
	 * The ProxyMessageContainer requires a DeviceClient to be set after its
	 * creation.
	 */
	public void setDeviceClient(DeviceClient device) {
		this.deviceClient = device;
		this.invocationHandler.setDeviceClient(device);
	}

	public Object createProxy(Class<?> classType) {
		Object proxy = ProxyCreator
				.create(classType, invocationHandler);
		return proxy;
	}

	public DeviceClient getDeviceClient() {
		return deviceClient;

	}
	
	public void addMessage(Message message) {
		invocationHandler.addMessage(message);
	}

}
