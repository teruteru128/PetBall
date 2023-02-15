package com.github.albatross256.PetBall.LoreWriter;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Frog;

import java.util.ArrayList;
import java.util.List;

public class FrogLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		lore.add("カエル");
		lore.add(getHealthMeter(((Frog)entity).getHealth(), ((Frog)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		return lore;
	}
}
