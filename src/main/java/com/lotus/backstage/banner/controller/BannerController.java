package com.lotus.backstage.banner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.backstage.banner.model.Banner;
import com.lotus.backstage.banner.service.IBannerService;
import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.jwt.annotation.ServerRequireLogin;

@RestController
@RequestMapping("banner")
public class BannerController extends BaseController<Banner> {

	@Autowired
	private IBannerService bannerService;
	
	@Override
	protected Class<?> getClassForLogger() {
		return BannerController.class;
	}
	
	/**
	 * 新增修改Banner
	 * @param banner
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/saveOrUpdateBanner")
	public Message saveOrUpdateBanner(@RequestBody Banner banner) {
		this.logger.info("-----------saveOrUpdateBanner()开始执行--------------");
		this.logger.info("参数是{}",banner.toString());
		
		Message message = null;
		try {
			message = bannerService.saveOrUpdateBanner(banner);
		} catch (Exception e) {
			message = setException(message, e, "saveOrUpdateBanner()");
		}
		
		this.logger.info("-----------saveOrUpdateBanner()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	 * 查询一个banner
	 * @param banner
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectBanner")
	public Message selectBanner(@RequestBody Banner banner) {
		this.logger.info("-----------selectBanner()开始执行--------------");
		this.logger.info("参数是{}",banner.toString());
		
		Message message = null;
		try {
			message = bannerService.selectBanner(banner);
		} catch (Exception e) {
			message = setException(message, e, "selectBanner()");
		}
		
		this.logger.info("-----------selectBanner()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	 * 分页查询Banner
	 * @param json
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectBanners")
	public Message selectBanners(@RequestBody Banner banner) {
		this.logger.info("-----------selectBanners()开始执行--------------");
		this.logger.info("参数是{}",banner.toString());
		
		Message message = null;
		try {
			message = bannerService.selectBanners(banner);
		} catch (Exception e) {
			message = setException(message, e, "selectBanners()");
		}
		
		this.logger.info("-----------selectBanners()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/deleteBanners")
	public Message deleteBanners(@RequestBody Banner banner) {
		this.logger.info("-----------deleteBanners()开始执行--------------");
		this.logger.info("参数是{}",banner.toString());
		
		Message message = null;
		try {
			message = bannerService.deleteBanners(banner);
		} catch (Exception e) {
		    message = setException(message, e, "deleteBanners()");
		}
		
		this.logger.info("-----------deleteBanners()执行结束--------------");
		printResult(message);
		return message;
	}
}
