package com.lotus.core.jwt;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.lotus.core.base.baselogger.AbstractLogger;
import com.lotus.core.jwt.annotation.ClientRequireLogin;
import com.lotus.core.jwt.annotation.ServerRequireLogin;

//@SpringBootConfiguration
public class RequireLoginClassExcludeContext extends AbstractLogger implements FactoryBean<RequireLoginClassExcludeContext> {
	
	//@Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping; 
	
	/**
	 * 存储controllerName=excludeUriName
	 */
	private Map<Class<?>,Collection<String>> excludeUris;
	
	/**
	 * 存储controllerName,无excludeUriName
	 */
	private Collection<Class<?>> noEexcludeUris;
	
	public RequireLoginClassExcludeContext initial(int excludeUrisCapacity,int noEexcludeUrisCapacity) {
		setExcludeUris(new HashMap<Class<?>, Collection<String>>(excludeUrisCapacity));
		setNoEexcludeUris(new ArrayList<Class<?>>(noEexcludeUrisCapacity));
		return this;
	}
	
	public boolean excludeMethods(ServletRequest request,Class<? extends Annotation> annotationClass)throws Exception{
		HttpServletRequest tempRequest = (HttpServletRequest)request;
		HandlerExecutionChain hec = requestMappingHandlerMapping.getHandler(tempRequest);
		Object handler = hec.getHandler();
		Class<?> customerClass =null;
		HandlerMethod handlerCast =null;
		if(null!= handler && handler instanceof HandlerMethod) {
			handlerCast = (HandlerMethod) handler;
			customerClass = handlerCast.getBeanType();
			
		}
		if(null!=customerClass && handlerCast!=null) {
			if(!this.containExcludeUrisKey(customerClass) 
					&& !this.containNoExcludeUris(customerClass)) {
				if (handlerCast.getBeanType().isAnnotationPresent(ServerRequireLogin.class)) {
                       ServerRequireLogin serverRequireLogin = (ServerRequireLogin)customerClass.getAnnotation(annotationClass);
					
						if(null!=serverRequireLogin) {
							String[] excludeUriNames = serverRequireLogin.excludeUriNames();
							if(null!=excludeUriNames && excludeUriNames.length!=0) {
								this.addValueExcludeUris(customerClass, Arrays.asList(excludeUriNames));
							}else {
								this.addValueNoEexcludeUris(customerClass);
							}
							forEach(customerClass,1);
							return verify(handlerCast.getMethod().getName(),customerClass);
						}
					
				}else if(handlerCast.getBeanType().isAnnotationPresent(ClientRequireLogin.class)){
					ClientRequireLogin clientRequireLogin = (ClientRequireLogin)customerClass.getAnnotation(annotationClass);
					
					if(null!=clientRequireLogin) {
						String[] excludeUriNames = clientRequireLogin.excludeMethodName();
						if(null!=excludeUriNames && excludeUriNames.length!=0) {
							this.addValueExcludeUris(customerClass, Arrays.asList(excludeUriNames));
						}else {
							this.addValueNoEexcludeUris(customerClass);
						}
						forEach(customerClass,2);
						return verify(handlerCast.getMethod().getName(),customerClass);
					}
				}
			
			}else {
				
				//存在
				String methodName = handlerCast.getMethod().getName();
				forEach(customerClass,3);
				return verify(methodName,customerClass);
				
			}
		}
		return false;
	}
	
	private boolean verify(String methodName,Class<?> customerClass) {
		if(null!=this.valueExcludeUris(customerClass)) {
			if(this.valueExcludeUris(customerClass).contains(methodName)) {
				return true;
			}
		}
		if(null!=this.noEexcludeUris) {
			if(this.containNoExcludeUris(customerClass)) {
				return false;
			}
		}
		
		
		return false;
	}
	
	public boolean containExcludeUrisKey(Class<?> classType) {
		return excludeUris.containsKey(classType);
	}
	
	public boolean containNoExcludeUris(Class<?> classType) {
		return noEexcludeUris.contains(classType);
	}
	
	public Collection<String> valueExcludeUris(Class<?> classType) {
		return excludeUris.get(classType);
	}
	
	public void addValueExcludeUris(Class<?> key,Collection<String> value) {
		excludeUris.put(key, value);
	}
	
	public Map<Class<?>,Collection<String>> getExcludeUris() {
		return excludeUris;
	}

	public void setExcludeUris(Map<Class<?>,Collection<String>> excludeUris) {
		this.excludeUris = excludeUris;
	}

	public Collection<Class<?>> getNoEexcludeUris() {
		return noEexcludeUris;
	}

	public void setNoEexcludeUris(Collection<Class<?>> noEexcludeUris) {
		this.noEexcludeUris = noEexcludeUris;
	}
	
	public void addValueNoEexcludeUris(Class<?> noEexcludeUris) {
		this.noEexcludeUris.add(noEexcludeUris);
	}
	
	public void clear() {
		excludeUris = null;
		noEexcludeUris = null;
	}

	@Override
	public RequireLoginClassExcludeContext getObject() throws Exception {
		return initial(requestMappingHandlerMapping.getHandlerMethods().size(), requestMappingHandlerMapping.getHandlerMethods().size()/2);
	}

	@Override
	public Class<?> getObjectType() {
		return this.getClass();
	}
	
	private void forEach(Class<?> customerClass,int position) {
		logger.info("----------------------position----------------"+position+"-----------------");
			 if(null!=this.excludeUris.get(customerClass)) {
				 logger.info("----------------------Exclude---------------------------------");
				 this.excludeUris.get(customerClass).forEach(
						 new Consumer<String>() {
							@Override
							public void accept(String t) {
								logger.info(t);
							}
							 
						}
				 );
			 }
			 
			 if(null!=this.noEexcludeUris && this.noEexcludeUris.size()>=1) {
				 logger.info("----------------------noExclude---------------------------------");
				 this.noEexcludeUris.forEach(
						 new Consumer<Class<?>>() {
							@Override
							public void accept(Class<?> t) {
								logger.info(t.toString());
							}
							 
						}
				 );
			 }
	}

	@Override
	protected Class<?> getClassForLogger() {
		return this.getClass();
	}
}
