package com.bankmanagement.repository;

import com.bankmanagement.entity.Role;
import com.bankmanagement.entity.Role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
} 