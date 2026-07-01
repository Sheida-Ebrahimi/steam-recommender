package com.example.steamapp.config;

import com.example.steamapp.service.SteamStoreCatalogService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(SteamStoreCatalogService catalogService) {
        return args -> {
            catalogService.ingestFeaturedGames();
        };
    }
}