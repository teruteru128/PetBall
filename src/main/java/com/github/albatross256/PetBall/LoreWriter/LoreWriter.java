package com.github.albatross256.PetBall.LoreWriter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Tameable;

public abstract class LoreWriter {

	private static DecimalFormat hpFormat = new DecimalFormat("0.0#");
	private static String adultDisplayStr = "大人";
	private static String childDisplayStr = "子供";
	private static String noOwnerDisplayStr = "なし";
	private static String loreOwnerDisplayStr = "飼い主: ";

	private String mobName;

	public LoreWriter(String mobName) {
		this.mobName = mobName;
	}

	public abstract List<String> generateLore(Entity entity);

	public List<String> generateCommonLore(Entity entity){
		List<String> lore = new ArrayList<String>();
		lore.add(this.mobName);
		if(entity instanceof Damageable && entity instanceof Attributable){
			lore.add(getHealthMeter(((Damageable)entity).getHealth(), ((Attributable)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		}
		if (entity instanceof Ageable){
			String age =  ((Ageable)entity).isAdult() ? adultDisplayStr : childDisplayStr;
			lore.add(age);
		}

		if(entity instanceof Tameable){
			Tameable tameable = (Tameable) entity;
			String owner = tameable.getOwner() == null ? noOwnerDisplayStr : tameable.getOwner().getName();
			lore.add(loreOwnerDisplayStr + owner);
		}
		return lore;
	}

	protected String getHealthMeter(double currentHealth, double maxHealth) {
		int rate = (int) (currentHealth * 20  /  maxHealth);

		return "体力 : [" + ChatColor.GREEN
				+ ":".repeat(rate) + ChatColor.GRAY + ":".repeat(20 - rate)
				+ ChatColor.DARK_PURPLE + "]  (" + hpFormat.format(currentHealth) + " / "
				+ hpFormat.format(maxHealth) + ")";
	}


}
