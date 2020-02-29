package uk.co.harieo.minigames.games;

public enum GameStage {

	STARTING("Turning the Server On"),
	LOBBY("In Lobby", true),
	PRE_GAME("Starting the Game"),
	IN_GAME("In Game"),
	POST_GAME("Game is Ending"),
	ENDING("Game is Over"),
	ERROR("Unavailable");

	private String description;
	private boolean isAcceptingPlayers;

	GameStage(String description, boolean isAcceptingPlayers) {
		this.description = description;
		this.isAcceptingPlayers = isAcceptingPlayers;
	}

	GameStage(String description) {
		this(description, false);
	}

	public String getDescription() {
		return description;
	}

	public boolean isAcceptingPlayers() {
		return isAcceptingPlayers;
	}

}
