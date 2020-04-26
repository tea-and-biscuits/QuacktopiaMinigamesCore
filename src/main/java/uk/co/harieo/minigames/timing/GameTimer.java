package uk.co.harieo.minigames.timing;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class GameTimer extends Timer {

	private String prefix;

	public GameTimer(JavaPlugin plugin, int endInSeconds) {
		super(plugin, endInSeconds);
		setOnTimerTick(tick -> onTick());
	}

	private void onTick() {
		String message = null;
		int seconds = getSecondsLeft();
		if (seconds == 120) {
			message = ChatColor.GRAY + "There are only " + ChatColor.YELLOW + "2 minutes " + ChatColor.GRAY
					+ "remaining!";
		} else if (seconds == 60) {
			message = ChatColor.GRAY + "Time is nearly up! Only " + ChatColor.RED + "1 minute " + ChatColor.GRAY
					+ "remaining!";
		} else if (seconds != 0 && (seconds == 30 || seconds == 15 || seconds <= 5)) {
			message = ChatColor.GRAY + "The game will be " + ChatColor.YELLOW + "a draw " + ChatColor.GRAY + "in "
					+ ChatColor.RED + seconds + " seconds!";
		}

		if (message != null) {
			Bukkit.broadcastMessage(formatMessage(message));
			broadcastPing();
		}
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	private String formatMessage(String message) {
		return prefix == null ? message : prefix + message;
	}

}
