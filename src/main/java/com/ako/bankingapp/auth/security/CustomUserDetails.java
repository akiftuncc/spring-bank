package com.ako.bankingapp.auth.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ako.bankingapp.auth.entity.AuthUser;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
	private final AuthUser user;

	public CustomUserDetails(AuthUser user) {
		this.user = user;
	}

	public AuthUser getUser() {
		return user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<String> roles = user.getRoles();
		return roles.stream()
			.map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toSet());
	}

	@Override public String getPassword() { return user.getPasswordHash(); }
	@Override public String getUsername() { return user.getEmail(); }
	@Override public boolean isAccountNonExpired() { return true; }
	@Override public boolean isAccountNonLocked() { return true; }
	@Override public boolean isCredentialsNonExpired() { return true; }
	@Override public boolean isEnabled() { return true; }
}