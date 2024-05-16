package com.github.albatross256.PetBall.io;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * config.ymlのローダー
 */
public class ConfigLoader {

  FileConfiguration config;

  /**
   * コンストラクタ
   *
   * @param plugin プラグイン本体
   */
  public ConfigLoader(Plugin plugin) {
    plugin.saveDefaultConfig();
    this.config = plugin.getConfig();
  }

  /**
   * Configから無効化ワールド一覧の取得
   *
   * @return 無効化ワールド一覧
   */
  public List<String> getUnsableWorldNames() {
    return this.config.getStringList("UnusableWorldNames");
  }

  /**
   * Configからログレベルを取得
   *
   * @return ログレベル
   */
  public String getLogLevel() {
    return this.config.getString("log-level");
  }
}
