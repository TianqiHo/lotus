package com.lotus.core.jwt;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;


import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baselogger.AbstractLogger;
import com.lotus.core.concurrent.CurrentBackstageUser;
import com.lotus.core.concurrent.CurrentWxMiniCustomer;
import com.lotus.core.jwt.JwtUtil;
import com.lotus.core.jwt.annotation.ClientRequireLogin;
import com.lotus.core.jwt.annotation.ServerRequireLogin;

/**
 * controller执行前，验证（需要登陆的controller）是否已经登陆系统
 * @author Tianqi.He
 *
 */
@SpringBootConfiguration
public class JwtTokenHandlerIntercepter extends AbstractLogger implements HandlerInterceptor{
	
	@Autowired
	private JwtUtil jwtUtil;

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		response.setHeader("Access-Control-Allow-Credentials","true");
		
		 // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
		 
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        
	    Method method=handlerMethod.getMethod();
	    
        if (method.isAnnotationPresent(ServerRequireLogin.class)) {
        	//验证后台是否需要登陆
        	ServerRequireLogin serverRequireLogin = method.getAnnotation(ServerRequireLogin.class);
        	 if (serverRequireLogin.require()) {
        		 if(null==CurrentBackstageUser.obtainUser()) {
        			 return false;
        		 }else {
        			 return true;
        		 }
        		// return annotation(request);
        	 }else {
        		 return true;
        	 }
        	
        }else if(method.isAnnotationPresent(ClientRequireLogin.class)) {
        	//验证前端是否需要登陆
        	ClientRequireLogin clientRequireLogin = method.getAnnotation(ClientRequireLogin.class);
	        if (clientRequireLogin.require()) {
	        	 if(null==CurrentWxMiniCustomer.obtainCustomer()) {
	        		 return false;
	        	 }else {
	        		 return true;
	        	 }
	       	 }else {
	       		 return true;
	       	 }
        }else {
        	
        	//不加注解的method走这里
        	return true;
        }

	}	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	@Override
	protected Class<?> getClassForLogger() {
		return JwtTokenHandlerIntercepter.class;
	}
}
