package com.lotus.smallroutine.follow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lotus.backstage.news.mapper.NewsMapper;
import com.lotus.backstage.news.model.News;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baseservice.AbstractService;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentWxMiniCustomer;
import com.lotus.smallroutine.follow.mapper.FollowMapper;
import com.lotus.smallroutine.follow.model.Follow;
import com.lotus.smallroutine.follow.service.IFollowService;

@Service
@Transactional
public class FollowServiceImpl extends AbstractService implements IFollowService {

	@Autowired
	private FollowMapper followMapper;
	
	@Autowired
	private NewsMapper newsMapper;
	
	@Override
	protected Class<?> getClassForLogger() {
		return FollowServiceImpl.class;
	}
	
	@Override
	public Message saveOrUpdateFollow(Follow follow) throws Exception {

		Message	message = this.iMessage.buildDefaultMessage();
			if(StringUtils.isEmpty(follow.getId())) {
				follow.buildCreateDefaultValue();
				follow.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
				followMapper.insertSelective(follow);
			}else {
				
			}
			message = this.iMessage.buildSuccessMessage();
			message.setData(follow.getId());
		return message;
	}

	@Override
	public Message selectFollow(Follow follow) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(null!=follow && !StringUtils.isEmpty(follow.getId())) {
			follow = followMapper.selectByPrimaryKey(follow.getId());
			message = this.iMessage.buildSuccessMessage();
			message.setData(follow);
		}else {
			throw new BaseException("参数ID为空");
		}
		return message;
	}

	@Override
	public Message selectFollows(Follow follow) throws Exception {
		 Message message = this.iMessage.buildDefaultMessage();
		 follow.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
			//启用分页
			  if(follow.isUsePagenation()) {
				  boolean usePage = false;
				  if((follow.getPageNumber()==null || (follow != null &&follow.getPageNumber()==0))
						  || (follow.getPageSize()==null || (follow != null &&follow.getPageSize()==0))
						  ){
					  logger.info("因前端程序未传参，默认只能查前10条数据");
					  PageHelper.startPage(1,10);
				  }else {
					  usePage=true;
					  PageHelper.startPage(follow.getPageNumber(), follow.getPageSize());
				  }
				  List<News> follows = newsMapper.selectMyFollows(follow);
			      message = this.iMessage.buildSuccessMessage();
			      PageInfo<News> pageInfo=new PageInfo<News>(follows);
			      if(!usePage){
			    	  message.setRealMessage("因前端程序未传参，默认只能查前10条数据");
			      }
			      message.setData(pageInfo);
			  }else {
				  //不启用分页
				  List<News> follows = newsMapper.selectMyFollows(follow);
				  message = this.iMessage.buildSuccessMessage();
				  message.setData(follows);
			  }
			return message;
	}

	@Override
	public Message deleteFollows(Follow follow) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(StringUtils.isEmpty(follow.getIds())) {
			throw new BaseException("ids数组为空");
		}
		String[] idArr = follow.getIds().split(",");
		int oprate = 0;
		for(String id:idArr) {
			oprate += followMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
		message = this.iMessage.buildSuccessMessage();
		message.setData(oprate);
		return message;
	}

}
