package com.github.albatross256.PetBall;

import java.util.ArrayList;
import java.util.List;

import com.github.albatross256.PetBall.io.ConfigLoader;

import com.github.teruteru128.logger.Logger;
import org.bukkit.plugin.Plugin;

/**
 * ワールドマネージャー
 */
public class WorldManager {

  private Logger logger;
  List<String> unusableWorldNames;

  /**
   * コンストラクタ
   *
   * @param plugin プラグイン本体
   * @param logger ログ出力クラス
   */
  public WorldManager(Plugin plugin, Logger logger) {
    logger.debug("BallRecipeManager:Start");
    this.logger = logger;
    this.unusableWorldNames = new ArrayList<String>();
    init(plugin);
    logger.debug("BallRecipeManager:End");
  }

  /**
   * 初期化処理
   *
   * @param plugin プラグイン本体
   */
  private void init(Plugin plugin) {
    logger.debug("BallRecipeManager.init:Start");
    List<String> worldNames = new ConfigLoader(plugin).getUnsableWorldNames();

    for (String worldName : worldNames) {
      logger.trace("worldName:" + worldName);
      registerUnusableWorldName(worldName);
    }
    logger.debug("BallRecipeManager.init:End");
  }

  /**
   * 無効化ワールド一覧への追加処理
   *
   * @param worldName 無効化するワールド名
   */
  public void registerUnusableWorldName(String worldName) {
    this.unusableWorldNames.add(worldName);
  }

  /**
   * 渡されたワールドが、利用可能ワールドかの判定
   *
   * @param worldName 確認するワールド名
   * @return チェック結果
   */
  public boolean isUsableWorld(String worldName) {
    return !this.unusableWorldNames.contains(worldName);
  }
}
