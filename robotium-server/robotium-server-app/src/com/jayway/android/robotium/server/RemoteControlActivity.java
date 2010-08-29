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

public class RemoteControlActivity extends Activity implements OnClickListener {
	
	// User Preferences
	public static final String PREFS = "RobotiumServerPrefs";
	
	private static final String TAG = "Robotium Remote Control";
	Button buttonStartService, buttonStopService;
	EditText serverPort;
	private boolean isServiceRunning;
	
    /** Called when the activity is first created. */
    @Override  
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        buttonStartService = (Button) findViewById(R.id.buttonStartService);
        buttonStopService = (Button) findViewById(R.id.buttonStopService);
        serverPort = (EditText) findViewById(R.id.portEditText);
        
        SharedPreferences preferences = getSharedPreferences(PREFS, 0);
        int portNumberValue = preferences.getInt("portNum", 8080);
        Log.d(TAG, "pref loaded: " + portNumberValue);
        serverPort.setText(String.valueOf(portNumberValue));
        
        buttonStartService.setOnClickListener(this);
        buttonStopService.setOnClickListener(this);
        
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        // Add text watcher for EditText Listener
        TextWatcher watcher = new TextWatcher() {
            public void afterTextChanged(android.text.Editable s) {
            	//save to settings
            	SharedPreferences settings = getSharedPreferences(PREFS, 0);
                SharedPreferences.Editor editor = settings.edit();
                
                if(serverPort.getText().toString() != null &&
                		serverPort.getText().toString().length() != 0) {
           
                	setServiceEnabled(false, false);
                	int value = Integer.parseInt(serverPort.getText().toString());
                	if( value > 65535 || value <= 0 ) {
                		serverPort.setError("valid range: 0 - 65535");
                		setServiceEnabled(false, true);
                	} else {
                		serverPort.setError(null);
                		setServiceEnabled(false, false);
                	}
                	editor.putInt("portNum", value);
                } else {
                	serverPort.setError("valid range: 0 - 65535");
                	setServiceEnabled(false, true);
                }
                
                // Commit the edits!
                editor.commit();
            	Log.d(TAG, "port number changed to " + serverPort.getText());
            }

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) { }

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) { }
        };
        serverPort.addTextChangedListener(watcher);        
        
    }
    
    /**
     * Check if service is running already,
     * and disable buttons if required.
     */
    public void onResume() {
    	super.onResume();
    	checkServiceStatus();
    }
    
    
    

	public void onClick(View arg0) {
		switch (arg0.getId()) { 
	    case R.id.buttonStartService:
	      Log.d(TAG, "onClick: starting service");
	      startService(new Intent(this, RemoteService.class));
	      setServiceEnabled(true, false);
	      hideSoftInput();
	      break;
	    case R.id.buttonStopService:
	      Log.d(TAG, "onClick: stopping service");
	      stopService(new Intent(this, RemoteService.class));
	      setServiceEnabled(false, false);
	      hideSoftInput();
	      break;
	    }
	}
	
	
	private void setServiceEnabled(boolean enabled, boolean disableAll) {
		serverPort.setEnabled(!enabled);
		
		if(disableAll) {
			buttonStartService.setEnabled(false);
		    buttonStopService.setEnabled(false);
		} else {
			if(enabled) {
				buttonStartService.setEnabled(false);
				buttonStopService.setEnabled(true);
			} else {
				buttonStartService.setEnabled(true);
				buttonStopService.setEnabled(false);
			}
			
			
		}
		
	}
	
	private void hideSoftInput() {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(serverPort.getWindowToken(), 0);
	}
	
	private void checkServiceStatus() {
		isServiceRunning = ServiceHelper.isServiceRunning(getBaseContext(), "com.jayway.android.robotium.server");
		if(isServiceRunning) {
        	setServiceEnabled(true, false);
        	Log.d(TAG, "service is already running");
        } else {
        	setServiceEnabled(false, false);
        }
	}
	
	
	
}