package uk.co.harieo.minigames.teams;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.md_5.bungee.api.ChatColor;

public class Team {

	private String teamName;
	private ChatColor chatColor;
	private Color armorColor;
	private int maxMembers;

	private List<UUID> teamMembers = new ArrayList<>();

	/**
	 * A team of players grouped with a given team name and colour
	 *
	 * @param teamName of the team
	 * @param teamColor to color team text
	 * @param armorColor to color team armour
	 */
	public Team(String teamName, ChatColor teamColor, Color armorColor, int maxMembers) {
		this.teamName = teamName;
		this.chatColor = teamColor;
		this.armorColor = armorColor;
		this.maxMembers = maxMembers;
	}

	/**
	 * @return the unformatted name of this team
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @return the chat color for this team
	 */
	public ChatColor getChatColor() {
		return chatColor;
	}

	/**
	 * @return the armor color for this team
	 */
	public Color getArmorColor() {
		return armorColor;
	}

	/**
	 * @return the maximum amount of members this team can have
	 */
	public int getMaxMembers() {
		return maxMembers;
	}

	/**
	 * Checks whether the specified player is a member of this team
	 *
	 * @param player to check membership of
	 * @return whether the given player is a member of this team
	 */
	public boolean isTeamMember(Player player) {
		return teamMembers.contains(player.getUniqueId());
	}

	/**
	 * Adds the specified player to the list of team members
	 *
	 * @param player to be added
	 */
	public void addTeamMember(Player player) {
		teamMembers.add(player.getUniqueId());
	}

	/**
	 * Removes the specified player from the list of team members, if they are on it
	 *
	 * @param player to be removed from the team
	 */
	public void removeTeamMember(Player player) {
		teamMembers.remove(player.getUniqueId());
	}

	/**
	 * @return the UUIDs of all team members
	 */
	public List<UUID> getTeamMembers() {
		return teamMembers;
	}

	/**
	 * @return a list of members which are online, excluding members which cannot be found
	 */
	public List<Player> getOnlineTeamMembers() {
		List<Player> players = new ArrayList<>();
		for (UUID uuid : getTeamMembers()) {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null) { // If is online
				players.add(player);
			}
		}
		return players;
	}

	/**
	 * @return the amount of players in this team currently
	 */
	public int countMembers() {
		return teamMembers.size();
	}

	/**
	 * @return whether the amount of members equals or exceeds the maximum allowed
	 */
	public boolean isFull() {
		return countMembers() >= getMaxMembers();
	}

}
