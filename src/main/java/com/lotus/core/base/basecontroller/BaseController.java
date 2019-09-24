package com.lotus.core.base.basecontroller;

import org.springframework.beans.factory.annotation.Autowired;

import com.lotus.core.base.basefile.BaseFileUpDown;
import com.lotus.core.base.baselogger.AbstractLogger;
import com.lotus.core.base.returnmessage.IMessage;
import com.lotus.core.base.returnmessage.Message;


public class BaseController<T> extends AbstractLogger{
	
	@Autowired
	protected IMessage iMessage;
	
	@Autowired
	protected BaseFileUpDown baseFileUpDown;
	
	@Override
	protected Class<?> getClassForLogger() {
		return this.getClass();
	}
	
	protected Message setException(Message message,Exception e,String methodName) {
		message = (message!=null?message:(message=this.iMessage.buildDefaultMessage()));
		message.obtainException(e);
		logger.error(methodName,e);
		return message;
	}
	
	protected void printResult(Message message) {
		if(message.isSuccess()){
			logger.info("结果是{}",message.toString());
		}
	}
}
