package com.github.albatross256.PetBall.LoreWriter;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Goat;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class GoatLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		String age =  ((Goat)entity).isAdult() ? "大人" : "子供";
		lore.add("ヤギ");
		lore.add(getHealthMeter(((Goat)entity).getHealth(), ((Goat)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		lore.add(age);
		return lore;
	}
}
