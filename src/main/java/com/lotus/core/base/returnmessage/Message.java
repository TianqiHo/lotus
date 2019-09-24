package com.lotus.core.base.returnmessage;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lotus.core.base.returnmessage.MessageConstant.MessageType;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"messageType"})
public final class Message implements Serializable {
	
	private static final long serialVersionUID = 9061210628252224755L;

	private int code;
	
	private boolean success;
	
	private String alertMessage;
	
	private String realMessage;
	
	private Throwable cause;
	
	private Object data;
	
	private MessageType messageType;

	public Message(int code, boolean success, String alertMessage, String realMessage) {
		super();
		this.code = code;
		this.success = success;
		this.alertMessage = alertMessage;
		this.realMessage = realMessage;
	}
	
	public void obtainException(Throwable cause) {
		this.cause = cause;
		realMessage = cause.getMessage();
	}

	public Message(MessageType messageType) {
		this.messageType = messageType;
	}
	
	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	public String getRealMessage() {
		return realMessage;
	}

	public void setRealMessage(String realMessage) {
		this.realMessage = realMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public String toString() {
		return "Message [code=" + code + ", success=" + success + ", alertMessage=" + alertMessage + ", realMessage="
				+ realMessage + ", data=" + data + ", messageType=" + messageType + "]";
	}
}
