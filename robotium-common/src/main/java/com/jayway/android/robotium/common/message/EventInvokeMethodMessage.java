package com.jayway.android.robotium.common.message;

import java.lang.reflect.Method;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.jayway.android.robotium.common.util.TypeUtils;

public class EventInvokeMethodMessage extends AbstractMessage {
	
	protected Class<?> targetObjectClass;
	protected String targetObjectId;
	protected Method methodReceived;
	protected Class<?>[] parameterTypes;
	protected Object[] parameters;
	
	public EventInvokeMethodMessage(Class<?> targetObjectClass, String targetObjectId, 
			Method methodReceived, Class<?>[] parameterTypes, Object... parameters) {
		// declares the event type is from client
		this.messageHeader = Message.HEADER_INVOKE_METHOD_EVENT;
		this.targetObjectClass = targetObjectClass;
		this.targetObjectId = targetObjectId;
		if(parameterTypes.length != parameters.length) {
			throw new IllegalArgumentException("Parameter types size does not match with given objects");
		}
		this.methodReceived = methodReceived;
		this.parameterTypes = parameterTypes;
		this.parameters = parameters;
	}
	
	
	public Class<?> getTargetObjectClass() {
		return this.targetObjectClass;
	}
	
	public String getTargetObjectId() {
		return targetObjectId;
	}
	
	public Method getMethodReceived() {
		return this.methodReceived;
	}
	
	public Class<?>[] getParameterTypes() {
		return this.parameterTypes;
	}
	
	public Object[] getParameters() {
		return this.parameters;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		JSONObject jsonObj = getHeader();
		if(targetObjectClass.equals(CharSequence.class)) {
			System.out.println("targeting char sequ");
		}
		jsonObj.put(Message.JSON_ATTR_TARGET_OBJECT_CLASS_NAME, TypeUtils.getClassName(targetObjectClass));
		jsonObj.put(Message.JSON_ATTR_TARGET_OBJECT_ID, targetObjectId);
		jsonObj.put(Message.JSON_ATTR_METHOD_RECEIVED, methodReceived.getName());
		JSONArray paramTypes = new JSONArray();
		JSONArray params = new JSONArray();
		for(int i=0; i < parameterTypes.length; i++) {
			paramTypes.add(TypeUtils.getClassName(parameterTypes[i]));
			params.add(TypeUtils.getPrimitiveStringValue(parameterTypes[i], parameters[i]));
		}
		jsonObj.put(Message.JSON_ATTR_PARAMETER_TYPES, paramTypes);
		jsonObj.put(Message.JSON_ATTR_PARAMETERS, params);
		
		return jsonObj.toString();
	}

}
