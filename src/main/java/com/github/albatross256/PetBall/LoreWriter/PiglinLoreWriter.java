package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Piglin;

public class PiglinLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		String age =  ((Piglin)entity).isAdult() ? "大人" : "子供";
		lore.add("ピグリン");
		lore.add(getHealthMeter(((Piglin)entity).getHealth(), ((Piglin)entity).getMaxHealth()));
		lore.add(age);
		return lore;
	}
}
