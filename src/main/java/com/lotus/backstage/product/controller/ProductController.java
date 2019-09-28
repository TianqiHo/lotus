package com.lotus.backstage.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.backstage.product.model.Product;
import com.lotus.backstage.product.service.IProductService;
import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.jwt.annotation.ServerRequireLogin;

@RestController
@RequestMapping("product")
public class ProductController extends BaseController<Product>{

	@Override
	protected Class<?> getClassForLogger() {
		return ProductController.class;
	}
	
	@Autowired
	private IProductService productService;
	
	/**
	 * 分页查询Product
	 * @param product
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectProducts")
	public Message selectProducts(@RequestBody Product product) {
		this.logger.info("-----------selectProducts()开始执行--------------");
		this.logger.info("参数是{}",product.toString());
		
		Message message = null;
		try {
			message = productService.selectProducts(product);
		} catch (Exception e) {
			message=setException(message, e, "selectProducts()");
		}
		
		this.logger.info("-----------selectProducts()执行结束--------------");
		printResult(message);
		return message;
	}
	
}
