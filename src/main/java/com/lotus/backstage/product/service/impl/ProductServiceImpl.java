package com.lotus.backstage.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lotus.backstage.product.mapper.ProductMapper;
import com.lotus.backstage.product.model.Product;
import com.lotus.backstage.product.service.IProductService;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baseservice.AbstractService;
import com.lotus.core.base.returnmessage.Message;


@Service
@Transactional
public class ProductServiceImpl extends AbstractService implements IProductService {

	@Autowired
	private ProductMapper productMapper;
	
	@Override
	protected Class<?> getClassForLogger() {
		return ProductServiceImpl.class;
	}
	
	
	@Override
	public Message saveOrUpdateProduct(Product product) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message selectProduct(Product product) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(null!=product && !StringUtils.isEmpty(product.getId())) {
			product = productMapper.selectByPrimaryKey(product.getId());
			message = this.iMessage.buildSuccessMessage();
			message.setData(product);
		}else {
			throw new BaseException("参数ID为空");
		}
		return message;
	}

	@Override
	public Message selectProducts(Product product) throws Exception {
		 Message message = this.iMessage.buildDefaultMessage();
		  //启用分页
		  if(product.isUsePagenation()) {
			  boolean usePage = false;
			  if((product.getPageNumber()==null || (product != null && product.getPageNumber()==0))
					  || (product.getPageSize()==null || (product != null && product.getPageSize()==0))
					  ){
				  logger.info("因前端程序未传参，默认只能查前10条数据");
				  PageHelper.startPage(1,10);
			  }else {
				  usePage=true;
				  PageHelper.startPage(product.getPageNumber(), product.getPageSize());
			  }
		      List<Product> products = productMapper.selectClientProducts(product);
		      message = this.iMessage.buildSuccessMessage();
		      PageInfo<Product> pageInfo=new PageInfo<Product>(products);
		      if(!usePage){
		    	  message.setRealMessage("因前端程序未传参，默认只能查前10条数据");
		      }
		      message.setData(pageInfo);
		  }else {
			  //不启用分页
			  List<Product> products = productMapper.selectClientProducts(product);
			  message = this.iMessage.buildSuccessMessage();
			  message.setData(products);
		  }
		return message;
	}

	@Override
	public Message deleteProducts(Product product) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * 客户端查询商品
	 */
	@Override
	public Message selectClientProducts(Product product) throws Exception {
		 Message message = this.iMessage.buildDefaultMessage();
		  //启用分页
		  if(product.isUsePagenation()) {
			  boolean usePage = false;
			  if((product.getPageNumber()==null || (product != null && product.getPageNumber()==0))
					  || (product.getPageSize()==null || (product != null && product.getPageSize()==0))
					  ){
				  logger.info("因前端程序未传参，默认只能查前10条数据");
				  PageHelper.startPage(1,10);
			  }else {
				  usePage=true;
				  PageHelper.startPage(product.getPageNumber(), product.getPageSize());
			  }
		      List<Product> products = productMapper.selectClientProducts(product);
		      message = this.iMessage.buildSuccessMessage();
		      PageInfo<Product> pageInfo=new PageInfo<Product>(products);
		      if(!usePage){
		    	  message.setRealMessage("因前端程序未传参，默认只能查前10条数据");
		      }
		      message.setData(pageInfo);
		  }else {
			  //不启用分页
			  List<Product> products = productMapper.selectClientProducts(product);
			  message = this.iMessage.buildSuccessMessage();
			  message.setData(products);
		  }
		return message;
	}

}
