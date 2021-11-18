package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.PufferFish;

public class PufferFishLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		lore.add("フグ");
		lore.add(getHealthMeter(((PufferFish)entity).getHealth(), ((PufferFish)entity).getMaxHealth()));
		return lore;
	}
}
