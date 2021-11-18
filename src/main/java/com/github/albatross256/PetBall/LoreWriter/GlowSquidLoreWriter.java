package com.github.albatross256.PetBall.LoreWriter;

import org.bukkit.entity.Entity;
import org.bukkit.entity.GlowSquid;
import org.bukkit.attribute.Attribute;

import java.util.ArrayList;
import java.util.List;

public class GlowSquidLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		lore.add("ヒカリイカ");
		lore.add(getHealthMeter(((GlowSquid)entity).getHealth(), ((GlowSquid)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		return lore;
	}
}
