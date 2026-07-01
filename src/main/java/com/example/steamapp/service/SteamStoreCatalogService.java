package com.example.steamapp.service;

import com.example.steamapp.dto.StoreFeaturedResponse;
import com.example.steamapp.dto.StoreGameItem;
import com.example.steamapp.entity.GameEntity;
import com.example.steamapp.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class SteamStoreCatalogService {

    private final GameRepository gameRepository;
    private final RestClient restClient;

    public SteamStoreCatalogService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.restClient = RestClient.create();
    }

    public void ingestFeaturedGames() {
        String url = "https://store.steampowered.com/api/featured/";
        try {
            StoreFeaturedResponse response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(StoreFeaturedResponse.class);

            if (response != null) {
                List<StoreGameItem> allItems = new ArrayList<>();
        
                if (response.largeCapsules() != null) allItems.addAll(response.largeCapsules());
                if (response.featuredWin() != null) allItems.addAll(response.featuredWin());

                for (StoreGameItem item : allItems) {
                    if (item.id() == null) continue; 

                    String appId = String.valueOf(item.id());
                    if (!gameRepository.existsById(appId)) {
                        
              
                        double currentPrice = item.finalPrice() != null ? item.finalPrice() / 100.0 : 0.0;
                        double originalPrice = item.originalPrice() != null ? item.originalPrice() / 100.0 : currentPrice;
                        int discount = item.discountPercent() != null ? item.discountPercent() : 0;

                        GameEntity game = new GameEntity(
                                appId,
                                item.name() != null ? item.name() : "Unknown Game",
                                currentPrice,
                                originalPrice,
                                discount,
                                new ArrayList<>(),
                                "Discovered via Steam Store Ingestion"
                        );
                        gameRepository.save(game);
                        System.out.println("Ingested new game into catalog: " + item.name());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error ingesting featured games: " + e.getMessage());
        }
    }
}