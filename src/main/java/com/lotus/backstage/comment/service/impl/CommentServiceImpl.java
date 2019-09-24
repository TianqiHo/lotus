package com.lotus.backstage.comment.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lotus.backstage.comment.mapper.CommentMapper;
import com.lotus.backstage.comment.model.Comment;
import com.lotus.backstage.comment.service.ICommentService;
import com.lotus.backstage.integral.mapper.IntegralMapper;
import com.lotus.backstage.integral.mapper.IntegralTypeMapper;
import com.lotus.backstage.integral.model.Integral;
import com.lotus.backstage.integral.model.IntegralType;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baseservice.AbstractService;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentWxMiniCustomer;

@Service
@Transactional
public class CommentServiceImpl extends AbstractService implements ICommentService {

	@Override
	protected Class<?> getClassForLogger() {
		return CommentServiceImpl.class;
	}
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private IntegralTypeMapper integralTypeMapper;
	
	@Autowired
	private IntegralMapper integralMapper;
	
	@Override
	public Message saveOrUpdateComment(Comment comment) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
			int oprate;
			StringBuffer alertMessage = new StringBuffer("请求成功");
			if(StringUtils.isEmpty(comment.getId())) {
				comment.buildCreateDefaultValue();
				comment.setStatus(1);
				
				oprate = commentMapper.insertSelective(comment);
				
				
				Comment selectCommentsParam = new Comment();
				selectCommentsParam.setCreateBy(comment.getCreateBy());
				selectCommentsParam.setBeginTime(new Date());
				selectCommentsParam.setCategoryType(comment.getCategoryType());
				int todayCommentCount = commentMapper.selectTodayCountEveryBody(selectCommentsParam);
				
				IntegralType integralType=integralTypeMapper.selectByPrimaryKey(comment.getIntegralTypeId());
				
				if(null!=integralType) {
					int todayCommentIntegral = todayCommentCount * integralType.getIntegralNum();
					if(todayCommentIntegral<integralType.getIntegralDailyCeilingNum()) {
						Integral isThisServiceExisParam = new Integral();
						isThisServiceExisParam.setServiceId(comment.getArticalId());
						isThisServiceExisParam.setCreateBy(comment.getCreateBy());
						List<Integral> integrals = integralMapper.selectIntegral(isThisServiceExisParam);
						
						if(null==integrals || (null!=integrals && integrals.size()==0)) {
							Integral integral = new Integral();
							integral.setIntegral(integralType.getIntegralNum());
							integral.setServiceId(comment.getArticalId());
							integral.setIntegralTypeId(comment.getIntegralTypeId());
							integral.setCreateBy(comment.getCreateBy());
							integral.buildCreateDefaultValue();
							int num = integralMapper.insertSelective(integral);
							if(num==1) {
								alertMessage.append(",并赠送了"+integralType.getIntegralNum()+"积分");
							}else {
								alertMessage.append(",未成功赠送积分");
							}
						}
						
					}
				}
				
				
			}else {
				comment.buildUpdateDefaultValue();
				
				oprate = commentMapper.updateByPrimaryKeySelective(comment);
			}
			message = this.iMessage.buildSuccessMessage();
			message.setAlertMessage(alertMessage.toString());
			message.setData(oprate);
		return message;
	}

	@Override
	public Message selectComment(Comment comment) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(null!=comment && !StringUtils.isEmpty(comment.getId())) {
			comment = commentMapper.selectByPrimaryKey(comment.getId());
			message = this.iMessage.buildSuccessMessage();
			message.setData(comment);
		}else {
			throw new BaseException("参数ID为空");
		}
		return message;
	}

	@Override
	public Message selectComments(Comment comment) throws Exception {
		 Message message = this.iMessage.buildDefaultMessage();
		//启用分页
		  if(comment.isUsePagenation()) {
			  boolean usePage = false;
			  if((comment.getPageNumber()==null || (comment != null &&comment.getPageNumber()==0))
					  || (comment.getPageSize()==null || (comment != null &&comment.getPageSize()==0))
					  ){
				  logger.info("因前端程序未传参，默认只能查前10条数据");
				  PageHelper.startPage(1,10);
			  }else {
				  usePage=true;
				  PageHelper.startPage(comment.getPageNumber(), comment.getPageSize());
			  }
		      List<Comment> comments = commentMapper.selectComments(comment);
		      message = this.iMessage.buildSuccessMessage();
		      PageInfo<Comment> pageInfo=new PageInfo<Comment>(comments);
		      if(!usePage){
		    	  message.setRealMessage("因前端程序未传参，默认只能查前10条数据");
		      }
		      message.setData(pageInfo);
		  }else {
			  //不启用分页
			  List<Comment> comments = commentMapper.selectComments(comment);
			  message = this.iMessage.buildSuccessMessage();
			  message.setData(comments);
		  }
		return message;
	}

	@Deprecated
	@Override
	public Message deleteByPrimaryKey(Comment comment) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message deleteComments(Comment comment) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(StringUtils.isEmpty(comment.getIds())) {
			throw new BaseException("ids数组为空");
		}
		String[] idArr = comment.getIds().split(",");
		int oprate = 0;
		for(String id:idArr) {
			oprate += commentMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
		message = this.iMessage.buildSuccessMessage();
		message.setData(oprate);
		return message;
	}

	@Override
	public Message selectClientComments(Comment comment) throws Exception {
		 Message message = this.iMessage.buildDefaultMessage();
		 comment.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
			//启用分页
			  if(comment.isUsePagenation()) {
				  boolean usePage = false;
				  if((comment.getPageNumber()==null || (comment != null &&comment.getPageNumber()==0))
						  || (comment.getPageSize()==null || (comment != null &&comment.getPageSize()==0))
						  ){
					  logger.info("因前端程序未传参，默认只能查前10条数据");
					  PageHelper.startPage(1,10);
				  }else {
					  usePage=true;
					  PageHelper.startPage(comment.getPageNumber(), comment.getPageSize());
				  }
			      List<Comment> comments = commentMapper.selectClientComments(comment);
			      message = this.iMessage.buildSuccessMessage();
			      PageInfo<Comment> pageInfo=new PageInfo<Comment>(comments);
			      if(!usePage){
			    	  message.setRealMessage("因前端程序未传参，默认只能查前10条数据");
			      }
			      message.setData(pageInfo);
			  }else {
				  //不启用分页
				  List<Comment> categorys = commentMapper.selectClientComments(comment);
				  message = this.iMessage.buildSuccessMessage();
				  message.setData(categorys);
			  }
			return message;
	}

}
