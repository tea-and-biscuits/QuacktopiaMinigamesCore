package uk.co.harieo.minigames.teams;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.*;
import net.md_5.bungee.api.ChatColor;

public class PlayerBasedTeam implements Team {

	private final String teamName;
	private final ChatColor chatColor;
	private final Color armorColor;
	private final Spawns spawns = new Spawns();

	private final Set<UUID> teamMembers = new HashSet<>();

	/**
	 * An implementation of {@link Team} which accepts {@link Player} on top of UUIDs for easier handling when players
	 * are online and readily available
	 *
	 * @param teamName of the team
	 * @param teamColor to color team text
	 * @param armorColor to color team armour
	 */
	public PlayerBasedTeam(String teamName, ChatColor teamColor, Color armorColor) {
		this.teamName = teamName;
		this.chatColor = teamColor;
		this.armorColor = armorColor;
	}

	/**
	 * @return the unformatted name of this team
	 */
	@Override
	public String getName() {
		return teamName;
	}

	/**
	 * @return the chat color for this team
	 */
	@Override
	public ChatColor getChatColor() {
		return chatColor;
	}

	/**
	 * @return the armor color for this team
	 */
	@Override
	public Color getArmorColor() {
		return armorColor;
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
