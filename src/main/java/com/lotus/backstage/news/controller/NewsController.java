package com.lotus.backstage.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.backstage.news.model.News;
import com.lotus.backstage.news.service.INewsService;
import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentBackstageUser;
import com.lotus.core.jwt.annotation.ServerRequireLogin;

@RestController
@RequestMapping("news")
public class NewsController extends BaseController<News>{

	@Override
	protected Class<?> getClassForLogger() {
		return NewsController.class;
	}
	
	@Autowired
	private INewsService newsService;
	

	/**
	 * 新增修改News
	 * @param json
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/saveOrUpdateNews")
	public Message saveOrUpdateNews(@RequestBody News json) {
		this.logger.info("-----------saveOrUpdateNews()开始执行--------------");
		this.logger.info("参数是{}",json.toString());
		
		Message message = null;

		try {
			if(StringUtils.isEmpty(json.getId())) {
				json.setCreateBy(CurrentBackstageUser.obtainUserID());
			}else {
				json.setUpdateBy(CurrentBackstageUser.obtainUserID());
			}
			message = newsService.saveOrUpdateNews(json);
		} catch (Exception e) {
			message=setException(message, e, "saveOrUpdateNews()");
		}
		this.logger.info("-----------saveOrUpdateNews()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	 * 分页查询News
	 * @param json
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectNewss")
	public Message selectNewss(@RequestBody News json) {
		this.logger.info("-----------selectNewss()开始执行--------------");
		this.logger.info("参数是{}",json.toString());
		
		Message message = null;

		try {
			message = newsService.selectNewss(json);
		} catch (Exception e) {
			message=setException(message, e, "selectNewss()");
		}
		this.logger.info("-----------selectNewss()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/deleteNewss")
	public Message deleteNewss(@RequestBody News json) {
		this.logger.info("-----------deleteNewss()开始执行--------------");
		this.logger.info("参数是{}",json.toString());
		
		Message message = null;

		try {
			message = newsService.deleteNewss(json);
		} catch (Exception e) {
			message=setException(message, e, "deleteNewss()");
		}
		this.logger.info("-----------deleteNewss()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectNews")
	public Message selectNews(@RequestBody News json) {
		this.logger.info("-----------selectNews()开始执行--------------");
		this.logger.info("参数是{}",json.toString());
		
		Message message = null;
		try {
			message = newsService.selectNews(json);
		} catch (Exception e) {
			message=setException(message, e, "selectNews()");
		}
		this.logger.info("-----------selectNews()执行结束--------------");
		printResult(message);
		return message;
	}
}
