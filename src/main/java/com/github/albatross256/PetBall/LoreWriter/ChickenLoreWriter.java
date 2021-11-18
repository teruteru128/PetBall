package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;

public class ChickenLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		String age =  ((Chicken)entity).isAdult() ? "大人" : "子供";
		lore.add("ニワトリ");
		lore.add(getHealthMeter(((Chicken)entity).getHealth(), ((Chicken)entity).getMaxHealth()));
		lore.add(age);
		return lore;
	}
}
