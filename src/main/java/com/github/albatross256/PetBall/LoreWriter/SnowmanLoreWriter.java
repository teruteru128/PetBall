package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Snowman;

public class SnowmanLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		lore.add("スノーゴーレム");
		lore.add(getHealthMeter(((Snowman)entity).getHealth(), ((Snowman)entity).getMaxHealth()));
		return lore;
	}
}
