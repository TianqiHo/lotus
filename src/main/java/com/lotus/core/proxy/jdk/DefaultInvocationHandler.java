package com.lotus.core.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DefaultInvocationHandler extends AbstractInvocationHandler<Object> implements InvocationHandler {

	public DefaultInvocationHandler(Object targetClass) {
		super(targetClass);
	}

	@Override
	protected void beforeInvoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterInvoke(Object proxy, Method method, Object[] args, Object InvokeResult) throws Throwable {
		// TODO Auto-generated method stub
		
	}
	
}
