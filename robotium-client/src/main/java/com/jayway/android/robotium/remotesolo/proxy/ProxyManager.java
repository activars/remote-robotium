package com.jayway.android.robotium.remotesolo.proxy;

import com.jayway.android.robotium.common.message.Message;
import com.jayway.android.robotium.remotesolo.DeviceClient;

public interface ProxyManager {
	
	
	public Object createProxy(Class<?> classType);
	
	public void addMessage(Message message);
	
	public DeviceClient getDeviceClient();

}