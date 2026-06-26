package com.example.steamapp.controller;

import com.example.steamapp.dto.GameRecommendation;
import com.example.steamapp.service.RecommendationEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationEngineService engineService;

    public RecommendationController(RecommendationEngineService engineService) {
        this.engineService = engineService;
    }

    @GetMapping("/{steamId}")
    public ResponseEntity<List<GameRecommendation>> getRecommendations(@PathVariable String steamId) {
        List<GameRecommendation> recommendations = engineService.generateRecommendations(steamId);
        return ResponseEntity.ok(recommendations);
    }
}