package uk.co.harieo.minigames.hotbar;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import uk.co.harieo.minigames.menus.MenuItem;

public class Hotbar {

	private final MenuItem[] hotbarItems = new MenuItem[9];

	/**
	 * Retrieves the item which corresponds to the specified slot
	 *
	 * @param slot to get the item for
	 * @return the item which goes in the specified slot, if any
	 */
	public MenuItem getItem(int slot) {
		if (slot < hotbarItems.length) {
			return hotbarItems[slot];
		} else {
			return null;
		}
	}

	/**
	 * Sets the item which corresponds to a slot
	 *
	 * @param slot to put the item in
	 * @param item to put in the slot
	 */
	public void setItem(int slot, MenuItem item) {
		hotbarItems[slot] = item;
	}

	/**
	 * @return a map of the slots and their corresponding items
	 */
	public MenuItem[] getItems() {
		return hotbarItems;
	}

	/**
	 * Sets the items in their corresponding slots in a player's inventory
	 *
	 * @param player to set the items of
	 */
	public void setItems(Player player) {
		PlayerInventory inventory = player.getInventory();
		for (int i = 0; i < hotbarItems.length; i++) {
			MenuItem menuItem = hotbarItems[i];

			ItemStack toSet = null; // Allows the slot to be emptied if no item goes there
			if (menuItem != null) {
				toSet = menuItem.getItem();
			}

			inventory.setItem(i, toSet);
		}
	}

}
