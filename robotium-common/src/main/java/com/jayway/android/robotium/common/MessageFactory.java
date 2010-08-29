package com.jayway.android.robotium.common;

import java.lang.reflect.Method;
import java.util.UUID;

public class MessageFactory {
	
	private static Message generateUuidForMessage(Message msg) {
		return msg.setMessageId(UUID.randomUUID());
	}
	
	public static Message createEventMessage(Class<?> targetObjectClass, String targetObjectId, 
			Method methodReceived, Object[] parameters) {		
		return generateUuidForMessage(new EventMessage(targetObjectClass, 
				targetObjectId, methodReceived, parameters));
	}
	
	public static Message createExceptionMessage(Exception ex, String message) {
		return generateUuidForMessage( new ExceptionMessage(ex, message));
	}
	
	public static Message createFailureMessage(String message) {
		return generateUuidForMessage(new FailureMessage(message));
	}
	
	public static Message createSuccessMessage() {
		return generateUuidForMessage(new SuccessMessage());
	}
	
	public static Message parseMessageString(String messageString) {
		//TODO: parse message
		
		// msg type
		
		// msg id
		
		// 
		return null;
	}
	

}
