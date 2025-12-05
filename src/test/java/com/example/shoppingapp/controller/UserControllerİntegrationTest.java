package com.example.shoppingapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.shoppingapp.model.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest // Bütün Spring Context-i qaldırır (H2, Service, Controller hamısı)
@AutoConfigureMockMvc // MockMvc-ni hazırlayır
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // Bu bizim "Robot Postman"ımızdır

    @Autowired
    private ObjectMapper objectMapper; // Java obyektini JSON-a çevirmək üçün

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        // 1. Göndərəcəyimiz məlumat (Body)
        CreateUserRequest request = new CreateUserRequest("IntegrationUser", "StrongPass1!");

        // 2. Sorğunu hazırlayırıq və göndəririk
        mockMvc.perform(post("/users/register")
                        .header("X-Tenant-ID", "test-tenant") // Tenant Header
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) // Obyekti JSON stringə çevirir

                // 3. Cavabı yoxlayırıq (Expectations)
                .andExpect(status().isOk()) // Status 200 olmalıdır
                .andExpect(content().string("İstifadəçi IntegrationUser uğurla yaradıldı!")); // Cavab mətni düz olmalıdır
    }

    @Test
    void shouldFailWhenPasswordIsWeak() throws Exception {
        // Zəif parol ilə test edirik
        CreateUserRequest request = new CreateUserRequest("WeakUser", "123");

        mockMvc.perform(post("/users/register")
                        .header("X-Tenant-ID", "test-tenant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                // Status 400 (Bad Request) olmalıdır (Validasiya xətası)
                .andExpect(status().isBadRequest());
    }
}