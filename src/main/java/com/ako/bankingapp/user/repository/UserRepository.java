package com.ako.bankingapp.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ako.bankingapp.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByAuthUserId(UUID authUserId);

    boolean existsByAuthUserId(UUID authUserId);
}