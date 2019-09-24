package com.lotus.core.base.returnmessage;

public class MessageConstant {

	enum MessageType{
		Success(1,true,"操作成功"),
		Fail(2,false,"操作失败"),
		ParameterEmptyController(3,false,"接口请求参数为空"),
		FileFail(4,false,"上传文件异常"),
		LgSuccess(5,true,"登陆成功"),
		LgFail(6,false,"登陆失败");
		
		private int code;
		private boolean ok;
		private String alertMessage;
		
		MessageType(int code, boolean ok, String alertMessage){
			this.code = code;
			this.ok = ok;
			this.alertMessage=alertMessage;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public boolean isOk() {
			return ok;
		}

		public void setOk(boolean ok) {
			this.ok = ok;
		}

		public String getAlertMessage() {
			return alertMessage;
		}

		public void setAlertMessage(String alertMessage) {
			this.alertMessage = alertMessage;
		}
		
	}
}
