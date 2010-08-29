package com.jayway.android.robotium.remotesolo.proxy;

import com.jayway.android.robotium.common.Message;
import com.jayway.android.robotium.remotesolo.DeviceClient;

public interface MessageContainer {
	
	public void addMessage(Message message);
	
	public void setDeviceClient(DeviceClient device);

}