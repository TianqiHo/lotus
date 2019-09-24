package com.lotus.smallroutine.category;

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
import com.lotus.core.concurrent.CurrentWxMiniCustomer;
import com.lotus.core.jwt.annotation.ClientRequireLogin;

@RestController
@RequestMapping("clientCategory")
public class ClientCategoryController extends BaseController<Category> {

	@Autowired
	private ICategoryService categoryService;
	
	@Override
	protected Class<?> getClassForLogger() {
		return ClientCategoryController.class;
	}
	
	/**
	 * 分页查询Category
	 * @param category
	 * @return
	 */
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
		this.logger.info("结果是{}",message.toString());
		return message;
	}
	
	/**
	 * 新增修改
	 * @param json
	 * @return
	 */
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/saveOrUpdateCategory")
	public Message saveOrUpdateCategory(@RequestBody Category categroy) {
		this.logger.info("-----------saveOrUpdateCategory()开始执行--------------");
		this.logger.info("参数是{}",categroy.toString());
		
		Message message = null;
		try {
			if(StringUtils.isEmpty(categroy.getId())){
				categroy.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
			}else {
				categroy.setUpdateBy(CurrentWxMiniCustomer.obtainCustomerID());
			}
			message = categoryService.saveOrUpdateCategory(categroy);
		} catch (Exception e) {
			message=setException(message, e, "saveOrUpdateCategory()");
		}
		
		this.logger.info("-----------saveOrUpdateCategory()执行结束--------------");
		printResult(message);
		return message;
	}
}
