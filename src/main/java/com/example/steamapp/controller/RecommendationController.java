package com.example.steamapp.controller;

import com.example.steamapp.service.SteamApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final SteamApiService steamApiService;

    public RecommendationController(SteamApiService steamApiService) {
        this.steamApiService = steamApiService;
    }

    @GetMapping("/{steamId}")
    public ResponseEntity<String> getRecommendations(@PathVariable String steamId) {
        String steamData = steamApiService.getOwnedGames(steamId);
        return ResponseEntity.ok(steamData);
    }
}