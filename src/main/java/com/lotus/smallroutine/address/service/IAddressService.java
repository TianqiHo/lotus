package com.lotus.smallroutine.address.service;

import com.lotus.core.base.returnmessage.Message;
import com.lotus.smallroutine.address.model.Address;

public interface IAddressService {

	    public Message saveOrUpdateAddress(Address address)throws Exception;
		
		public Message selectAddress(Address address)throws Exception;
		
		public Message selectAddresss(Address address)throws Exception;
		
		public Message selectClientAddresss(Address address)throws Exception;
		
		public Message deleteAddresss(Address address)throws Exception;
}
