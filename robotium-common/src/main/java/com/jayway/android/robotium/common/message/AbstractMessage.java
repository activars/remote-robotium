package com.jayway.android.robotium.common.message;

import java.util.UUID;

import org.json.simple.JSONObject;

public abstract class AbstractMessage implements Message {
	
	protected String messageHeader;
	protected UUID messageId;
	
	public Message setMessageId(UUID id) {
		this.messageId = id;
		return this;
	}
	
	public UUID getMessageId() {
		return this.messageId;
	}
	
	public String getMessageHeader() {
		return this.messageHeader;
	}
	
	protected JSONObject getHeader() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(Message.JSON_ATTR_HEADER, messageHeader);
		jsonObj.put(Message.JSON_ATTR_MESSAGE_ID, messageId.toString());
		return jsonObj;
	}
	
	public abstract String  toString();
	
}
