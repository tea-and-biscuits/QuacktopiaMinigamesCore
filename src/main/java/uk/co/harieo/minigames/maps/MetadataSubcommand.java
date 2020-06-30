package uk.co.harieo.minigames.maps;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Set;
import uk.co.harieo.minigames.commands.Subcommand;

public class MetadataSubcommand implements Subcommand {

	private final String prefix;

	/**
	 * An instance of {@link Subcommand} which handles generic metadata setters for {@link MapImpl}
	 *
	 * @param messagePrefix to be put before messages
	 */
	public MetadataSubcommand(String messagePrefix) {
		this.prefix = messagePrefix;
	}

	@Override
	public Set<String> getSubcommandAliases() {
		return Sets.newHashSet("author", "setname", "commit", "load");
	}

	@Override
	public String getUsage() {
		return "";
	}

	@Override
	public String getRequiredPermission() {
		return null;
	}

	@Override
	public void onSubcommand(CommandSender sender, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			switch (commandLabel.toLowerCase()) {
				case "author":
					author(player, args);
					break;
				case "setname":
					setName(player, args);
					break;
				case "commit":
					commit(player);
					break;
				case "load":
					load(player);
					break;
				default:
					throw new IllegalArgumentException("Sub-command request with unrecognised alias");
			}
		} else {
			sender.sendMessage("This command is location-based, you cannot use it!");
		}
	}

	/**
	 * The 'author' sub-command which lets a player display, add or remove authors from their current world map
	 *
	 * @param player who has issued the command
	 * @param args supplied with the command
	 */
	private void author(Player player, String[] args) {
		MapImpl map = MapImpl.get(player.getWorld());
		if (args.length < 3) {
			StringBuilder authorsBuilder = new StringBuilder();
			List<String> authors = map.getAuthors();
			if (authors.isEmpty()) {
				authorsBuilder.append(ChatColor.RED);
				authorsBuilder.append("None");
			} else {
				for (int i = 0; i < authors.size(); i++) {
					authorsBuilder.append(authors.get(i));
					if (i + 1 < authors.size()) {
						authorsBuilder.append(", ");
					}
				}
			}
			player.sendMessage("Current Authors: " + authorsBuilder.toString());
		} else {
			String addRemove = args[1];
			boolean add;
			if (addRemove.equalsIgnoreCase("add")) {
				add = true;
			} else if (addRemove.equalsIgnoreCase("remove")) {
				add = false;
			} else {
				player.sendMessage(formatMessage(ChatColor.RED + "Neither add nor remove was specified: " + addRemove));
				return;
			}

			String username = args[2];
			if (add) {
				map.addAuthor(username);
				player.sendMessage(formatMessage(
						ChatColor.GREEN + "Added " + ChatColor.GRAY + "the user " + ChatColor.YELLOW + username
								+ ChatColor.GRAY + " as a map author"));
			} else {
				if (map.getAuthors().contains(username)) {
					map.removeAuthor(username);
					player.sendMessage(formatMessage(
							ChatColor.RED + "Removed " + ChatColor.GRAY + " the user " + ChatColor.YELLOW + username
									+ ChatColor.GRAY + " from being a map author"));
				} else {
					player.sendMessage(formatMessage(ChatColor.RED
							+ "That user isn't currently an author, did you capitalize the name properly?"));
				}
			}
		}
	}

	/**
	 * The 'setname' command which allows a player to set a map's name
	 *
	 * @param player which has issued the command
	 * @param args supplied with the command
	 */
	private void setName(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage(formatMessage(ChatColor.RED + "Insufficient Arguments. Expected: /maps setname <name>"));
		} else {
			StringBuilder nameBuilder = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				nameBuilder.append(args[i]);
				nameBuilder.append(" ");
			}
			String name = nameBuilder.toString();

			MapImpl map = MapImpl.get(player.getWorld());
			map.setFullName(name);
			player.sendMessage(formatMessage(ChatColor.GRAY + "Set the world name to " + ChatColor.GREEN + name));
		}
	}

	/**
	 * Commits this map to a a file with {@link MapImpl#commitToFile()} and catches any known errors, reporting them to
	 * the player
	 *
	 * @param player who has issued the command
	 */
	private void commit(Player player) {
		MapImpl map = MapImpl.get(player.getWorld());
		if (map.isValid()) {
			try {
				boolean success = map.commitToFile();
				if (success) {
					player.sendMessage(formatMessage(
							ChatColor.GRAY + "The world has been " + ChatColor.GREEN + "successfully committed "
									+ ChatColor.GRAY + "to storage as an Protect The Egg game map!"));
				} else {
					player.sendMessage(formatMessage(
							ChatColor.RED + "An unexpected error occurred creating the data file!"));
				}
			} catch (FileAlreadyExistsException e) {
				e.printStackTrace();
				player.sendMessage(
						ChatColor.RED + "An internal storage error occurred: Unable to overwrite existing file");
			}
		} else {
			player.sendMessage(formatMessage(
					ChatColor.RED + "Your world is not a valid game map, consult '/maps info' for more information"));
		}
	}

	/**
	 * Parses the world a player is in for a {@link MapImpl} which loads all its metadata, if it exists
	 *
	 * @param player who has issued the command
	 */
	private void load(Player player) {
		try {
			MapImpl map = MapImpl.parseWorld(player.getWorld());
			if (map != null) {
				player.sendMessage(formatMessage(
						ChatColor.GRAY + "Loaded " + ChatColor.GREEN + map.getFullName() + ChatColor.GRAY
								+ " successfully!"));
			} else {
				player.sendMessage(formatMessage(ChatColor.RED + "Failed to load world due to an unknown error!"));
			}
		} catch (IOException e) {
			player.sendMessage(formatMessage(ChatColor.RED + "An error occurred loading the world"));
		}
	}

	/**
	 * Adds a prefix to a message then returns it
	 *
	 * @param message to put after the prefix
	 * @return the formatted message
	 */
	private String formatMessage(String message) {
		return prefix + message;
	}

}
