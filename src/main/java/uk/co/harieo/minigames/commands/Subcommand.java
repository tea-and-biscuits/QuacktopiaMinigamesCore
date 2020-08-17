package uk.co.harieo.minigames.commands;

import org.bukkit.command.CommandSender;

import java.util.Set;

public interface Subcommand {

	Set<String> getSubcommandAliases();

	String getUsage();

	String getRequiredPermission();

	void onSubcommand(CommandSender sender, String commandLabel, String[] args);

}
