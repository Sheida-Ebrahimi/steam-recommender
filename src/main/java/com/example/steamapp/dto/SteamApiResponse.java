package com.example.steamapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public record SteamApiResponse(
    @JsonProperty("response") SteamResponse response
) implements Serializable {}