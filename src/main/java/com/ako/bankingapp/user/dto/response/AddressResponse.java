
package com.ako.bankingapp.user.dto.response;

public record AddressResponse(
        String streetAddress,
        String city,
        String state,
        String postalCode,
        String country) {
}