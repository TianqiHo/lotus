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
import org.springframework.util.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotus.backstage.user.model.User;
import com.lotus.core.base.baselogger.AbstractLogger;
import com.lotus.core.concurrent.CurrentBackstageUser;
import com.lotus.core.jwt.annotation.ServerRequireLogin;

@WebFilter(urlPatterns = {
		"/banner/*",
		"/category/*",
		"/comment/*",
		"/integralType/*",
		"/news/*",
		"/product/*"
		},filterName = "backstageLoginer")
public class BackstageLoginer extends AbstractLogger implements Filter,Loginer{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	//@Autowired
	private RequireLoginClassExcludeContext requireLoginClassExcludeContext;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		 try {
			 //if(!requireLoginClassExcludeContext.excludeMethods(request,ServerRequireLogin.class)) {
				 String token = jwtUtil.obtainToken(request);
				 if(!StringUtils.isEmpty(token)) {
			        	
			        	Claim data = null;
			        	data = JWT.decode(token).getClaim("data");
			        	 if (data != null) {
			        		 User user = null;
			        		 user = objectMapper.readValue(data.asString(),User.class);
			        		 CurrentBackstageUser.setUser(user);
			             }
			        	
			       
				 }else {
					throw new RuntimeException("Landing timeout,Please login again");
				 }
			// }
		 } catch (Exception e) {
	          throw new RuntimeException(e);
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
		//requireLoginClassExcludeContext.clear();
		Filter.super.destroy();
	}
	
	@Override
	protected Class<?> getClassForLogger() {
		return BackstageLoginer.class;
	}


}
