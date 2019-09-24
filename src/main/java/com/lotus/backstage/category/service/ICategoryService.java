package com.lotus.backstage.category.service;

import com.lotus.backstage.category.model.Category;
import com.lotus.core.base.returnmessage.Message;

public interface ICategoryService {


	public Message saveOrUpdateCategory(Category newsCategroy)throws Exception;
	
	public Message selectCategory(Category newsCategroy)throws Exception;
	
	public Message selectCategorys(Category newsCategroy)throws Exception;
	
	public Message deleteCategorys(Category newsCategroy)throws Exception;

}
