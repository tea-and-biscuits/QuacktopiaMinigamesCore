package uk.co.harieo.minigames.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

/**
 * An item which holds data to be converted into {@link ItemStack} for interacting with an {@link org.bukkit.inventory.Inventory}
 *
 * @author Harieo
 */
public class MenuItem {

	private final ItemStack item;
	private Consumer<Player> onClick;

	/**
	 * Creates an {@link ItemStack} with {@link Material}, amount and byte damage
	 *
	 * @param material to set for the item
	 * @param amount of the item
	 * @param damage to set for the item
	 */
	public MenuItem(Material material, int amount, int damage) {
		this(new ItemStack(material, amount));
		setDamageData(this.item, damage);
	}

	/**
	 * Creates an {@link ItemStack} with the {@link Material} and amount
	 *
	 * @param material to set for the item
	 * @param amount of the item
	 */
	public MenuItem(Material material, int amount) {
		this(new ItemStack(material, amount));
	}

	/**
	 * Creates an {@link ItemStack} with just the {@link Material}
	 *
	 * @param material to set for the item
	 */
	public MenuItem(Material material) {
		this(new ItemStack(material));
	}

	/**
	 * Sets the {@link ItemStack} directly, assuming values have already been inserted into it
	 *
	 * @param item to set
	 */
	public MenuItem(ItemStack item) {
		this.item = item;
	}

	/**
	 * Clones the instance of this class
	 *
	 * @param item to be cloned
	 */
	public MenuItem(MenuItem item) {
		this(item.getItem());
		this.onClick = item.onClick;
	}

	/**
	 * Sets 1.14 compatible damage data to an item using the non-deprecated method
	 *
	 * @param item to set the damage data for
	 * @param damage to set the damage to
	 */
	private void setDamageData(ItemStack item, int damage) {
		ItemMeta meta = getItemMeta(item);
		if (meta instanceof Damageable) {
			((Damageable) meta).setDamage(damage);
		}
		item.setItemMeta(meta);
	}

	/**
	 * Returns the present ItemMeta on an item or creates a new one for the specified item from the item factory. This
	 * is necessary due to a 1.14 update which makes {@link ItemStack#getItemMeta()} nullable.
	 *
	 * @param itemStack to get the metadata of
	 * @return a non-null instance of {@link ItemMeta}
	 */
	public ItemMeta getItemMeta(ItemStack itemStack) {
		return item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
	}

	/**
	 * Sets the item name
	 *
	 * @param name to set
	 */
	public MenuItem setName(String name) {
		metaUpdate(meta -> meta.setDisplayName(name));
		return this;
	}

	/**
	 * @return the name of this item
	 */
	public String getName() {
		return getItemMeta(item).getDisplayName();
	}

	/**
	 * Sets the lore
	 *
	 * @param lore to set
	 */
	public MenuItem setLore(List<String> lore) {
		metaUpdate(meta -> meta.setLore(lore));
		return this;
	}

	/**
	 * @return the lore of this item
	 */
	public List<String> getLore() {
		return getItemMeta(item).getLore();
	}

	/**
	 * Updates and sets the Item Meta for the item
	 *
	 * @param toEdit edits to the meta before re-set
	 */
	private void metaUpdate(Consumer<ItemMeta> toEdit) {
		ItemMeta meta = item.getItemMeta();
		toEdit.accept(meta);
		item.setItemMeta(meta);
	}

	/**
	 * Sets a Consumer to be called when this item is clicked by a Player
	 *
	 * @param onClick for when the item is clicked
	 */
	public MenuItem setOnClick(Consumer<Player> onClick) {
		this.onClick = onClick;
		return this;
	}

	/**
	 * Calls the onClick Consumer for this item, if one has been set
	 *
	 * @param player which has clicked the item
	 */
	public void onClick(Player player) {
		if (onClick != null) {
			onClick.accept(player);
		}
	}

	/**
	 * @return the Bukkit item which represents this instance
	 */
	public ItemStack getItem() {
		return item;
	}

}
