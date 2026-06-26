package com.example.steamapp.dto;

import java.util.List;

public record GameRecommendation(
    String appId,
    String name,
    double currentPrice,
    double originalPrice,
    int discountPercentage,
    List<String> vibes,
    String matchReason
) {}