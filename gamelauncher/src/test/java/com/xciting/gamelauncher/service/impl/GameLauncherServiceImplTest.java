package com.xciting.gamelauncher.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.xciting.gamelauncher.entity.GameSession;
import com.xciting.gamelauncher.repository.GameSessionRepository;

public class GameLauncherServiceImplTest {

    @Mock
    private GameSessionRepository gameSessionRepository;

    private GameLauncherServiceImpl gameLauncherService;

    private final int gameCodeLength = 8;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize the gameLauncherService with a mocked repository and the gameCodeLength
        gameLauncherService = new GameLauncherServiceImpl(gameSessionRepository, gameCodeLength);
    }

    @Test
    public void testCreateGameSession() {
        String gameCode = "TEST123";
        GameSession expectedSession = GameSession.builder()
                .gameCode(gameCode)
                .sessionId(UUID.randomUUID().toString())
                .createdAt(ZonedDateTime.now(ZoneId.of("Europe/Rome")).toLocalDateTime())
                .build();

        when(gameSessionRepository.save(any(GameSession.class))).thenReturn(expectedSession);

        GameSession actualSession = gameLauncherService.createGameSession(gameCode);

        assertNotNull(actualSession);
        assertEquals(expectedSession.getGameCode(), actualSession.getGameCode());
        assertEquals(expectedSession.getSessionId(), actualSession.getSessionId());
    }

    @Test
    public void testGenerateRedirectUrl() {
        String gameCode = "TEST123";
        String sessionId = "session123";
        String language = "en";
        String expectedUrl = String.format("https://games.xciting.com/%s?session=%s&language=%s", gameCode, sessionId, language);

        String actualUrl = gameLauncherService.generateRedirectUrl(gameCode, sessionId, language);

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void testIsGameCodeValid_ValidCode() {
        String gameCode = "TEST1234";
        boolean isValid = gameLauncherService.isGameCodeValid(gameCode);

        assertTrue(isValid);
    }

    @Test
    public void testIsGameCodeValid_InvalidCode() {
        String gameCode = "INVALID_CODE";
        boolean isValid = gameLauncherService.isGameCodeValid(gameCode);

        assertFalse(isValid);
    }
}
