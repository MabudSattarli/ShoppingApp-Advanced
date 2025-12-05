package com.example.shoppingapp.repository;

import com.example.shoppingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // Tenant-a görə axtarış
    List<User> findByTenantId(String tenantId);
}