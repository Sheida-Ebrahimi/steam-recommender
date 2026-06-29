package com.example.steamapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public record SteamGame(
    @JsonProperty("appid") int appId,
    @JsonProperty("name") String name,
    @JsonProperty("playtime_forever") int playtimeForever
) implements Serializable {}