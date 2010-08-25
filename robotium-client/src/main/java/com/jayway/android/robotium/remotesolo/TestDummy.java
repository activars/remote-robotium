package com.jayway.android.robotium.remotesolo;


public class TestDummy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Typical setup()
		RemoteSolo solo = new RemoteSolo(Class.class);
		// emulator
		solo.addDevice("emulator-5554", 5000, 5000);
		// v1.6 device
		solo.addDevice("HT98YLZ00039", 5001, 5001);
		solo.connect();
		
		
		// tearDown()
		// solo.disconnect();
		
	}
	
	

}
