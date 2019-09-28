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
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotus.core.base.baselogger.AbstractLogger;
import com.lotus.core.concurrent.CurrentWxMiniCustomer;
import com.lotus.core.jwt.annotation.ClientRequireLogin;
import com.lotus.smallroutine.customer.model.Customer;

/**
 * 小程序当前登陆人工具
 * @author Tianqi.He
 *
 */
@WebFilter(urlPatterns = {
		"/clientComment/*",
		"/clientBanner/*",
		"/clientCategory/*",
		"/clientFabulous/*",
		"/clientIntegral/*",
		"/clientNews/*",
		"/customer/selectCustomer",
		"/clientAddress/*",
		"/clientOrder/*"
		},filterName = "wxMiniLoginer")
public class WxMiniLoginer extends AbstractLogger implements Filter,Loginer{

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
			
			//if(!requireLoginClassExcludeContext.excludeMethods(request,ClientRequireLogin.class)) {
				String token = jwtUtil.obtainToken(request);
				if(!StringUtils.isEmpty(token)) {
					Claim data = null;
			        try {
			        	data = JWT.decode(token).getClaim("data");
			        	 if (data != null) {
			        		 Customer customer = null;
			        		 customer = objectMapper.readValue(data.asString(),Customer.class);
			        		 CurrentWxMiniCustomer.setCustomer(customer);
			             }
			        	
			        } catch (JWTDecodeException e) {
			            throw new RuntimeException(e);
			        }
				}else {
					throw new RuntimeException("Landing timeout,Please login again");
				}
			//}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		CurrentWxMiniCustomer.obtain();
		Filter.super.init(filterConfig);
	}
	
	@Override
	public void destroy() {
		CurrentWxMiniCustomer.clear();
		//requireLoginClassExcludeContext.clear();
		Filter.super.destroy();
	}

	@Override
	protected Class<?> getClassForLogger() {
		return WxMiniLoginer.class;
	}
}
