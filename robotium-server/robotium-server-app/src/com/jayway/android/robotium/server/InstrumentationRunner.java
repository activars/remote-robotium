package com.jayway.android.robotium.server;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.app.*;

public class InstrumentationRunner extends Instrumentation {
	
	private static final String TAG = "InstrumentationRunner";
	
	/** server port number argument */
	private static final String ARGUMENT_SERVER_PORT = "port";
	
	private int portNum;
	
	Bundle mresult;
	
	@Override
	public void onCreate(Bundle arguments) {
		super.onCreate(arguments);
		mresult = new Bundle();
		mresult.putString(TAG, "created");
		
		start();
		
	}
	
	public void onStart() {
		super.onStart();
		Looper.prepare();
		//Instrumentation ints = new Instrumentation();
		//ints.
		//.newActivity(cl, className, intent)
		
		//Bundle mresult = new Bundle();
		
		//mresult.putString(tag, value);
		//Intent intent = new Intent(Intent.ACTION_MAIN);
		//intent.addCategory(Intent.CATEGORY_LAUNCHER);
		
		
		//newActivity(Class.forName(""), "NoteList", intent);
		//getContext().startService(service)
		//this.finish(Activity.RESULT_OK, mresult);
	}
	
	
	public void onDestroy() {
		super.onDestroy();
	}
	
	
	public ClassLoader getLoader() {
        return InstrumentationRunner.class.getClassLoader();
    }
	
}
