package com.example.steamapp.service;

import com.example.steamapp.dto.SteamApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "userLibraries", key = "#steamId")
    public SteamApiResponse getOwnedGames(String steamId) {
        System.out.println("CACHE MISS: Fetching fresh data from Steam API for ID: " + steamId);
        System.out.println("VERIFYING API KEY IS LOADED: " + apiKey);
        
        String url = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key={key}&steamid={steamId}&format=json&include_appinfo=1";
        
        return restClient.get()
                .uri(url, apiKey, steamId)
                .retrieve()
                .body(SteamApiResponse.class);
    }
}