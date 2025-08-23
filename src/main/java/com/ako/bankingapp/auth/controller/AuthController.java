package com.ako.bankingapp.auth.controller;

import com.ako.bankingapp.auth.dto.request.LoginRequest;
import com.ako.bankingapp.auth.dto.request.RegisterRequest;
import com.ako.bankingapp.auth.dto.response.AuthResponse;
import com.ako.bankingapp.auth.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService service;

	public AuthController(AuthService service) {
		this.service = service;
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public AuthResponse register(@RequestBody @Valid RegisterRequest req) {
		return service.register(req);
	}

	@PostMapping("/login")
	public AuthResponse login(@RequestBody @Valid LoginRequest req) {
		return service.login(req);
	}
}