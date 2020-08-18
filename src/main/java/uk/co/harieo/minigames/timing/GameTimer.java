package uk.co.harieo.minigames.timing;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.harieo.minigames.scoreboards.elements.RenderableElement;

public class GameTimer extends Timer implements RenderableElement {

	private String prefix;

	/**
	 * A timer which is formatted for counting down to the end of a game
	 *
	 * @param plugin which is using this timer
	 * @param endInSeconds the seconds until this timer ends
	 */
	public GameTimer(JavaPlugin plugin, int endInSeconds) {
		super(plugin, endInSeconds);
		setOnTimerTick(tick -> announceTimeLeft());
	}

	/**
	 * Announces to the entire server at certain intervals of time remaining, starting at 2 minutes
	 */
	protected void announceTimeLeft() {
		String message = null;
		int seconds = getSecondsLeft();
		if (seconds == 120) {
			message = ChatColor.GRAY + "There are only " + ChatColor.YELLOW + "2 minutes " + ChatColor.GRAY
					+ "remaining!";
		} else if (seconds == 60) {
			message = ChatColor.GRAY + "Time is nearly up! Only " + ChatColor.GOLD + "1 minute " + ChatColor.GRAY
					+ "remaining!";
		} else if (seconds != 0 && (seconds == 30 || seconds == 15 || seconds <= 5)) {
			message = ChatColor.GRAY + "The game will be " + ChatColor.YELLOW + "a draw " + ChatColor.GRAY + "in "
					+ ChatColor.GOLD + seconds + " seconds!";
		}

		if (message != null) {
			Bukkit.broadcastMessage(formatMessage(message));
			broadcastPing();
		}
	}

	/**
	 * Sets the prefix to use at the start of system messages from this timer
	 *
	 * @param prefix to go before system messages
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Adds the prefix, if set, to the given message
	 *
	 * @param message to add the prefix before
	 * @return the formatted message
	 */
	private String formatMessage(String message) {
		return prefix == null ? message : prefix + message;
	}

	@Override
	public String getText(Player player) {
		if (isCancelled()) {
			return "Waiting...";
		} else {
			int timeRemaining = getSecondsLeft();
			int minutes = timeRemaining / 60;
			int seconds = timeRemaining % 60;
			if (seconds == 0) {
				return minutes + " minutes";
			} else {
				return ChatColor.WHITE.toString() + minutes + " minutes, " + seconds + " seconds";
			}
		}
	}

}
