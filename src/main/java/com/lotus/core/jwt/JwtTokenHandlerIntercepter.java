package com.lotus.core.jwt;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lotus.core.base.baselogger.AbstractLogger;
import com.lotus.core.concurrent.CurrentBackstageUser;
import com.lotus.core.concurrent.CurrentWxMiniCustomer;
import com.lotus.core.jwt.annotation.ClientRequireLogin;
import com.lotus.core.jwt.annotation.ServerRequireLogin;

/**
 * controller执行前，验证（需要登陆的controller）是否已经登陆系统
 * @author Tianqi.He
 *
 */
@SpringBootConfiguration
public class JwtTokenHandlerIntercepter extends AbstractLogger implements HandlerInterceptor{
	
	//@Autowired
	//private ConfigurableApplicationContext context;
	
	//private RequireLoginClassExcludeContext requireLoginClassExcludeContext =null;
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
        		// return verifyMethod(handlerMethod);
        		 
        		 if(null==CurrentBackstageUser.obtainUser()) {
	        		 return false;
	        	 }else {
	        		 return true;
	        	 }
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
	        	//return verifyMethod(handlerMethod);
	       	 }else {
	       		 return true;
	       	 }
        }else {
        	
        	//不加注解的method走这里
        	return true;
        }
	}	
	
	/**
	private boolean verifyMethod(HandlerMethod handlerMethod) {

		 Class<?> customerClass = handlerMethod.getBeanType();
		 String methodName = handlerMethod.getMethod().getName();
		 if(null==requireLoginClassExcludeContext) {
			 requireLoginClassExcludeContext = context.getBean(RequireLoginClassExcludeContext.class);
		 }
		 
		 if(requireLoginClassExcludeContext.containExcludeUrisKey(customerClass)
				 && requireLoginClassExcludeContext.valueExcludeUris(customerClass).contains(methodName)){
			 return true;
		 }
		 
		 if(requireLoginClassExcludeContext.containExcludeUrisKey(customerClass)
				 && !requireLoginClassExcludeContext.valueExcludeUris(customerClass).contains(methodName)){
			 if(null!=CurrentBackstageUser.obtainUser()) {
				return true; 
			 }else {
				 return false;
			 }
		 }
		 
		 if(!requireLoginClassExcludeContext.containExcludeUrisKey(customerClass)
				 && requireLoginClassExcludeContext.containNoExcludeUris(customerClass)){
			 if(null!=CurrentBackstageUser.obtainUser()) {
				return true; 
			 }else {
				 return false;
			 }
		 }
		 
		 if(!requireLoginClassExcludeContext.containExcludeUrisKey(customerClass)
				 && !requireLoginClassExcludeContext.containNoExcludeUris(customerClass)){
			 
			 if(null!=CurrentBackstageUser.obtainUser()) {
				return true; 
			 }else {
				 return false;
			 }
		 }
		 
		 return false;
	}
	*/
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
