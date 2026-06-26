package com.example.steamapp.dto;

import java.util.List;

public record PythonVibeResponse(
    String app_id,
    List<String> vibes,
    String message
) {}