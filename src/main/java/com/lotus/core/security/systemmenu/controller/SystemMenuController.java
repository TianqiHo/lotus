package com.lotus.core.security.systemmenu.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentBackstageUser;
import com.lotus.core.jwt.annotation.ServerRequireLogin;
import com.lotus.core.security.systemmenu.model.SystemMenu;
import com.lotus.core.security.systemmenu.service.ISystemMenuService;


@RestController
@RequestMapping("systemMenu")
public class SystemMenuController extends BaseController<SystemMenu>{

	@Override
	protected Class<?> getClassForLogger() {
		return SystemMenuController.class;
	}
	
	private ISystemMenuService systemMenuService;
	
	/**
	 * 新增修改
	 * @param systemMenu
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/saveOrUpdateSystemMenu")
	public Message saveOrUpdateSystemMenu(@RequestBody SystemMenu systemMenu) {
		this.logger.info("-----------saveOrUpdateSystemMenu()开始执行--------------");
		this.logger.info("参数是{}",systemMenu.toString());
		
		Message message = null;
		try {
			if(StringUtils.isEmpty(systemMenu.getId())) {
				systemMenu.setCreateBy(CurrentBackstageUser.obtainUserID());
			}else {
				systemMenu.setUpdateBy(CurrentBackstageUser.obtainUserID());
			}
			message = systemMenuService.saveOrupdateSystemMenu(systemMenu);
		} catch (Exception e) {
			message = setException(message, e, "saveOrUpdateSystemMenu()");
		}
		
		this.logger.info("-----------saveOrUpdateSystemMenu()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	 * 分页查询SystemMenu
	 * @param category
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectSystemMenus")
	public Message selectSystemMenus(@RequestBody SystemMenu category) {
		this.logger.info("-----------selectSystemMenus()开始执行--------------");
		this.logger.info("参数是{}",category.toString());
		
		Message message = null;
		try {
			message = systemMenuService.selectSystemMenus(category);
		} catch (Exception e) {
			message=setException(message, e, "selectSystemMenus()");
		}
		
		this.logger.info("-----------selectSystemMenus()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	 * 批量删除
	 * @param systemMenu
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/deleteSystemMenus")
	public Message deleteSystemMenus(@RequestBody SystemMenu systemMenu) {
		this.logger.info("-----------deleteSystemMenus()开始执行--------------");
		this.logger.info("参数是{}",systemMenu.toString());
		
		Message message = null;

		try {
			message = systemMenuService.deleteSystemMenus(systemMenu);
		} catch (Exception e) {
			message=setException(message, e, "deleteSystemMenus()");
		}
		
		this.logger.info("-----------deleteSystemMenus()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	 * 查询一个
	 * @param systemMenu
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectSystemMenu")
	public Message selectSystemMenu(@RequestBody SystemMenu systemMenu) {
		this.logger.info("-----------selectSystemMenu()开始执行--------------");
		this.logger.info("参数是{}",systemMenu.toString());
		
		Message message = null;
		try {
			message = systemMenuService.selectSystemMenu(systemMenu);
		} catch (Exception e) {
			message=setException(message, e, "selectSystemMenu()");
		}
		
		this.logger.info("-----------selectSystemMenu()执行结束--------------");
		printResult(message);
		return message;
	}
	
}
