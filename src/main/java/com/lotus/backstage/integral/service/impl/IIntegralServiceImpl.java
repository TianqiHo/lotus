package com.lotus.backstage.integral.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lotus.backstage.integral.mapper.IntegralMapper;
import com.lotus.backstage.integral.mapper.IntegralTypeMapper;
import com.lotus.backstage.integral.model.Integral;
import com.lotus.backstage.integral.model.IntegralType;
import com.lotus.backstage.integral.service.IIntegralService;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baseservice.AbstractService;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentWxMiniCustomer;

@Service
@Transactional
public class IIntegralServiceImpl extends AbstractService implements IIntegralService {

	@Autowired
	private IntegralMapper integralMapper;
	
	@Autowired
	private IntegralTypeMapper integralTypeMapper;
	
	@Override
	protected Class<?> getClassForLogger() {
		return IIntegralServiceImpl.class;
	}
	
	@Override
	public Message saveOrUpdateIntegral(Integral integral) throws BaseException {
		Message	message = this.iMessage.buildDefaultMessage();
		if(null!=integral) {
			IntegralType integralType=integralTypeMapper.selectByPrimaryKey(integral.getIntegralTypeId());
			if(null!=integralType && null!=integralType.getIntegralNum()) {
				int oprate;
				if(StringUtils.isEmpty(integral.getId())) {
					integral.buildCreateDefaultValue();
					integral.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
					oprate = integralMapper.insertSelective(integral);
				}else {
					integral.buildUpdateDefaultValue();
					integral.setUpdateBy(CurrentWxMiniCustomer.obtainCustomerID());
					oprate = integralMapper.updateByPrimaryKeySelective(integral);
				}
				message = this.iMessage.buildSuccessMessage();
				message.setData(oprate);
			}else {
				message.setAlertMessage("当前积分规则不存在或未配置积分额");
			}
			
		}else {
			this.logger.error("saveOrUpdateIntegral()参数integral对象为空");
			message.setRealMessage("参数integral对象为空");
		}
		return message;
	}

	@Override
	public Message selectIntegral(Integral integral) throws BaseException {
		Message	message = this.iMessage.buildDefaultMessage();
		if(null!=integral && !StringUtils.isEmpty(integral.getId())) {
			List<Integral> integrals = integralMapper.selectIntegral(integral);
			message = this.iMessage.buildSuccessMessage();
			if(null!=integrals && integrals.size()!=0) {
				message.setData(integrals.get(0));
			}
			
		}else {
			this.logger.error("selectIntegral()参数ID为空");
			message.setRealMessage("参数ID对象为空");
		}
		return message;
	}

	@Override
	public Message selectIntegrals(Integral integral) throws BaseException {
		 Message message = this.iMessage.buildDefaultMessage();
		
		 Long currentCustomerId = CurrentWxMiniCustomer.obtainCustomerID();
		 integral.setCreateBy(currentCustomerId);
		 Integer integralSum = integralMapper.selectIntegralSum(currentCustomerId);
		  Map<String,Object> result =  new HashMap<String,Object>(2){
			    private static final long serialVersionUID = -3882193286969174388L;

				{
					put("integralSum",integralSum);
				}
			};
		  //启用分页
		  if(integral.isUsePagenation()) {
			 
			  boolean usePage = false;
			  if((integral.getPageNumber()==null || (integral != null && integral.getPageNumber()==0))
					  || (integral.getPageSize()==null || (integral != null && integral.getPageSize()==0))
					  ){
				  logger.info("因前端程序未传参，默认只能查前10条数据");
				  PageHelper.startPage(1,10);
			  }else {
				  usePage=true;
				  PageHelper.startPage(integral.getPageNumber(), integral.getPageSize());
			  }
		      List<Integral> integrals = integralMapper.selectIntegrals(integral);
		      message = this.iMessage.buildSuccessMessage();
		      PageInfo<Integral> pageInfo=new PageInfo<Integral>(integrals);
		      if(!usePage){
		    	  message.setRealMessage("因前端程序未传参，默认只能查前10条数据");
		      }
		      
		      result.put("pageData", pageInfo);
		      message.setData(result);
		  }else {
			  //不启用分页
			  List<Integral> integrals = integralMapper.selectIntegrals(integral);
			  message = this.iMessage.buildSuccessMessage();
			  result.put("pageData", integrals);
			  message.setData(integrals);
		  }
		  
	      
		return message;
	}

	@Override
	public Message deleteIntegrals(Integral integral) throws BaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
