package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Panda;

public class PandaLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		String age =  ((Panda)entity).isAdult() ? "大人" : "子供";
		lore.add("パンダ");
		lore.add(getHealthMeter(((Panda)entity).getHealth(), ((Panda)entity).getMaxHealth()));
		lore.add(age);
		return lore;
	}
}
