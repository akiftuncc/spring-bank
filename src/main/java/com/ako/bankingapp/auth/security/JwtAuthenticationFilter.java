package com.ako.bankingapp.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ako.bankingapp.auth.repository.AuthUserRepository;
import com.ako.bankingapp.auth.service.JwtService;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final AuthUserRepository repo;
	private final UserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtService jwtService, AuthUserRepository repo, UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.repo = repo;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws ServletException, IOException {

		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			try {
				Jws<Claims> jws = jwtService.parse(token);
				String userId = jws.getBody().getSubject();
				String email = jws.getBody().get("email", String.class);

				if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					if (repo.existsById(UUID.fromString(userId))) {
						UserDetails userDetails = userDetailsService.loadUserByUsername(email);
						UsernamePasswordAuthenticationToken auth =
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
						auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(auth);
					}
				}
			} catch (Exception ignored) { }
		}
		chain.doFilter(request, response);
	}
}