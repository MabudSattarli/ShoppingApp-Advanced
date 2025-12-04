package com.example.shoppingapp.service;

import com.example.shoppingapp.model.User;
import com.example.shoppingapp.notification.NotificationBuilder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

private final ObjectFactory<NotificationBuilder> notificationBuilderFactory;

    public UserService(ObjectFactory<NotificationBuilder> notificationBuilderFactory) {
        this.notificationBuilderFactory = notificationBuilderFactory;
    }

public User getUserById(Long id){
    try {
        System.out.println("Istifadecini axtariram, 3 saniye davam edecek");
        Thread.sleep(3000);
    }catch (InterruptedException e){
        e.printStackTrace();
    }

        return new User(id, "User_" + id);
    }

    public void sendWelcomeMessage(String username) {
        NotificationBuilder builder = notificationBuilderFactory.getObject();

        builder.to(username)
                .message("Xoş gəldiniz!")
                .priority("Yüksək")
                .send();
    }

    @Transactional // <--- Bu annotasiya bizim Aspect-i işə salacaq
    public void createUser(String username, String password) {
        System.out.println("... (Service daxilində) İstifadəçi bazaya yazılır: " + username);

        // Simulyasiya: Əgər username "error" olarsa, qəsdən xəta ataq (Rollback testi üçün)
        if ("error".equals(username)) {
            throw new RuntimeException("Bazada xəta baş verdi! (Simulyasiya)");
        }
    }

    @Transactional // Bu annotasiya Aspect-i işə salacaq
    public void createUserInTransaction(String username) {
        System.out.println("... (Service) Bazaya yazılır: " + username);

        // Test üçün: Əgər ad "error" olarsa, qəsdən sistemi partladırıq
        if ("error".equals(username)) {
            throw new RuntimeException("Simulyasiya edilmiş xəta!");
        }
    }
}

