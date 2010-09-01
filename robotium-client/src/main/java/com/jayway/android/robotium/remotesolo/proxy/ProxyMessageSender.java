package com.jayway.android.robotium.remotesolo.proxy;

import java.lang.reflect.Method;
import org.powermock.reflect.Whitebox;

import com.jayway.android.robotium.common.Message;
import com.jayway.android.robotium.remotesolo.DeviceClient;

public class ProxyMessageSender implements MessageSender {
	
	private DeviceClient deviceClient;
	private static ClientInvocationHandler invocationHandler = new ClientInvocationHandler();
	
	public ProxyMessageSender() {
	}

	/**
	 * The ProxyMessageContainer requires a DeviceClient to be set after its
	 * creation.
	 */
	@SuppressWarnings("static-access")
	public void setDeviceClient(DeviceClient device) {
		this.deviceClient = device;
		this.invocationHandler.setDeviceClient(device);
	}

	public static Object createProxy(Object target) {
		Object proxy = ProxyCreator
				.create(target.getClass(), invocationHandler);
		return proxy;
	}

	public static Object createProxy(Class<?> targetClass) {
		Object target = Whitebox.newInstance(targetClass);
		Object proxy = createProxy(target);
		return proxy;
	}

	public Object invokeProxy(Object proxy, Method method, Object[] args)
			throws Throwable {
		return invocationHandler.invoke(proxy, method, args);
	}

	public DeviceClient getDeviceClient() {
		return deviceClient;

	}
	
	public void addMessage(Message message) {
		invocationHandler.addMessage(message);
	}

}
