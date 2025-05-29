package com.bankmanagement.service;

import com.bankmanagement.dto.*;
import java.util.List;

public interface CustomerService {
    CustomerDTO register(CustomerRegistrationDTO registrationDTO);
    CustomerDTO getById(Long id);
    CustomerDTO update(Long id, CustomerRegistrationDTO updateDTO);
    void delete(Long id);
    List<CustomerDTO> getAll();
    void freeze(Long id);
    void unfreeze(Long id);
} 