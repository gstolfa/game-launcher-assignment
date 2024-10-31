package com.xciting.gamelauncher.service.impl;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.xciting.gamelauncher.entity.GameSession;
import com.xciting.gamelauncher.repository.GameSessionRepository;
import com.xciting.gamelauncher.service.IGameLauncherService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class GameLauncherServiceImpl implements IGameLauncherService {

    private final GameSessionRepository gameSessionRepository; // Make the field final for immutability
    
    private final int gameCodeLength;

    /**
     * Simulates the creation of a new game session for the given game code.
     * This approach is intended for testing purposes and can be expanded with
     * further logic to integrate a real game code validation check.
     *
     * @param gameCode - Input Game Code
     * @return GameSession created with the given gameCode
     */
    @Override
    public GameSession createGameSession(String gameCode) {
        log.info("Creating game session for game code: {}", gameCode);
        GameSession gameSession = GameSession.builder()
                .gameCode(gameCode)
                .sessionId(UUID.randomUUID().toString())
                .createdAt(ZonedDateTime.now(ZoneId.of("Europe/Rome")).toLocalDateTime())
                .build();
        
        GameSession savedSession = gameSessionRepository.save(gameSession);
        log.info("Game session created with ID: {}", savedSession.getSessionId());
        return savedSession;
    }

    /**
     * Generates the redirect URL with the specified game code, session ID, and language.
     *
     * @param gameCode - Game code for which the URL is generated
     * @param sessionId - Unique session ID for this game session
     * @param language - Language preference for the game
     * @return Redirect URL containing the game code, session ID, and language
     */
    @Override
    public String generateRedirectUrl(String gameCode, String sessionId, String language) {
        String redirectUrl = String.format("https://games.xciting.com/%s?session=%s&language=%s", gameCode, sessionId, language);
        log.info("Generated redirect URL: {}", redirectUrl);
        return redirectUrl;
    }

    /**
     * Checks if the specified game code is valid. Currently, this is a simulated check for testing purposes.
     * In a production environment, this could be modified to perform a real check against a game repository.
     *
     * @param gameCode - Input Game Code
     * @return true if the game code is valid, false otherwise
     */
    @Override
    public boolean isGameCodeValid(String gameCode) {
        // Validate that the game code is an alphanumeric sequence of the specified length.
        String regexPattern = "^[A-Za-z0-9]{" + gameCodeLength + "}$";
        boolean isValid = gameCode != null && gameCode.matches(regexPattern);
        log.info("Game code '{}' validity check: {}", gameCode, isValid);
        return isValid;
    }
}
