package com.example.steamapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StoreGameItem(
    @JsonProperty("id") Integer id,
    @JsonProperty("name") String name,
    @JsonProperty("final_price") Integer finalPrice,
    @JsonProperty("original_price") Integer originalPrice,
    @JsonProperty("discount_percent") Integer discountPercent
) {}