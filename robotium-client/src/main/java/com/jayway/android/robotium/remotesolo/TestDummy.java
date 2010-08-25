package com.jayway.android.robotium.remotesolo;


public class TestDummy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RemoteSolo solo = new RemoteSolo(Class.class);
		
		solo.addDevice("emulator-5554", 5000, 5001);
		
		solo.connect();
			
	}
	
	

}
