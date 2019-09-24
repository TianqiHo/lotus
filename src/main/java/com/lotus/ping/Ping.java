package com.lotus.ping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.core.base.basecontroller.BaseController;


@RestController
public class Ping extends BaseController<Ping>{

//	@RequestMapping(value="/ping")
	@GetMapping("/ping")
	public String ping() {
		this.logger.info("开始执行ping接口");
		return "OK";
	}
	
	@RequestMapping(value="/pingMessage")
	public String ping(String message) {
		this.logger.info("开始执行ping接口,参数是message="+message);
		return message;
	}

	@Override
	protected Class<?> getClassForLogger() {
		return Ping.class;
	}
}
