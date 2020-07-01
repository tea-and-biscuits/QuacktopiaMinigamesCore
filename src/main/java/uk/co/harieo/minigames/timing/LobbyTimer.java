package uk.co.harieo.minigames.timing;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import uk.co.harieo.minigames.games.DefaultMinigame;
import uk.co.harieo.minigames.scoreboards.elements.RenderableElement;

public class LobbyTimer extends Timer implements RenderableElement {

	private static final Random RANDOM = new Random();

	private DefaultMinigame minigame;
	private String prefix;
	private int optimalTime;
	private int fullTime = 10;
	private boolean adminForce = false;
	private List<String> countdownMessages = Collections.singletonList("It's nearly time to start");

	/**
	 * An instance of {@link Timer} to countdown to the start of the game
	 *
	 * @param minigame  which is using this timer
	 * @param maximumDuration the maximum amount of time in seconds that this timer will run
	 */
	public LobbyTimer(DefaultMinigame minigame, int maximumDuration) {
		super(minigame, maximumDuration);
		this.minigame = minigame;
		optimalTime = maximumDuration;
		setOnTimerTick(seconds -> onTick());
	}

	/**
	 * An instance of this class with the maximum duration set to a default of 30
	 *
	 * @param minigame which is using this timer
	 */
	public LobbyTimer(DefaultMinigame minigame) {
		this(minigame, 30);
	}

	/**
	 * Broadcasts the amount of seconds left is the seconds are less than 3 or MOD 10 is 0 (every 10 seconds)
	 */
	protected void onTick() {
		int secondsLeft = getSecondsLeft(); // There is a 1 offset for some reason
		if (secondsLeft <= 3 && secondsLeft != 0) {
			Bukkit.broadcastMessage(formatMessage(
					ChatColor.GRAY + "The game will start in " + ChatColor.YELLOW + secondsLeft + " seconds!"));
			broadcastPing();
		} else if (secondsLeft % 10 == 0 && secondsLeft != 0) {
			String message = countdownMessages.get(RANDOM.nextInt(countdownMessages.size()));
			Bukkit.broadcastMessage(formatMessage(
					ChatColor.GRAY + message + ", starting in " + ChatColor.YELLOW + secondsLeft
							+ " seconds!"));
		}
	}

	/**
	 * Updates the timer on the assertion than there aren't enough players to start the game
	 */
	public void updateToInsufficient() {
		if (adminForce) {
			return;
		} else if (!isPaused()) {
			Bukkit.broadcastMessage(formatMessage(
					ChatColor.GRAY + "There aren't enough players, pausing the countdown to wait!"));
			pause();
		}

		// Reset the timer if we've dropped below optimal to let the server fill up again
		if (getEndInSeconds() < optimalTime) {
			setEndInSeconds(optimalTime);
			setSeconds(0);
		}
	}

	/**
	 * Updates the timer on the assertion that there are enough players to start but it is worth waiting for more
	 */
	public void updateToOptimal() {
		if (isPaused()) {
			start();
		}
	}

	/**
	 * Updates the timer on the assertion that the server is full and the game should start ASAP
	 */
	public void updateToFull() {
		if (isPaused()) {
			start();
		}

		if (getEndInSeconds() > fullTime) { // Likely the server wasn't full until now
			// Begin the countdown
			setEndInSeconds(fullTime);
			setSeconds(0);
		}
	}

	/**
	 * Forces the time to a certain amount of seconds and prevents the action from being reversed
	 *
	 * @param seconds to set the end time to
	 */
	public void forceTime(int seconds) {
		// This starts a count up from 0 to seconds
		setEndInSeconds(seconds);
		setSeconds(0);
		adminForce = true;
		start();
	}

	/**
	 * @return whether an admin has force started this timer
	 */
	public boolean isForceStarted() {
		return adminForce;
	}

	/**
	 * Sets the prefix for messages to be formatted with
	 *
	 * @param prefix to format messages with
	 */
	public LobbyTimer setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	/**
	 * Sets the amount of time that will run if the game has optimal players but isn't full yet
	 *
	 * @param optimalTime time in seconds
	 */
	public LobbyTimer setOptimalTime(int optimalTime) {
		this.optimalTime = optimalTime;
		return this;
	}

	/**
	 * Sets the amount of time that will run if the game is completely full
	 *
	 * @param fullTime time in seconds
	 */
	public LobbyTimer setFullTime(int fullTime) {
		this.fullTime = fullTime;
		return this;
	}

	/**
	 * Sets a list of messages which will be sent when the countdown nears completion
	 *
	 * @param countdownMessages a list of messages to randomly select from
	 */
	public LobbyTimer setCountdownMessages(List<String> countdownMessages) {
		this.countdownMessages = Objects.requireNonNull(countdownMessages);
		return this;
	}

	/**
	 * Formats a message with the prefix, if one has been set
	 *
	 * @param message to be formatted
	 * @return the formatted message
	 */
	private String formatMessage(String message) {
		return prefix == null ? message : prefix + message;
	}

	@Override
	public String getText(Player player) {
		if (Bukkit.getOnlinePlayers().size() < minigame.getOptimalPlayers() && !isForceStarted()) {
			return ChatColor.WHITE + "Waiting for Players";
		} else {
			int secondsLeft = getSecondsLeft();
			String word = secondsLeft == 1 ? "second" : "seconds";
			return ChatColor.WHITE.toString() + secondsLeft + " " + word;
		}
	}

}
