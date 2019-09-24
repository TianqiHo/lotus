package com.lotus.core.base.returnmessage;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringBootConfiguration;

import com.lotus.core.proxy.ProxyUtil;


@SpringBootConfiguration
public class MessageConfig implements FactoryBean<IMessage> {

	@Override
	public IMessage getObject() throws Exception {
		MessageRealization mr = new MessageRealization();
		IMessage iMessage = ProxyUtil.Instance.createProxy(mr,new MessageInvocationHandler(mr));
		return iMessage;
	}

	@Override
	public Class<?> getObjectType() {
		return IMessage.class;
	}

	@Override
	public boolean isSingleton() {
		return FactoryBean.super.isSingleton();
	}
	
}
