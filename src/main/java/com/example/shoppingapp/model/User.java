package com.example.shoppingapp.model;

import jakarta.persistence.Entity; // JPA Entity
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "users") // H2 bazasında "User" sözü rezerv olunub, adını dəyişirik
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // YENİ SAHƏ:
    private String tenantId;

    public User() {}

    public User(Long id, String name, String tenantId) {
        this.id = id;
        this.name = name;
        this.tenantId = tenantId;
    }

    // Getter & Setter
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
}