package com.jayway.android.robotium.common.util;

import java.util.ArrayList;

import net.sf.cglib.proxy.Enhancer;

public class TypeUtils {

	public static Class<?> getClassName(String name)
			throws ClassNotFoundException {
		if (name.equals("byte"))
			return byte.class;
		if (name.equals("short"))
			return short.class;
		if (name.equals("int"))
			return int.class;
		if (name.equals("long"))
			return long.class;
		if (name.equals("char"))
			return char.class;
		if (name.equals("float"))
			return float.class;
		if (name.equals("double"))
			return double.class;
		if (name.equals("boolean"))
			return boolean.class;
		if (name.equals("void"))
			return void.class;
		
		return Class.forName(name);
	}

	public static boolean isCollectionType(Class<?> classType) {

		if (classType.equals(ArrayList.class)) {
			return true;
		}
		return false;
	}

	public static String getPrimitiveStringValue(Class<?> type, Object obj) {
		if (obj == null) {
			return "null";
		}

		String fullQualifiedClassName = getClassName(type);

		if (fullQualifiedClassName.equals(String.class.getName())
				|| fullQualifiedClassName.equals(byte.class.getName())
				|| fullQualifiedClassName.equals(int.class.getName())
				|| fullQualifiedClassName.equals(short.class.getName())
				|| fullQualifiedClassName.equals(long.class.getName())
				|| fullQualifiedClassName.equals(float.class.getName())
				|| fullQualifiedClassName.equals(double.class.getName())
				|| fullQualifiedClassName.equals(boolean.class.getName())
				|| fullQualifiedClassName.equals(char.class.getName())
				|| fullQualifiedClassName.equals(CharSequence.class.getName())) {
			return obj.toString();
		} else {
			throw new UnsupportedOperationException(
					"The type of object is not primitive, ");
		}
	}

	public static String getClassName(Class<?> classType) {
		if (classType.getName().contains("$$")) {
			String[] splName = classType.getName().split("[$$]");
			if (splName.length > 0) {
				return splName[0];
			}
		} else if (classType.equals(Boolean.class)) {
			return boolean.class.getName();
			//TODO: adding more primitive return type checking
		} 
		return classType.getName();
	}

	public static Object getObject(String typeName, String objectValue) {

		if (typeName.equals(String.class.getName())) {
			return objectValue.toString();
		} else if (typeName.equals(byte.class.getName())) {
			return Byte.parseByte(objectValue);
		} else if (typeName.equals(int.class.getName())) {
			return Integer.parseInt(objectValue);
		} else if (typeName.equals(short.class.getName())) {
			return Short.parseShort(objectValue);
		} else if (typeName.equals(long.class.getName())) {
			return Long.parseLong(objectValue);
		} else if (typeName.equals(float.class.getName())) {
			return Float.parseFloat(objectValue);
		} else if (typeName.equals(double.class.getName())) {
			return Double.parseDouble(objectValue);
		} else if (typeName.equals(boolean.class.getName())) {
			return Boolean.parseBoolean(objectValue);
		} else if (typeName.equals(char.class.getName())) {
			return objectValue.toCharArray()[0];
		} else if (typeName.equals(CharSequence.class.getName())) {
			return objectValue.toString();
		} else {
			throw new UnsupportedOperationException(
					"The type of object is not primitive, ");
		}
	}
}
