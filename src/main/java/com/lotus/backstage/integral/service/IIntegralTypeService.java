package com.lotus.backstage.integral.service;

import com.lotus.backstage.integral.model.IntegralType;
import com.lotus.core.base.returnmessage.Message;

public interface IIntegralTypeService {

    public Message saveOrUpdateIntegralType(IntegralType integralType)throws Exception;
	
	public Message selectIntegralType(IntegralType integralType)throws Exception;
	
	public Message selectIntegralTypes(IntegralType integralType)throws Exception;
	
	public Message deleteIntegralTypes(IntegralType integralType)throws Exception;
}
