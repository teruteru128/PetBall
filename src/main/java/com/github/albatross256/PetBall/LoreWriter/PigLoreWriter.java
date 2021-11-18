package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;

public class PigLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		String age =  ((Pig)entity).isAdult() ? "大人" : "子供";

		lore.add("ブタ");
		lore.add(getHealthMeter(((Pig)entity).getHealth(), ((Pig)entity).getMaxHealth()));
		lore.add(age);
		return lore;
	}
}
