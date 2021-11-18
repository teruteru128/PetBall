package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.WanderingTrader;

public class WanderingTraderLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		lore.add("行商人");
		lore.add(getHealthMeter(((WanderingTrader)entity).getHealth(), ((WanderingTrader)entity).getMaxHealth()));
		return lore;
	}
}
