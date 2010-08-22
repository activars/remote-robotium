package com.jayway.android.robotium.remotesolo;


public class TestDummy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DeviceClientManager dcm = new DeviceClientManager();
		dcm.connectDevice("HT04TP800408", 5001, 8080);
		dcm.connectDevice("emulator-5554", 5000, 5000);
		
		dcm.sendMessage("hello");
		dcm.disconnectAllDevices();
	}
	
	

}
