package com.example.steamapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.io.Serializable;

public record SteamResponse(
    @JsonProperty("game_count") int gameCount,
    @JsonProperty("games") List<SteamGame> games
) implements Serializable {}