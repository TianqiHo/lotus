package com.lotus.core.base.returnmessage;

import com.lotus.core.base.returnmessage.MessageConstant.MessageType;

public class MessageRealization implements IMessage {

	@Override
	public Message buildMessage(Message message) {
		return message;
	}

	@Override
	public Message buildDefaultMessage() {
		return new Message(MessageType.Fail);
	}

	@Override
	public Message buildSuccessMessage() {
		return new Message(MessageType.Success);
	}

	@Override
	public Message buildControllerParameterEmptyMessage() {
		return new Message(MessageType.ParameterEmptyController);
	}

	@Override
	public Message buildFileFailMessage() {
		return new Message(MessageType.FileFail);
	}

	@Override
	public Message buildLoginSuccessMessage() {
		return new Message(MessageType.LgSuccess);
	}

	@Override
	public Message buildLoginFailMessage() {
		return new Message(MessageType.LgFail);
	}
}
