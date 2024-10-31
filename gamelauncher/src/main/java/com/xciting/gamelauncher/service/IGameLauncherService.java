package com.xciting.gamelauncher.service;

import com.xciting.gamelauncher.entity.GameSession;

public interface IGameLauncherService {
	
    /**
    *
    * @param gameCode - Input Game Code
    * @return GameSession created with given gameCode
    */
	GameSession createGameSession(String gameCode);
	
    String generateRedirectUrl(String gameCode, String sessionId, String language);

	/**
	 * Checks if the specified game code is valid. Currently, this is a simulated check for testing purposes.
	 * In a production environment, this could be modified to perform a real check against a game repository.
	 *
	 * @param gameCode - Input Game Code
	 * @return true if the game code is valid, false otherwise
	 */
	boolean isGameCodeValid(String gameCode);
}
