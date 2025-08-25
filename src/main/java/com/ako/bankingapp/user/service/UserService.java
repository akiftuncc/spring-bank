package com.ako.bankingapp.user.service;

import com.ako.bankingapp.user.dto.request.UpdateAddressRequest;
import com.ako.bankingapp.user.dto.request.UpdateProfileRequest;
import com.ako.bankingapp.user.dto.response.AddressResponse;
import com.ako.bankingapp.user.dto.response.UserProfileResponse;
import com.ako.bankingapp.user.entity.User;
import com.ako.bankingapp.user.repository.UserRepository;
import com.ako.bankingapp.auth.entity.AuthUser;
import com.ako.bankingapp.auth.repository.AuthUserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final AuthUserRepository authUserRepo;

    public UserService(UserRepository userRepo, AuthUserRepository authUserRepo) {
        this.userRepo = userRepo;
        this.authUserRepo = authUserRepo;
    }

    public UserProfileResponse getProfile(UUID authUserId) {
        User user = userRepo.findByAuthUserId(authUserId)
                .orElseGet(() -> createEmptyProfile(authUserId));

        return mapToUserProfileResponse(user);
    }

    @Transactional
    public UserProfileResponse updateProfile(UUID authUserId, UpdateProfileRequest request) {
        User user = userRepo.findByAuthUserId(authUserId)
                .orElseGet(() -> createEmptyProfile(authUserId));

        if (request.firstName() != null) {
            user.setFirstName(request.firstName().trim());
        }
        if (request.lastName() != null) {
            user.setLastName(request.lastName().trim());
        }
        if (request.phone() != null) {
            user.setPhone(request.phone().trim());
        }
        if (request.dateOfBirth() != null) {
            user.setDateOfBirth(request.dateOfBirth());
        }

        user = userRepo.save(user);
        return mapToUserProfileResponse(user);
    }

    @Transactional
    public UserProfileResponse updateAddress(UUID authUserId, UpdateAddressRequest request) {
        User user = userRepo.findByAuthUserId(authUserId)
                .orElseGet(() -> createEmptyProfile(authUserId));

        if (request.streetAddress() != null) {
            user.setStreetAddress(request.streetAddress().trim());
        }
        if (request.city() != null) {
            user.setCity(request.city().trim());
        }
        if (request.state() != null) {
            user.setState(request.state().trim());
        }
        if (request.postalCode() != null) {
            user.setPostalCode(request.postalCode().trim());
        }
        if (request.country() != null) {
            user.setCountry(request.country().trim());
        }

        user = userRepo.save(user);
        return mapToUserProfileResponse(user);
    }

    @Transactional
    public void deleteProfile(UUID authUserId) {
        userRepo.findByAuthUserId(authUserId)
                .ifPresent(userRepo::delete);
    }

    private User createEmptyProfile(UUID authUserId) {
        AuthUser authUser = authUserRepo.findById(authUserId)
                .orElseThrow(() -> new RuntimeException("Auth user not found"));

        User user = new User();
        user.setAuthUser(authUser);
        return userRepo.save(user);
    }

    private UserProfileResponse mapToUserProfileResponse(User user) {
        AddressResponse address = new AddressResponse(
                user.getStreetAddress(),
                user.getCity(),
                user.getState(),
                user.getPostalCode(),
                user.getCountry());

        return new UserProfileResponse(
                user.getId(),
                user.getAuthUser().getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getDateOfBirth(),
                address,
                user.getCreatedAt(),
                user.getUpdatedAt());
    }
}