package com.lotus.smallroutine.fabulous.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.jwt.annotation.ClientRequireLogin;
import com.lotus.smallroutine.fabulous.model.Fabulous;
import com.lotus.smallroutine.fabulous.service.IFabulousService;


@RestController
@RequestMapping("clientFabulous")
public class ClientFabulousController extends BaseController<Fabulous> {

	@Override
	protected Class<?> getClassForLogger() {
		return ClientFabulousController.class;
	}
	
	@Autowired
	private IFabulousService fabulousService;
	
	/**
	  *  点赞评论
	 * @param json
	 * @return
	 */
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/fabulousSum")
	public Message fabulousSum(@RequestBody Fabulous fabulous) {
		this.logger.info("-----------fabulousSum()开始执行--------------");
		this.logger.info("参数是{}",fabulous.toString());
		
		Message message = null;

		try {
			message = fabulousService.fabulousSum(fabulous);
		} catch (Exception e) {
			message=setException(message, e, "fabulousSum()");
		}
		this.logger.info("-----------fabulousSum()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	   * 取消点赞评论
	 * @param json
	 * @return
	 */
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/cancleFabulousSum")
	public Message cancleFabulousSum(@RequestBody Fabulous fabulous) {
		this.logger.info("-----------cancleFabulousSum()开始执行--------------");
		this.logger.info("参数是{}",fabulous.toString());
		
		Message message = null;

		try {
			message = fabulousService.cancleFabulousSum(fabulous);
		} catch (Exception e) {
			message=setException(message, e, "cancleFabulousSum()");
		}
		this.logger.info("-----------cancleFabulousSum()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectFabulous")
	public Message selectFabulous(@RequestBody Fabulous json) {
		this.logger.info("-----------selectFabulous()开始执行--------------");
		this.logger.info("参数是{}",json.toString());
		
		Message message = null;

		try {
			message = fabulousService.selectFabulous(json);
		} catch (Exception e) {
			message=setException(message, e, "selectFabulous()");
		}
		this.logger.info("-----------selectFabulous()执行结束--------------");
		printResult(message);
		return message;
	}
	
	
	
	
	
	/**
	 * 新增修改Fabulous
	 * @param json
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/saveOrUpdateFabulous")
	public Message saveOrUpdateFabulous(@RequestBody Fabulous json) {
		this.logger.info("-----------saveOrUpdateFabulous()开始执行--------------");
		this.logger.info("参数是{}",json.toString());
		
		Message message = null;

		try {
			message = fabulousService.saveOrUpdateFabulous(json);
		} catch (Exception e) {
			message=setException(message, e, "saveOrUpdateFabulous()");
		}
		this.logger.info("-----------saveOrUpdateFabulous()执行结束--------------");
		printResult(message);
		return message;
	}
	
	
	
	/**
	 * 分页查询Fabulous
	 * @param json
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectFabulouss")
	public Message selectFabulouss(@RequestBody Fabulous json) {
		this.logger.info("-----------selectFabulouss()开始执行--------------");
		this.logger.info("参数是{}",json.toString());
		
		Message message = null;

		try {
			message = fabulousService.selectFabulouss(json);
		} catch (Exception e) {
			message=setException(message, e, "selectFabulouss()");
		}
		this.logger.info("-----------selectFabulouss()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/deleteFabulouss")
	public Message deleteFabulouss(@RequestBody Fabulous json) {
		this.logger.info("-----------deleteFabulouss()开始执行--------------");
		this.logger.info("参数是{}",json.toString());
		
		Message message = null;

		try {
			message = fabulousService.deleteFabulouss(json);
		} catch (Exception e) {
			message=setException(message, e, "deleteFabulouss()");
		}
		this.logger.info("-----------deleteFabulouss()执行结束--------------");
		printResult(message);
		return message;
	}
}
