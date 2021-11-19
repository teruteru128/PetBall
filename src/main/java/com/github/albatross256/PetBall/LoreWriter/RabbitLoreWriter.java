package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Rabbit;

public class RabbitLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		String age =  ((Rabbit)entity).isAdult() ? "大人" : "子供";
		lore.add("ウサギ");
		lore.add(getHealthMeter(((Rabbit)entity).getHealth(), ((Rabbit)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		lore.add(age);
		return lore;
	}
}
