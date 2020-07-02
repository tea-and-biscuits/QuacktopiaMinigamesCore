package uk.co.harieo.minigames.teams;

import org.bukkit.Color;

import java.util.Collection;
import java.util.UUID;
import net.md_5.bungee.api.ChatColor;

public interface Team {

	/**
	 * @return the unformatted name of this team
	 */
	String getName();

	/**
	 * @return the chat color for this team
	 */
	ChatColor getChatColor();

	/**
	 * @return a coloured version of {@link #getName()} with the word 'Team' on the end, on the assumption that this
	 * word isn't included in the name itself
	 */
	default String getFormattedName() {
		return getChatColor() + getName() + " Team";
	}

	/**
	 * @return the armor color for this team
	 */
	Color getArmorColor();

	/**
	 * Checks whether the specified player is a member of this team
	 *
	 * @param uuid of the player to check the membership of
	 * @return whether the uuid is from a member of this team
	 */
	boolean isMember(UUID uuid);

	/**
	 * Adds the specified player to the list of team members
	 *
	 * @param uuid of the player to add
	 */
	void addMember(UUID uuid);

	/**
	 * Removes the specified player from the list of team members, if they are on it
	 *
	 * @param uuid of the player to remove
	 */
	void removeMember(UUID uuid);

	/**
	 * @return the UUIDs of all team members
	 */
	Collection<UUID> getMembers();

	/**
	 * @return the amount of members in this team
	 */
	int getTeamSize();

	/**
	 * @return the spawn location handler for this team's spawn points
	 */
	Spawns getSpawns();

}
