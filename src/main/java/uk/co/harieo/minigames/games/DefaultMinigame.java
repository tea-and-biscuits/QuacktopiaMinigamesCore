package uk.co.harieo.minigames.games;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class DefaultMinigame extends JavaPlugin implements Minigame {

	private GameStage gameStage = GameStage.ERROR;

	@Override
	public GameStage getGameStage() {
		return gameStage;
	}

	/**
	 * Sets the stage that this minigame is at
	 *
	 * @param gameStage to set the stage to
	 */
	public void setGameStage(GameStage gameStage) {
		this.gameStage = gameStage;
	}

	/**
	 * Registers an array of listeners with the plugin handler
	 *
	 * @param listeners to be registered
	 */
	public void registerListeners(Listener... listeners) {
		for (Listener listener : listeners) {
			Bukkit.getPluginManager().registerEvents(listener, this);
		}
	}

	/**
	 * Registers an executor to all commands which apply to it
	 *
	 * @param executor to execute the commands
	 * @param commands which can be executed by the provided executor
	 */
	public void registerCommand(CommandExecutor executor, String... commands) {
		for (String command : commands) {
			PluginCommand pluginCommand = Bukkit.getPluginCommand(command);
			if (pluginCommand != null) {
				pluginCommand.setExecutor(executor);
			} else {
				getLogger().warning("Failed to set command executor for command (command not found): " + command);
			}
		}
	}

}
