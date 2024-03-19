package com.github.albatross256.PetBall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.github.albatross256.PetBall.BallData.BallData;

import net.minecraft.nbt.CompoundTag;

public class BallRecipeManager {

	private Map<String, BallData> ballDatas;
	private Plugin plugin;


	public BallRecipeManager(Plugin plugin, Map<String, BallData> map) {
		this.ballDatas = map;
		this.plugin = plugin;
	}

	public void init() {
		ItemStack ball = new ItemStack(Material.ENDERMAN_SPAWN_EGG, 1);
		CompoundTag nbttag = new CompoundTag();
		nbttag.putString(BallData.ENTITYBALL_CONTENT_KEY, BallData.ENTITYBALL_CONTENT_EMPTY);
		net.minecraft.world.item.ItemStack itemCopy = CraftItemStack.asNMSCopy(ball);
		itemCopy.setTag(nbttag);
		ball = CraftItemStack.asBukkitCopy(itemCopy);

		ItemMeta meta = ball.getItemMeta();
		meta.setDisplayName(BallData.BALL_NAME);
		List<String> lore = new ArrayList<String>();
		lore.add("Empty");
		meta.setLore(lore);
		ball.setItemMeta(meta);

		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this.plugin, BallData.BALL_NAME), ball);
		recipe.shape(	"CBC",
							"BAB",
							"CBC");
		recipe.setIngredient('A', Material.DRAGON_EGG);
		recipe.setIngredient('B', Material.NETHER_STAR);
		recipe.setIngredient('C', Material.ENDER_CHEST);
		this.plugin.getServer().addRecipe(recipe);
	}
}
