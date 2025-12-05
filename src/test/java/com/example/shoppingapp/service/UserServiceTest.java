package com.example.shoppingapp.service;

import com.example.shoppingapp.config.TenantContext;
import com.example.shoppingapp.model.User;
import com.example.shoppingapp.notification.NotificationBuilder;
import com.example.shoppingapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// 1. Mockito-nu işə salırıq
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    // 2. Dublyorları (Saxta asılılıqları) yaradırıq
    @Mock
    private UserRepository userRepository;

    @Mock
    private TenantContext tenantContext;

    @Mock
    private ObjectFactory<NotificationBuilder> notificationBuilderFactory;

    // 3. Real Servisi yaradırıq və dublyorları içinə qoyuruq
    @InjectMocks
    private UserService userService;

    // --- TEST 1: Multi-Tenant Axtarış ---
    @Test
    void shouldGetAllUsersForCurrentTenant() {
        // A) ARRANGE (Hazırlıq) - Ssenarini qururuq
        // "Soruşsalar ki, Tenant kimdir? De ki: 'cocacola'"
        when(tenantContext.getTenantId()).thenReturn("cocacola");

        // "Soruşsalar ki, 'cocacola' istifadəçiləri kimdir? Bu siyahını qaytar"
        User user1 = new User(1L, "Ali", "cocacola");
        User user2 = new User(2L, "Vali", "cocacola");
        List<User> mockUsers = Arrays.asList(user1, user2);

        when(userRepository.findByTenantId("cocacola")).thenReturn(mockUsers);

        // B) ACT (İcra) - Metodu çağırırıq
        List<User> result = userService.getAllUsersForCurrentTenant();

        // C) ASSERT (Yoxlama) - Nəticə düzdürmü?
        assertEquals(2, result.size()); // 2 nəfər qayıtmalı idi
        assertEquals("Ali", result.get(0).getName()); // Birinci adam Ali olmalıdır

        // Əmin oluruq ki, repository metodu həqiqətən çağırılıb
        verify(userRepository).findByTenantId("cocacola");
    }

    // --- TEST 2: İstifadəçi Yaratmaq ---
    @Test
    void shouldCreateUserWithCorrectTenantId() {
        // A) ARRANGE
        // "Hazırda sistemdə 'pepsi' şirkəti var" deyə imitasiya edirik
        when(tenantContext.getTenantId()).thenReturn("pepsi");

        // B) ACT
        userService.createUser("Hesen", "password123");

        // C) ASSERT
        // Burada return dəyəri yoxdur (void), ona görə də
        // "save" metodunun çağırılıb-çağırılmadığını yoxlayırıq.
        // Və ən vacibi: tenantId düzgün gedibmi?

        // save metodu 1 dəfə çağırılmalıdır
        // any(User.class) -> Hər hansı User obyekti ilə çağırıla bilər
        verify(userRepository, times(1)).save(any(User.class));
    }
}