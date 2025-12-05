package com.example.shoppingapp;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimpleLoadTest {

    public static void main(String[] args) throws InterruptedException {
        int numberOfUsers = 10; // Eyni anda 10 istifadəçi
        ExecutorService executor = Executors.newFixedThreadPool(numberOfUsers);
        HttpClient client = HttpClient.newHttpClient();

        System.out.println(">>> 🚀 HÜCUM BAŞLAYIR: " + numberOfUsers + " sorğu göndərilir...");

        for (int i = 0; i < numberOfUsers; i++) {
            int userId = i; // Hər sorğu üçün nömrə
            executor.submit(() -> {
                try {
                    // Sorğunu hazırlayırıq
                    HttpRequest request = HttpRequest.newBuilder()
                            // Test etdiyimiz URL
                            .uri(URI.create("http://localhost:8080/users/transaction/JavaUser" + userId))
                            // Multi-Tenant Header-i unutmuruq!
                            .header("X-Tenant-ID", "test-tenant")
                            .GET()
                            .build();

                    long start = System.currentTimeMillis();

                    // Göndəririk
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    long duration = System.currentTimeMillis() - start;

                    // Cavabı yoxlayırıq
                    if (response.statusCode() == 200) {
                        System.out.println("✅ UĞURLU (200) - " + duration + "ms");
                    } else if (response.statusCode() == 429) {
                        System.out.println("⛔ BLOKLANDI (429) - Rate Limit İşləyir!");
                    } else {
                        System.out.println("⚠️ Digər Status: " + response.statusCode());
                    }

                } catch (Exception e) {
                    System.err.println("Xəta: " + e.getMessage());
                }
            });
        }

        // Bütün işçiləri dayandırırıq
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(">>> 🏁 TEST BİTDİ.");
    }
}