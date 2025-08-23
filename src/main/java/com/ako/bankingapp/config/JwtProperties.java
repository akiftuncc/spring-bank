package com.ako.bankingapp.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.security.jwt")
public class JwtProperties {
	private @NotBlank String secret;
	private @NotNull Long expiration; // milliseconds
	private @NotBlank String issuer;

	public String getSecret() { return secret; }
	public void setSecret(String secret) { this.secret = secret; }
	public Long getExpiration() { return expiration; }
	public void setExpiration(Long expiration) { this.expiration = expiration; }
	public String getIssuer() { return issuer; }
	public void setIssuer(String issuer) { this.issuer = issuer; }
}