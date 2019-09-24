package com.lotus.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public enum ProxyUtil{
	
	
	Instance;
	
	public <InterfaceByTargetClass> InterfaceByTargetClass createProxy(InterfaceByTargetClass targetClass,InvocationHandler handler) {
		InterfaceByTargetClass proxyInstance = createProxy(targetClass.getClass().getClassLoader(),
				(Class<? extends InterfaceByTargetClass>[]) targetClass.getClass().getGenericInterfaces(), handler); 
		return proxyInstance;
	}
	
	public <InterfaceByTargetClass> InterfaceByTargetClass createProxy(ClassLoader targetLoader,
			Class<? extends InterfaceByTargetClass>[] targetInterfaces,InvocationHandler handler) {
		InterfaceByTargetClass proxyInstance = (InterfaceByTargetClass)Proxy.newProxyInstance(targetLoader, targetInterfaces, handler); 
		return proxyInstance;
	}
	
}
