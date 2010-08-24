package com.jayway.android.robotium.server;

import com.jayway.android.robotium.solo.ISolo;
import com.jayway.android.robotium.solo.Solo;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class ServerConfigRunner extends Instrumentation {
	
	private static String TAG = "ServerLauncher";
	private static String ARGUMENT_SERVER_PORT = "port";
	
	private int portNum = 8080;
	
	public void onCreate(Bundle arguments) {
		super.onCreate(arguments);
		// arguments can be: launch the server
		// 					 shutdown server
		//   				 setup port
		// 
		if (arguments != null) {
			String portNumStr = arguments.getString(ARGUMENT_SERVER_PORT);
			portNum = Integer.valueOf(portNumStr);
			Log.d(TAG, "portnumber from cmd: " + portNum);
		}
		SharedPreferences settings = getTargetContext().getSharedPreferences(RemoteControlActivity.PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
		editor.putInt("portNum", portNum);
		editor.commit();
		
		start();
	}
	
	public void onStart() {
		super.onStart();
		// start server
//		
//		//turning these off
//		stopAllocCounting();
//		stopProfiling();
//		
//		Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setClass(getTargetContext(), RemoteControlActivity.class);
//        RemoteControlActivity rca = (RemoteControlActivity)startActivitySync(intent);
//        
//		ISolo solo = new Solo(this, rca);
//		solo.clickOnButton(0);
//		while(!ServiceHelper.isServiceRunning(this.getTargetContext(), "com.jayway.android.robotium.server")) {
//			solo.sleep(1000);
//		}
//		waitForIdleSync();
		// output result
		
		Bundle result = new Bundle();
		result.putInt(ARGUMENT_SERVER_PORT, this.portNum);
		// exit
		finish(Activity.RESULT_OK, result);
	}
	

}
