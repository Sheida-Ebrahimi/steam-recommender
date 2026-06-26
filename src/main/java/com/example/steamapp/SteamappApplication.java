package com.example.steamapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class SteamappApplication {

    public static void main(String[] args) {
        SpringApplication.run(SteamappApplication.class, args);
    }

}