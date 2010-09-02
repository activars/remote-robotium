package com.jayway.android.robotium.common.message;

public class UnsupportedMessage extends FailureMessage {

	public UnsupportedMessage(String message) {
		super(message);
		this.messageHeader = Message.HEADER_RESPONSE_UNSUPPORTED_OPERATION;
	}
	
}
