package uk.co.harieo.minigames.teams;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class TeamHandler<T extends Team> {

	private final Map<UUID, T> playerTeams = new HashMap<>(); // Saves time scanning through allTeams and member lists
	private final Collection<T> allTeams;
	private final int playersPerTeam;

	/**
	 * A handler which more efficiently stores what {@link PlayerBasedTeam} a {@link Player} is in and handles assigning
	 * that player a team where needed. This class uses {@link UUID} for representing a Player so that offline players
	 * are supported.
	 *
	 * @param teams all the possible teams a player can join
	 * @param playersPerTeam the maximum amount of players that can join a team
	 */
	public TeamHandler(Collection<T> teams, int playersPerTeam) {
		this.allTeams = teams;
		this.playersPerTeam = playersPerTeam;
	}

	/**
	 * Retrieves the team that the specified player is in, assuming it was assigned with this handler
	 *
	 * @param player to get the team of
	 * @return the player's team or null if none was found
	 */
	public T getTeam(Player player) {
		return playerTeams.get(player.getUniqueId());
	}

	/**
	 * Assigns a player to a team based on a predicate. The predicate can be null, in which case it will be ignored.
	 *
	 * @param player to assign a team to
	 * @return the assigned team or null if none could be assigned
	 */
	public T assignTeam(Player player, Predicate<T> predicate) {
		boolean usePredicate = predicate != null;
		for (T team : allTeams) {
			if ((!usePredicate || predicate.test(team)) && team.getTeamSize() < playersPerTeam) {
				setTeam(player, team);
				return team;
			}
		}

		return null;
	}

	/**
	 * An overload of {@link #assignTeam(Player, Predicate)} where predicate is null, meaning it will be ignored
	 *
	 * @param player to assign a team to
	 * @return the assigned team or null if none could be assigned
	 */
	public T assignTeam(Player player) {
		return assignTeam(player, null);
	}

	/**
	 * Sets a player's team
	 *
	 * @param player to have their team set
	 * @param team to set the player's team to
	 */
	public void setTeam(Player player, T team) {
		unsetTeam(player);
		playerTeams.put(player.getUniqueId(), team);
		team.addMember(player.getUniqueId());
	}

	/**
	 * Removes the specified player from their team and this cache's handler, assuming it was assigned by this handler
	 * in the first place
	 *
	 * @param player to have their team removed
	 */
	public void unsetTeam(Player player) {
		UUID uuid = player.getUniqueId();
		if (playerTeams.containsKey(uuid)) {
			playerTeams.get(uuid).removeMember(player.getUniqueId());
			playerTeams.remove(uuid);
		}
	}

}
