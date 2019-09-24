package com.lotus.core.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.lotus.core.base.baselogger.AbstractLogger;

abstract public class AbstractInvocationHandler<TargetClass> extends AbstractLogger implements InvocationHandler{
	
    private TargetClass targetClass;
     
	public AbstractInvocationHandler(TargetClass targetClass) {
		super();
		this.targetClass = targetClass;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		beforeInvoke(proxy, method, args);
		Object result = method.invoke(targetClass, args);
		afterInvoke(proxy, method, args, result);
		return result;
	}
	
	protected abstract void beforeInvoke(Object proxy, Method method, Object[] args) throws Throwable;
	
	protected abstract void afterInvoke(Object proxy, Method method, Object[] args,Object InvokeResult) throws Throwable;

	@Override
	protected Class<?> getClassForLogger() {
		return this.getClass();
	}
}
