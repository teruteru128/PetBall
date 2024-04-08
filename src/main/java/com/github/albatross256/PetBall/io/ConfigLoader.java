package com.github.albatross256.PetBall.io;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigLoader {

	FileConfiguration config;

	public ConfigLoader(Plugin plugin) {
		plugin.saveDefaultConfig();
		this.config = plugin.getConfig();
	}

	public List<String> getUnsableWorldNames(){
		return this.config.getStringList("UnusableWorldNames");
	}

	public String getLogLevel() { return this.config.getString("log-level"); }
}
