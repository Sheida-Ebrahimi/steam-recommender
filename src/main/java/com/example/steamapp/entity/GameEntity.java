package com.example.steamapp.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "catalog_games")
public class GameEntity {

    @Id
    private String appId;
    private String name;
    private double currentPrice;
    private double originalPrice;
    private int discountPercentage;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> vibes;

    private String matchReason;

    public GameEntity() {}

    public GameEntity(String appId, String name, double currentPrice, double originalPrice, int discountPercentage, List<String> vibes, String matchReason) {
        this.appId = appId;
        this.name = name;
        this.currentPrice = currentPrice;
        this.originalPrice = originalPrice;
        this.discountPercentage = discountPercentage;
        this.vibes = vibes;
        this.matchReason = matchReason;
    }

    // Getters
    public String getAppId() { return appId; }
    public String getName() { return name; }
    public double getCurrentPrice() { return currentPrice; }
    public double getOriginalPrice() { return originalPrice; }
    public int getDiscountPercentage() { return discountPercentage; }
    public List<String> getVibes() { return vibes; }
    public String getMatchReason() { return matchReason; }
}