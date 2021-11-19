package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Parrot;

public class ParrotLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		String owner = ((Parrot)entity).getOwner() == null ? "なし" : ((Parrot)entity).getOwner().getName();
		lore.add("オウム");
		lore.add(getHealthMeter(((Parrot)entity).getHealth(), ((Parrot)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		lore.add("飼い主: " + owner);
		return lore;
	}
}
