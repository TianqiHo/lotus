package com.lotus.smallroutine.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.backstage.order.model.Order;
import com.lotus.backstage.order.model.OrderStatus;
import com.lotus.backstage.order.service.IOrderService;
import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.jwt.annotation.ClientRequireLogin;

@RestController
@RequestMapping("clientOrder")
public class ClientOrderController extends BaseController<Order> {
	
	@Override
	protected Class<?> getClassForLogger() {
		return ClientOrderController.class;
	}
	
	@Autowired
	private IOrderService orderService;

	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/saveOrUpdateOrder")
	public Message saveOrUpdateOrder(@RequestBody Order order) {
		this.logger.info("-----------saveOrUpdateOrder()开始执行--------------");
		this.logger.info("参数是{}",order.toString());
		
		Message message = null;

		try {
			message = orderService.saveOrUpdateOrder(order);
		} catch (Exception e) {
			message=setException(message, e, "saveOrUpdateOrder()");
		}
		this.logger.info("-----------saveOrUpdateOrder()执行结束--------------");
		printResult(message);
		return message;
	}
	 
	
	/**
	 * 分页查询Order
	 * @param order
	 * @return
	 */
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectOrders")
	public Message selectOrders(@RequestBody Order order) {
		this.logger.info("-----------selectOrders()开始执行--------------");
		this.logger.info("参数是{}",order.toString());
		
		Message message = null;
		try {
			message = orderService.selectClientOrders(order);
		} catch (Exception e) {
			message = setException(message, e, "selectOrders()");
		}
		
		this.logger.info("-----------selectOrders()执行结束--------------");
		printResult(message);
		return message;
	}
	

	/**
	 * 更新订单状态
	 * @param order
	 * @return
	 */
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/updateOrderStutus")
	public Message updateOrderStutus(@RequestBody OrderStatus orderStatus) {
		this.logger.info("-----------updateOrderStutus()开始执行--------------");
		this.logger.info("参数是{}",orderStatus.toString());
		
		Message message = null;
		try {
			message = orderService.updateStutus(orderStatus);
		} catch (Exception e) {
			message = setException(message, e, "updateOrderStutus()");
		}
		
		this.logger.info("-----------updateOrderStutus()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/deleteOrders")
	public Message deleteOrders(@RequestBody Order order) {
		this.logger.info("-----------deleteOrders()开始执行--------------");
		this.logger.info("参数是{}",order.toString());
		
		Message message = null;
		try {
			message = orderService.deleteOrders(order);
		} catch (Exception e) {
			message=setException(message, e, "deleteOrders()");
		}
		this.logger.info("-----------deleteOrders()执行结束--------------");
		printResult(message);
		return message;
	}
	
}
