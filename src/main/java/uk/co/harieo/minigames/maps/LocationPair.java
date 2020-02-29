package uk.co.harieo.minigames.maps;

import org.bukkit.Location;

public class LocationPair {

	private Location location;
	private String key;
	private String value;

	public LocationPair(Location location, String key, String value) {
		this.location = location;
		this.key = key;
		this.value = value;
	}

	public Location getLocation() {
		return location;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean compareLocations(Location toCompare) {
		return location.getBlockX() == toCompare.getBlockX() && location.getBlockY() == toCompare.getBlockY()
				&& location.getBlockZ() == toCompare.getBlockZ();
	}

}
