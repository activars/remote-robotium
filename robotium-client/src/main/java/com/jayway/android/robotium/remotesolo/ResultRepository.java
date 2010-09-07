package com.jayway.android.robotium.remotesolo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jayway.android.robotium.common.util.TypeUtils;

public class ResultRepository {
	// Integer key: object hashcode, Map<String,Integer>: device id, object id
	// hashcode on other devices
	private static HashMap<Integer, HashMap<String, Integer>> objectMaps = new HashMap<Integer, HashMap<String, Integer>>();
	
	public Integer getRelatedObjectHashCode(Object proxyObj, DeviceClient device) {
		int proxyRef = System.identityHashCode(proxyObj);
		
		if(objectMaps.containsKey(proxyRef)) {
			return objectMaps.get(proxyRef).get(device.getDeviceKey());
		}
		
		return 0;
	}
	
	public Object checkConsistancy(Map<DeviceClient, Object> results,
			String methodDetails) throws InconsistentResultException,
			RemoteException {

		if (results.keySet().size() > 0) {
			// method on Solo object, only returns either void, primitive, or
			// list of non-primitive.
			// non-primitives have recorded by the invocation handler
			// primitive: all primitive and include String
			DeviceClient mKey = (DeviceClient) results.keySet().toArray()[0];
			Object mResult = results.get(mKey);

			if (mResult == null) {
				// should be a void return type.
				// TODO: should also check if all the results are null too!
				return null;
			}
			boolean hasListInterface = TypeUtils.hasListInterfaceType(mResult
					.getClass());
			boolean hasCollectionInterface = TypeUtils
					.hasCollectionInterfaceType(mResult.getClass());
			boolean isResultPrimitive = TypeUtils.isPrimitive(mResult
					.getClass());

			Iterator<DeviceClient> itr = results.keySet().iterator();
			while (itr.hasNext()) {
				DeviceClient compareKey = itr.next();
					Object comparedResult = results.get(compareKey);
					if (isResultPrimitive) {
						if (!mResult.equals(comparedResult)) {
							String errorMsg = String
									.format(
											"Device %s returned %s, but %s returned %s:%s",
											mKey.getDeviceSerial(), mResult
													.toString(), compareKey
													.getDeviceSerial(),
											results.get(compareKey).toString(),
											methodDetails);
							throw new InconsistentResultException(errorMsg);
						}
					} else if (hasListInterface) {
						if (((List<?>) mResult).size() != ((List<?>) comparedResult)
								.size()) {
							String errorMsg = String
									.format(
											"Device %s has %s items, but %s returned %s items:%s",
											mKey.getDeviceSerial(),
											((List<?>) mResult).size(),
											compareKey.getDeviceSerial(),
											((List<?>) comparedResult).size(),
											methodDetails);
							throw new InconsistentResultException(errorMsg);
						}
						
						
						// store object mapping
						int keyObjRef, valueObjRef;
						for(int i = 0; i < ((List<?>) mResult).size(); i++) {
							keyObjRef = System.identityHashCode(((List<?>) mResult).get(i));
							valueObjRef = System.identityHashCode(((List<?>) comparedResult).get(i));
							if(objectMaps.containsKey(keyObjRef)) {
								objectMaps.get(keyObjRef).put(compareKey.getDeviceKey(), valueObjRef);
								System.out.println("Added ProxyRef key:" + keyObjRef + " value:" + valueObjRef);
							} else {
								HashMap<String, Integer> temp = new HashMap<String, Integer>();
								temp.put(compareKey.getDeviceKey(), valueObjRef);
								objectMaps.put(keyObjRef, temp);
								System.out.println("Added ProxyRef key:" + keyObjRef + " value:" + valueObjRef);
							}
						}
					} else if(!isResultPrimitive && !hasListInterface && !hasCollectionInterface) {
						
					}
			}

			return mResult;
		} else {
			throw new RemoteException("Server could be disconnected");
		}
	}

}
