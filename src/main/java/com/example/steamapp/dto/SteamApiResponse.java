package com.example.steamapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SteamApiResponse(
    @JsonProperty("response") SteamResponse response
) {}