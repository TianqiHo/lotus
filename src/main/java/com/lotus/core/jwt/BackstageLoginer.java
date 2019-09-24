package com.lotus.core.jwt;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.beans.factory.annotation.Autowired;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotus.backstage.user.model.User;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baselogger.AbstractLogger;
import com.lotus.core.concurrent.CurrentBackstageUser;

@WebFilter(urlPatterns = {
		"/banner/*",
		"/category/*",
		"/comment/*",
		"/integralType/*",
		"/news/*"
		},filterName = "backstageLoginer")
public class BackstageLoginer extends AbstractLogger implements Filter,Loginer{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
        String token = jwtUtil.obtainToken(request);
        
		if(null!=token) {
			Claim data = null;
	        try {
	        	data = JWT.decode(token).getClaim("data");
	        	 if (data != null) {
	        		 User user = null;
	        		 user = objectMapper.readValue(data.asString(),User.class);
	        		 CurrentBackstageUser.setUser(user);
	             }
	        	
	        } catch (JWTDecodeException e) {
	            throw new RuntimeException(e);
	        }
		}else {
			throw new RuntimeException("Landing timeout,Please login again");
		}

		chain.doFilter(request, response);
		
		
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		CurrentBackstageUser.obtain();
		Filter.super.init(filterConfig);
	}
	@Override
	public void destroy() {
		CurrentBackstageUser.clear();
		Filter.super.destroy();
	}
	
	@Override
	protected Class<?> getClassForLogger() {
		return BackstageLoginer.class;
	}
}
