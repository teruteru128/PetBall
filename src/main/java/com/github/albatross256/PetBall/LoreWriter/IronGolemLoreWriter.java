package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;

public class IronGolemLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		lore.add("アイアンゴーレム");
		lore.add(getHealthMeter(((IronGolem)entity).getHealth(), ((IronGolem)entity).getMaxHealth()));
		return lore;
	}
}
