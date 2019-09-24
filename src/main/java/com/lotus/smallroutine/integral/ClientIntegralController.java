package com.lotus.smallroutine.integral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.backstage.integral.model.Integral;
import com.lotus.backstage.integral.service.IIntegralService;
import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.jwt.annotation.ClientRequireLogin;

@RestController
@RequestMapping("clientIntegral")
public class ClientIntegralController extends BaseController<Integral> {

	@Autowired
	private IIntegralService integralService;
	
	@Override
	protected Class<?> getClassForLogger() {
		return ClientIntegralController.class;
	}
	
	/**
	 * 分页查询IntegralType
	 * @param json
	 * @return
	 */
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectIntegrals")
	public Message selectIntegrals(@RequestBody Integral integral) {
		this.logger.info("-----------selectIntegrals()开始执行--------------");
		this.logger.info("参数是{}",integral.toString());
		
		Message message = null;

		try {
			message = integralService.selectIntegrals(integral);
		} catch (Exception e) {
			message=setException(message, e, "selectIntegrals()");
		}
		this.logger.info("-----------selectIntegrals()执行结束--------------");
		printResult(message);
		return message;
	}
}
