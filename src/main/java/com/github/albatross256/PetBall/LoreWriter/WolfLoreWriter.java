package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;

public class WolfLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		String owner = ((Wolf)entity).getOwner() == null ? "なし" : ((Wolf)entity).getOwner().getName();

		lore.add("オオカミ");
		lore.add(getHealthMeter(((Wolf)entity).getHealth(), ((Wolf)entity).getMaxHealth()));
		lore.add("飼い主: " + owner);
		return lore;
	}
}
