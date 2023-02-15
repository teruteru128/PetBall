package com.github.albatross256.PetBall;

import java.util.ArrayList;
import java.util.List;

import com.github.albatross256.PetBall.io.ConfigLoader;

import org.bukkit.plugin.Plugin;

public class WorldManager {

	List<String> unusableWorldNames;

	public WorldManager(Plugin plugin) {
		this.unusableWorldNames = new ArrayList<String>();
		init(plugin);
	}

	private void init(Plugin plugin) {
		List<String> worldNames = new ConfigLoader(plugin).getUnsableWorldNames();

		for(String worldName : worldNames) {
			registerUnusableWorldName(worldName);
		}
	}

	public void registerUnusableWorldName(String worldName) {
		this.unusableWorldNames.add(worldName);
	}

	public boolean isUsableWorld(String worldName) {
		return !this.unusableWorldNames.contains(worldName);
	}
}
