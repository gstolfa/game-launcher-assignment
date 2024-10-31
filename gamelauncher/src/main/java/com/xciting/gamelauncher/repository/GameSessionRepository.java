package com.xciting.gamelauncher.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xciting.gamelauncher.entity.GameSession;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
}