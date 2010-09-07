package com.jayway.android.robotium.remotesolo;

import java.util.Map;

import com.jayway.maven.plugins.android.ExecutionException;

public interface DeviceClient {
	
	public abstract void setTargetClass(Class<?> targetClass);
	
	public abstract Class<?> getTargetClass();
	
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
	 * 
	 * @return
	 */
	public String getDeviceKey();

	/**
	 * Tries to connect to the remote device
	 * @throws ExecutionException 
	 */
	public abstract boolean connect();

	/**
	 * send a message to the remote device
	 * 
	 * @param msg
	 * @return
	 * @throws RemoteException 
	 */
	public abstract void sendMessage(String msg) throws RemoteException;
	
	/**
	 * Close the connection
	 * @throws RemoteException 
	 */
	public abstract void disconnect() throws RemoteException;

}