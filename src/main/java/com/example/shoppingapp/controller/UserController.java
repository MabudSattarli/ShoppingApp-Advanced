package com.example.shoppingapp.controller;


import com.example.shoppingapp.annotation.RateLimit;
import com.example.shoppingapp.model.CreateUserRequest;
import com.example.shoppingapp.model.User;
import com.example.shoppingapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.example.shoppingapp.service.UserExportService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final Optional<UserExportService> exportService;

    public UserController(UserService userService, Optional<UserExportService> exportService){
        this.userService = userService;
        this.exportService = exportService;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @PostMapping("/register")
    @RateLimit(limit = 2, duration = 60) // Rate Limit hələ də qalır
    public String registerUser(@Valid @RequestBody CreateUserRequest request) {

        // Artıq işi Service-ə həvalə edirik
        userService.createUser(request.getUsername(), request.getPassword());

        return "İstifadəçi " + request.getUsername() + " uğurla yaradıldı!";
    }

    @GetMapping("/notify/{username}")
    public String sendNotification(@PathVariable String username) {
        userService.sendWelcomeMessage(username);
        return "Bildiriş göndərildi: " + username;
    }

    @GetMapping("/transaction/{name}")
    @RateLimit(limit = 2, duration = 60)
    public String testTransaction(@PathVariable String name) {
        userService.createUserInTransaction(name);
        return "Əməliyyat bitdi: " + name;
    }
    @GetMapping("/export")
    public String exportUsers() {
        // Qutunun içinə baxırıq: Servis varmı?
        if (exportService.isPresent()) {
            return exportService.get().exportUsersToCsv();
        } else {
            // Əgər Feature Flag = false olarsa, bura işləyəcək
            return "TƏƏSSÜF Kİ: Bu xidmət hazırda aktiv deyil (Feature Disabled).";
        }
    }
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsersForCurrentTenant();
    }

}
