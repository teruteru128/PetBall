package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Llama;

public class LlamaLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		String age =  ((Llama)entity).isAdult() ? "大人" : "子供";

		lore.add("ラマ");
		lore.add(getHealthMeter(((Llama)entity).getHealth(), ((Llama)entity).getMaxHealth()));
		lore.add(age);
		return lore;
	}
}
