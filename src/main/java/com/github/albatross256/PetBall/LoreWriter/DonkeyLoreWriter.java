package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Donkey;
import org.bukkit.entity.Entity;

public class DonkeyLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		lore.add("ロバ");
		lore.add(getHealthMeter(((Donkey)entity).getHealth(), ((Donkey)entity).getMaxHealth()));
		return lore;
	}
}
