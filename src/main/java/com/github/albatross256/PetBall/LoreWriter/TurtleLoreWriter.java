package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Turtle;

public class TurtleLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		String age =  ((Turtle)entity).isAdult() ? "大人" : "子供";
		lore.add("カメ");
		lore.add(getHealthMeter(((Turtle)entity).getHealth(), ((Turtle)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		lore.add(age);
		return lore;
	}
}
