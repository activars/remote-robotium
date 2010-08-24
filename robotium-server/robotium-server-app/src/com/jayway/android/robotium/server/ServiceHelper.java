package com.jayway.android.robotium.server;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceHelper {
	
	
	
	public static boolean isServiceRunning( Context context, String pkgName) {
		final ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
		final List<RunningServiceInfo> services = activityManager
				.getRunningServices(Integer.MAX_VALUE);

		boolean isServiceFound = false;

		for (int i = 0; i < services.size(); i++) {

			if (pkgName.equals(services.get(i).service
					.getPackageName())) {
				isServiceFound = true;
			}
		}
		return isServiceFound;
	}
}
