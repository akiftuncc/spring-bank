package com.ako.bankingapp.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ako.bankingapp.auth.entity.AuthUser;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserRepository extends JpaRepository<AuthUser, UUID> {
	boolean existsByEmailIgnoreCase(String email);
	Optional<AuthUser> findByEmailIgnoreCase(String email);
}