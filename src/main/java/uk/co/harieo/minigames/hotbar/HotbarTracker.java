package uk.co.harieo.minigames.hotbar;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import uk.co.harieo.minigames.menus.MenuItem;

/**
 * A listener which assigns and scans for interactions with hotbar items
 */
public class HotbarTracker implements Listener {

	// Stores items for different game stages
	private static final Map<Player, Hotbar> currentHotbars = new HashMap<>();

	/**
	 * Checks if a clicked item is a known {@link MenuItem} from a {@link Hotbar} and executes {@link
	 * MenuItem#onClick(Player)} if it is
	 */
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		HumanEntity entity = event.getWhoClicked();
		Inventory inventory = event.getClickedInventory();
		if (entity instanceof Player && inventory != null && inventory.getType() == InventoryType.PLAYER) {
			Player player = (Player) entity;
			if (currentHotbars.containsKey(player)) {
				event.setCancelled(true);
				MenuItem menuItem = currentHotbars.get(player).getItem(event.getSlot());
				if (menuItem != null) {
					menuItem.onClick(player);
				}
			}
		}
	}

	/**
	 * Checks if the item a player is interacting with is a known {@link MenuItem} from a {@link Hotbar} and executes
	 * {@link MenuItem#onClick(Player)} if it is
	 */
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (currentHotbars.containsKey(player)) {
			MenuItem menuItem = currentHotbars.get(player)
					.getItem(player.getInventory().getHeldItemSlot());
			if (menuItem != null) {
				event.setCancelled(true);
				menuItem.onClick(player);
			}
		}
	}

	@EventHandler
	public void onHotbarSwitch(PlayerSwapHandItemsEvent event) {
		if (currentHotbars.containsKey(event.getPlayer())) {
			event.setCancelled(true); // Prevent moving items if a hotbar is active
		}
	}

	/**
	 * Sets a player's hotbar
	 *
	 * @param player to set the hotbar of
	 * @param hotbar which contains the items to put in the hotbar
	 */
	public void setHotbar(Player player, Hotbar hotbar) {
		hotbar.setItems(player);
		currentHotbars.put(player, hotbar);
	}

	/**
	 * Clears a player's inventory and saved hotbar
	 *
	 * @param player to clear the hotbar of
	 */
	public void removeHotbar(Player player) {
		player.getInventory().clear();
		currentHotbars.remove(player);
	}

	/**
	 * Empties the inventory of all players with registered hotbars then clears the register
	 */
	public void clearHotbars() {
		for (Player player : currentHotbars.keySet()) {
			player.getInventory().clear();
		}
		currentHotbars.clear();
	}

}
