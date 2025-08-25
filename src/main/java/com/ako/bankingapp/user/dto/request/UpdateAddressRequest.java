package com.ako.bankingapp.user.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateAddressRequest(
        @Size(max = 255, message = "Street address must not exceed 255 characters") String streetAddress,

        @Size(max = 100, message = "City must not exceed 100 characters") String city,

        @Size(max = 100, message = "State must not exceed 100 characters") String state,

        @Size(max = 20, message = "Postal code must not exceed 20 characters") String postalCode,

        @Size(max = 100, message = "Country must not exceed 100 characters") String country) {
}