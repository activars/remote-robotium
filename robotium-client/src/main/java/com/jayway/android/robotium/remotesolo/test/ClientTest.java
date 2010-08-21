package com.jayway.android.robotium.remotesolo.test;

import com.jayway.android.robotium.remotesolo.SoloClient;

public class ClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SoloClient client = new SoloClient(5000, 5000, "emulator-5554");
		SoloClient client2 = new SoloClient(5001, 8080, "HT04TP800408");
		client.sendMessage("hello");
		client.sendMessage("hello1");
		//client.close();
		//client2.close();
	}
	
	

}
