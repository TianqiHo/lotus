package com.lotus.core.security;
/**
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@SpringBootConfiguration
public class ServerWebMvcConfigurationSupport extends WebMvcConfigurationSupport {

	/*
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(
	    		   new HandlerInterceptor() {
				}).addPathPatterns("/**").excludePathPatterns("/signIn","/contol/**","/css/**","/images/**","/js/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	

	 @Override
     protected void addViewControllers(ViewControllerRegistry registry){
         //registry.addViewController("/").setViewName("signIn");
         super.addViewControllers(registry);
     }
	 
	 
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		super.addResourceHandlers(registry);
	}
}*/
