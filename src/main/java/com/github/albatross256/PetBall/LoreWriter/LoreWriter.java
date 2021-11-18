package com.github.albatross256.PetBall.LoreWriter;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;

public abstract class LoreWriter {

	public abstract List<String> generateLore(Entity entity);

	protected String getHealthMeter(double currentHealth, double maxHealth) {
		double rate = currentHealth * 20  /  maxHealth;

		String healthMeter = ChatColor.GREEN.toString() + "";

		int i = 0;
		for(; i < rate; i ++) {
			healthMeter = healthMeter + ":";
		}
		healthMeter = healthMeter + ChatColor.GRAY.toString() + "";
		for(; i < 20; i++) {
			healthMeter = healthMeter + ":";
		}
		healthMeter = healthMeter + ChatColor.DARK_PURPLE.toString();

		return "体力 : [" + healthMeter + "]  (" + String.valueOf(currentHealth) + " / " + String.valueOf(maxHealth) + ")";
	}


}
