package com.lotus.backstage.order.service;

import com.lotus.backstage.order.model.Order;
import com.lotus.backstage.order.model.OrderStatus;
import com.lotus.core.base.returnmessage.Message;

public interface IOrderService {

	Message saveOrUpdateOrder(Order order)throws Exception;
	
	Message selectOrders(Order order)throws Exception;
	
	Message selectClientOrders(Order order)throws Exception;
	
	Message deleteOrders(Order order)throws Exception;
	
	Message updateStutus(OrderStatus orderStatus)throws Exception;
}
