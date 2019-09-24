package com.lotus.wx.minipro;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SpringBootConfiguration
@ConfigurationProperties(prefix = "lotus.wechat.minipro")
public class WxMiniProConfigAttribute {

	private String jscode2sessionApi;
	private String appId;
	private String secret;
	private String grantType;
	
	private Long expires;
	
	public Long getExpires() {
		return expires;
	}
	public void setExpires(Long expires) {
		this.expires = expires;
	}
	public String getJscode2sessionApi() {
		return jscode2sessionApi;
	}
	public void setJscode2sessionApi(String jscode2sessionApi) {
		this.jscode2sessionApi = jscode2sessionApi;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	@Override
	public String toString() {
		return "WxMiniProConfigAttribute [jscode2sessionApi=" + jscode2sessionApi + ", appId=" + appId + ", secret="
				+ secret + ", grantType=" + grantType + ", expires=" + expires + "]";
	}
}
