package com.lotus.wx.minipro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.lotus.core.base.baselogger.AbstractLogger;

@Component
public class WxMiniProJscode2Session extends AbstractLogger{
	
	@Autowired
	private WxMiniProConfigAttribute wxMiniProConfigAttribute;
	
	@Autowired
	private ObjectMapper objectMapper;
	
    private RestTemplate wxAuthRestTemplate = new RestTemplate();
	
	@Override
	protected Class<?> getClassForLogger() {
		return WxMiniProJscode2Session.class;
	}

	 public WxMiniProAuthCodeResponse getWxSession(String code)throws Exception {
	        this.logger.info("WxLogin前端传的参数code是{}",code);
	        
	       // String urlString = "?appid={appid}&secret={srcret}&grant_type={grantType}&js_code={code}";
	        
	        String urlString = wxMiniProConfigAttribute.getJscode2sessionApi()
	        		        +"?appid="+wxMiniProConfigAttribute.getAppId()
	        		        +"&secret="+wxMiniProConfigAttribute.getSecret()
	        		        +"&js_code="+code
	                        +"&grant_type="+wxMiniProConfigAttribute.getGrantType();
	        
	        this.logger.info("Wx服务配置---url---》{}",urlString);
	        String response = wxAuthRestTemplate.getForObject(urlString, String.class);
	        /*String response = wxAuthRestTemplate.getForObject(
	        		wxMiniProConfigAttribute.getJscode2sessionApi() + urlString,
	        		String.class,
	        		wxMiniProConfigAttribute.getAppId(),
	        		wxMiniProConfigAttribute.getSecret(),
	                wxMiniProConfigAttribute.getGrantType(),
	                code);*/
	        this.logger.info("请求结果是-----》{}",response);
	        ObjectReader reader = objectMapper.readerFor(WxMiniProAuthCodeResponse.class);
	        WxMiniProAuthCodeResponse res = reader.readValue(response);
	        res.setExpiresIn(res.getExpiresIn() != null ? res.getExpiresIn() : wxMiniProConfigAttribute.getExpires());
	        return res;
	    }
}
