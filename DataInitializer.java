package com.bankmanagement.config;

import com.bankmanagement.dto.CustomerRegistrationDTO;
import com.bankmanagement.entity.Role;
import com.bankmanagement.repository.RoleRepository;
import com.bankmanagement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final CustomerService customerService;

    @Override
    public void run(String... args) {
        // Insert default roles if not exists
        if (roleRepository.findByName(Role.RoleName.USER).isEmpty()) {
            roleRepository.save(new Role(null, Role.RoleName.USER));
        }
        if (roleRepository.findByName(Role.RoleName.ADMIN).isEmpty()) {
            roleRepository.save(new Role(null, Role.RoleName.ADMIN));
        }

        // Insert default admin user if not exists
        try {
            CustomerRegistrationDTO adminDTO = CustomerRegistrationDTO.builder()
                    .name("Admin")
                    .email("admin@bank.com")
                    .contact("1234567890")
                    .dob(LocalDate.now().minusYears(30))
                    .password("admin123")
                    .build();
            customerService.register(adminDTO);
        } catch (Exception e) {
            // Admin already exists
        }
    }
} 