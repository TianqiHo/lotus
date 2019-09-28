package com.lotus.smallroutine.news;

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
import com.lotus.core.concurrent.CurrentWxMiniCustomer;
import com.lotus.core.jwt.annotation.ClientRequireLogin;

@RestController
@RequestMapping("clientNews")
@ClientRequireLogin(require = false,excludeMethodName = {"selectNewss"})
public class ClientNewsController extends BaseController<News>{

	@Override
	protected Class<?> getClassForLogger() {
		return ClientNewsController.class;
	}
	
	@Autowired
	private INewsService newsService;
	

	/**
	 * 新增修改News
	 * @param json
	 * @return
	 */
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/saveOrUpdateNews")
	public Message saveOrUpdateNews(@RequestBody News news) {
		this.logger.info("-----------saveOrUpdateNews()开始执行--------------");
		this.logger.info("参数是{}",news.toString());
		
		Message message = null;

		try {
			if(StringUtils.isEmpty(news.getId())) {
				news.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
				news.setNewsAuthor(CurrentWxMiniCustomer.obtainCustomer().getNickName());
				news.setNewsFrom(CurrentWxMiniCustomer.obtainCustomer().getNickName());
			}else {
				news.setUpdateBy(CurrentWxMiniCustomer.obtainCustomerID());
			}
			message = newsService.saveOrUpdateNews(news);
		} catch (Exception e) {
			message=setException(message, e, "saveOrUpdateNews()");
		}
		this.logger.info("-----------saveOrUpdateNews()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/seeSum")
	public Message seeSum(@RequestBody News json) {
		this.logger.info("-----------seeSum()开始执行--------------");
		this.logger.info("参数是{}",json.toString());
		
		Message message = null;
		try {
			message = newsService.seeSum(json);
		} catch (Exception e) {
			message=setException(message, e, "seeSum()");
		}
		
		this.logger.info("-----------seeSum()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	 * 分页查询News
	 * @param json
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectNewss")
	public Message selectNewss(@RequestBody News json) {
		this.logger.info("-----------selectNewss()开始执行--------------");
		this.logger.info("参数是{}",json.toString());
		
		Message message = null;
		try {
			message = newsService.selectClientNewss(json);
		} catch (Exception e) {
			message=setException(message, e, "selectNewss()");
		}
		this.logger.info("-----------selectNewss()执行结束--------------");
		printResult(message);
		return message;
	}
	
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
	
	
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectNews")
	public Message selectNews(@RequestBody News news) {
		this.logger.info("-----------selectNews()开始执行--------------");
		this.logger.info("参数是{}",news.toString());
		
		Message message = null;

		try {
			message = newsService.selectNewsAndIsFollow(news);//selectNews(json);
		} catch (Exception e) {
			message=setException(message, e, "selectNews()");
		}
		this.logger.info("-----------selectNews()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/shareNews")
	public Message shareNews(@RequestBody News news) {
		this.logger.info("-----------shareNews()开始执行--------------");
		this.logger.info("参数是{}",news.toString());
		
		Message message = null;

		try {
			message = newsService.shareNews(news);
		} catch (Exception e) {
			message=setException(message, e, "shareNews()");
		}
		this.logger.info("-----------shareNews()执行结束--------------");
		this.logger.info("结果是{}",message.toString());
		return message;
	}
	
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/fabulousNews")
	public Message fabulousNews(@RequestBody News news) {
		this.logger.info("-----------fabulousNews()开始执行--------------");
		this.logger.info("参数是{}",news.toString());
		
		Message message = null;

		try {
			message = newsService.fabulousNews(news);
		} catch (Exception e) {
			message=setException(message, e, "fabulousNews()");
		}
		this.logger.info("-----------fabulousNews()执行结束--------------");
		this.logger.info("结果是{}",message.toString());
		return message;
	}
}
