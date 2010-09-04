package com.jayway.android.robotium.remotesolo.proxy;


import net.sf.cglib.proxy.InterfaceMaker;

public class FinalClassInterfaceMaker extends InterfaceMaker {
	
	public FinalClassInterfaceMaker(String namePrefix) {
        super();
        super.setNamePrefix(namePrefix);
        super.setAttemptLoad(true);
	}
	
//	// interface maker
//	FinalClassInterfaceMaker mk = new FinalClassInterfaceMaker("TempInterface");
//	//mk.setNamingPolicy(new CustomNamingPolicy("android.view", "TempInterface"));
//	Class cl = android.view.ViewTreeObserver.class;
//	mk.add(cl);
//	Class newInterface = mk.create();
//	
//	
//	System.out.println(newInterface.getPackage() + "///" + newInterface.getName());
//	
//	// class reader
//	ClassReader cr = null;
//	try {
//		cr = new ClassReader("android.view.ViewTreeObserver");
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	// class writer
//	ClassWriter cw = new ClassWriter(cr,0);
//	// class visitor
//	InterfaceAdder ifa = new InterfaceAdder(cw, newInterface);
//	cr.accept(ifa, 0);
//	
//	byte[] b = cw.toByteArray();
//	
//	MyClassLoader loader = new MyClassLoader();
//	Class<?> newClass = loader.defineClass("android.view.ViewTreeObserver", b);
//	
//	//TraceClassVisitor tc = new TraceClassVisitor(cw,new PrintWriter(System.out));
//	//cr.accept(tc, 0);
//	System.out.println(newClass.toString());
//	
//	Object obj = Whitebox.newInstance(newClass);
//	
//	Object objProxy = MethodCallReceiver.createProxy(obj);
//	if (objProxy instanceof android.view.ViewTreeObserver) {
//		System.out.print(true);
//	} else if (Proxy.isProxyClass(newClass)) {
//		System.out.print("proxy");
//	}

}

