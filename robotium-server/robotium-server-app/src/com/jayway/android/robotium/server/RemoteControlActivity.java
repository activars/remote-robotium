package com.jayway.android.robotium.server;

import com.jayway.android.robotium.server.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class RemoteControlActivity extends Activity  {
	
	// User Preferences
	public static final String PREFS = "RobotiumServerPrefs";
	
	private static final String TAG = "Robotium Remote Control";

    /** Called when the activity is first created. */
    @Override  
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d(TAG, "created");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    
    /**
     * Check if service is running already,
     * and disable buttons if required.
     */
    public void onResume() {
    	super.onResume();
    }
    
	
	
	
}