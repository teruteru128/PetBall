package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;

public class CowLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		String age =  ((Cow)entity).isAdult() ? "大人" : "子供";
		lore.add("牛");
		lore.add(getHealthMeter(((Cow)entity).getHealth(), ((Cow)entity).getMaxHealth()));
		lore.add(age);
		return lore;
	}
}
