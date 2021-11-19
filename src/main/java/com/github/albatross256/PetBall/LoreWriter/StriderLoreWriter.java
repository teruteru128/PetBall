package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Strider;

public class StriderLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		String age =  ((Strider)entity).isAdult() ? "大人" : "子供";
		lore.add("ストライダー");
		lore.add(getHealthMeter(((Strider)entity).getHealth(), ((Strider)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		lore.add(age);
		return lore;
	}
}
