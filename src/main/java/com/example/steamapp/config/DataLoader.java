package com.example.steamapp.config;

import com.example.steamapp.entity.GameEntity;
import com.example.steamapp.repository.GameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(GameRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                    new GameEntity("413150", "Stardew Valley", 14.99, 14.99, 0, List.of("cozy", "pixel graphics", "farming"), "A relaxing classic."),
                    new GameEntity("914800", "Coffee Talk", 12.99, 12.99, 0, List.of("cozy", "visual novel", "pixel graphics"), "Relaxing barista simulation."),
                    new GameEntity("1150690", "Omori", 19.99, 19.99, 0, List.of("pixel graphics", "story rich", "rpg"), "Highly rated narrative experience."),
                    new GameEntity("211820", "Starbound", 14.99, 14.99, 0, List.of("sandbox", "pixel graphics", "survival"), "Endless space exploration.")
                ));
                System.out.println("Catalog loaded into PostgreSQL!");
            }
        };
    }
}