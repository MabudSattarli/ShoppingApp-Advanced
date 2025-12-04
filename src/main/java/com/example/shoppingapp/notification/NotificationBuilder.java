package com.example.shoppingapp.notification;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class NotificationBuilder {
    private String recipient;
    private String message;
    private String priority;

    public NotificationBuilder() {
        System.out.println(">>> YENİ NotificationBuilder YARANDI! HashCode: " + this.hashCode());
    }

    public NotificationBuilder to(String recipient) {
        this.recipient = recipient;
        return this; // Obyektin özünü qaytarır ki, nöqtə ilə davam edə bilək
    }

    public NotificationBuilder message(String message) {
        this.message = message;
        return this;
    }

    public NotificationBuilder priority(String priority) {
        this.priority = priority;
        return this;
    }

    public void send() {
        System.out.println("GÖNDƏRİLDİ -> Kimə: " + recipient +
                " | Mesaj: " + message +
                " | Prioritet: " + priority +
                " | (Builder HashCode: " + this.hashCode() + ")");
    }
}