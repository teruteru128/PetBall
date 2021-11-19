package com.github.albatross256.PetBall.LoreWriter;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class AxolotlLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		String age =  ((Axolotl)entity).isAdult() ? "大人" : "子供";
		lore.add("ウーパールーパー");
		lore.add(getHealthMeter(((Axolotl)entity).getHealth(), ((Axolotl)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		lore.add(age);
		return lore;
	}
}
