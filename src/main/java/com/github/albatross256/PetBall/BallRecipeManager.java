package com.github.albatross256.PetBall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.teruteru128.logger.Logger;
import jline.internal.Log;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.github.albatross256.PetBall.BallData.BallData;

import net.minecraft.nbt.CompoundTag;

/**
 * ボールレシピマネージャー
 * */
public class BallRecipeManager {

	private Map<EntityType, BallData> ballDatas;
	private Plugin plugin;
    private NamespacedKey key;
	private Logger logger;


	/**
	 * コンストラクタ
	 * @param plugin プラグイン本体
	 * @param map 利用できるボールデータの一覧
	 * @param logger ロガー
	 * */
	public BallRecipeManager(Plugin plugin, Map<EntityType, BallData> map, Logger logger) {
		this.ballDatas = map;
		this.plugin = plugin;
		this.logger = logger;
	}

	/**
	 * 初期化。レシピの登録処理。
	 * */
	public void init() {
		logger.debug("BallRecipeManager.init:Start");

		ItemStack ball = new ItemStack(Material.ENDERMAN_SPAWN_EGG, 1);
		CompoundTag nbttag = new CompoundTag();
		nbttag.putString(BallData.ENTITYBALL_CONTENT_KEY, BallData.ENTITYBALL_CONTENT_EMPTY);
		net.minecraft.world.item.ItemStack itemCopy = CraftItemStack.asNMSCopy(ball);
		itemCopy.setTag(nbttag);
		ball = CraftItemStack.asBukkitCopy(itemCopy);
		logger.trace("ball:" + ball);
		logger.trace("nbttag:" + nbttag);
		logger.trace("itemCopy:" + itemCopy);
		logger.trace("ball:" + ball);

		ItemMeta meta = ball.getItemMeta();
		meta.setDisplayName(BallData.BALL_NAME);
		List<String> lore = new ArrayList<String>();
		lore.add("Empty");
		meta.setLore(lore);
		ball.setItemMeta(meta);
		logger.trace("meta:" + meta);
		logger.trace("lore:" + lore);

        key = new NamespacedKey(this.plugin, BallData.BALL_NAME);
        ShapedRecipe recipe = new ShapedRecipe(key, ball);
		recipe.shape(	"CBC",
							"BAB",
							"CBC");
		recipe.setIngredient('A', Material.DRAGON_EGG);
		recipe.setIngredient('B', Material.NETHER_STAR);
		recipe.setIngredient('C', Material.ENDER_CHEST);
		this.plugin.getServer().addRecipe(recipe);
		logger.debug("BallRecipeManager.init:End");
	}

	/**
	 * レシピの無効化処理
	 * */
	public void disable(){
		logger.debug("BallRecipeManager.disable:Start");

		this.plugin.getServer().removeRecipe(key);

		logger.debug("BallRecipeManager.disable:End");
	}
}

