package com.lotus.smallroutine.address.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.jwt.annotation.ClientRequireLogin;
import com.lotus.smallroutine.address.model.Address;
import com.lotus.smallroutine.address.service.IAddressService;


@RestController
@RequestMapping("clientAddress")
public class ClientAddressController extends BaseController<Address>{

	@Override
	protected Class<?> getClassForLogger() {
		return ClientAddressController.class;
	}
	
	@Autowired
	private IAddressService addressService;
	
	
	/**
	 * 新增修改Address
	 * @param address
	 * @return
	 */
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/saveOrUpdateAddress")
	public Message saveOrUpdateAddress(@RequestBody Address address) {
		this.logger.info("-----------saveOrUpdateAddress()开始执行--------------");
		this.logger.info("参数是{}",address.toString());
		
		Message message = null;

		try {
			message = addressService.saveOrUpdateAddress(address);
		} catch (Exception e) {
			message=setException(message, e, "saveOrUpdateAddress()");
		}
		this.logger.info("-----------saveOrUpdateAddress()执行结束--------------");
		printResult(message);
		return message;
	}
	
	
	/**
	 * 分页查询Address
	 * @param address
	 * @return
	 */
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectAddresss")
	public Message selectAddresss(@RequestBody Address address) {
		this.logger.info("-----------selectClientAddresss()开始执行--------------");
		this.logger.info("参数是{}",address.toString());
		
		Message message = null;

		try {
			message = addressService.selectClientAddresss(address);
		} catch (Exception e) {
			message=setException(message, e, "selectClientAddresss()");
		}
		this.logger.info("-----------selectClientAddresss()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/deleteAddresss")
	public Message deleteAddresss(@RequestBody Address address) {
		this.logger.info("-----------deleteAddresss()开始执行--------------");
		this.logger.info("参数是{}",address.toString());
		
		Message message = null;

		try {
			message = addressService.deleteAddresss(address);
		} catch (Exception e) {
			message=setException(message, e, "deleteAddresss()");
		}
		this.logger.info("-----------deleteAddresss()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectAddress")
	public Message selectAddress(@RequestBody Address address) {
		this.logger.info("-----------selectAddress()开始执行--------------");
		this.logger.info("参数是{}",address.toString());
		
		Message message = null;

		try {
			message = addressService.selectAddress(address);
		} catch (Exception e) {
			message=setException(message, e, "selectAddress()");
		}
		this.logger.info("-----------selectAddress()执行结束--------------");
		printResult(message);
		return message;
	}
	
}
