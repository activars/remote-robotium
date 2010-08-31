package com.jayway.android.robotium.common;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.naming.OperationNotSupportedException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class MessageFactory {

	private static Message generateUuidForMessage(Message msg) {
		return msg.setMessageId(UUID.randomUUID());
	}

	public static Message createEventMessage(Class<?> targetObjectClass, String targetObjectId, 
			Method methodReceived, Class<?>[] parameterTypes, Object... parameters) {
		return generateUuidForMessage(new EventMessage(targetObjectClass,  targetObjectId, 
											methodReceived,  parameterTypes,  parameters));
	}

	public static Message createExceptionMessage(Exception ex, String message) {
		return generateUuidForMessage(new ExceptionMessage(ex.getClass(),
				message));
	}

	public static Message createFailureMessage(String message) {
		return generateUuidForMessage(new FailureMessage(message));
	}

	public static Message createSuccessMessage() {
		return generateUuidForMessage(new SuccessMessage());
	}
	
	public static Message createTargetActivityMessage(Class targetClass) {
		return generateUuidForMessage(new TargetActivityMessage(targetClass));
	}
	
	public static Message createTargetActivityRequestMessage() {
		return generateUuidForMessage(new TargetActivityRequestMessage());
	}

	public static Message parseMessageString(String messageString)
			throws ClassNotFoundException, Exception {
		
		JSONObject jsonObj = (JSONObject) JSONValue.parse(messageString);
		String header = (String) jsonObj.get(Message.JSON_ATTR_HEADER);
		
		Message mMsg = null;

		// check the message header for specific message type
		if (header.equals(Message.HEADER_RESPONSE_SUCCESS)) {
			mMsg = new SuccessMessage();

		} else if (header.equals(Message.HEADER_RESPONSE_FAILURE)) {
			mMsg = new FailureMessage((String) jsonObj
					.get(Message.JSON_ATTR_DESCRIPTION));

		} else if (header.equals(Message.HEADER_RESPONSE_EXCEPTION)) {
			Class<?> exceptionClass = Class.forName((String) jsonObj
					.get(Message.JSON_ATTR_EXCEPTION_TYPE));
			String exceptionMsg = (String) jsonObj
					.get(Message.JSON_ATTR_DESCRIPTION);
			mMsg = new ExceptionMessage(exceptionClass, exceptionMsg);

		} else if (header.equals(Message.HEADER_TARGET_ACTIVITY_CLASS)) {
			String activityClassName = (String) jsonObj.get(Message.JSON_ATTR_ACTIVITY_CLASS);
			
			mMsg = new TargetActivityMessage(activityClassName);

		} else if (header.equals(Message.HEADER_REQUEST_TARGET_ACTIVITY_CLASS)) {
			mMsg = new TargetActivityRequestMessage();
			
		} else if (header.equals(Message.HEADER_CLIENT_EVENT)) {
			//TODO: create this type of messages
			String expectClassName = (String) jsonObj.get(Message.JSON_ATTR_TARGET_OBJECT_CLASS_NAME);
			Class<?> expectClass = Class.forName(expectClassName);
			JSONArray tempParamTypes = (JSONArray) jsonObj.get(Message.JSON_ATTR_PARAMETER_TYPES);
			JSONArray tempParams = (JSONArray) jsonObj.get(Message.JSON_ATTR_PARAMETERS);
			Class<?>[] paramTypes = new Class<?>[tempParamTypes.size()];
			Object[] params = new Object[tempParamTypes.size()];
			for(int i = 0; i < tempParamTypes.size(); i++ ) {
				paramTypes[i] = TypeUtility.getClassName(tempParamTypes.get(i).toString());
				params[i] = TypeUtility.getObject(tempParamTypes.get(i).toString(),
										tempParams.get(i).toString());
			}
			String methodName = jsonObj.get(Message.JSON_ATTR_METHOD_RECEIVED).toString();
			String objectID = jsonObj.get(Message.JSON_ATTR_TARGET_OBJECT_ID).toString();
			Method classMethod = expectClass.getMethod(methodName, paramTypes);
			
			mMsg = new EventMessage(expectClass, objectID, classMethod, paramTypes, params);

		} else if (header.equals(Message.HEADER_SERVER_EVENT)) {
			//TODO: create this type of messages
			throw new UnsupportedOperationException("Not implemented yet");
		}
		
		// copy the message uuid from json to newly created message 
		copyUuidToMessage(mMsg, jsonObj);

		return mMsg;
	}

	private static void copyUuidToMessage(Message msg, JSONObject jsonObj) {
		if (msg != null & jsonObj != null) {
			UUID uuid = UUID.fromString((String) jsonObj
					.get(Message.JSON_ATTR_MESSAGE_ID));
			msg.setMessageId(uuid);
		}
	}

}
