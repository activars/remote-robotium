package com.jayway.android.robotium.common;

import org.json.simple.JSONObject;

public class FailureMessage extends AbstractMessage {

	protected String failureMessage;
	
	public FailureMessage(String message) {
		this.messageHeader = Message.HEADER_RESPONSE_FAILURE;
		this.failureMessage = message;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		JSONObject jsonObj = this.getHeader();
		jsonObj.put(Message.JSON_ATTR_DESCRIPTION, (failureMessage != null) ? failureMessage : "");
		return jsonObj.toString();
	}

}
