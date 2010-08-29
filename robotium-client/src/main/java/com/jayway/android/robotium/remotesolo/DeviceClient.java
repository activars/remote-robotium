package com.jayway.android.robotium.remotesolo;

public interface DeviceClient {

	/**
	 * Returns the PC port number
	 */
	public abstract int getPcPort();

	/**
	 * Returns the device port number
	 */
	public abstract int getDevicePort();

	/**
	 * Returns the device serial number
	 */
	public abstract String getDeviceSerial();

	/**
	 * Tries to connect to the remote device
	 */
	public abstract void connect();

	/**
	 * send a message to the remote device
	 * 
	 * @param msg
	 * @return
	 */
	public abstract boolean sendMessage(String msg);
	
	public Object invokeMethod(String methodToExecute, Class<?>[] argumentTypes, Object... arguments) throws Exception;

	/**
	 * Close the connection
	 */
	public abstract void disconnect();

}