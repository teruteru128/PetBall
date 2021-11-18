package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Mule;

public class MuleLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		String age =  ((Mule)entity).isAdult() ? "大人" : "子供";
		lore.add("ラバ");
		lore.add(getHealthMeter(((Mule)entity).getHealth(), ((Mule)entity).getMaxHealth()));
		lore.add(age);
		return lore;
	}
}
