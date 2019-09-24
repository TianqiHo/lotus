package com.lotus.backstage.integral.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lotus.backstage.integral.mapper.IntegralTypeMapper;
import com.lotus.backstage.integral.model.IntegralType;
import com.lotus.backstage.integral.service.IIntegralTypeService;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baseservice.AbstractService;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentBackstageUser;

@Service
@Transactional
public class IntegralTypeServiceImpl extends AbstractService implements IIntegralTypeService {

	@Autowired
	private IntegralTypeMapper integralTypeMapper;
	
	@Override
	protected Class<?> getClassForLogger() {
		return IntegralTypeServiceImpl.class;
	}
	
	@Override
	public Message saveOrUpdateIntegralType(IntegralType integralType) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
			int oprate;
			if(StringUtils.isEmpty(integralType.getId())) {
				integralType.buildCreateDefaultValue();
				integralType.setCreateBy(CurrentBackstageUser.obtainUserID());
				oprate = integralTypeMapper.insertSelective(integralType);
			}else {
				integralType.buildUpdateDefaultValue();
				integralType.setUpdateBy(CurrentBackstageUser.obtainUserID());
				oprate = integralTypeMapper.updateByPrimaryKeySelective(integralType);
			}
			message = this.iMessage.buildSuccessMessage();
			message.setData(oprate);
		return message;
	}

	@Override
	public Message selectIntegralType(IntegralType integralType) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(null!=integralType && !StringUtils.isEmpty(integralType.getId())) {
			integralType = integralTypeMapper.selectByPrimaryKey(integralType.getId());
			message = this.iMessage.buildSuccessMessage();
			message.setData(integralType);
		}else {
			throw new BaseException("参数ID为空");
		}
		return message;
	}

	@Override
	public Message selectIntegralTypes(IntegralType integralType) throws Exception {
		 Message message = this.iMessage.buildDefaultMessage();
		  //启用分页
		  if(integralType.isUsePagenation()) {
			  boolean usePage = false;
			  if((integralType.getPageNumber()==null || (integralType != null && integralType.getPageNumber()==0))
					  || (integralType.getPageSize()==null || (integralType != null && integralType.getPageSize()==0))
					  ){
				  logger.info("因前端程序未传参，默认只能查前10条数据");
				  PageHelper.startPage(1,10);
			  }else {
				  usePage=true;
				  PageHelper.startPage(integralType.getPageNumber(), integralType.getPageSize());
			  }
		      List<IntegralType> integralTypes = integralTypeMapper.selectIntegralTypes(integralType);
		      message = this.iMessage.buildSuccessMessage();
		      PageInfo<IntegralType> pageInfo=new PageInfo<IntegralType>(integralTypes);
		      if(!usePage){
		    	  message.setRealMessage("因前端程序未传参，默认只能查前10条数据");
		      }
		      message.setData(pageInfo);
		  }else {
			  //不启用分页
			  List<IntegralType> integralTypes = integralTypeMapper.selectIntegralTypes(integralType);
			  message = this.iMessage.buildSuccessMessage();
			  message.setData(integralTypes);
		  }
		  
		return message;
	}

	@Override
	public Message deleteIntegralTypes(IntegralType integralType) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(StringUtils.isEmpty(integralType.getIds())) {
			throw new BaseException("ids数组为空");
		}
		String[] idArr = integralType.getIds().split(",");
		int oprate = 0;
		for(String id:idArr) {
			oprate += integralTypeMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
		message = this.iMessage.buildSuccessMessage();
		message.setData(oprate);
		return message;
	}

}
