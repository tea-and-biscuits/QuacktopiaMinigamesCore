package uk.co.harieo.minigames.teams;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Spawns {

	private final List<Location> spawnLocations = new ArrayList<>();
	private int lastIndex = 0;

	/**
	 * Adds a location to the list of possible spawn points
	 *
	 * @param location to be added
	 */
	public void addSpawn(Location location) {
		spawnLocations.add(location);
	}

	/**
	 * Removes a location from the list of possible spawn points
	 *
	 * @param location to be removed
	 */
	public void removeSpawn(Location location) {
		spawnLocations.remove(location);
	}

	/**
	 * @return a list of all possible spawns
	 */
	public List<Location> getAllSpawns() {
		return spawnLocations;
	}

	/**
	 * @return whether there are no spawns in this handler
	 */
	public boolean isEmpty() {
		return getAllSpawns().isEmpty();
	}

	/**
	 * Retrieves the next spawn in the list or the first if the end of the list has been reached
	 *
	 * @return the next spawn in the list, if applicable
	 */
	public Location getNextSpawn() {
		int locationListSize = spawnLocations.size();

		int nextIndex = lastIndex + 1;
		if (nextIndex >= locationListSize) {
			nextIndex = 0;
		}

		if (nextIndex < locationListSize) {
			lastIndex = nextIndex;
			return spawnLocations.get(nextIndex);
		} else {
			return null;
		}
	}

}
