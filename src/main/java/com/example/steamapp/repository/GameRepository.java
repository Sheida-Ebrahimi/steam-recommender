package com.example.steamapp.repository;

import com.example.steamapp.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, String> {
}