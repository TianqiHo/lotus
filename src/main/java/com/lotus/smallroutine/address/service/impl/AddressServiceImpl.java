package com.lotus.smallroutine.address.service.impl;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baseservice.AbstractService;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentWxMiniCustomer;
import com.lotus.smallroutine.address.mapper.AddressMapper;
import com.lotus.smallroutine.address.model.Address;
import com.lotus.smallroutine.address.service.IAddressService;

@Service
@Transactional
public class AddressServiceImpl extends AbstractService implements IAddressService {

	@Override
	protected Class<?> getClassForLogger() {
		return AddressServiceImpl.class;
	}
	
	@Autowired
	private AddressMapper addressMapper;

	@Override
	public Message saveOrUpdateAddress(Address address) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		
		Address defaultAddressParam = new Address();
		defaultAddressParam.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
		defaultAddressParam.setDefaultAddress(2);
		List<Address> defaultAddress = addressMapper.selectClientAddresss(defaultAddressParam);
		
		int oprate;
		if(StringUtils.isEmpty(address.getId())) {
			address.buildCreateDefaultValue();
			address.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
			Integer defaultAddressFlag = address.getDefaultAddress();
			swichDefaultAddress(defaultAddressFlag,defaultAddress);
			oprate = addressMapper.insertSelective(address);
		}else {
			address.buildUpdateDefaultValue();
			address.setUpdateBy(CurrentWxMiniCustomer.obtainCustomerID());
			Integer defaultAddressFlag = address.getDefaultAddress();
			swichDefaultAddress(defaultAddressFlag,defaultAddress);
			oprate = addressMapper.updateByPrimaryKeySelective(address);
		}
		message = this.iMessage.buildSuccessMessage();
		message.setData(oprate);
	  return message;
	}
	
	private void swichDefaultAddress(Integer defaultAddressFlag,List<Address> defaultAddresses) {
		if(null==defaultAddressFlag || (null!=defaultAddressFlag && defaultAddressFlag.intValue()==2)) {
			if(null!=defaultAddresses && defaultAddresses.size()>=1) {
				defaultAddresses.forEach(new Consumer<Address>() {
					@Override
					public void accept(Address t) {
						t.setDefaultAddress(1);
						addressMapper.updateByPrimaryKeySelective(t);
					}
				});
			}
		}
	}

	@Override
	public Message selectAddress(Address address) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(null!=address && !StringUtils.isEmpty(address.getId())) {
			address = addressMapper.selectByPrimaryKey(address.getId());
			message = this.iMessage.buildSuccessMessage();
			message.setData(address);
		}else {
			throw new BaseException("参数ID为空");
		}
		return message;
	}

	@Override
	public Message selectAddresss(Address address) throws Exception {
		
		return null;
	}

	@Override
	public Message selectClientAddresss(Address address) throws Exception {
		 Message message = this.iMessage.buildDefaultMessage();
		 address.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
		  //启用分页
		  if(address.isUsePagenation()) {
			  boolean usePage = false;
			  if((address.getPageNumber()==null || (address != null && address.getPageNumber()==0))
					  || (address.getPageSize()==null || (address != null && address.getPageSize()==0))
					  ){
				  logger.info("因前端程序未传参，默认只能查前10条数据");
				  PageHelper.startPage(1,10);
			  }else {
				  usePage=true;
				  PageHelper.startPage(address.getPageNumber(), address.getPageSize());
			  }
		      List<Address> addresss = addressMapper.selectClientAddresss(address);
		      message = this.iMessage.buildSuccessMessage();
		      PageInfo<Address> pageInfo=new PageInfo<Address>(addresss);
		      if(!usePage){
		    	  message.setRealMessage("因前端程序未传参，默认只能查前10条数据");
		      }
		      message.setData(pageInfo);
		  }else {
			  //不启用分页
			  List<Address> addresss = addressMapper.selectClientAddresss(address);
			  message = this.iMessage.buildSuccessMessage();
			  message.setData(addresss);
		  }
		return message;
	}

	@Override
	public Message deleteAddresss(Address address) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(StringUtils.isEmpty(address.getIds())) {
			throw new BaseException("ids数组为空");
		}
		String[] idArr = address.getIds().split(",");
		int oprate = 0;
		for(String id:idArr) {
			oprate += addressMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
		message = this.iMessage.buildSuccessMessage();
		message.setData(oprate);
		return message;
	}
}
