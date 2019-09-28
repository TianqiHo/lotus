package com.lotus.core.jwt;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.cookie.ReadCookie;

@Component
public class JwtUtil {
	
	@Autowired
	private JwtAttribute jwtAttribute;
	
	/**
	 * 生成token
	 * @param id
	 * @param data
	 * @return
	 */
	public String generateToken(Long id,String data)throws Exception{
		if(null==id) {
			throw new BaseException("请重新登陆");
		}
		Date date = new Date();
		Long currentMi = System.currentTimeMillis();
        return JWT
        		.create()
        	//headerClaims
        		//.withHeader(new HashMap<String,Object>(4) {{
        		//	put("", "");
        		//}})
        		.withIssuedAt(date)
        		.withIssuer(jwtAttribute.getIssuer())
        		.withJWTId(String.valueOf(id!=null?id:jwtAttribute.getJwtId()))
        		.withSubject(jwtAttribute.getSubject())
        		.withClaim("data", data)
        		.withExpiresAt(new Date(currentMi+jwtAttribute.getExpiresAt()))
        		//.withNotBefore(new Date(currentMi+jwtAttribute.getNotBefore()))
        		.sign(Algorithm.HMAC256(jwtAttribute.getSecret()));
	}
	
	/**
	 * 验证token
	 * @param token
	 * @return
	 * @throws BaseException
	 */
	public boolean verify(String token)throws BaseException{
		 // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtAttribute.getSecret())).build();
        try {
        	jwtVerifier.verify(token);
          return true;
        } catch (JWTVerificationException e) {
        	if(e instanceof TokenExpiredException) {
        		throw new BaseException("账户失效,请重新登陆");
        	}else {
        		throw e;
        	}
        }
        
	}
	
	public String obtainToken(ServletRequest request) {
	    String token = null;
		if(null!=request) {
			HttpServletRequest requestTemp = (HttpServletRequest)request;
			token = ReadCookie.getVal((HttpServletRequest) request, jwtAttribute.getTokenName());
			if(null==token) {
				token = requestTemp.getHeader(jwtAttribute.getTokenName());
			}
			if(null==token) {
				token = requestTemp.getParameter(jwtAttribute.getTokenName());
			}
		}
	
		return token;
	}
}
