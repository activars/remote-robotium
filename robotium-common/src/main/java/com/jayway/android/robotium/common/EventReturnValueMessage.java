package com.jayway.android.robotium.common;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import android.util.Log;

public class EventReturnValueMessage extends AbstractMessage {
	
	private static final String TAG = "EventReturnValueMessage";
	private boolean isPrimitive;
	private boolean isCollection;
	//the root type
	private Class<?> classType;
	//class type for collection value
	private Class<?> innerClassType;
	private boolean isInnerPrimitive;
	private Object[] returnValue;
	
	/**
	 * An event message about return value from an invoked method
	 * 
	 * @param classType The class of returned value
	 * @param innerClassType This is the class of the objects in the collection. Set this to void.class if the classType is not a collection class.
	 * @param arguments the actual returned object
	 */
	public EventReturnValueMessage(Class<?> classType, Class<?> innerClassType, Object[] arguments) {
		this.messageHeader = Message.HEADER_RETURN_VALUE_EVENT;
		this.classType = classType;
		this.isPrimitive = classType.isPrimitive();
		this.innerClassType = innerClassType;
		this.isInnerPrimitive = innerClassType.isPrimitive();
		// void if the returned value is not a collection, managed by MessageFactory
		this.isCollection = !innerClassType.equals(void.class);
		this.returnValue = arguments;
	}
	
	public Class<?> getClassType(){
		return this.classType;
	}
	
	public boolean isPrimitive() {
		return this.isPrimitive;
	}
	
	public boolean isInnerPrimitive() {
		return this.isInnerPrimitive;
	}
	
	public Class<?> getInnerClassType() {
		return this.innerClassType;
	}
	
	public boolean isCollection() {
		return this.isCollection;
	}
	
	public Object[] getReturnValue() {
		return this.returnValue;
	}
	
	
	@Override
	public String toString() {
		JSONObject jsonObj = getHeader();
		jsonObj.put(Message.JSON_ATTR_CLASS_TYPE, TypeUtility.getClassName(classType));
		jsonObj.put(Message.JSON_ATTR_IS_PRIMITIVE, isPrimitive);
		jsonObj.put(Message.JSON_ATTR_INNER_CLASS_TYPE, TypeUtility.getClassName(innerClassType));
		jsonObj.put(Message.JSON_ATTR_IS_COLLECTION, isCollection);
		
		// if the returned values are not collection of primitives,
		// they will be a list of object references (i.e the key in the WeakHashMap)
		JSONArray values = new JSONArray();
		for(int i = 0; i < returnValue.length; i ++) {
			Log.d(TAG, returnValue[i].getClass().getName());
			values.add(TypeUtility.getPrimitiveStringValue(returnValue[i].getClass(), returnValue[i]));
		}
		jsonObj.put(Message.JSON_ATTR_RETURN_VALUE, values);
		
		return jsonObj.toString();
	}

}
