package com.jayway.android.robotium.remotesolo.test;

import com.jayway.android.robotium.remotesolo.SoloClient;

public class ClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SoloClient client = new SoloClient(5000, 5000);
		client.sendMessage("hello");
		client.sendMessage("hello2");
		client.sendMessage("hello3");
		client.close();
	}
	
	

}
