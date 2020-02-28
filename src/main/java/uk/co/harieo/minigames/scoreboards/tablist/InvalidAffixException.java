package uk.co.harieo.minigames.scoreboards.tablist;

import uk.co.harieo.minigames.scoreboards.tablist.modules.Affix;

public class InvalidAffixException extends IllegalArgumentException {

	public InvalidAffixException(Affix invalidAffix, String error) {
		super(error + " (affix id: '" + invalidAffix.getUniqueId() + "')");
	}

}
