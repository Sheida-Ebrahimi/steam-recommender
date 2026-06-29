package com.example.steamapp.dto;

import java.util.List;

public record RecommendationResponse(
    List<GameRecommendation> recommended,
    List<GameRecommendation> owned
) {}