package com.lotus.core.interceptorconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lotus.core.jwt.JwtTokenHandlerIntercepter;

@SpringBootConfiguration
public class InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	private JwtTokenHandlerIntercepter jwtTokenHandlerIntercepter;
	
	/**
	 * 添加监听器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtTokenHandlerIntercepter).addPathPatterns("/*/*");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
	
}
