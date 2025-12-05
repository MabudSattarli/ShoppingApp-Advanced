package com.example.shoppingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShoppingappApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingappApplication.class, args);
    }

}
