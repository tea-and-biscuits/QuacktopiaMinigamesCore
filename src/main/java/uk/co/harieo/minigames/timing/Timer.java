package uk.co.harieo.minigames.timing;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

public class Timer extends BukkitRunnable {

	private int seconds = 0;
	private boolean paused = true;

	private int endInSeconds;
	private Consumer<Void> onTimerEnd;
	private Consumer<Integer> onTimerTick;

	public Timer(JavaPlugin plugin, int endInSeconds) {
		this.endInSeconds = endInSeconds;
		runTaskTimer(plugin, 0, 20);
	}

	@Override
	public void run() {
		if (!paused) {
			seconds++;
			if (onTimerTick != null) {
				onTimerTick.accept(seconds);
			}

			if (seconds >= endInSeconds) {
				cancel();
				if (onTimerEnd != null) {
					onTimerEnd.accept(null);
				}
			}
		}
	}

	public boolean isPaused() {
		return paused;
	}

	public void pause() {
		if (!isPaused()) {
			paused = true;
		}
	}

	public void start() {
		if (isPaused()) {
			paused = false;
		}
	}

	public Timer setOnTimerEnd(Consumer<Void> onTimerEnd) {
		this.onTimerEnd = onTimerEnd;
		return this;
	}

	public Timer setOnTimerTick(Consumer<Integer> onTimerTick) {
		this.onTimerTick = onTimerTick;
		return this;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getEndInSeconds() {
		return endInSeconds;
	}

	public int getSecondsLeft() {
		return getEndInSeconds() - getSeconds();
	}

	public void setEndInSeconds(int endInSeconds) {
		this.endInSeconds = endInSeconds;
	}

	/**
	 * Plays a ping sound to all online players
	 */
	protected void broadcastPing() {
		Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(),
				Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.5F));
	}

}
