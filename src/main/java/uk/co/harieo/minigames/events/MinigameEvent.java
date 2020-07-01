package uk.co.harieo.minigames.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import uk.co.harieo.minigames.games.Minigame;

public class MinigameEvent extends Event {

	private final Minigame minigame;

	public MinigameEvent(Minigame minigame) {
		this.minigame = minigame;
	}

	public Minigame getMinigame() {
		return minigame;
	}

	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
