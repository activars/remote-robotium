package com.jayway.android.robotium.common.message;

import org.json.simple.JSONObject;

public class ExceptionMessage extends AbstractMessage {

	protected Class<?> exceptionClass;
	protected String exceptionMessage;
	
	
	public ExceptionMessage(Class ex, String message) {
		init(ex, message);
	}

	public ExceptionMessage(Exception ex) {
		init(ex.getClass(), ex.getMessage());
	}
	
	private void init(Class ex, String message) {
		this.messageHeader = Message.HEADER_RESPONSE_EXCEPTION;
		this.exceptionClass = ex.getClass();
		this.exceptionMessage = message;
	}
	
	public String getExceptionName(){
		return exceptionClass.getName();
	}
	
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		JSONObject jsonObj = getHeader();
		jsonObj.put(Message.JSON_ATTR_EXCEPTION_TYPE, exceptionClass.getName());
		jsonObj.put(Message.JSON_ATTR_DESCRIPTION, (exceptionMessage != null) ? exceptionMessage : "");
		return jsonObj.toString();
	}

}
