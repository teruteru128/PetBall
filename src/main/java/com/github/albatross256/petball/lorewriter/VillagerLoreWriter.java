package com.github.albatross256.petball.lorewriter;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

public class VillagerLoreWriter extends LoreWriter {

  /**
   * Loreに表示する村人の日本語名
   */
  private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
      "com.github.albatross256.petball.lorewriter.VillagerLoreWriter", Locale.JAPANESE);

  /**
   * Loreに表示するMobの日本語名
   */
  private static String loreMobName = "村人";

  /**
   * コンストラクタ
   */
  public VillagerLoreWriter() {
    super(loreMobName);
  }

  /**
   * Lore情報の作成
   *
   * @param entity Loreを作成するエンティティ情報
   */
  @Override
  public List<String> generateLore(Entity entity) {
    List<String> lore = generateCommonLore(entity);

    String trade1;
    String trade2;
    if (((Villager) entity).getRecipeCount() < 1) {
      trade1 = "なし";
    } else {
      trade1 = this.getRecipeText(((Villager) entity).getRecipe(0));
    }
    if (((Villager) entity).getRecipeCount() < 2) {
      trade2 = "なし";
    } else {
      trade2 = this.getRecipeText(((Villager) entity).getRecipe(1));
    }

    // String profession = this.translateCareerName(((Villager)entity).getProfession());
    String profession = BUNDLE.getString(((Villager) entity).getProfession().name());
    lore.add("職業: " + profession);
    lore.add("交易1: " + trade1);
    lore.add("交易2: " + trade2);
    return lore;
  }

  /**
   * 交易情報の作成
   *
   * @param recipe 村人の交易情報
   */
  private String getRecipeText(MerchantRecipe recipe) {
    String ingredientString = "";
    for (ItemStack ingredient : recipe.getIngredients()) {
			if (ingredient.getType().equals(Material.AIR)) {
				continue;
			}
      ingredientString =
          ingredientString + ingredient.getType().toString() + " × " + ingredient.getAmount()
              + ",  ";
    }
    int last = ingredientString.lastIndexOf(",  ");
    ingredientString = ingredientString.substring(0, last);
    ItemStack result = recipe.getResult();

    return ingredientString + " => " + result.getType().toString() + " × " + result.getAmount();
  }
}
