package com.lotus.backstage.integral.service;

import com.lotus.backstage.integral.model.Integral;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.returnmessage.Message;

public interface IIntegralService {

    public Message saveOrUpdateIntegral(Integral integral)throws BaseException;
	
	public Message selectIntegral(Integral integral)throws BaseException;
	
	public Message selectIntegrals(Integral integral)throws BaseException;
	
	public Message deleteIntegrals(Integral integral)throws BaseException;
}
