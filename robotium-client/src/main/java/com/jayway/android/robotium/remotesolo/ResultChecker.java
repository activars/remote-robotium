package com.jayway.android.robotium.remotesolo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jayway.android.robotium.common.util.TypeUtils;

public class ResultChecker {
	
	public static Object checkConsistancy(Map<DeviceClient, Object> results, String methodDetails) throws InconsistentResultException, RemoteException {
		
		if (results.keySet().size() > 0) {
			// method on Solo object, only returns either void, primitive, or
			// list of non-primitive.
			// non-primitives have recorded by the invocation handler
			// primitive: all primitive and include String
			DeviceClient mKey = (DeviceClient) results.keySet().toArray()[0];
			Object mResult = results.get(mKey);
			if(mResult == null) {
				// should be a void return type. 
				// TODO: should also check if all the results are null too!
				return null;
			}
 			boolean hasListInterface = TypeUtils.hasListInterfaceType(mResult
					.getClass());
			boolean hasCollectionInterface = TypeUtils
					.hasCollectionInterfaceType(mResult.getClass());
			boolean isResultPrimitive = TypeUtils.isPrimitive(mResult.getClass());
			
			Iterator<DeviceClient> itr = results.keySet().iterator();
			while (itr.hasNext()) {
				DeviceClient compareKey = itr.next();
				Object comparedResult = results.get(compareKey);
				if (isResultPrimitive) {
					if (!mResult.equals(comparedResult)) {
						String errorMsg = String.format(
								"Device %s returned %s, but %s returned %s:%s",
								mKey.getDeviceSerial(), mResult.toString(),
								compareKey.getDeviceSerial(), results.get(
										compareKey).toString(), methodDetails);
						throw new InconsistentResultException(errorMsg);
					}
				} else if (hasListInterface) {
					if(((List<?>)mResult).size() != ((List<?>)comparedResult).size()) {
						String errorMsg = String.format(
								"Device %s has %s items, but %s returned %s items:%s",
								mKey.getDeviceSerial(), ((List<?>)mResult).size(),
								compareKey.getDeviceSerial(), ((List<?>)comparedResult).size(), methodDetails);
						throw new InconsistentResultException(errorMsg);
					}
				}
			}

			return mResult;
		} else {
			throw new RemoteException("Server could be disconnected");
		}
	}

}
