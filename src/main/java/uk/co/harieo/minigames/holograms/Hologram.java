package uk.co.harieo.minigames.holograms;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.*;

/**
 * A class which handles spawning invisible named armour stands (holograms)
 */
public class Hologram {

	private final List<String> lines = new ArrayList<>();
	private final List<ArmorStand> stands = new ArrayList<>();
	private Location location;

	/**
	 * @return the location which represents the X and Z coordinates of where this hologram will spawn. The Y axis is
	 * off-set from the base location due to an armour stand being a ~2 block high entity
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Sets the location that the armour stand will spawn at
	 *
	 * @param location which the armour stand should spawn at, not compensating for the Y axis height
	 * @return this instance
	 */
	public Hologram setLocation(Location location) {
		this.location = location.clone();
		return this;
	}

	/**
	 * @return the lines of text to be displayed
	 */
	public List<String> getLines() {
		return lines;
	}

	/**
	 * Adds a line of text to be displayed to the list
	 *
	 * @param line of text to be displayed
	 */
	public void addLine(String line) {
		lines.add(line);
	}

	/**
	 * Removes the line in the list at the specified index, if applicable
	 *
	 * @param index to remove the line at in the list
	 */
	public void removeLine(int index) {
		if (index < lines.size()) {
			lines.remove(index);
		}
	}

	/**
	 * Sets the line in the list at the specified index, if applicable
	 *
	 * @param index of the line in the list to be replaced
	 * @param line to set at the specified index
	 */
	public void setLine(int index, String line) {
		if (index < lines.size()) {
			lines.set(index, line);
		}
	}

	/**
	 * Spawns the armour stand entities and formats them based on displayed lines. This method requires that the
	 * location has been set and there is at least 1 line to display.
	 */
	public void updateLines() {
		if (!lines.isEmpty() && location != null) {
			int lineCount = lines.size();
			for (int i = 0; i < lineCount; i++) {
				String line = ChatColor.translateAlternateColorCodes('&', lines.get(i));
				double yAxisOffset = (0.25 * (lineCount - i)) - 1;

				if (i >= stands.size()) { // This armor stand hasn't been spawned yet
					ArmorStand newStand = (ArmorStand) Objects.requireNonNull(location.getWorld())
							.spawnEntity(location.clone().add(0, yAxisOffset, 0), EntityType.ARMOR_STAND);
					newStand.setGravity(false);
					newStand.setVisible(false);
					newStand.setCustomNameVisible(true);
					newStand.setInvulnerable(true);
					newStand.setCustomName(line);
					stands.add(newStand);
				} else {
					ArmorStand stand = stands.get(i);
					stand.setCustomName(line);
					stand.teleport(location.clone().add(0, yAxisOffset, 0));
				}
			}
		}
	}

	/**
	 * Removes all spawned armour stands, effectively deleting any displayed holograms
	 */
	private void clearHologram() {
		for (ArmorStand stand : stands) {
			stand.remove();
		}
		stands.clear();
	}

}
