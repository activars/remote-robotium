package com.jayway.android.robotium.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	public static boolean hasListInterfaceType(Class<?> classType) {
		Class<?>[] collectionInterfaces = classType.getInterfaces();

		for (Class<?> c : collectionInterfaces) {
			if (c.getName().equals(List.class.getName())) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasCollectionInterfaceType(Class<?> classType) {

		Class<?>[] collectionInterfaces = classType.getInterfaces();

		for (Class<?> c : collectionInterfaces) {
			if (c.getName().equals(Collection.class.getName())) {
				return true;
			}
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
				|| fullQualifiedClassName.equals(char.class.getName())) {
			return obj.toString();
		} else if (type.equals(Class.class)) {
			return ((Class)obj).getName();
		} else {
			// they are remote references ID
			return obj.toString();
		}
	}
	
	
	public static boolean isPrimitive(Class<?> classType) {
		if(classType.equals(int.class) || classType.equals(Integer.class) 
				|| classType.equals(boolean.class) || classType.equals(Boolean.class) 
				|| classType.equals(short.class) || classType.equals(Short.class) 
				|| classType.equals(long.class) || classType.equals(Long.class) 
				|| classType.equals(float.class) || classType.equals(Float.class) 
				|| classType.equals(double.class) || classType.equals(Double.class)
				|| classType.equals(byte.class) || classType.equals(Byte.class) 
				|| classType.equals(char.class) || classType.equals(Character.class)
				|| classType.equals(String.class)) {
			return true;
		}
		return false;
	}

	public static String getClassName(Class<?> classType) {
		if (classType.getName().contains("$$")) {
			String[] splName = classType.getName().split("[$$]");
			for (String cnm : splName) {
				if (!cnm.trim().equals("")) {
					return cnm;
				}
			}
		} else if (classType.equals(Boolean.class)) {
			return boolean.class.getName();
			// TODO: adding more primitive return type checking
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
		} else if (typeName.equals(Class.class.getName())) {
			try {
				return Class.forName(objectValue);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			// this could be a remote reference
			// so simply return the string
			return objectValue.toString();
		}
	}
	
	public static String getArgumentsStringValue(Object[] arguments) {
		String result = "";
		for(Object obj : arguments) {
			result += String.format("[%s]", obj.toString());
		}
		return result;
	}
}
