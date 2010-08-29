package com.jayway.android.robotium.remotesolo.proxy;

import java.io.IOException;

import net.sf.cglib.proxy.InterfaceMaker;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class MyClassLoader extends ClassLoader { 
	
	public Class<?> defineClass(String name, byte[] b) {
		return defineClass(name, b, 0, b.length);
	}
	
}