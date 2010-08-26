package com.jayway.android.robotium.remotesolo;

import java.util.ArrayList;

import org.mockito.internal.util.reflection.Whitebox;



public class TestDummy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		// Typical setup()
		RemoteSolo solo = new RemoteSolo(Class.class);
//		// emulator
		solo.addDevice("emulator-5554", 5000, 5000);
//		// v1.6 device
//		solo.addDevice("HT98YLZ00039", 5001, 5001);
		solo.connect();
		
		
		//Object value =  Whitebox.getInternalState(lists, "size");
		//System.out.print(Object.class.getClassLoader().toString());
		// tearDown()
		// solo.disconnect();
		
	}
	
	

}
