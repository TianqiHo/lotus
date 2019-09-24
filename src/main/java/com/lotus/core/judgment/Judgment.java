package com.lotus.core.judgment;

import org.springframework.web.servlet.HandlerInterceptor;

public interface Judgment extends HandlerInterceptor {
	
	public boolean judgment();
}
