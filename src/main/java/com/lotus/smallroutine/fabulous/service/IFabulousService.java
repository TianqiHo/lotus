package com.lotus.smallroutine.fabulous.service;

import com.lotus.core.base.returnmessage.Message;
import com.lotus.smallroutine.fabulous.model.Fabulous;

public interface IFabulousService {

    Message saveOrUpdateFabulous(Fabulous fabulous)throws Exception;
	
	Message selectFabulous(Fabulous fabulous)throws Exception;
	
	Message selectFabulouss(Fabulous fabulous)throws Exception;
	
	Message deleteByPrimaryKey(Fabulous fabulous)throws Exception;

	Message deleteFabulouss(Fabulous fabulous)throws Exception;

	Message fabulousSum(Fabulous fabulous)throws Exception;

	Message cancleFabulousSum(Fabulous fabulous)throws Exception;
}
