package com.lotus.smallroutine.follow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.jwt.annotation.ClientRequireLogin;
import com.lotus.smallroutine.follow.model.Follow;
import com.lotus.smallroutine.follow.service.IFollowService;

@RestController
@RequestMapping("clientFollow")
public class ClientFollowController extends BaseController<Follow> {

	@Autowired
	private IFollowService followService;
	
	
	@Override
	protected Class<?> getClassForLogger() {
		return ClientFollowController.class;
	}
	
	
	/**
	 * 分页查询
	 * @param follow
	 * @return
	 */
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectFollows")
	public Message selectFollows(@RequestBody Follow follow) {
		this.logger.info("-----------selectFollows()开始执行--------------");
		this.logger.info("参数是{}",follow.toString());
		
		Message message = null;

		try {
			message = followService.selectFollows(follow);
		} catch (Exception e) {
			message=setException(message, e, "selectFollows()");
		}
		this.logger.info("-----------selectFollows()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	 * 新增修改
	 * @param follow
	 * @return
	 */
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/saveOrUpdateFollow")
	public Message saveOrUpdateFollow(@RequestBody Follow follow) {
		this.logger.info("-----------saveOrUpdateFollow()开始执行--------------");
		this.logger.info("参数是{}",follow.toString());
		
		Message message = null;

		try {
			message = followService.saveOrUpdateFollow(follow);
		} catch (Exception e) {
			message=setException(message, e, "saveOrUpdateFollow()");
		}
		this.logger.info("-----------saveOrUpdateFollow()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	 * 删除关注
	 * @param follow
	 * @return
	 */
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/deleteFollows")
	public Message deleteFollows(@RequestBody Follow follow) {
		this.logger.info("-----------deleteFollows()开始执行--------------");
		this.logger.info("参数是{}",follow.toString());
		
		Message message = null;
		try {
			message = followService.deleteFollows(follow);
		} catch (Exception e) {
			message=setException(message, e, "deleteFollows()");
		}
		this.logger.info("-----------deleteFollows()执行结束--------------");
		printResult(message);
		return message;
	}
}
