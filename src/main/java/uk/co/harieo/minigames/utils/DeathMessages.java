package uk.co.harieo.minigames.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.Validate;

/**
 * A handler to change death messages to custom implementations
 */
public class DeathMessages implements Listener {

	private Map<DamageCause, String> deathMessages = new HashMap<>();
	private String defaultMessage =
			ChatColor.YELLOW + Placeholder.VICTIM.toString() + ChatColor.GRAY + " has been killed!";

	/**
	 * Creates a new handler and registers it as a {@link Listener}
	 *
	 * @param plugin to register the listener under
	 */
	public DeathMessages(JavaPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Sets the death message which will occur for the given cause of death
	 *
	 * @param cause when the message should be used
	 * @param message which should be set as the death message
	 */
	public void setDeathMessage(DamageCause cause, String message) {
		Validate.notNull(message, "Death message cannot be null, unset instead");
		if (deathMessages.containsKey(cause)) {
			deathMessages.replace(cause, message);
		} else {
			deathMessages.put(cause, message);
		}
	}

	/**
	 * Deletes a death message for the given cause, meaning it will likely revert to the default message
	 *
	 * @param cause to remove the death message for
	 */
	public void unsetDeathMessage(DamageCause cause) {
		deathMessages.remove(cause);
	}

	/**
	 * Sets the default message given in the event that no custom death message has been set
	 *
	 * @param message to set as default
	 */
	public void setDefaultMessage(String message) {
		Validate.notNull(message, "Default message cannot be null");
		this.defaultMessage = message;
	}

	/**
	 * Retrieves the death message for the given cause and replaces the known placeholders, returning the result. Note:
	 * this accounts for the possibility that the killer may be null in the event that the damage cause isn't entity
	 * related.
	 *
	 * If a message cannot be found, the default message will be used.
	 *
	 * @param cause to get the message for
	 * @param killer which killed the victim
	 * @param victim which died
	 * @return the formatted string
	 */
	private String getAndFormat(DamageCause cause, Player killer, Player victim) {
		String unformatted = deathMessages.getOrDefault(cause, defaultMessage);
		unformatted = unformatted.replaceAll(Placeholder.VICTIM.toString(), victim.getName());
		if (killer != null) { // It is possible they were killed by something else
			unformatted = unformatted.replaceAll(Placeholder.KILLER.toString(), killer.getName());
		}
		return unformatted;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		EntityDamageEvent damageEvent = player.getLastDamageCause();
		if (damageEvent == null) {
			return;
		}

		Player damager = null;
		if (damageEvent instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent entityDamageEntityEvent = (EntityDamageByEntityEvent) damageEvent;
			if (entityDamageEntityEvent.getDamager() instanceof Player) {
				damager = (Player) entityDamageEntityEvent.getDamager();
			}
		}

		Bukkit.broadcastMessage(getAndFormat(damageEvent.getCause(), damager, player));
		event.setDeathMessage(null);
	}

	public enum Placeholder {
		KILLER("killer"),
		VICTIM("victim");

		private String value;

		Placeholder(String value) {
			this.value = "%" + value + "%";
		}


		@Override
		public String toString() {
			return value;
		}
	}

}
