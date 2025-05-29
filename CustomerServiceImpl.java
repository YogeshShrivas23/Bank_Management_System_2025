package com.bankmanagement.service.impl;

import com.bankmanagement.dto.*;
import com.bankmanagement.entity.Customer;
import com.bankmanagement.entity.Role;
import com.bankmanagement.exception.ResourceNotFoundException;
import com.bankmanagement.repository.CustomerRepository;
import com.bankmanagement.repository.RoleRepository;
import com.bankmanagement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomerDTO register(CustomerRegistrationDTO registrationDTO) {
        Customer customer = Customer.builder()
                .name(registrationDTO.getName())
                .email(registrationDTO.getEmail())
                .contact(registrationDTO.getContact())
                .dob(registrationDTO.getDob())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .status(Customer.Status.ACTIVE)
                .roles(Set.of(roleRepository.findByName(Role.RoleName.USER)
                        .orElseThrow(() -> new ResourceNotFoundException("Role USER not found"))))
                .build();
        customer = customerRepository.save(customer);
        return toDTO(customer);
    }

    @Override
    public CustomerDTO getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return toDTO(customer);
    }

    @Override
    public CustomerDTO update(Long id, CustomerRegistrationDTO updateDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customer.setName(updateDTO.getName());
        customer.setEmail(updateDTO.getEmail());
        customer.setContact(updateDTO.getContact());
        customer.setDob(updateDTO.getDob());
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isBlank()) {
            customer.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }
        return toDTO(customerRepository.save(customer));
    }

    @Override
    public void delete(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customerRepository.delete(customer);
    }

    @Override
    public List<CustomerDTO> getAll() {
        return customerRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public void freeze(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customer.setStatus(Customer.Status.FROZEN);
        customerRepository.save(customer);
    }

    @Override
    public void unfreeze(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customer.setStatus(Customer.Status.ACTIVE);
        customerRepository.save(customer);
    }

    private CustomerDTO toDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .contact(customer.getContact())
                .dob(customer.getDob())
                .status(customer.getStatus().name())
                .accounts(List.of()) // Fill with account summaries if needed
                .build();
    }
} 