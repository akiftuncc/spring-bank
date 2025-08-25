package com.ako.bankingapp.user.dto.request;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateProfileRequest(
        @Size(max = 100, message = "First name must not exceed 100 characters") String firstName,

        @Size(max = 100, message = "Last name must not exceed 100 characters") String lastName,

        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format") String phone,

        @Past(message = "Date of birth must be in the past") LocalDate dateOfBirth) {
}