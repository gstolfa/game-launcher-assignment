package com.xciting.gamelauncher.controller;

import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import com.xciting.gamelauncher.entity.GameSession;
import com.xciting.gamelauncher.service.IGameLauncherService;

public class GameLauncherControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IGameLauncherService gameLauncherService;

    @InjectMocks
    private GameLauncherController gameLauncherController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gameLauncherController).build();
    }

    @Test
    public void testLaunchGame_ValidGameCode() throws Exception {
        String gameCode = "TEST123";
        String language = "en";
        GameSession session = GameSession.builder().sessionId("session123").build();

        when(gameLauncherService.isGameCodeValid(gameCode)).thenReturn(true);
        when(gameLauncherService.createGameSession(gameCode)).thenReturn(session);
        when(gameLauncherService.generateRedirectUrl(gameCode, session.getSessionId(), language))
            .thenReturn("https://games.xciting.com/TEST123?session=session123&language=en");

        mockMvc.perform(get("/play/{gameCode}", gameCode)
                .param("language", language)
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("https://games.xciting.com/TEST123?session=session123&language=en"));
    }

    @Test
    public void testLaunchGame_InvalidGameCode() throws Exception {
        String gameCode = "INVALID";

        when(gameLauncherService.isGameCodeValid(gameCode)).thenReturn(false);

        mockMvc.perform(get("/play/{gameCode}", gameCode))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testLaunchGame_Exception() throws Exception {
        String gameCode = "TEST123";

        when(gameLauncherService.isGameCodeValid(gameCode)).thenThrow(new ResponseStatusException(INTERNAL_SERVER_ERROR));

        mockMvc.perform(get("/play/{gameCode}", gameCode))
                .andExpect(status().isInternalServerError());
    }
}
