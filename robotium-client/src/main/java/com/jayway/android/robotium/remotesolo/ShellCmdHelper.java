package com.jayway.android.robotium.remotesolo;

import java.util.ArrayList;
import java.util.List;

import com.jayway.maven.plugins.android.CommandExecutor;
import com.jayway.maven.plugins.android.ExecutionException;

class ShellCmdHelper {
	
	static void forwardingPort(int pcPort, int devicePort, String deviceSerial) {
		CommandExecutor executor = CommandExecutor.Factory
				.createDefaultCommmandExecutor();
		List<String> commands = new ArrayList<String>();
		if (!deviceSerial.equals("") && deviceSerial != null) {
			commands.add("-s");
			commands.add(deviceSerial);
		}
		commands.add("forward");
		commands.add("tcp:" + pcPort);
		commands.add("tcp:" + devicePort);

		try {
			executor.executeCommand("adb", commands, false);
		} catch (ExecutionException e) {
			// this happens when multiple devices connected
			// or the envirment varialbe wasn't setup property
			// need to have ANDROID_HOME setup
			e.printStackTrace();
		}

	}
}
