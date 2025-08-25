package com.ako.bankingapp.user.controller;

import com.ako.bankingapp.user.dto.request.UpdateAddressRequest;
import com.ako.bankingapp.user.dto.request.UpdateProfileRequest;
import com.ako.bankingapp.user.dto.response.UserProfileResponse;
import com.ako.bankingapp.user.service.UserService;
import com.ako.bankingapp.auth.security.CustomUserDetails;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public UserProfileResponse getProfile() {
        UUID authUserId = getCurrentAuthUserId();
        return userService.getProfile(authUserId);
    }

    @PutMapping("/profile")
    public UserProfileResponse updateProfile(@RequestBody @Valid UpdateProfileRequest request) {
        UUID authUserId = getCurrentAuthUserId();
        return userService.updateProfile(authUserId, request);
    }

    @PutMapping("/address")
    public UserProfileResponse updateAddress(@RequestBody @Valid UpdateAddressRequest request) {
        UUID authUserId = getCurrentAuthUserId();
        return userService.updateAddress(authUserId, request);
    }

    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteProfile() {
        UUID authUserId = getCurrentAuthUserId();
        userService.deleteProfile(authUserId);
        return ResponseEntity.noContent().build();
    }

    private UUID getCurrentAuthUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser().getId();
    }
}