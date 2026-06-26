package com.example.steamapp.service;

import com.example.steamapp.dto.PythonVibeResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PythonVibeClient {

    private final RestClient restClient;

    public PythonVibeClient() {
        this.restClient = RestClient.create();
    }

    public PythonVibeResponse getVibes(String appId) {
        String url = "http://localhost:8000/api/vibe/{appId}";
        
        return restClient.get()
                .uri(url, appId)
                .retrieve()
                .body(PythonVibeResponse.class);
    }
}