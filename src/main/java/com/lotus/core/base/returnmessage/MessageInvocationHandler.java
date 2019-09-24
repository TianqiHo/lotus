package com.lotus.core.base.returnmessage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.lotus.core.proxy.jdk.AbstractInvocationHandler;


public class MessageInvocationHandler extends AbstractInvocationHandler<MessageRealization> implements InvocationHandler{

	public MessageInvocationHandler(MessageRealization targetClass) {
		super(targetClass);
	}

	@Override
	protected void beforeInvoke(Object proxy, Method method, Object[] args) throws Throwable {
		
	}

	@Override
	protected void afterInvoke(Object proxy, Method method, Object[] args, Object invokeResult) throws Throwable {
		if(null!=invokeResult) {
			Message message = (Message)invokeResult;
			/*
			switch (message.getMessageType()) {
			case Success:
				message.setCode(message.getMessageType().getCode());
				message.setSuccess(message.getMessageType().isOk());
				message.setAlertMessage(message.getMessageType().getAlertMessage());
				break;

			default:
				message.setCode(message.getMessageType().getCode());
				message.setSuccess(message.getMessageType().isOk());
				message.setAlertMessage(message.getMessageType().getAlertMessage());
				break;
			}*/
			if(null!=message.getMessageType()) {
				message.setCode(message.getMessageType().getCode());
				message.setSuccess(message.getMessageType().isOk());
				message.setAlertMessage(message.getMessageType().getAlertMessage());
				message.setMessageType(null);
			}
		}
		
		
	}

	@Override
	protected Class<?> getClassForLogger() {
		return this.getClass();
	}
}
