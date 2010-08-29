package com.jayway.android.robotium.remotesolo.proxy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class InterfaceAdder extends ClassAdapter {
	private Class newInterface;
	public InterfaceAdder(ClassVisitor cv, Class newInterface) {
		super(cv);
		this.newInterface = newInterface;
	}
	

	public void visit(int version, int access,String name, String signature, String superName, String[] interfaces) {
		String str = newInterface.getSimpleName();
		String[] strInterfaces = {str};
		cv.visit(version, access, name, signature,superName, strInterfaces);
	}
}
