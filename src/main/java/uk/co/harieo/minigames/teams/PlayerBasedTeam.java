package uk.co.harieo.minigames.teams;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerBasedTeam implements Team {

	private final String teamName;
	private final ColourGroup teamColour;
	private final Spawns spawns = new Spawns();

	private final Set<UUID> teamMembers = new HashSet<>();

	/**
	 * An implementation of {@link Team} which accepts {@link Player} on top of UUIDs for easier handling when players
	 * are online and readily available
	 *
	 * @param teamName of the team
	 * @param colourGroup the colour which represents this team
	 */
	public PlayerBasedTeam(String teamName, ColourGroup colourGroup) {
		this.teamName = teamName;
		this.teamColour = colourGroup;
	}

	/**
	 * @return the unformatted name of this team
	 */
	@Override
	public String getName() {
		return teamName;
	}

	@Override
	public ColourGroup getColour() {
		return teamColour;
	}

	/**
	 * Checks whether the specified player is a member of this team
	 *
	 * @param player to check membership of
	 * @return whether the given player is a member of this team
	 */
	public boolean isMember(Player player) {
		return isMember(player.getUniqueId());
	}

	@Override
	public boolean isMember(UUID uuid) {
		return teamMembers.contains(uuid);
	}

	/**
	 * Adds the specified player to the set of team members
	 *
	 * @param player to be added
	 */
	public void addMember(Player player) {
		addMember(player.getUniqueId());
	}

	@Override
	public void addMember(UUID uuid) {
		teamMembers.add(uuid);
	}

	/**
	 * Removes the specified player from the set of team members, if they are on it
	 *
	 * @param player to be removed from the team
	 */
	public void removeMember(Player player) {
		removeMember(player.getUniqueId());
	}

	@Override
	public void removeMember(UUID uuid) {
		teamMembers.remove(uuid);
	}

	/**
	 * @return the UUIDs of all team members
	 */
	@Override
	public Collection<UUID> getMembers() {
		return teamMembers;
	}

	/**
	 * @return a list of members which are online, excluding members which cannot be found
	 */
	public Set<Player> getOnlineMembers() {
		Set<Player> players = new HashSet<>();
		for (UUID uuid : getMembers()) {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null) { // If is online
				players.add(player);
			}
		}
		return players;
	}

	/**
	 * @return the amount of members in this team, regardless of whether they are online or not
	 */
	@Override
	public int getTeamSize() {
		return teamMembers.size();
	}

	@Override
	public Spawns getSpawns() {
		return spawns;
	}

}
