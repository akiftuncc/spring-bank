
package com.ako.bankingapp.user.dto.response;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record UserProfileResponse(
        UUID id,
        String email,
        String firstName,
        String lastName,
        String phone,
        LocalDate dateOfBirth,
        AddressResponse address,
        Instant createdAt,
        Instant updatedAt) {
}