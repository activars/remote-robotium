package com.jayway.android.robotium.server;

import com.jayway.android.robotium.server.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class RemoteControlActivity extends Activity  {
	
	// User Preferences
	public static final String PREFS = "RobotiumServerPrefs";
	
	private static final String TAG = "Robotium Remote Control";

    /** Called when the activity is first created. */
    @Override  
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      
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