package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.SkeletonHorse;

public class SkeletonHorseLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();
		SkeletonHorse skeletonHorse = (SkeletonHorse)entity;
		//String owner = skeletonHorse.getOwner() == null ? "なし" : skeletonHorse.getOwner().getName();
		lore.add("スケルトン馬");
		lore.add(getHealthMeter(skeletonHorse.getHealth(), skeletonHorse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		//lore.add("飼い主: " + owner);
		return lore;
	}
}
