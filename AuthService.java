package com.bankmanagement.service;

import com.bankmanagement.dto.*;

public interface AuthService {
    JwtResponseDTO register(CustomerRegistrationDTO registrationDTO);
    JwtResponseDTO login(LoginRequestDTO loginRequestDTO);
} 