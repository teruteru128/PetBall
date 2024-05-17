package com.github.albatross256.petball;

import com.github.albatross256.petball.balldata.BallData;
import com.github.teruteru128.logger.Logger;
import com.saicone.rtag.RtagEditor;
import com.saicone.rtag.RtagItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

/**
 * <p>
 * ボールレシピを管理するマネージャー.
 * </p>
 */
public class BallRecipeManager {

  /**
   * <p>
   * プラグイン本体.<br>
   * {@link Plugin}
   * </p>
   * */
  private final Plugin plugin;

  /**
   * <p>
   * プラグインで設定するレシピを管理するキー.<br>
   * {@link NamespacedKey}
   * </p>
   * */
  private NamespacedKey key;

  /**
   * <p>
   * クラス内のログを出力するロガー.<br>
   * {@link com.github.teruteru128.logger.Logger}
   * </p>
   * */
  private final Logger logger;


  /**
   * <p>
   * コンストラクタ.
   * </p>
   *
   * @param plugin  {@link Plugin} プラグイン本体.
   * @param map  {@link Map} 利用できるボールデータの一覧.
   * @param logger  {@link Logger} ロガー本体.
   */
  public BallRecipeManager(Plugin plugin, Map<EntityType, BallData> map, Logger logger) {
    this.plugin = plugin;
    this.logger = logger;
  }

  /**
   * <p>
   * BallRecipeManager初期化.<br>
   * レシピの登録処理を実施.
   * </p>
   */
  public void init() {
    logger.debug("BallRecipeManager.init:Start");

    ItemStack ball = new ItemStack(Material.ENDERMAN_SPAWN_EGG, 1);
    RtagEditor tag = new RtagItem(ball);
    logger.trace("tag:" + tag);
    tag.set(BallData.ENTITYBALL_CONTENT_EMPTY, BallData.ENTITYBALL_CONTENT_KEY);
    tag.load();

    logger.trace("ball:" + ball);
    logger.trace("tag:" + tag);

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
    recipe.shape("CBC",
        "BAB",
        "CBC");
    recipe.setIngredient('A', Material.DRAGON_EGG);
    recipe.setIngredient('B', Material.NETHER_STAR);
    recipe.setIngredient('C', Material.ENDER_CHEST);
    this.plugin.getServer().addRecipe(recipe);
    logger.debug("BallRecipeManager.init:End");
  }

  /**
   * <p>
   * レシピの無効化処理.<br>
   * </p>
   */
  public void disable() {
    logger.debug("BallRecipeManager.disable:Start");

    this.plugin.getServer().removeRecipe(key);

    logger.debug("BallRecipeManager.disable:End");
  }
}
