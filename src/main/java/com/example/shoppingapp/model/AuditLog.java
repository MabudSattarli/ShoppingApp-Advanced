package com.example.shoppingapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private LocalDateTime createdDate;

    // Default Constructor
    public AuditLog() {}

    public AuditLog(String message, LocalDateTime createdDate) {
        this.message = message;
        this.createdDate = createdDate;
    }

    // Getterlər
    public LocalDateTime getCreatedDate() { return createdDate; }
}