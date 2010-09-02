package com.jayway.android.robotium.common.message;

import org.json.simple.JSONObject;

public class FailureMessage extends AbstractMessage {

	protected String failureMessage;
	
	public FailureMessage(String message) {
		this.messageHeader = Message.HEADER_RESPONSE_FAILURE;
		this.failureMessage = message;
	}
	
	public String getMessage(){
		return failureMessage;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		JSONObject jsonObj = this.getHeader();
		jsonObj.put(Message.JSON_ATTR_DESCRIPTION, (failureMessage != null) ? failureMessage : "");
		return jsonObj.toString();
	}

}
