package com.lotus.core.base.baseexception;

import com.lotus.core.base.returnmessage.Message;

public final class BaseException extends Exception{
	
	private static final long serialVersionUID = -1055305222477511340L;
	
	private Message serverMessage;
	
	public BaseException(final Message serverMessage) {
		this.serverMessage=serverMessage;
	}


	public BaseException(String msg) {
		super(msg);
	}

	public BaseException(Exception e) {
		super(e);
	}

	public Message getServerMessage() {
		return serverMessage;
	}


	public void setServerMessage(Message serverMessage) {
		this.serverMessage = serverMessage;
		serverMessage.setCause(this);
		serverMessage.setRealMessage(this.getMessage());
	}
}
