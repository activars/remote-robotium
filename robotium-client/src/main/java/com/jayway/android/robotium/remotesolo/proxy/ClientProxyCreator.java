package com.jayway.android.robotium.remotesolo.proxy;

public class ClientProxyCreator {
	
	private static ClientInvocationHandler invocationHandler  = new ClientInvocationHandler();
	
	public static Object createProxy(Class<?> classType) {
		Object proxy = ProxyCreator
				.create(classType, invocationHandler);
		return proxy;
	}
	
}
