package com.example.steamapp.service;

import com.example.steamapp.dto.GameRecommendation;
import com.example.steamapp.dto.SteamApiResponse;
import com.example.steamapp.dto.SteamGame;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationEngineService {

    private final SteamApiService steamApiService;

    public RecommendationEngineService(SteamApiService steamApiService) {
        this.steamApiService = steamApiService;
    }

    public List<GameRecommendation> generateRecommendations(String steamId) {
        SteamApiResponse steamData = steamApiService.getOwnedGames(steamId);

        Set<Integer> ownedAppIds = steamData.response().games().stream()
                .map(SteamGame::appId)
                .collect(Collectors.toSet());

        List<GameRecommendation> catalog = List.of(
                new GameRecommendation("413150", "Stardew Valley", 14.99, 14.99, 0, List.of("cozy", "pixel graphics", "farming"), "A relaxing classic."),
                new GameRecommendation("914800", "Coffee Talk", 12.99, 12.99, 0, List.of("cozy", "visual novel", "pixel graphics"), "Relaxing barista simulation."),
                new GameRecommendation("1150690", "Omori", 19.99, 19.99, 0, List.of("pixel graphics", "story rich", "rpg"), "Highly rated narrative experience."),
                new GameRecommendation("211820", "Starbound", 14.99, 14.99, 0, List.of("sandbox", "pixel graphics", "survival"), "Endless space exploration.")
        );

        return catalog.stream()
                .filter(game -> !ownedAppIds.contains(Integer.parseInt(game.appId())))
                .collect(Collectors.toList());
    }
}