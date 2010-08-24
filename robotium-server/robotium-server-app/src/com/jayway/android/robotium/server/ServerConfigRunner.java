package com.jayway.android.robotium.server;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;


/**
 * ServerConfigRunner is a simple adb instrumentation utility that 
 * helps to configure Robotium server port remotely. 
 * 
 * @author Jing Dong jing.dong@activars.com
 *
 */
public class ServerConfigRunner extends Instrumentation {
	
	/** hide **/
	private static String TAG = "ServerLauncher";
	/** port number argument key **/
	private static String ARGUMENT_SERVER_PORT = "port";
	/** default port number **/
	private int portNum = 8080;
	
	/**
	 * Obtains the port number from arguments. 
	 * If there are no argument for port configuration, the default port number 
	 * is 8080.
	 */
	@Override
	public void onCreate(Bundle arguments) {
		super.onCreate(arguments);

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
	
	
	@Override
	public void onStart() {
		super.onStart();
		
		// output port information
		Bundle result = new Bundle();
		result.putInt(ARGUMENT_SERVER_PORT, this.portNum);
		// exit
		finish(Activity.RESULT_OK, result);
	}
	

}
