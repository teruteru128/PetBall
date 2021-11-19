package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.attribute.Attribute;

public class BatLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		lore.add("コウモリ");
		lore.add(getHealthMeter(((Bat)entity).getHealth(), ((Bat)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		return lore;
	}


}
