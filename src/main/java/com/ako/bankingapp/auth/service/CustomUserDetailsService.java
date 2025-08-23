package com.ako.bankingapp.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ako.bankingapp.auth.entity.AuthUser;
import com.ako.bankingapp.auth.repository.AuthUserRepository;
import com.ako.bankingapp.auth.security.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final AuthUserRepository repo;

	public CustomUserDetailsService(AuthUserRepository repo) {
		this.repo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AuthUser user = repo.findByEmailIgnoreCase(email)
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new CustomUserDetails(user);
	}
}