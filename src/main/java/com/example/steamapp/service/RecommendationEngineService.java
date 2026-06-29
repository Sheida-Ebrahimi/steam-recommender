package com.example.steamapp.service;

import com.example.steamapp.dto.GameRecommendation;
import com.example.steamapp.dto.SteamApiResponse;
import com.example.steamapp.dto.SteamGame;
import com.example.steamapp.entity.GameEntity;
import com.example.steamapp.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationEngineService {

    private final SteamApiService steamApiService;
    private final GameRepository gameRepository;

    public RecommendationEngineService(SteamApiService steamApiService, GameRepository gameRepository) {
        this.steamApiService = steamApiService;
        this.gameRepository = gameRepository;
    }

    public List<GameRecommendation> generateRecommendations(String steamId, String desiredVibe) {
        SteamApiResponse steamData = steamApiService.getOwnedGames(steamId);

        Set<Integer> ownedAppIds = steamData.response().games().stream()
                .map(SteamGame::appId)
                .collect(Collectors.toSet());

        List<GameEntity> dbCatalog = gameRepository.findAll();

        return dbCatalog.stream()
                .filter(game -> game.getVibes() != null && game.getVibes().stream().anyMatch(v -> v.equalsIgnoreCase(desiredVibe)))
                .filter(game -> !ownedAppIds.contains(Integer.parseInt(game.getAppId())))
                .map(game -> new GameRecommendation(
                        game.getAppId(),
                        game.getName(),
                        game.getCurrentPrice(),
                        game.getOriginalPrice(),
                        game.getDiscountPercentage(),
                        game.getVibes(),
                        game.getMatchReason()
                ))
                .collect(Collectors.toList());
    }
}