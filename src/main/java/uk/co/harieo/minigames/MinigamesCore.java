package uk.co.harieo.minigames;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.harieo.QuackConnector.QuackConnector;

public class MinigamesCore extends JavaPlugin {

	private static boolean isConnectorEnabled = false;

	@Override
	public void onEnable() {
		isConnectorEnabled = Bukkit.getPluginManager().isPluginEnabled("QuackConnector");
	}

	public static void setAcceptingPlayers(boolean isAcceptingPlayers) {
		if (isConnectorEnabled) {
			QuackConnector.getInstance().setAcceptingPlayers(isAcceptingPlayers);
			System.out.println("This server is " + (isAcceptingPlayers ? "now accepting players" : "no longer accepting players"));
		}
	}

	public static void sendAllPlayersToFallbackServer() {
		if (isConnectorEnabled) {
			QuackConnector.getInstance().sendAllToFallback();
		}
	}

}
