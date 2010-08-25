package com.jayway.android.robotium.server;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.app.*;

public class InstrumentationRunner extends Instrumentation {
	
	private static final String TAG = "InstrumentationRunner";
	
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
		
		Log.d(TAG, getComponentName().toString());
		//	com.example.android.notepad
		Log.d(TAG, getTargetContext().getPackageName());
		Log.d(TAG, getContext().getClass().toString());
		
		Log.d(TAG, getTargetContext().getClass().getName());
		Log.d(TAG, getTargetContext().getClassLoader().getClass().getName());
		
		//Instrumentation ints = new Instrumentation();
		//ints.
		//.newActivity(cl, className, intent)
		
		//Bundle mresult = new Bundle();
		
		//mresult.putString(tag, value);
		//Intent intent = new Intent(Intent.ACTION_MAIN);
		//intent.addCategory(Intent.CATEGORY_LAUNCHER);
		
		
		//newActivity(Class.forName(""), "NoteList", intent);
		//getContext().startService(service)
		this.finish(Activity.RESULT_OK, new Bundle());
	}
	
	
	public void onDestroy() {
		super.onDestroy();
	}
	
}
