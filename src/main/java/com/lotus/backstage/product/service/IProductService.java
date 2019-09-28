package com.lotus.backstage.product.service;
import com.lotus.backstage.product.model.Product;
import com.lotus.core.base.returnmessage.Message;

public interface IProductService {

	
    public Message saveOrUpdateProduct(Product product)throws Exception;
	
	public Message selectProduct(Product product)throws Exception;
	
	public Message selectProducts(Product product)throws Exception;
	
	public Message selectClientProducts(Product product)throws Exception;
	
	public Message deleteProducts(Product product)throws Exception;
}
