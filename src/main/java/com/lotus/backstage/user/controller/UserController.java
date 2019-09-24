package com.lotus.backstage.user.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.backstage.user.model.User;
import com.lotus.backstage.user.service.IUserService;
import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;

@RestController
@RequestMapping("user")
public class UserController extends BaseController<User>{

	@Autowired
	private IUserService userService;
	
	/**
	 * 新增修改用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/saveOrUpdateUser")
	public Message saveOrUpdateUser(@RequestBody User user) {
		this.logger.info("-----------saveOrUpdateUser开始执行--------------");
		this.logger.info("参数是{}",user.toString());
		
		Message message=null;
		try {
			message = userService.insertOrUpdate(user);
		} catch (Exception e) {
			message=setException(message, e, "saveOrUpdateUser()");
		}
		this.logger.info("-----------saveOrUpdateUser执行结束--------------");
		printResult(message);
		return message;
	}
	
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/login")
	public Message login(HttpServletResponse response,@RequestBody User user) {
		this.logger.info("-----------login()开始执行--------------");
		this.logger.info("参数是{}",user.toString());
		Message message=null;
		
		try {
			message = userService.login(user);
			if(message.isSuccess()) {
				Cookie cookie = new Cookie("LOTUS",message.getData().toString());  //对比入参数据
		        response.addCookie(cookie);
			}
		} catch (Exception e) {
			message=setException(message, e, "login()");
		}
		
		this.logger.info("-----------login()执行结束--------------");
		printResult(message);
		return message;
	}
	
}
