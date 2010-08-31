package com.jayway.android.robotium.common;

import java.util.UUID;

/**
 * All the messages has this two attributes
 * header:      message type
 * messageID:   unique message id
 * 
 * - RESPONSE_SUCCESS
 *   has standard header with no further content
 *   
 * - RESPONSE_FAILURE
 * Description (Optional): description of the failure reason 
 * 
 * - RESPONSE_EXCEPTION
 * ExceptionType: the class name of the exception
 * Description: description of the exception
 * 
 * - REQUEST_TARGET_ACTIVITY_CLASS
 *   has standard header with no further content
 * 
 * - TARGET_ACTIVITY_CLASS
 * ActivityClass: class name of the class(should include package.ClassName)
 * 
 * - CLIENT_EVENT and SERVER_EVENT 
 *   (contains more complex messages structure)
 * 
 * */
public interface Message {
	/** Client usually constructs message as an event as message header */
	public final static String HEADER_CLIENT_EVENT = "CLIENT_EVENT";
	
	/** Server need to send responses: success, failure  or exception */
	/** Success: doesn't have any more messages */
	public final static String HEADER_RESPONSE_SUCCESS = "RESPONSE_SUCCESS";
	
	/** Failure: */
	public final static String HEADER_RESPONSE_FAILURE = "RESPONSE_FAILURE";
	
	/** Exception: server end received exception. **/
	public final static String HEADER_RESPONSE_EXCEPTION = "RESPONSE_EXCEPTION";
	
	public final static String HEADER_SERVER_EVENT = "SERVER_EVENT";
	
	public final static String HEADER_REQUEST_TARGET_ACTIVITY_CLASS = "REQUEST_TARGET_ACTIVITY_CLASS";
	
	public final static String HEADER_TARGET_ACTIVITY_CLASS = "TARGET_ACTIVITY_CLASS";
	
	
	/* Predefined JSON attribute keys */
	public final static String JSON_ATTR_HEADER = "Header";
	public final static String JSON_ATTR_MESSAGE_ID = "MessageID";
	public final static String JSON_ATTR_DESCRIPTION = "Description";
	public final static String JSON_ATTR_ACTIVITY_CLASS = "ActivityClass";
	public final static String JSON_ATTR_EXCEPTION_TYPE = "ExceptionType";
	public final static String JSON_ATTR_TARGET_OBJECT_CLASS_NAME = "TargetObjectClass";
	public final static String JSON_ATTR_TARGET_OBJECT_ID = "TargetObjectID";
	public final static String JSON_ATTR_METHOD_RECEIVED = "MethodReceived";
	public final static String JSON_ATTR_PARAMETER_TYPES = "ParameterTypes";
	public final static String JSON_ATTR_PARAMETERS = "Parameters";
	
	
	
	public Message setMessageId(UUID id);
	
	
	public UUID getMessageId();
	
	/**
	 * Returns the String represents JSONObejt. 
	 * The String value does not contain escapes.
	 */
	public String toString();
	
	
}
