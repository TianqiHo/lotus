package com.lotus.smallroutine.fabulous.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lotus.backstage.comment.mapper.CommentMapper;
import com.lotus.backstage.comment.model.Comment;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baseservice.AbstractService;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentWxMiniCustomer;
import com.lotus.smallroutine.fabulous.mapper.FabulousMapper;
import com.lotus.smallroutine.fabulous.model.Fabulous;
import com.lotus.smallroutine.fabulous.service.IFabulousService;

@Service
@Transactional
public class FabulousServiceImpl extends AbstractService implements IFabulousService {

	@Override
	protected Class<?> getClassForLogger() {
		return FabulousServiceImpl.class;
	}
	
	@Autowired
	private FabulousMapper fabulousMapper;
	
	@Autowired
	private CommentMapper commentMapper;
	
	
	@Override
	public Message saveOrUpdateFabulous(Fabulous fabulous) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
			int oprate;
			if(StringUtils.isEmpty(fabulous.getId())) {
				fabulous.buildCreateDefaultValue();
				oprate = fabulousMapper.insertSelective(fabulous);
				
			}else {
				fabulous.buildUpdateDefaultValue();
				oprate = fabulousMapper.updateByPrimaryKeySelective(fabulous);
			}
			message = this.iMessage.buildSuccessMessage();
			message.setData(oprate);
		return message;
	}

	@Override
	public Message selectFabulous(Fabulous fabulous) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		fabulous.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
		if(null!=fabulous && !StringUtils.isEmpty(fabulous.getServiceId()) ) {
			
			List<Fabulous> fabulouss = fabulousMapper.selectFabulouss(fabulous);
			if(null!=fabulouss && fabulouss.size()==1) {
				message = this.iMessage.buildSuccessMessage();
				message.setData(fabulouss.get(0));
			}else {
				message.setRealMessage("不存在点赞记录");
			}
			
		}else {
			throw new BaseException("参数ID为空");
		}
		return message;
	}

	@Override
	public Message selectFabulouss(Fabulous fabulous) throws Exception {
		 Message message = this.iMessage.buildDefaultMessage();
		//启用分页
		  if(fabulous.isUsePagenation()) {
			  boolean usePage = false;
			  if((fabulous.getPageNumber()==null || (fabulous != null &&fabulous.getPageNumber()==0))
					  || (fabulous.getPageSize()==null || (fabulous != null &&fabulous.getPageSize()==0))
					  ){
				  logger.info("因前端程序未传参，默认只能查前10条数据");
				  PageHelper.startPage(1,10);
			  }else {
				  usePage=true;
				  PageHelper.startPage(fabulous.getPageNumber(), fabulous.getPageSize());
			  }
		      List<Fabulous> fabulouss = fabulousMapper.selectFabulouss(fabulous);
		      message = this.iMessage.buildSuccessMessage();
		      PageInfo<Fabulous> pageInfo=new PageInfo<Fabulous>(fabulouss);
		      if(!usePage){
		    	  message.setRealMessage("因前端程序未传参，默认只能查前10条数据");
		      }
		      message.setData(pageInfo);
		  }else {
			  //不启用分页
			  List<Fabulous> categorys = fabulousMapper.selectFabulouss(fabulous);
			  message = this.iMessage.buildSuccessMessage();
			  message.setData(categorys);
		  }
		return message;
	}

	@Override
	public Message deleteByPrimaryKey(Fabulous fabulous) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message deleteFabulouss(Fabulous fabulous) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		String[] idArr = fabulous.getIds().split(",");
		int oprate = 0;
		for(String id:idArr) {
			oprate += fabulousMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
		message = this.iMessage.buildSuccessMessage();
		message.setData(oprate);
		return message;
	}

	@Override
	public Message fabulousSum(Fabulous fabulous) throws Exception {
        Message	message = this.iMessage.buildDefaultMessage();
		
		if(!StringUtils.isEmpty(fabulous.getServiceId()) && 0!=fabulous.getServiceId()) {
			
			fabulous.buildCreateDefaultValue();
			fabulous.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
			fabulous.setType(2);
			int oprate = fabulousMapper.insertSelective(fabulous);
			
			if(oprate!=0) {
				Comment comment = new Comment();
				comment.setId(fabulous.getServiceId());
				commentMapper.fabulousSum(comment);
			}
			
			message = this.iMessage.buildSuccessMessage();
			message.setData(oprate);
		}else {
			throw new BaseException("参数serviceID为空");
		}
		return message;
	}

	@Override
	public Message cancleFabulousSum(Fabulous fabulous) throws Exception {
		    Message	message = this.iMessage.buildDefaultMessage();
			
			if(!StringUtils.isEmpty(fabulous.getId()) && 0!=fabulous.getId() && 0!=fabulous.getServiceId()) {
				
				int oprate = fabulousMapper.deleteByPrimaryKey(fabulous.getId());
				if(oprate!=0) {
					Comment comment = new Comment();
					comment.setId(fabulous.getServiceId());
					commentMapper.cancleFabulousSum(comment);
				}
				message = this.iMessage.buildSuccessMessage();
				message.setData(oprate);
			}else {
				throw new BaseException("参数ID或serviceId为空");
			}
			return message;
	}

}
