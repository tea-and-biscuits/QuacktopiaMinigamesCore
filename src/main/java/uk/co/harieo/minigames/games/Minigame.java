package uk.co.harieo.minigames.games;

public interface Minigame {

	/**
	 * @return the name of this minigame
	 */
	String getMinigameName();

	/**
	 * @return the absolute maximum amount of players that can play this game
	 */
	int getMaxPlayers();

	/**
	 * @return the least amount of players needed for the game to start
	 */
	int getOptimalPlayers();

}
