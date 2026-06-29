package com.example.steamapp.controller;

import com.example.steamapp.dto.RecommendationResponse;
import com.example.steamapp.service.RecommendationEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationEngineService engineService;

    public RecommendationController(RecommendationEngineService engineService) {
        this.engineService = engineService;
    }

    @GetMapping("/{steamId}")
    public ResponseEntity<RecommendationResponse> getRecommendations(
            @PathVariable String steamId,
            @RequestParam(required = false, defaultValue = "cozy") String vibe) {
        
        RecommendationResponse response = engineService.generateRecommendations(steamId, vibe);
        return ResponseEntity.ok(response);
    }
}