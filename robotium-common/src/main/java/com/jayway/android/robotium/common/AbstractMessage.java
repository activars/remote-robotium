package com.jayway.android.robotium.common;

import java.util.UUID;

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
	
	public abstract String  toString();
	
}
