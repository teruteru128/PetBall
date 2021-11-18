package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;

public class HorseLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		Horse horse = (Horse)entity;
		String owner = horse.getOwner() == null ? "なし" : horse.getOwner().getName();
		List<String> lore = new ArrayList<String>();
		lore.add("馬");
		lore.add(getHealthMeter(((Horse)entity).getHealth(), ((Horse)entity).getMaxHealth()));
		lore.add("飼い主: " + owner);
		return lore;
	}
}
