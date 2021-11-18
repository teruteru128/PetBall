package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.TraderLlama;

public class TraderLlamaLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		lore.add("行商人のラマ");
		lore.add(getHealthMeter(((TraderLlama)entity).getHealth(), ((TraderLlama)entity).getMaxHealth()));
		return lore;
	}
}
