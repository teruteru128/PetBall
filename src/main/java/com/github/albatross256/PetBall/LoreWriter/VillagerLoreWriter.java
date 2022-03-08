package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

public class VillagerLoreWriter extends LoreWriter {

	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = new ArrayList<String>();

		//String pro = ((Villager)entity).getCareer().name();

		String trade1;
		String trade2;
		lore.add("村人");
		lore.add(getHealthMeter(((Villager)entity).getHealth(), ((Villager)entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		if(((Villager)entity).getRecipeCount() < 1) {
			trade1 = "なし";
		}else {
			trade1 = this.getRecipeText(((Villager)entity).getRecipe(0));
		}
		if(((Villager)entity).getRecipeCount() < 2) {
			trade2 = "なし";
		}else {
			trade2 = this.getRecipeText(((Villager)entity).getRecipe(1));
		}

		String profession = this.translateCareerName(((Villager)entity).getProfession());
		lore.add("職業: " + profession);
		lore.add("交易1: " + trade1);
		lore.add("交易2: " + trade2);
		return lore;
	}


	private String getRecipeText(MerchantRecipe recipe) {
		String ingredientString ="";
		for(ItemStack ingredient : recipe.getIngredients()) {
			if(ingredient.getType().equals(Material.AIR)) continue;
			ingredientString = ingredientString + ingredient.getType().toString()+ " × " + ingredient.getAmount() + ",  ";
		}
		int last = ingredientString.lastIndexOf(",  ");
		ingredientString = ingredientString.substring(0, last);
		ItemStack result = recipe.getResult();

		return ingredientString + " => " + result.getType().toString() + " × " + result.getAmount();
	}


	private String translateCareerName(Villager.Profession eName) {
		switch(eName) {
		case FARMER:
			return "農民";
		case FISHERMAN:
			return "釣り人";
		case SHEPHERD:
			return "羊飼い";
		case FLETCHER:
			return "矢師";
		case LIBRARIAN:
			return "司書";
		case CARTOGRAPHER:
			return "製図家";
		case CLERIC:
			return "聖職者";
		case ARMORER:
			return "防具鍛冶";
		case WEAPONSMITH:
			return "武器鍛冶";
		case TOOLSMITH:
			return "道具鍛冶";
		case BUTCHER:
			return "肉屋";
		case LEATHERWORKER:
			return "革細工師";
		case NITWIT:
			return "無職";
		case MASON:
			return "石工職人";
		default:
			return eName.name();
		}
	}
}
