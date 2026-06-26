package com.example.steamapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record SteamResponse(
    @JsonProperty("game_count") int gameCount,
    @JsonProperty("games") List<SteamGame> games
) {}