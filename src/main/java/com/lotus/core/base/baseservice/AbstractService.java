package com.lotus.core.base.baseservice;

import org.springframework.beans.factory.annotation.Autowired;

import com.lotus.core.base.baselogger.AbstractLogger;
import com.lotus.core.base.returnmessage.IMessage;


public abstract class AbstractService extends AbstractLogger {

	@Autowired
	protected IMessage iMessage;

	@Override
	protected Class<?> getClassForLogger() {
		return AbstractService.class;
	}
	
	
}
