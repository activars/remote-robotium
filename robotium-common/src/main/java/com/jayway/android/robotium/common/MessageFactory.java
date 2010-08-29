package com.jayway.android.robotium.common;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.naming.OperationNotSupportedException;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class MessageFactory {

	private static Message generateUuidForMessage(Message msg) {
		return msg.setMessageId(UUID.randomUUID());
	}

	public static Message createEventMessage(Class<?> targetObjectClass,
			String targetObjectId, Method methodReceived, Object[] parameters) {
		return generateUuidForMessage(new EventMessage(targetObjectClass,
				targetObjectId, methodReceived, parameters));
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
			Class<?> activityClass = Class.forName((String) jsonObj
					.get(Message.JSON_ATTR_ACTIVITY_CLASS));
			mMsg = new TargetActivityMessage(activityClass);

		} else if (header.equals(Message.HEADER_REQUEST_TARGET_ACTIVITY_CLASS)) {
			mMsg = new TargetActivityRequestMessage();
			
		} else if (header.equals(Message.HEADER_CLIENT_EVENT)) {
			//TODO: create this type of messages
			throw new UnsupportedOperationException("Not implemented yet");

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
