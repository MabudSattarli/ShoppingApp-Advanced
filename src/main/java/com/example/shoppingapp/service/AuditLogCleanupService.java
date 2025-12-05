
  package com.example.shoppingapp.service;

import com.example.shoppingapp.repository.AuditLogRepository;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

    @Service
    public class AuditLogCleanupService {

        private final AuditLogRepository auditLogRepository;

        // application.properties-dən dəyəri oxuyuruq
        @Value("${audit.retention.days}")
        private int retentionDays;

        public AuditLogCleanupService(AuditLogRepository auditLogRepository) {
            this.auditLogRepository = auditLogRepository;
        }

        // Cron: Gecə saat 02:00-da işə düşəcək
        @Scheduled(cron = "${audit.cleanup.cron}")
        @Transactional // Silmə əməliyyatı üçün lazımdır
        public void cleanupOldLogs() {
            long startTime = System.currentTimeMillis();

            System.out.println(">>> [SCHEDULER] Təmizlik başladı...");

            // Bu gündən 90 gün əvvəlki tarixi hesablayırıq
            LocalDateTime cutoffDate = LocalDateTime.now().minusDays(retentionDays);

            // Həmin tarixdən köhnə olanları silirik
            auditLogRepository.deleteByCreatedDateBefore(cutoffDate);

            long duration = System.currentTimeMillis() - startTime;
            System.out.println("<<< [SCHEDULER] Təmizlik bitdi. Vaxt: " + duration + "ms. (90 gündən köhnə loglar silindi)");
        }

        // Bean Lifecycle: Tətbiq dayananda (Stop) işə düşür
        @PreDestroy
        public void onShutdown() {
            System.out.println("!!! [SHUTDOWN] Tətbiq dayanır. Planlanmış tapşırıqlar ləğv edilir.");
        }
    }


