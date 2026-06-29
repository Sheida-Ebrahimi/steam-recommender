package com.example.steamapp.controller;

import com.example.steamapp.dto.GameRecommendation;
import com.example.steamapp.service.RecommendationEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationEngineService engineService;

    public RecommendationController(RecommendationEngineService engineService) {
        this.engineService = engineService;
    }

    @GetMapping("/{steamId}")
    public ResponseEntity<List<GameRecommendation>> getRecommendations(
            @PathVariable String steamId,
            @RequestParam(required = false, defaultValue = "cozy") String vibe) {
        
        List<GameRecommendation> recommendations = engineService.generateRecommendations(steamId, vibe);
        return ResponseEntity.ok(recommendations);
    }
}