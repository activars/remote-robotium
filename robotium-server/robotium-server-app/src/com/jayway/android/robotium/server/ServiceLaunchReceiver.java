package com.jayway.android.robotium.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServiceLaunchReceiver extends BroadcastReceiver {
	
	public static final String SERVICE_LAUNCH_INTENT = "com.jayway.android.robotium.server.LAUNCHSERVICE";
	private static final String TAG = "ServiceLaunchReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {

        if(intent.getAction() != null
        		&& intent.getAction().equalsIgnoreCase(SERVICE_LAUNCH_INTENT)) {
        	context.startService(new Intent(context, RemoteService.class));
        	Log.d(TAG, "RemoteService Started");
        }
		
	}

}
