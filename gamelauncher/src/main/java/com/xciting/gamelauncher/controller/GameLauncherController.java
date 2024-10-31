package com.xciting.gamelauncher.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import com.xciting.gamelauncher.entity.GameSession;
import com.xciting.gamelauncher.service.IGameLauncherService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class GameLauncherController {

    private final IGameLauncherService gameLauncherService;

    @GetMapping("/play/{gameCode}")
    public RedirectView launchGame(@PathVariable String gameCode,
                                   @RequestParam(defaultValue = "en") String language) {
        log.info("Received request to launch game with code: {}", gameCode); // Log request details
        try {
            // Check if the game code exists. This is currently a simulation and
            // could be implemented to verify the game code against a database.
            if (!gameLauncherService.isGameCodeValid(gameCode)) {
                log.warn("Game code not found: {}", gameCode); // Log a warning if the game code is invalid
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game code not found: " + gameCode);
            }

            // Create a new game session for the provided game code
            GameSession session = gameLauncherService.createGameSession(gameCode);
            log.info("Created game session with ID: {}", session.getSessionId());

            // Generate the redirect URL for the created session
            String redirectUrl = gameLauncherService.generateRedirectUrl(gameCode, session.getSessionId(), language);
            log.info("Redirecting to URL: {}", redirectUrl);

            // Return the redirect
            return new RedirectView(redirectUrl); // Directly return RedirectView with the URL

        } catch (ResponseStatusException ex) {
            // Re-throws the custom exception for global exception handling
            log.error("ResponseStatusException occurred: {}", ex.getReason(), ex); // Log the exception details
            throw ex;
        } catch (Exception e) {
            // Handles unexpected errors with a 500 status code
            log.error("Failed to launch game session", e); // Log any unexpected exceptions
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to launch game session", e);
        }
    }
}
