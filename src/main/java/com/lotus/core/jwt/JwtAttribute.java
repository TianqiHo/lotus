package com.lotus.core.jwt;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * jwt属性配置 /lotus/src/main/resources/config/application-lotusapp.yml
 * @author Tianqi.He
 *
 */
@SpringBootConfiguration
@ConfigurationProperties(prefix = "lotus.jwt")
public class JwtAttribute {

	
	private String tokenName;
	
	private String secret;
	
	private Long expiresAt;
	
	private String issuer;
	
	private String subject;
	
	private Long notBefore;
	
	private Long issuedAt;
	
	private Long jwtId;
	
	private String audience;

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Long getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Long expiresAt) {
		this.expiresAt = expiresAt;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getNotBefore() {
		return notBefore;
	}

	public void setNotBefore(Long notBefore) {
		this.notBefore = notBefore;
	}

	public Long getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(Long issuedAt) {
		this.issuedAt = issuedAt;
	}

	public Long getJwtId() {
		return jwtId;
	}

	public void setJwtId(Long jwtId) {
		this.jwtId = jwtId;
	}

	public String getAudience() {
		return audience;
	}

	public void setAudience(String audience) {
		this.audience = audience;
	}
}
