package com.jayway.android.robotium.server;


import android.app.Instrumentation;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

public class InstrumentationRunner extends Instrumentation {
	
	private static final String TAG = "InstrumentationRunner";
	
	Bundle mresult;
	
	@Override
	public void onCreate(Bundle arguments) {
		super.onCreate(arguments);
		Log.d(TAG, "Intrumentation created");
		start();
		
	}
	
	/**
	 * Starts Instrumentation and pass itself to server handler
	 */
	@Override
	public void onStart() {
		super.onStart();
		Looper.prepare();

		ServerHandler.injectInstrumentation(this);
		Log.d(TAG, "Intrumentation passed to ServerHandler");
		
		return;
	}
	
}
