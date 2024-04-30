package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Armadillo;
import org.bukkit.entity.Entity;

public class ArmadilloLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		lore.add("アルマジロ");
		lore.add(getHealthMeter(((Armadillo)entity).getHealth(), ((Armadillo)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		return lore;
	}
}
