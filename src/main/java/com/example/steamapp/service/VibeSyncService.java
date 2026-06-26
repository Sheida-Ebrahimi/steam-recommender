package com.example.steamapp.service;

import com.example.steamapp.dto.PythonVibeResponse;
import com.example.steamapp.entity.GameEntity;
import com.example.steamapp.repository.GameRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VibeSyncService {

    private final GameRepository gameRepository;
    private final PythonVibeClient pythonVibeClient;

    public VibeSyncService(GameRepository gameRepository, PythonVibeClient pythonVibeClient) {
        this.gameRepository = gameRepository;
        this.pythonVibeClient = pythonVibeClient;
    }

    @Scheduled(fixedDelay = 60000)
    public void syncGameVibes() {
        List<GameEntity> games = gameRepository.findAll();

        for (GameEntity game : games) {
            try {
                PythonVibeResponse response = pythonVibeClient.getVibes(game.getAppId());

                if (response != null && response.vibes() != null && !response.vibes().isEmpty()) {
                    game.setVibes(response.vibes());
                    gameRepository.save(game);
                    System.out.println("Updated vibes for " + game.getName() + ": " + response.vibes());
                }
            } catch (Exception e) {
                System.err.println("Failed to update vibes for " + game.getName());
            }
        }
    }
}