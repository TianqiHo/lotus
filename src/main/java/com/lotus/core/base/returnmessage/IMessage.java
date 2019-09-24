package com.lotus.core.base.returnmessage;

public interface IMessage {

	Message buildMessage(Message message);
	
	Message buildDefaultMessage();
	
	Message buildSuccessMessage();
	
	Message buildControllerParameterEmptyMessage();
	
	Message buildFileFailMessage();
	
	Message buildLoginSuccessMessage();
	
	Message buildLoginFailMessage();
}
