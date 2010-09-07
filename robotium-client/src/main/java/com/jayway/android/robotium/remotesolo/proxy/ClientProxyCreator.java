package com.jayway.android.robotium.remotesolo.proxy;

import java.lang.reflect.Method;
import org.powermock.reflect.Whitebox;

import com.jayway.android.robotium.common.message.Message;
import com.jayway.android.robotium.remotesolo.DeviceClient;

public class ClientProxyCreator {
	
	private static ClientInvocationHandler invocationHandler  = new ClientInvocationHandler();
	
	public static Object createProxy(Class<?> classType) {
		Object proxy = ProxyCreator
				.create(classType, invocationHandler);
		return proxy;
	}
	
}
