package com.bankmanagement.service.impl;

import com.bankmanagement.dto.*;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.entity.Role;
import com.bankmanagement.exception.ResourceNotFoundException;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.repository.RoleRepository;
import com.bankmanagement.service.AuthService;
import com.bankmanagement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;

    @Override
    public JwtResponseDTO register(CustomerRegistrationDTO registrationDTO) {
        CustomerDTO customerDTO = customerService.register(registrationDTO);
        return generateJwtResponse(customerDTO);
    }

    @Override
    public JwtResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Customer customer = customerRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return generateJwtResponse(toDTO(customer));
    }

    private JwtResponseDTO generateJwtResponse(CustomerDTO customerDTO) {
        String token = jwtConfig.generateToken(customerDTO.getEmail());
        Set<String> roles = customerDTO.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        return JwtResponseDTO.builder()
                .token(token)
                .type("Bearer")
                .userId(customerDTO.getId())
                .email(customerDTO.getEmail())
                .roles(roles)
                .build();
    }

    private CustomerDTO toDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .contact(customer.getContact())
                .dob(customer.getDob())
                .status(customer.getStatus().name())
                .roles(customer.getRoles())
                .accounts(List.of())
                .build();
    }
} 