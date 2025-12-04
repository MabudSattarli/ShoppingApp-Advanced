package com.example.shoppingapp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC; // Loglama üçün kontekst
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.UUID;

@Aspect
@Component
public class TransactionMonitoringAspect {

    // 1. Transaction BAŞLAYANDA (@Before)
    // Harada @Transactional varsa, orada işə düş
    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void logTransactionStart(JoinPoint joinPoint) {

        // Hər tranzaksiya üçün unikal bir ID yaraduruq ki, loglarda izləyə bilək
        String transactionId = UUID.randomUUID().toString().substring(0, 8);
        MDC.put("txId", transactionId); // Loga yapışdırırıq

        boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        String isolationLevel = TransactionSynchronizationManager.getCurrentTransactionIsolationLevel() != null
                ? TransactionSynchronizationManager.getCurrentTransactionIsolationLevel().toString()
                : "DEFAULT";

        System.out.println(" [" + transactionId + "] >>> TRANSACTION BAŞLADI: " + joinPoint.getSignature().getName());
        System.out.println("    |-- Aktivdir? " + isActive);
        System.out.println("    |-- Read Only? " + isReadOnly);
        System.out.println("    |-- Isolation: " + isolationLevel);
    }

    // 2. Transaction UĞURLA BİTƏNDƏ (Commit)
    @AfterReturning("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void logTransactionCommit(JoinPoint joinPoint) {
        String txId = MDC.get("txId");
        System.out.println(" [" + txId + "] <<< TRANSACTION UĞURLA BİTDİ (COMMIT).");
        MDC.clear(); // Təmizlik
    }

    // 3. Transaction XƏTA İLƏ BİTƏNDƏ (Rollback)
    @AfterThrowing(pointcut = "@annotation(org.springframework.transaction.annotation.Transactional)", throwing = "ex")
    public void logTransactionRollback(JoinPoint joinPoint, Exception ex) {
        String txId = MDC.get("txId");
        System.out.println(" [" + txId + "] !!! TRANSACTION LƏĞV EDİLDİ (ROLLBACK). Səbəb: " + ex.getMessage());
        MDC.clear(); // Təmizlik
    }
}