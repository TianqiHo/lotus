package com.lotus.core.base.baseservice;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.lotus.core.base.baselogger.AbstractLogger;
import com.lotus.core.base.returnmessage.IMessage;


public abstract class AbstractService extends AbstractLogger {

	@Autowired
	protected IMessage iMessage;
	
	//@Autowired
	//protected PageHelper pageHelper;

	@Override
	protected Class<?> getClassForLogger() {
		return AbstractService.class;
	}
	
	
}
