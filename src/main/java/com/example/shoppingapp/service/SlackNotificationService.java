package com.example.shoppingapp.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "notification.slack.enabled", havingValue = "true")
public class SlackNotificationService implements NotificationService {
    @PostConstruct
    public void init(){
        System.out.println("SlackNotificationService init");
    }


    @Override
    public void sendNotification(String message) {
        System.out.println("\u001B[35m[SLACK BİLDİRİŞİ]: " + message + "\u001B[0m");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("SlackNotificationService destroy");
    }

}
