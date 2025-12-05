package com.example.shoppingapp.service;

import com.example.shoppingapp.annotation.ExecutionTimeAlert;
import com.example.shoppingapp.config.TenantContext;
import com.example.shoppingapp.model.User;
import com.example.shoppingapp.notification.NotificationBuilder;
import com.example.shoppingapp.repository.UserRepository;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TenantContext tenantContext;
    private final ObjectFactory<NotificationBuilder> notificationBuilderFactory;

    // XƏTA HƏLLİ 1: Bütün Dependency-ləri TEK BİR konstruktorda birləşdirdik
    public UserService(UserRepository userRepository,
                       TenantContext tenantContext,
                       ObjectFactory<NotificationBuilder> notificationBuilderFactory) {
        this.userRepository = userRepository;
        this.tenantContext = tenantContext;
        this.notificationBuilderFactory = notificationBuilderFactory;
    }

    // XƏTA HƏLLİ 2: Duplicate olan 'createUser' metodlarını birləşdirdik.
    // Həm Tenant məntiqi var, həm də Transaction testi üçün error yoxlaması.
    @Transactional
    public void createUser(String username, String password) {
        // 1. Kartdan Tenant ID-ni oxu
        String currentTenant = tenantContext.getTenantId();

        System.out.println("... Bazaya yazılır: " + username + " (Tenant: " + currentTenant + ")");

        // Simulyasiya: Transaction Rollback testi üçün
        if ("error".equals(username)) {
            throw new RuntimeException("Bazada xəta baş verdi! (Simulyasiya)");
        }

        // 2. İstifadəçini bu Tenant ID ilə yadda saxla
        // (null -> ID avtomatik artır, username, currentTenant)
        User user = new User(null, username, currentTenant);
        userRepository.save(user);
    }

    // Multi-Tenant Axtarış
    public List<User> getAllUsersForCurrentTenant() {
        String currentTenant = tenantContext.getTenantId();
        return userRepository.findByTenantId(currentTenant);
    }

    // Notification Logic
    public void sendWelcomeMessage(String username) {
        NotificationBuilder builder = notificationBuilderFactory.getObject();
        builder.to(username)
                .message("Xoş gəldiniz!")
                .priority("Yüksək")
                .send();
    }

    // Transaction Test Logic (Köhnə tapşırıqdan qalan, adını fərqli saxladıq ki, qarışmasın)
    @Transactional
    public void createUserInTransaction(String username) {
        System.out.println("... (Transaction Test) Bazaya yazılır: " + username);

        if ("error".equals(username)) {
            throw new RuntimeException("Simulyasiya edilmiş xəta!");
        }
    }

    // Slow Method Logic
    @ExecutionTimeAlert(threshold = 2000)
    public User getUserById(Long id){
        try{
            System.out.println("istifadecini axtarmaga basladim 3 saniye davam edecek! ");
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // XƏTA HƏLLİ 3: User constructoruna tenantId (məsələn "default") əlavə etdik
        return new User(id, "User_" + id, "default");
    }
}