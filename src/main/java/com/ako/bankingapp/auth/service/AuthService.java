package com.ako.bankingapp.auth.service;

import com.ako.bankingapp.auth.dto.request.LoginRequest;
import com.ako.bankingapp.auth.dto.request.RegisterRequest;
import com.ako.bankingapp.auth.dto.response.AuthResponse;
import com.ako.bankingapp.auth.entity.AuthUser;
import com.ako.bankingapp.auth.repository.AuthUserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthService {

	private final AuthUserRepository repo;
	private final PasswordEncoder encoder;
	private final AuthenticationManager authManager;
	private final JwtService jwtService;

	public AuthService(AuthUserRepository repo, PasswordEncoder encoder, AuthenticationManager authManager, JwtService jwtService) {
		this.repo = repo;
		this.encoder = encoder;
		this.authManager = authManager;
		this.jwtService = jwtService;
	}

	@Transactional
	public AuthResponse register(RegisterRequest req) {
		String email = req.email().trim().toLowerCase();
		if (repo.existsByEmailIgnoreCase(email)) {
			throw new org.springframework.dao.DataIntegrityViolationException("Email already in use");
		}
		AuthUser user = new AuthUser();
		user.setEmail(email);
		user.setPasswordHash(encoder.encode(req.password()));
		user.setRoles(Set.of("USER"));
		repo.save(user);

		String token = jwtService.generateToken(user);
		return new AuthResponse(token, "Bearer", Math.floorDiv(jwtService.getExpirationMs(), 1000L));
	}

	public AuthResponse login(LoginRequest req) {
		String email = req.email().trim().toLowerCase();
		authManager.authenticate(new UsernamePasswordAuthenticationToken(email, req.password()));
		AuthUser user = repo.findByEmailIgnoreCase(email).orElseThrow();
		String token = jwtService.generateToken(user);
		return new AuthResponse(token, "Bearer", Math.floorDiv(jwtService.getExpirationMs(), 1000L));
	}
}
