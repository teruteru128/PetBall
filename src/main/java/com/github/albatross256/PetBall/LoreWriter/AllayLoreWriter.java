package com.github.albatross256.PetBall.LoreWriter;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class AllayLoreWriter extends LoreWriter {
	private static String loreMobName = "アレイ";
	public AllayLoreWriter(){
		super(loreMobName);
	}

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = generateCommonLore(entity);
		return lore;
	}
}
