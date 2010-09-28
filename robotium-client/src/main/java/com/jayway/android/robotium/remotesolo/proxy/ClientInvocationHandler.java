package com.jayway.android.robotium.remotesolo.proxy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.jayway.android.robotium.common.message.Message;
import com.jayway.android.robotium.common.message.MessageFactory;
import com.jayway.android.robotium.common.util.TypeUtils;
import com.jayway.android.robotium.remotesolo.DeviceClient;
import com.jayway.android.robotium.remotesolo.DeviceClientImpl;
import com.jayway.android.robotium.remotesolo.MessageWorker;
import com.jayway.android.robotium.remotesolo.RemoteException;
import com.jayway.android.robotium.remotesolo.ResultRepository;
import com.jayway.android.robotium.solo.Solo;

public class ClientInvocationHandler implements InvocationHandler {

	private static ResultRepository resultRepository;
	private MessageWorker messageWorker;
	public static final int TIMEOUT = 15000;
	public static final int SLEEP_TIME = 500;

	public ClientInvocationHandler() {
		messageWorker = new MessageWorker();
		resultRepository = new ResultRepository();
	}

	public Object invoke(final Object proxyObj, final Method method,
			final Object[] args) throws Throwable {

		if (shouldBeRecorded(method)) {
			
			if(!isSolo(proxyObj) &&  method.getName().equals("hashCode")) {

				return  System.identityHashCode(proxyObj);
			}
			
			// execute commands for all the devices
			final Map<String, DeviceClient> devices = DeviceClientImpl
					.getCurrentDevices();
			Iterator<String> it = devices.keySet().iterator();
			if (devices.size() == 0) {
				throw new RemoteException(
						"Server Error: Robotium test server is disconnected");
			}
			ExecutorService pool = Executors.newFixedThreadPool(devices.size());
			final Map<DeviceClient, Object> results = Collections
					.synchronizedMap(new HashMap<DeviceClient, Object>());

			while (it.hasNext()) {
				final DeviceClient dc = devices.get(it.next());
				
				pool.execute(new Runnable() {
					public void run() {
						try {

							Object obj = null;
							if (isSolo(proxyObj)) {
								// simply send for multiple devices
								obj = sendMessages(dc, proxyObj, method, args);
							} else {
								
								// otherwise, need to find equivalent objects
								// for a specific device
								int dcProxyHashCode = resultRepository
										.getRelatedObjectHashCode(proxyObj, dc);

								// the mapping object for this device
								Object dcProxyObj = messageWorker
										.getProxyObject(dcProxyHashCode);

								if (dcProxyObj != null) {
									obj = sendMessages(dc, dcProxyObj, method,
											args);
								} else {
								//	throw new RemoteException("missing referenced object");
								}
							}

							synchronized (results) {
								// no consistent order of value returned
								results.put(dc, obj);
							}
						} catch (Throwable e) {
							e.printStackTrace();
						}
					}
				});
			}
			pool.shutdown();
			try {
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
			}
			// comparing values, only for primitives
			// collection check the collection size matches
			String methodDetails = method.getName() ;

			// always return the one in top of the keyset.
			Object invokeResult = resultRepository.checkConsistancy(results,
					methodDetails);

			return invokeResult;

		}

		return null;
	}

	public void addMessage(Message message) {
		MessageWorker.addMessage(message);
	}

	private Object sendMessages(DeviceClient device, Object proxyObj,
			Method method, Object[] args) throws Throwable {
		Message message = null;
		// check arguments are primitives
		Object[] checkedArgs = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			if (TypeUtils.isPrimitive(args[i].getClass())
					|| args[i].getClass().equals(Class.class)) {
				checkedArgs[i] = args[i];
			} else {
				int sysRef = System.identityHashCode(proxyObj);
				String objRemoteID = messageWorker
						.getProxyObjectRemoteID(sysRef);
				if (objRemoteID != null)
					checkedArgs[i] = objRemoteID;
				else
					throw new UnsupportedOperationException(
							"Argument type is not supported.");
			}
		}

		if (isSolo(proxyObj)) {
			message = MessageFactory.createEventMessage(proxyObj.getClass(),
					UUID.randomUUID().toString(), method, method
							.getParameterTypes(), checkedArgs);
		} else {

			// TODO: the invoke executes method on single remote object.
			// The client should find the same objects on other devices and
			// execute the invocation too!!
			// only for non-solo based invocation

			

			// Then it could be a proxy object
			int sysRef = System.identityHashCode(proxyObj);
			String objRemoteID = messageWorker.getProxyObjectRemoteID(sysRef);
			// remove them from memory
			// messageWorker.removeProxyObject(sysRef, proxyObj);
			if (objRemoteID != null) {
				// construct a message and request for action
				message = MessageFactory.createEventMessage(
						proxyObj.getClass(), objRemoteID, method, method
								.getParameterTypes(), checkedArgs);
			} else {
				System.err.println("cannot find proxy object");
				return null;
			}
		}

		device.sendMessage(message.toString());
		//System.out.println("Sent Msg: " + message.toString());

		// wait for response
		int slept = 0;
		boolean timedOut = false;
		while (!messageWorker.hasResponseFor(message)) {
			if (slept >= TIMEOUT) {
				break;
			}
			Thread.sleep(SLEEP_TIME);
			slept += SLEEP_TIME;
		}

		if (timedOut == true)
			throw new RemoteException("Server time out");

		return messageWorker.digestMessage(message.getMessageId().toString());
	}

	private boolean isSolo(Object obj) {
		return TypeUtils.getClassName(obj.getClass()).equals(
				TypeUtils.getClassName(Solo.class));
	}

	/**
	 * ignore calls on object and finalize method
	 */
	private boolean shouldBeRecorded(Method method) {
		return !((method.getDeclaringClass().equals(Object.class) || method
				.getDeclaringClass().equals(Solo.class)) && method.getName()
				.equals("finalize"));
	}

}
