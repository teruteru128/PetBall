package com.github.albatross256.PetBall;

import java.util.ArrayList;
import java.util.List;

import com.github.albatross256.PetBall.io.ConfigLoader;

import com.twitter.teruteru128.logger.Logger;
import org.bukkit.plugin.Plugin;

public class WorldManager {

	private Logger logger;
	List<String> unusableWorldNames;

	public WorldManager(Plugin plugin, Logger logger) {
		logger.debug("BallRecipeManager:Start");
		this.logger = logger;
		this.unusableWorldNames = new ArrayList<String>();
		init(plugin);
		logger.debug("BallRecipeManager:End");
	}

	private void init(Plugin plugin) {
		logger.debug("BallRecipeManager.init:Start");
		List<String> worldNames = new ConfigLoader(plugin).getUnsableWorldNames();

		for(String worldName : worldNames) {
			logger.trace("worldName:" + worldName);
			registerUnusableWorldName(worldName);
		}
		logger.debug("BallRecipeManager.init:End");
	}

	public void registerUnusableWorldName(String worldName) {
		this.unusableWorldNames.add(worldName);
	}

	public boolean isUsableWorld(String worldName) {
		return !this.unusableWorldNames.contains(worldName);
	}
}
