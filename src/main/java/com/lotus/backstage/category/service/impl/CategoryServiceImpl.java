package com.lotus.backstage.category.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lotus.backstage.category.mapper.CategoryMapper;
import com.lotus.backstage.category.model.Category;
import com.lotus.backstage.category.service.ICategoryService;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baseservice.AbstractService;
import com.lotus.core.base.returnmessage.Message;

@Service
@Transactional
public class CategoryServiceImpl extends AbstractService implements ICategoryService {
	
	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	protected Class<?> getClassForLogger() {
		return CategoryServiceImpl.class;
	}
	
	@Override
	public Message saveOrUpdateCategory(Category categroy) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
			int oprate;
			if(StringUtils.isEmpty(categroy.getId())) {
				categroy.buildCreateDefaultValue();
				oprate = categoryMapper.insertSelective(categroy);
			}else {
				categroy.buildUpdateDefaultValue();
				oprate = categoryMapper.updateByPrimaryKeySelective(categroy);
			}
			message = this.iMessage.buildSuccessMessage();
			message.setData(oprate);
		return message;
	}

	@Override
	public Message selectCategory(Category categroy) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(null!=categroy && !StringUtils.isEmpty(categroy.getId())) {
			categroy = categoryMapper.selectByPrimaryKey(categroy.getId());
			message = this.iMessage.buildSuccessMessage();
			message.setData(categroy);
		}else {
			throw new BaseException("参数ID为空");
		}
		return message;
	}

	@Override
	public Message selectCategorys(Category categroy) throws Exception {
		  Message message = this.iMessage.buildDefaultMessage();
		  //启用分页
		  if(categroy.isUsePagenation()) {
			  boolean usePage = false;
			  if((categroy.getPageNumber()==null || (categroy != null && categroy.getPageNumber()==0))
					  || (categroy.getPageSize()==null || (categroy != null && categroy.getPageSize()==0))
					  ){
				  logger.info("因前端程序未传参，默认只能查前10条数据");
				  PageHelper.startPage(1,10);
			  }else {
				  usePage=true;
				  PageHelper.startPage(categroy.getPageNumber(), categroy.getPageSize());
			  }
		      List<Category> categorys = categoryMapper.selectCategorys(categroy);
		      message = this.iMessage.buildSuccessMessage();
		      PageInfo<Category> pageInfo=new PageInfo<Category>(categorys);
		      if(!usePage){
		    	  message.setRealMessage("因前端程序未传参，默认只能查前10条数据");
		      }
		      message.setData(pageInfo);
		  }else {
			  //不启用分页
			  List<Category> categorys = categoryMapper.selectCategorys(categroy);
			  message = this.iMessage.buildSuccessMessage();
			  message.setData(categorys);
		  }
		return message;
	}

	@Override
	public Message deleteCategorys(Category categroy) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(StringUtils.isEmpty(categroy.getIds())) {
			throw new BaseException("ids数组为空");
		}
		String[] idArr = categroy.getIds().split(",");
		int oprate = 0;
		for(String id:idArr) {
			oprate += categoryMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
		message = this.iMessage.buildSuccessMessage();
		message.setData(oprate);
		return message;
	}

}
