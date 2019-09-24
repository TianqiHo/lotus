package com.lotus.backstage.banner.service;

import com.lotus.backstage.banner.model.Banner;
import com.lotus.core.base.returnmessage.Message;

public interface IBannerService {
	
	Message saveOrUpdateBanner(Banner banner)throws Exception;
	
	Message selectBanner(Banner banner)throws Exception;
	
	Message selectBanners(Banner banner)throws Exception;
	
	Message deleteByPrimaryKey(Banner banner)throws Exception;

	Message deleteBanners(Banner banner)throws Exception;

}
