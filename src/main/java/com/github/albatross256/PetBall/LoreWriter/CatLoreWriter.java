package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;

public class CatLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();

		String owner = ((Cat)entity).getOwner() == null ? "なし" : ((Cat)entity).getOwner().getName();
		lore.add("猫");
		lore.add(getHealthMeter(((Cat)entity).getHealth(), ((Cat)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		lore.add("飼い主: " + owner);
		return lore;
	}
}
