package uk.co.harieo.minigames.commands;

import java.util.HashSet;
import java.util.Set;

public class CommandUtils {

	/**
	 * Takes an array of strings and concatenates them into a single, trimmed String with a space between each String in
	 * the array. For example, {"green", "team"} will be concatenated to "green team".
	 *
	 * @param arguments to be separated and concatenated
	 * @param startingIndex the index to start at in the array
	 * @param range the point to stop looping through the array. The loop will run such that (startingIndex < range).
	 * @return the single, trimmed string from the starting and ending index in the specified array
	 */
	public static String concatenateArguments(String[] arguments, int startingIndex, int range) {
		StringBuilder builder = new StringBuilder();
		for (int i = startingIndex; i < range && i < arguments.length; i++) {
			builder.append(arguments[i]);
			builder.append(" ");
		}
		return builder.toString().trim(); // Trim to remove trailing space
	}

	/**
	 * An overload of {@link #concatenateArguments(String[], int, int)} where the range is to the end of the array
	 *
	 * @param arguments to be concatenated
	 * @param startingIndex the index to start at in the array
	 * @return the concatenated string from the starting index to the end of the array
	 */
	public static String concatenateArguments(String[] arguments, int startingIndex) {
		return concatenateArguments(arguments, startingIndex, arguments.length);
	}

	/**
	 * Parses a set of {@link Subcommand} and returns any instances where the {@param subCommandLabel} matches an alias
	 * in {@link Subcommand#getSubcommandAliases()} ignoring case
	 *
	 * @param subCommandLabel to be matched against the {@link Subcommand} aliases
	 * @param options the set of {@link Subcommand} to find matches in
	 * @return a new set of the matching instances found in the available options
	 */
	public static Set<Subcommand> matchSubcommands(String subCommandLabel, Set<Subcommand> options) {
		Set<Subcommand> matches = new HashSet<>();
		for (Subcommand subcommand : options) {
			for (String alias : subcommand.getSubcommandAliases()) {
				if (alias.equalsIgnoreCase(subCommandLabel)) {
					matches.add(subcommand);
				}
			}
		}
		return matches;
	}

}
