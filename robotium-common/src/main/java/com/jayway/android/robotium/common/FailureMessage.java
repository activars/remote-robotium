package com.jayway.android.robotium.common;

public class FailureMessage extends AbstractMessage {

	protected String failureMessage;
	
	public FailureMessage(String message) {
		this.messageHeader = Message.HEADER_RESPONSE_FAILURE;
		this.failureMessage = message;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
