package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;

public class SheepLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		String age =  ((Sheep)entity).isAdult() ? "大人" : "子供";
		lore.add("ヒツジ");
		lore.add(getHealthMeter(((Sheep)entity).getHealth(), ((Sheep)entity).getMaxHealth()));
		lore.add(age);
		return lore;
	}
}
