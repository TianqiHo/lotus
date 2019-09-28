package com.lotus.backstage.news.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

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
import com.lotus.backstage.news.mapper.News2CategoryMapper;
import com.lotus.backstage.news.mapper.NewsMapper;
import com.lotus.backstage.news.model.News;
import com.lotus.backstage.news.model.News2Category;
import com.lotus.backstage.news.service.INewsService;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baseservice.AbstractService;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentWxMiniCustomer;

@Service
@Transactional
public class NewsServiceImpl extends AbstractService implements INewsService {

	@Autowired
	private NewsMapper newsMapper;
	
	@Autowired
	private News2CategoryMapper news2CategoryMapper;
	
	@Autowired
	private IntegralTypeMapper integralTypeMapper;
	
	@Autowired
	private IntegralMapper integralMapper;
	
	
	@Override
	protected Class<?> getClassForLogger() {
		return NewsServiceImpl.class;
	}
	
	@Override
	public Message saveOrUpdateNews(News news) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
			int oprate = 0;
			if(StringUtils.isEmpty(news.getId())) {
				news.buildCreateDefaultValue();
				newsMapper.insertSelective(news);
				++oprate;
			}else {
				news.buildUpdateDefaultValue();
				oprate = newsMapper.updateByPrimaryKeySelective(news);
			}
			boolean isSave = saveCategorys(news.getCategorys(),news.getId());
			message = this.iMessage.buildSuccessMessage();
			if(!isSave)message.setAlertMessage("新闻资讯保存成功，但未分配资讯分类");
			message.setData(oprate);
		return message;
	}
	
	private boolean saveCategorys(List<News2Category> categorys,Long newsId) {
		if(null!=categorys && categorys.size()!=0){
			news2CategoryMapper.deleteByForeignKey(newsId);
			for(News2Category category:categorys) {
				category.setNewsId(newsId);
				if(StringUtils.isEmpty(category.getId())) {
					news2CategoryMapper.insertSelective(category);
				}else {
					news2CategoryMapper.updateByPrimaryKeySelective(category);
				}
			}
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Message selectNews(News news) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(null!= news&& !StringUtils.isEmpty(news.getId())) {
			news = newsMapper.selectByPrimaryKey(news.getId());
			News2Category news2Category = new News2Category();
			news2Category.setNewsId(news.getId());
			news.setCategorys(news2CategoryMapper.selectCategorys(news2Category));
			message = this.iMessage.buildSuccessMessage();
			message.setData(news);
		}else {
			throw new BaseException("参数ID为空");
		}
		return message;
	}
	
	
	@Override
	public Message selectNewsAndIsFollow(News news) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(null!= news&& !StringUtils.isEmpty(news.getId())) {
			news = newsMapper.selectNewsAndIsFollow(CurrentWxMiniCustomer.obtainCustomerID(),news.getId());
			message = this.iMessage.buildSuccessMessage();
			message.setData(news);
		}else {
			throw new BaseException("参数ID为空");
		}
		return message;
	}

	@Override
	public Message selectNewss(News news) throws Exception {
		 Message message = this.iMessage.buildDefaultMessage();
		//启用分页
		  if(news.isUsePagenation()) {
			  boolean usePage = false;
			  if((news.getPageNumber()==null || (news != null &&news.getPageNumber()==0))
					  || (news.getPageSize()==null || (news != null &&news.getPageSize()==0))
					  ){
				  logger.info("因前端程序未传参，默认只能查前10条数据");
				  PageHelper.startPage(1,10);
			  }else {
				  usePage=true;
				  PageHelper.startPage(news.getPageNumber(), news.getPageSize());
			  }
		      List<News> newss = newsMapper.selectNwess(news);
		      message = this.iMessage.buildSuccessMessage();
		      PageInfo<News> pageInfo=new PageInfo<News>(newss);
		      if(!usePage){
		    	  message.setAlertMessage("因前端程序未传参，默认只能查前10条数据");
		      }
		      message.setData(pageInfo);
		  }else {
			  List<News> newss = newsMapper.selectNwess(news);
			  message = this.iMessage.buildSuccessMessage();
			  message.setData(newss);
		  }
		
		return message;
	}

	@Deprecated
	@Override
	public Message deleteByPrimaryKey(News news) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message deleteNewss(News news) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(StringUtils.isEmpty(news.getIds())) {
			throw new BaseException("ids数组为空");
		}
		String[] idArr = news.getIds().split(",");
		int oprate = 0;
		for(String id:idArr) {
			oprate += newsMapper.deleteByPrimaryKey(Long.valueOf(id));
			news2CategoryMapper.deleteByForeignKey(news.getId());
		}
		message = this.iMessage.buildSuccessMessage();
		message.setData(oprate);
		return message;
	}

	@Override
	public Message seeSum(News news) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		
		if(!StringUtils.isEmpty(news.getId()) && 0!=news.getId()) {
			int oprate =  newsMapper.seeSum(news);
			message = this.iMessage.buildSuccessMessage();
			message.setData(oprate);
		}else {
			throw new BaseException("参数ID为空");
		}
		return message;
	}

	@Override
	public Message shareNews(News news) throws Exception {
		Message	integralMessage = this.iMessage.buildDefaultMessage();

		int todayShareNewsCount = integralMapper.todayShareNewsCountByCategory(new Date(),
				CurrentWxMiniCustomer.obtainCustomerID(),
				news.getCategoryType()
				);
		
		IntegralType integralType = integralTypeMapper.selectByPrimaryKey(news.getIntegralTypeId());
		
		if(null!=integralType) {
			int todayShareNewsIntegral = todayShareNewsCount * integralType.getIntegralNum();
			integralMessage = this.iMessage.buildSuccessMessage();
			StringBuffer sb = new StringBuffer("请求成功");
			if(todayShareNewsIntegral<integralType.getIntegralDailyCeilingNum()) {
				
				Integral isThisServiceExisParam = new Integral();
				isThisServiceExisParam.setServiceId(news.getId());
				isThisServiceExisParam.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
				List<Integral> integrals = integralMapper.selectIntegral(isThisServiceExisParam);
				
				if(null==integrals || (null!=integrals && integrals.size()==0)) {
					//Integral integral = new Integral();
					isThisServiceExisParam.setServiceId(news.getId());
					isThisServiceExisParam.setIntegralTypeId(news.getIntegralTypeId());
					isThisServiceExisParam.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
					isThisServiceExisParam.setIntegral(integralType.getIntegralNum());
					isThisServiceExisParam.buildCreateDefaultValue();
					int num = integralMapper.insertSelective(isThisServiceExisParam);
					if(num!=0) {
						sb.append(",并赠送了"+integralType.getIntegralNum()+"积分");
						integralMessage.setAlertMessage(sb.toString());
					}
					//integralMessage = integralService.saveOrUpdateIntegral(integral);
				}
				
			}else {
				integralMessage.setAlertMessage("今日分享送积分已上限，请明日再来");
			}
		}
		
		return integralMessage;
	}

	@Override
	public Message selectClientNewss(News news) throws Exception {
		Message message = this.iMessage.buildDefaultMessage();
		//启用分页
		  if(news.isUsePagenation()) {
			  boolean usePage = false;
			  if((news.getPageNumber()==null || (news != null &&news.getPageNumber()==0))
					  || (news.getPageSize()==null || (news != null &&news.getPageSize()==0))
					  ){
				  logger.info("因前端程序未传参，默认只能查前10条数据");
				  PageHelper.startPage(1,10);
			  }else {
				  usePage=true;
				  PageHelper.startPage(news.getPageNumber(), news.getPageSize());
			  }
		      List<News> newss = newsMapper.selectClientNewss(news);
		      message = this.iMessage.buildSuccessMessage();
		      PageInfo<News> pageInfo=new PageInfo<News>(newss);
		      if(!usePage){
		    	  message.setAlertMessage("因前端程序未传参，默认只能查前10条数据");
		      }
		      message.setData(pageInfo);
		  }else {
			  List<News> newss = newsMapper.selectClientNewss(news);
			  message = this.iMessage.buildSuccessMessage();
			  message.setData(newss);
		  }
		return message;
	}

	
	/**
	 * 点赞文章
	 */
	@Override
	public Message fabulousNews(News news) throws Exception {
	    Message	message = this.iMessage.buildDefaultMessage();
		
	    if(!StringUtils.isEmpty(news.getIntegralTypeId())) {
	    	Long customerId = CurrentWxMiniCustomer.obtainCustomerID();
		    //对应的积分规则
		    IntegralType integralType = integralTypeMapper.selectByPrimaryKey(news.getIntegralTypeId());
			
		    if(null != integralType){
		    	Integral integralQuery = new Integral();
		    	integralQuery.setCreateBy(customerId);
		    	integralQuery.setIntegralTypeId(news.getIntegralTypeId());
		    	List<Integral> integrals = integralMapper.selectIntegral(integralQuery);
		    	if(null!=integrals) {
		    		ConsumerObtain consumerObtain = new ConsumerObtain(news, false, integralType, integrals.size());
		    		integrals.forEach(consumerObtain);
		    		consumerObtain.andThen(consumerObtain);
		    		
		    	}
		    	
		    }
	    }
		
	    if(!StringUtils.isEmpty(news.getId()) && 0!=news.getId()) {
			int oprate =  newsMapper.fabulousNews(news.getId());
			message = this.iMessage.buildSuccessMessage();
			message.setData(oprate);
		}else {
			throw new BaseException("参数ID为空");
		}
		
		return message;
	}
	
	class ConsumerObtain implements Consumer<Integral>{

		private IntegralType integralType;
		private News news;
		private int fabulousNewsCount;
		private boolean isFabulousNews;
		
		public ConsumerObtain(News news,boolean isFabulousNews,IntegralType integralType,int fabulousNewsCount) {
			super();
			this.news = news;
			this.isFabulousNews = isFabulousNews;
			this.integralType = integralType;
			this.fabulousNewsCount = fabulousNewsCount;
		}

		
		public boolean isFabulousNews() {
			return isFabulousNews;
		}


		public void setFabulousNews(boolean isFabulousNews) {
			this.isFabulousNews = isFabulousNews;
		}


		public News getNews() {
			return news;
		}

		public void setNews(News news) {
			this.news = news;
		}

		@Override
		public void accept(Integral t) {

			if(t.getServiceId().equals(news.getId())) {
				 isFabulousNews = true;
				 return;
			}
		}
		
		@Override
		public Consumer<Integral> andThen(Consumer<? super Integral> after) {
			if(!isFabulousNews) {
				int integralSum = fabulousNewsCount * integralType.getIntegralNum();
	    		
	    		if(integralSum<integralType.getIntegralDailyCeilingNum()) {
	    			Integral integral = new Integral();
	    			integral.buildCreateDefaultValue();
	    			integral.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
	    			integral.setIntegral(integralType.getIntegralNum());
	    			integral.setServiceId(news.getId());
	    			integral.setIntegralTypeId(news.getIntegralTypeId());
	    			
	    			integralMapper.insertSelective(integral);
	    			
	    		}
			}
			return Consumer.super.andThen(after);
		}
		 
		
	}
}
