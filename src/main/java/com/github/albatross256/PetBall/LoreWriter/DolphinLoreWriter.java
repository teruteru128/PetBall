package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Dolphin;
import org.bukkit.entity.Entity;

public class DolphinLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		lore.add("イルカ");
		lore.add(getHealthMeter(((Dolphin)entity).getHealth(), ((Dolphin)entity).getMaxHealth()));
		return lore;
	}
}
