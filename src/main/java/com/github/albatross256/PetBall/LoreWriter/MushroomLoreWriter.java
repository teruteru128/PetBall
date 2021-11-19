package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.MushroomCow;

public class MushroomLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		lore.add("ムーシュルーム");
		lore.add(getHealthMeter(((MushroomCow)entity).getHealth(), ((MushroomCow)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		return lore;
	}
}
