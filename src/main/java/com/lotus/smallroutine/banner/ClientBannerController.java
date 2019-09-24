package com.lotus.smallroutine.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.backstage.banner.model.Banner;
import com.lotus.backstage.banner.service.IBannerService;
import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;

@RestController
@RequestMapping("clientBanner")
public class ClientBannerController extends BaseController<Banner> {

	@Autowired
	private IBannerService bannerService;
	
	@Override
	protected Class<?> getClassForLogger() {
		return ClientBannerController.class;
	}
	
	/**
	 * 分页查询Banner
	 * @param banner
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectBanners")
	public Message selectBanners(@RequestBody Banner banner) {
		this.logger.info("-----------selectBanners()开始执行--------------");
		this.logger.info("参数是{}",banner.toString());
		
		Message message = null;
		try {
			message = bannerService.selectBanners(banner);
		} catch (Exception e) {
			message = setException(message, e, "selectBanners()");
		}
		
		this.logger.info("-----------selectBanners()执行结束--------------");
		printResult(message);
		return message;
	}
}
