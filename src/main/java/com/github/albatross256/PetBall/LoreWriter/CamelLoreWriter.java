package com.github.albatross256.PetBall.LoreWriter;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Camel;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class CamelLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		lore.add("ラクダ");
		lore.add(getHealthMeter(((Camel)entity).getHealth(), ((Camel)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		return lore;
	}
}
