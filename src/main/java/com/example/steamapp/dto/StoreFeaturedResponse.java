package com.example.steamapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StoreFeaturedResponse(
    @JsonProperty("large_capsules") List<StoreGameItem> largeCapsules,
    @JsonProperty("featured_win") List<StoreGameItem> featuredWin
) {}