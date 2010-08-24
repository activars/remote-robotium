package com.jayway.android.robotium.remotesolo;


public class TestDummy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DeviceClientManager dcm = new DeviceClientManager();
		// helper to start server
		//ideally for setup process
		ShellCmdHelper.startRobotiumServer(5001, "emulator-5554");
		
		//dcm.connectDevice("HT04TP800408", 5001, 8080);
		dcm.connectDevice("emulator-5554", 5000, 5001);
		
		dcm.sendMessage("hello");
		dcm.disconnectAllDevices();
	}
	
	

}
