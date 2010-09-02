package com.jayway.android.robotium.common.message;

public class SuccessMessage extends AbstractMessage {
	
	public SuccessMessage() {
		this.messageHeader = Message.HEADER_RESPONSE_SUCCESS;
	}
	
	@Override
	public String toString() {
		return getHeader().toString();
	}

}
