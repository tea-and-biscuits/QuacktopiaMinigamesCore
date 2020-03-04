package uk.co.harieo.minigames.holograms;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Hologram {

	private Location location;
	private List<String> lines;
	private Map<Integer, ArmorStand> stands = new HashMap<>();

	public Hologram(Location location, List<String> lines) {
		this.location = location;
		this.lines = lines;
	}

	public Location getLocation() {
		return location;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public void updateLines() {
		for (int i = 0; i < lines.size(); i++) {
			String line = ChatColor.translateAlternateColorCodes('&', lines.get(i));
			double yAxisOffset = (0.25 * i) - 1;
			if (stands.containsKey(i)) {
				ArmorStand stand = stands.get(i);
				stand.setCustomName(line);
				stand.teleport(location.clone().add(0, yAxisOffset, 0));
			} else if (!line.isEmpty()) { // No point spawning an entity that will have no purpose
				ArmorStand newStand = (ArmorStand) Objects.requireNonNull(location.getWorld())
						.spawnEntity(location.clone().add(0, yAxisOffset, 0), EntityType.ARMOR_STAND);
				newStand.setGravity(false);
				newStand.setVisible(false);
				newStand.setCustomNameVisible(true);
				newStand.setInvulnerable(true);
				newStand.setCustomName(line);
				stands.put(i, newStand);
			}
		}
	}

	void destroyHologram() {
		for (ArmorStand stand : stands.values()) {
			stand.remove();
		}

		lines.clear();
		stands.clear();
	}

}
