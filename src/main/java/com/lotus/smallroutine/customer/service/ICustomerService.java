package com.lotus.smallroutine.customer.service;

import com.lotus.backstage.integral.model.Integral;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.smallroutine.customer.model.Customer;

public interface ICustomerService {
	
	Message insertOrUpdate(Customer customer)throws Exception;
	
	Message login(Customer customer)throws Exception;
	
	Message update(Customer customer);
	
	Message selectCustomer(Customer customer)throws Exception;
	
	Message selects(Customer customer);

	Message signIn(Customer customer)throws Exception;

	Message isSignIn(Integral integral)throws Exception;

}
