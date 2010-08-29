package com.jayway.android.robotium.common;

public class TargetActivityRequestMessage extends AbstractMessage {
	
	public TargetActivityRequestMessage() {
		this.messageHeader = Message.HEADER_REQUEST_TARGET_ACTIVITY_CLASS;
	}
	
	@Override
	public String toString() {
		return getHeader().toString();
	}
}
