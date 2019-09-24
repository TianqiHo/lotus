package com.lotus.backstage.banner.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lotus.backstage.banner.mapper.BannerMapper;
import com.lotus.backstage.banner.model.Banner;
import com.lotus.backstage.banner.service.IBannerService;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baseservice.AbstractService;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentBackstageUser;

@Service
@Transactional
public class BannerServiceImpl extends AbstractService implements IBannerService {

	@Autowired
	private BannerMapper bannerMapper;
	
	@Override
	protected Class<?> getClassForLogger() {
		return BannerServiceImpl.class;
	}

	/**
	 * 新增修改
	 */
	@Override
	public Message saveOrUpdateBanner(Banner banner) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
			int oprate;
			if(StringUtils.isEmpty(banner.getId())) {
				banner.buildCreateDefaultValue();
				banner.setCreateBy(CurrentBackstageUser.obtainUserID());
				oprate = bannerMapper.insertSelective(banner);
			}else {
				banner.buildUpdateDefaultValue();
				banner.setUpdateBy(CurrentBackstageUser.obtainUserID());
				oprate = bannerMapper.updateByPrimaryKeySelective(banner);
			}
			message = this.iMessage.buildSuccessMessage();
			message.setData(oprate);
		return message;
	}

	/**
	 * 查询一条
	 */
	@Override
	public Message selectBanner(Banner banner) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(null!=banner && !StringUtils.isEmpty(banner.getId())) {
			banner = bannerMapper.selectByPrimaryKey(banner.getId());
			message = this.iMessage.buildSuccessMessage();
			message.setData(banner);
		}else {
			throw new BaseException("参数ID为空");
		}
		return message;
	}

	/**
	 * 查询分页
	 */
	@Override
	public Message selectBanners(Banner banner) throws Exception {
		  Message message = this.iMessage.buildDefaultMessage();
		  //启用分页
		  if(banner.isUsePagenation()) {
			  boolean usePage = false;
			  if((banner.getPageNumber()==null || (banner != null &&banner.getPageNumber()==0))
					  || (banner.getPageSize()==null || (banner != null &&banner.getPageSize()==0))
					  ){
				  logger.info("因前端程序未传参，默认只能查前10条数据");
				  PageHelper.startPage(1,10);
			  }else {
				  usePage=true;
				  PageHelper.startPage(banner.getPageNumber(), banner.getPageSize());
			  }
			  
		      List<Banner> banners = bannerMapper.selectBanners(banner);
		      
		      message = this.iMessage.buildSuccessMessage();
		      PageInfo<Banner> pageInfo=new PageInfo<Banner>(banners);
		      if(!usePage){
		    	  message.setRealMessage("因前端程序未传参，默认只能查前10条数据");
		      }
		      message.setData(pageInfo);
		  }else {
			  List<Banner> banners = bannerMapper.selectBanners(banner);
		      message = this.iMessage.buildSuccessMessage();
		      message.setData(banners);
		  }
		 
		return message;
	}

	@Deprecated
	@Override
	public Message deleteByPrimaryKey(Banner banner) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(null!=banner && !StringUtils.isEmpty(banner.getId())) {
			int oprate = bannerMapper.deleteByPrimaryKey(banner.getId());
			message = this.iMessage.buildSuccessMessage();
			message.setData(oprate);
		}else {
			this.logger.error("saveOrUpdateBanner()参数ID为空");
			message.setRealMessage("参数ID对象为空");
		}
		return message;
	}

	/**
	 * 删除多条
	 */
	@Override
	public Message deleteBanners(Banner banner) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(StringUtils.isEmpty(banner.getIds())) {
			throw new BaseException("ids数组为空");
		}
		String[] idArr = banner.getIds().split(",");
		int oprate = 0;
		for(String id:idArr) {
			oprate += bannerMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
		message = this.iMessage.buildSuccessMessage();
		message.setData(oprate);
		return message;
	}
}
