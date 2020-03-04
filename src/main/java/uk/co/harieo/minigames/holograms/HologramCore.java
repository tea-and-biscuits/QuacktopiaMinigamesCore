package uk.co.harieo.minigames.holograms;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;

public class HologramCore {

	private static final HologramCore INSTANCE = new HologramCore();

	private List<Hologram> holograms = new ArrayList<>();

	private HologramCore() {

	}

	public List<Hologram> getHolograms() {
		return ImmutableList.copyOf(holograms);
	}

	public void addHologram(Hologram hologram) {
		hologram.updateLines();
		holograms.add(hologram);
	}

	public void destroyHologram(Hologram hologram) {
		hologram.destroyHologram();
		holograms.remove(hologram);
	}

	public static HologramCore getInstance() {
		return INSTANCE;
	}

}
