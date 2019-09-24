package com.lotus.smallroutine.customer.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.backstage.integral.model.Integral;
import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.jwt.annotation.ClientRequireLogin;
import com.lotus.smallroutine.customer.model.Customer;
import com.lotus.smallroutine.customer.service.ICustomerService;

@RestController
@RequestMapping("customer")
public class CustomerController extends BaseController<Customer>{

	@Autowired
	private ICustomerService customerService;
	
	/**
	 * 新增修改用户信息
	 * @param customer
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/saveOrUpdateCustomer")
	public Message saveOrUpdateCustomer(@RequestBody Customer customer) {
		this.logger.info("-----------saveOrUpdateCustomer()开始执行--------------");
		this.logger.info("参数是{}",customer.toString());
		
		Message message=null;

		try {
			message = customerService.insertOrUpdate(customer);
		} catch (Exception e) {
			message=setException(message, e, "saveOrUpdateCustomer()");
		}
		this.logger.info("-----------saveOrUpdateCustomer()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/login")
	public Message login(@RequestBody Customer cuetomer) {
		this.logger.info("-----------login()开始执行--------------");
		this.logger.info("参数是{}",cuetomer.toString());
		Message message=null;
		
		try {
			message = customerService.login(cuetomer);
		} catch (Exception e) {
			message=setException(message, e, "login()");
		}
		
		this.logger.info("-----------login()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectCustomer")
	public Message selectCustomer(@RequestBody Customer customer) {
		this.logger.info("-----------selectCustomer()开始执行--------------");
		this.logger.info("参数是{}",customer.toString());
		
		Message message = null;

		try {
			message = customerService.selectCustomer(customer);
		} catch (Exception e) {
			message=setException(message, e, "selectCustomer()");
		}
		this.logger.info("-----------selectCustomer()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/signIn")
	public Message signIn(@RequestBody Customer customer) {
		this.logger.info("-----------signIn()开始执行--------------");
		this.logger.info("参数是{}",customer.toString());
		
		Message message = null;

		try {
			message = customerService.signIn(customer);
		} catch (Exception e) {
			message=setException(message, e, "signIn()");
		}
		this.logger.info("-----------signIn()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/isSignIn")
	public Message isSignIn(@RequestBody Integral integral) {
		this.logger.info("-----------isSignIn()开始执行--------------");
		
		Message message = null;

		try {
			message = customerService.isSignIn(integral);
		} catch (Exception e) {
			message=setException(message, e, "isSignIn()");
		}
		this.logger.info("-----------isSignIn()执行结束--------------");
		printResult(message);
		return message;
	}
	
}
