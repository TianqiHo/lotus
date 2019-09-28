package com.lotus.backstage.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.backstage.category.model.Category;
import com.lotus.backstage.category.service.ICategoryService;
import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentBackstageUser;
import com.lotus.core.jwt.annotation.ServerRequireLogin;

@RestController
@RequestMapping("category")
@ServerRequireLogin(excludeUriNames = {"selectCategorys","deleteCategorys","selectCategory"},require = false)
public class CategoryController extends BaseController<Category> {

	@Autowired
	private ICategoryService categoryService;
	
	@Override
	protected Class<?> getClassForLogger() {
		return CategoryController.class;
	}
	
	/**
	 * 新增修改
	 * @param categroy
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/saveOrUpdateCategory")
	public Message saveOrUpdateCategory(@RequestBody Category categroy) {
		this.logger.info("-----------saveOrUpdateCategory()开始执行--------------");
		this.logger.info("参数是{}",categroy.toString());
		
		Message message = null;
		try {
			if(StringUtils.isEmpty(categroy.getId())) {
				categroy.setCreateBy(CurrentBackstageUser.obtainUserID());
			}else {
				categroy.setUpdateBy(CurrentBackstageUser.obtainUserID());
			}
			message = categoryService.saveOrUpdateCategory(categroy);
		} catch (Exception e) {
			message = setException(message, e, "saveOrUpdateCategory()");
		}
		
		this.logger.info("-----------saveOrUpdateCategory()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	 * 分页查询Category
	 * @param category
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectCategorys")
	public Message selectCategorys(@RequestBody Category category) {
		this.logger.info("-----------selectCategorys()开始执行--------------");
		this.logger.info("参数是{}",category.toString());
		
		Message message = null;
		try {
			message = categoryService.selectCategorys(category);
		} catch (Exception e) {
			message=setException(message, e, "selectCategorys()");
		}
		
		this.logger.info("-----------selectCategorys()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	 * 批量删除
	 * @param categroy
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/deleteCategorys")
	public Message deleteCategorys(@RequestBody Category categroy) {
		this.logger.info("-----------deleteCategorys()开始执行--------------");
		this.logger.info("参数是{}",categroy.toString());
		
		Message message = null;

		try {
			message = categoryService.deleteCategorys(categroy);
		} catch (Exception e) {
			message=setException(message, e, "deleteCategorys()");
		}
		
		this.logger.info("-----------deleteCategorys()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	 * 查询一个
	 * @param categroy
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectCategory")
	public Message selectCategory(@RequestBody Category categroy) {
		this.logger.info("-----------selectCategory()开始执行--------------");
		this.logger.info("参数是{}",categroy.toString());
		
		Message message = null;
		try {
			message = categoryService.selectCategory(categroy);
		} catch (Exception e) {
			message=setException(message, e, "selectCategory()");
		}
		
		this.logger.info("-----------selectCategory()执行结束--------------");
		printResult(message);
		return message;
	}
	
}
