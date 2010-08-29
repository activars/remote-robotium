package com.jayway.android.robotium.common;

public class ExceptionMessage extends AbstractMessage {

	protected Class<?> exceptionClass;
	protected String exceptionMessage;
	
	
	public ExceptionMessage(Exception ex, String message) {
		init(ex, message);
	}

	public ExceptionMessage(Exception ex) {
		init(ex, ex.getMessage());
	}
	
	private void init(Exception ex, String message) {
		this.messageHeader = Message.HEADER_RESPONSE_EXCEPTION;
		this.exceptionClass = ex.getClass();
		this.exceptionMessage = message;
	}
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
