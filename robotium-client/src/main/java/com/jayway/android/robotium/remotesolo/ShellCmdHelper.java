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

	// TODO: return an array of device serial.
	static void listDevicesSerials() {

	}

	
	
	static void startRobotiumServer(int port, String deviceSerial) {
		
		// setup port number first
		setRobotiumServerPort(port, deviceSerial);
		
		CommandExecutor executor = CommandExecutor.Factory
		.createDefaultCommmandExecutor();
		List<String> commands = new ArrayList<String>();
		
		if (!deviceSerial.equals("") && deviceSerial != null) {
			commands.add("-s");
			commands.add(deviceSerial);
		}
		
		commands.add("shell");
		commands.add("am");
		commands.add("startservice");
		commands.add("-n");
		commands.add("com.jayway.android.robotium.server/com.jayway.android.robotium.server.RemoteService");
		
		try {
			executor.executeCommand("adb", commands, false);
			// wait for a few seconds before service started
			Thread.sleep(5000);
			
		} catch (ExecutionException e) {
			//TODO: better error message
			// this happens when multiple devices connected
			// or the envirment varialbe wasn't setup property
			// need to have ANDROID_HOME setup
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private static void setRobotiumServerPort(int port, String deviceSerial) {
		CommandExecutor executor = CommandExecutor.Factory
				.createDefaultCommmandExecutor();
		List<String> commands = new ArrayList<String>();
		
		if (!deviceSerial.equals("") && deviceSerial != null) {
			commands.add("-s");
			commands.add(deviceSerial);
		}
		
		commands.add("shell");
		commands.add("am");
		commands.add("instrument");
		commands.add("-w");
		commands.add("-e");
		commands.add("port");
		commands.add(Integer.toString(port));
		commands.add("com.jayway.android.robotium.server/com.jayway.android.robotium.server.ServerConfigRunner");

		try {
			executor.executeCommand("adb", commands, false);
		} catch (ExecutionException e) {
			//TODO: better error message
			// this happens when multiple devices connected
			// or the envirment varialbe wasn't setup property
			// need to have ANDROID_HOME setup
			e.printStackTrace();
		}
		
	}
}
