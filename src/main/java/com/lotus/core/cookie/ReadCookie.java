package com.lotus.core.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class ReadCookie {

	public final static String getVal(HttpServletRequest request,String cookieName){
		String result = null;
		
		if(null!=request) {
			Cookie[] cookies =  request.getCookies();
			if(null!=cookies && cookies.length!=0) {
				for(Cookie cookie:cookies) {
					if(cookie.getName()!=null && cookie.getName().equals(cookieName)) {
						result = cookie.getValue();
					}
				}
			}
		}
	
		
		return result;
	}
}
