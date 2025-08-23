package com.ako.bankingapp.auth.dto.response;

public record AuthResponse(    
    String accessToken,
    String tokenType,
    long expiresIn    
){}
