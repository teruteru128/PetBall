package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ZombieHorse;

public class ZombieHorseLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		ZombieHorse zombieHorse = (ZombieHorse)entity;
		//String owner = zombieHorse.getOwner() == null ? "なし" : zombieHorse.getOwner().getName();
		lore.add("ゾンビ馬");
		lore.add(getHealthMeter(zombieHorse.getHealth(), zombieHorse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		//lore.add("飼い主: " + owner);
		return lore;
	}
}
