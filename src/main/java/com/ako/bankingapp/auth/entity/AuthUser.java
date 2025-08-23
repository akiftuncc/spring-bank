package com.ako.bankingapp.auth.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "auth_user", uniqueConstraints = @UniqueConstraint(name = "uk_auth_user_email", columnNames = "email"))
public class AuthUser {

	@Id
	@GeneratedValue
	@UuidGenerator
	private UUID id;

	@Column(nullable = false, length = 320)
	private String email;

	@Column(name = "password_hash", nullable = false, length = 60)
	private String passwordHash;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "auth_user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role", nullable = false, length = 64)
	private Set<String> roles = new HashSet<>();

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	@PrePersist
	void prePersist() {
		Instant now = Instant.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	void preUpdate() {
		updatedAt = Instant.now();
	}

	public UUID getId() { return id; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPasswordHash() { return passwordHash; }
	public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
	public Set<String> getRoles() { return roles; }
	public void setRoles(Set<String> roles) { this.roles = roles; }
	public Instant getCreatedAt() { return createdAt; }
	public Instant getUpdatedAt() { return updatedAt; }
}
