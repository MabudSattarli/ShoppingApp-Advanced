package com.example.shoppingapp.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "notification.slack.enabled", havingValue = "false", matchIfMissing = true)
public class ConsoleNotificationService implements NotificationService {
    @Override
    public void sendNotification(String message){
        System.out.println("ConsoleNotificationService: " + message);
    }
}
