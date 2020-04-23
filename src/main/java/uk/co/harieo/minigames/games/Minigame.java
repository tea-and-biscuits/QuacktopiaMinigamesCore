package uk.co.harieo.minigames.games;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class Minigame extends JavaPlugin {

	/**
	 * @return the name of this minigame
	 */
	public abstract String getMinigameName();

	/**
	 * @return the absolute maximum amount of players that can play this game
	 */
	public abstract int getMaxPlayers();

	/**
	 * @return the least amount of players needed for the game to start
	 */
	public abstract int getOptimalPlayers();

}
