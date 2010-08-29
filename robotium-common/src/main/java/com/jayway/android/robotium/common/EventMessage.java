package com.jayway.android.robotium.common;

import java.lang.reflect.Method;

public class EventMessage extends AbstractMessage {
	
	protected Class<?> targetObjectClass;
	protected String targetObjectId;
	protected Method methodReceived;
	protected Object[] parameters;
	
	public EventMessage(Class<?> targetObjectClass, String targetObjectId, 
			Method methodReceived, Object[] parameters) {
		this.messageHeader = Message.HEADER_CLIENT_EVENT;	
		this.targetObjectClass = targetObjectClass;
		this.targetObjectId = targetObjectId;
		this.methodReceived = methodReceived;
		this.parameters = parameters;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
