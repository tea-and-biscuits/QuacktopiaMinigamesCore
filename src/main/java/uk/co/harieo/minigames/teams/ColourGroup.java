package uk.co.harieo.minigames.teams;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;

public enum ColourGroup {

	AQUA(ChatColor.AQUA, Color.AQUA, Material.LIGHT_BLUE_WOOL, Material.LIGHT_BLUE_STAINED_GLASS),
	BLACK(ChatColor.BLACK, Color.BLACK, Material.BLACK_WOOL, Material.BLACK_STAINED_GLASS),
	BLUE(ChatColor.BLUE, Color.BLUE, Material.BLUE_WOOL, Material.BLUE_STAINED_GLASS),
	DARK_AQUA(ChatColor.DARK_AQUA, Color.TEAL, Material.CYAN_WOOL, Material.CYAN_STAINED_GLASS),
	DARK_BLUE(ChatColor.DARK_BLUE, Color.BLUE, Material.BLUE_WOOL, Material.BLUE_STAINED_GLASS),
	DARK_GRAY(ChatColor.DARK_GRAY, Color.GRAY, Material.GRAY_WOOL, Material.GRAY_STAINED_GLASS),
	DARK_GREEN(ChatColor.DARK_GREEN, Color.GREEN, Material.GREEN_WOOL, Material.GREEN_STAINED_GLASS),
	DARK_PURPLE(ChatColor.DARK_PURPLE, Color.PURPLE, Material.PURPLE_WOOL, Material.PURPLE_STAINED_GLASS),
	DARK_RED(ChatColor.DARK_RED, Color.RED, Material.RED_WOOL, Material.RED_STAINED_GLASS),
	GOLD(ChatColor.GOLD, Color.ORANGE, Material.ORANGE_WOOL, Material.ORANGE_STAINED_GLASS),
	GRAY(ChatColor.GRAY, Color.GRAY, Material.LIGHT_GRAY_WOOL, Material.LIGHT_GRAY_STAINED_GLASS),
	GREEN(ChatColor.GREEN, Color.LIME, Material.LIME_WOOL, Material.LIME_STAINED_GLASS),
	LIGHT_PURPLE(ChatColor.LIGHT_PURPLE, Color.PURPLE, Material.PURPLE_WOOL, Material.PURPLE_STAINED_GLASS),
	RED(ChatColor.RED, Color.RED, Material.RED_WOOL, Material.RED_STAINED_GLASS),
	WHITE(ChatColor.WHITE, Color.WHITE, Material.WHITE_WOOL, Material.WHITE_STAINED_GLASS),
	YELLOW(ChatColor.YELLOW, Color.YELLOW, Material.YELLOW_WOOL, Material.YELLOW_STAINED_GLASS);

	private final ChatColor chatColor;
	private final Color equipmentColor;
	private final Material woolType;
	private final Material glassType;

	/**
	 * A group which represents a {@link ChatColor} primary key alongside all its coloured variations such as wool colours
	 * and glass colours
	 *
	 * @param chatColor which represents this colour as its primary key
	 * @param equipmentColor which shows this colour for leather equipment etc...
	 * @param woolType which shows this colour as a wool block
	 * @param glassType which shows this colour as a glass block
	 */
	ColourGroup(ChatColor chatColor, Color equipmentColor, Material woolType, Material glassType) {
		this.chatColor = chatColor;
		this.equipmentColor = equipmentColor;
		this.woolType = woolType;
		this.glassType = glassType;
	}

	/**
	 * @return the chat colour for this group
	 */
	public ChatColor getChatColor() {
		return chatColor;
	}

	/**
	 * @return the equipment colour for this group
	 */
	public Color getEquipmentColor() {
		return equipmentColor;
	}

	/**
	 * @return the solid glass material with the colour for this group
	 */
	public Material getGlassType() {
		return glassType;
	}

	/**
	 * @return the solid wool material with the colour for this group
	 */
	public Material getWoolType() {
		return woolType;
	}

}
