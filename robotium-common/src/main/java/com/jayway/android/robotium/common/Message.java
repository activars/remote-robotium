package com.jayway.android.robotium.common;

import java.util.UUID;

public interface Message {
	/** Client usually constructs message as an event as message header */
	public final static String HEADER_EVENT = "EVENT";
	
	/** Server need to send responses: success, failure  or exception */
	/* Success: doesn't have any more messages */
	public final static String HEADER_RESPONSE_SUCCESS = "RESPONSE_SUCCESS";
	
	/* Failure: */
	public final static String HEADER_RESPONSE_FAILURE = "RESPONSE_FAILURE";
	
	/* Exception: server end received exception. The message contains
	 * MessageHeader;ExceptionType;ExceptionMessage */
	public final static String HEADER_RESPONSE_EXCEPTION = "RESPONSE_EXCEPTION";
	
	public final static String HEADER_RESPONSE_SERVER_EVENT = "RESPONSE_SERVER_EVENT";
	
	
	public Message setMessageId(UUID id);
	
}
