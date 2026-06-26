package com.example.steamapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SteamApiService {

    private final RestClient restClient;

    @Value("${steam.api.key}")
    private String apiKey;

    public SteamApiService() {
        this.restClient = RestClient.create();
    }

    public String getOwnedGames(String steamId) {
        String url = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key={key}&steamid={steamId}&format=json&include_appinfo=1";
        
        return restClient.get()
                .uri(url, apiKey, steamId)
                .retrieve()
                .body(String.class);
    }
}