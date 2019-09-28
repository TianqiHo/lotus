package com.lotus.core.security.systemmenu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baseservice.AbstractService;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentBackstageUser;
import com.lotus.core.security.systemmenu.mapper.SystemMenuMapper;
import com.lotus.core.security.systemmenu.model.SystemMenu;
import com.lotus.core.security.systemmenu.service.ISystemMenuService;

@Service
@Transactional
public class SystemMenuServiceImpl extends AbstractService implements ISystemMenuService {

	@Override
	protected Class<?> getClassForLogger() {
		return SystemMenuServiceImpl.class;
	}
	
	@Autowired
	private SystemMenuMapper systemMenuMapper;
	
	@Override
	public Message saveOrupdateSystemMenu(SystemMenu systemMenu) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		int oprate;
		if(StringUtils.isEmpty(systemMenu.getId())) {
			systemMenu.buildCreateDefaultValue();
			systemMenu.setCreateBy(CurrentBackstageUser.obtainUserID());
			oprate = systemMenuMapper.insertSelective(systemMenu);
		}else {
			systemMenu.buildUpdateDefaultValue();
			systemMenu.setUpdateBy(CurrentBackstageUser.obtainUserID());
			oprate = systemMenuMapper.updateByPrimaryKeySelective(systemMenu);
		}
		message = this.iMessage.buildSuccessMessage();
		message.setData(oprate);
	return message;
	}

	@Override
	public Message deleteSystemMenus(SystemMenu systemMenu) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(StringUtils.isEmpty(systemMenu.getIds())) {
			throw new BaseException("ids数组为空");
		}
		String[] idArr = systemMenu.getIds().split(",");
		int oprate = 0;
		for(String id:idArr) {
			oprate += systemMenuMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
		message = this.iMessage.buildSuccessMessage();
		message.setData(oprate);
		return message;
	}

	@Override
	public Message selectSystemMenus(SystemMenu systemMenu) throws Exception {
		  Message message = this.iMessage.buildDefaultMessage();
		  //启用分页
		  if(systemMenu.isUsePagenation()) {
			  boolean usePage = false;
			  if((systemMenu.getPageNumber()==null || (systemMenu != null && systemMenu.getPageNumber()==0))
					  || (systemMenu.getPageSize()==null || (systemMenu != null && systemMenu.getPageSize()==0))
					  ){
				  logger.info("因前端程序未传参，默认只能查前10条数据");
				  PageHelper.startPage(1,10);
			  }else {
				  usePage=true;
				  PageHelper.startPage(systemMenu.getPageNumber(), systemMenu.getPageSize());
			  }
		      List<SystemMenu> categorys = systemMenuMapper.selectSystemMenus(systemMenu);
		      message = this.iMessage.buildSuccessMessage();
		      PageInfo<SystemMenu> pageInfo=new PageInfo<SystemMenu>(categorys);
		      if(!usePage){
		    	  message.setRealMessage("因前端程序未传参，默认只能查前10条数据");
		      }
		      message.setData(pageInfo);
		  }else {
			  //不启用分页
			  List<SystemMenu> categorys = systemMenuMapper.selectSystemMenus(systemMenu);
			  message = this.iMessage.buildSuccessMessage();
			  message.setData(categorys);
		  }
		return message;
	}

	@Override
	public Message selectSystemMenu(SystemMenu systemMenu) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(null!=systemMenu && !StringUtils.isEmpty(systemMenu.getId())) {
			systemMenu = systemMenuMapper.selectByPrimaryKey(systemMenu.getId());
			message = this.iMessage.buildSuccessMessage();
			message.setData(systemMenu);
		}else {
			throw new BaseException("参数ID为空");
		}
		return message;
	}

}
