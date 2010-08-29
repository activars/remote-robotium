package com.jayway.android.robotium.common;

import org.json.simple.JSONObject;

public class TargetActivityMessage extends AbstractMessage {
	protected Class<?> targetClass;
	
	public TargetActivityMessage(Class<?> targetClass) {
		this.messageHeader = Message.HEADER_TARGET_ACTIVITY_CLASS;
		this.targetClass = targetClass;
	}
	
	@SuppressWarnings("unchecked")
	public String toString() {
		JSONObject jsonObj = getHeader();
		jsonObj.put(Message.JSON_ATTR_ACTIVITY_CLASS, targetClass.getName());
		return jsonObj.toString();
	}

}
