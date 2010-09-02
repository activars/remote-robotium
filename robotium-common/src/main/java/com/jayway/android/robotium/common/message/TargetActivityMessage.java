package com.jayway.android.robotium.common.message;

import org.json.simple.JSONObject;

public class TargetActivityMessage extends AbstractMessage {
	protected String targetClassName;
	
	public TargetActivityMessage(Class<?> targetClass) {
		this.messageHeader = Message.HEADER_TARGET_ACTIVITY_CLASS;
		targetClassName = targetClass.getName();
	}
	
	public TargetActivityMessage(String fullClassName) {
		this.messageHeader = Message.HEADER_TARGET_ACTIVITY_CLASS;
		targetClassName = fullClassName;
	}
	
	public String getTargetClassName() {
		return targetClassName;
	}
	
	@SuppressWarnings("unchecked")
	public String toString() {
		JSONObject jsonObj = getHeader();
		jsonObj.put(Message.JSON_ATTR_ACTIVITY_CLASS, targetClassName);
		return jsonObj.toString();
	}

}
