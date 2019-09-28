package com.lotus.backstage.integral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.backstage.integral.model.IntegralType;
import com.lotus.backstage.integral.service.IIntegralTypeService;
import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.jwt.annotation.ServerRequireLogin;

@RestController
@RequestMapping("integralType")
@ServerRequireLogin(excludeUriNames = {"selectIntegralTypes","deleteIntegralTypes","selectIntegralType"},require = false)
public class IntegralTypeController extends BaseController<IntegralType> {

	@Autowired
	private IIntegralTypeService integralTypeService;
	
	@Override
	protected Class<?> getClassForLogger() {
		return IntegralTypeController.class;
	}
	
	/**
	 * 新增修改Banner
	 * @param json
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/saveOrUpdateIntegralType")
	public Message saveOrUpdateIntegralType(@RequestBody IntegralType json) {
		this.logger.info("-----------saveOrUpdateIntegralType()开始执行--------------");
		this.logger.info("参数是{}",json.toString());
		
		Message message = null;

		try {
			message = integralTypeService.saveOrUpdateIntegralType(json);
		} catch (Exception e) {
			message=setException(message, e, "saveOrUpdateIntegralType()");
		}
		this.logger.info("-----------saveOrUpdateIntegralType()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	 * 分页查询IntegralType
	 * @param json
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectIntegralTypes")
	public Message selectIntegralTypes(@RequestBody IntegralType json) {
		this.logger.info("-----------selectIntegralTypes()开始执行--------------");
		this.logger.info("参数是{}",json.toString());
		
		Message message = null;

		try {
			message = integralTypeService.selectIntegralTypes(json);
		} catch (Exception e) {
			message=setException(message, e, "selectIntegralTypes()");
		}
		this.logger.info("-----------selectIntegralTypes()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/deleteIntegralTypes")
	public Message deleteIntegralTypes(@RequestBody IntegralType json) {
		this.logger.info("-----------deleteIntegralTypes()开始执行--------------");
		this.logger.info("参数是{}",json.toString());
		
		Message message = null;

		try {
			message = integralTypeService.deleteIntegralTypes(json);
		} catch (Exception e) {
			message=setException(message, e, "deleteIntegralTypes()");
		}
		this.logger.info("-----------deleteIntegralTypes()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectIntegralType")
	public Message selectIntegralType(@RequestBody IntegralType json) {
		this.logger.info("-----------selectIntegralType()开始执行--------------");
		this.logger.info("参数是{}",json.toString());
		
		Message message = null;

		try {
			message = integralTypeService.selectIntegralType(json);
		} catch (Exception e) {
			message=setException(message, e, "");
		}
		this.logger.info("-----------selectIntegralType()执行结束--------------");
		printResult(message);
		return message;
	}
}
