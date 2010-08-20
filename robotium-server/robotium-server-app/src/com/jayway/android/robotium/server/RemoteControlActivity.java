package com.jayway.android.robotium.server;

import com.jayway.android.robotium.server.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RemoteControlActivity extends Activity implements OnClickListener {
	private static final String TAG = "Robotium Remote Control";
	Button buttonStartService, buttonStopService;
	
    /** Called when the activity is first created. */
    @Override  
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        buttonStartService = (Button) findViewById(R.id.buttonStartService);
        buttonStopService = (Button) findViewById(R.id.buttonStopService);
        buttonStartService.setOnClickListener(this);
        buttonStopService.setOnClickListener(this);
        setServiceEnabled(false);
    }
     

	public void onClick(View arg0) {
		switch (arg0.getId()) { 
	    case R.id.buttonStartService:
	      Log.d(TAG, "onClick: starting service");
	      startService(new Intent(this, RemoteService.class));
	      setServiceEnabled(true);
	      break;
	    case R.id.buttonStopService:
	      Log.d(TAG, "onClick: stopping service");
	      stopService(new Intent(this, RemoteService.class));
	      setServiceEnabled(false);
	      break;
	    }
	}
	
	
	private void setServiceEnabled(boolean enabled) {
		if(enabled) {
			buttonStartService.setEnabled(false);
		    buttonStopService.setEnabled(true);
		} else {
			buttonStartService.setEnabled(true);
		    buttonStopService.setEnabled(false);
		}
		
	}
}