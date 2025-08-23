package com.ako.bankingapp.auth.service;

import com.ako.bankingapp.auth.entity.AuthUser;
import com.ako.bankingapp.config.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

	private final JwtProperties props;
	private final Key key;

	public JwtService(JwtProperties props) {
		this.props = props;
		this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(AuthUser user) {
		Instant now = Instant.now();
		return Jwts.builder()
			.setSubject(user.getId().toString())
			.setIssuer(props.getIssuer())
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(now.plusMillis(props.getExpiration())))
			.addClaims(Map.of(
				"email", user.getEmail(),
				"roles", user.getRoles()
			))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public Jws<Claims> parse(String token) {
		return Jwts.parserBuilder()
			.requireIssuer(props.getIssuer())
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);
	}

	public long getExpirationMs() {
		return props.getExpiration();
	}
}
