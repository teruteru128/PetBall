package com.github.albatross256.petball;

import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * <p>
 * config.ymlのローダー.
 * </p>
 */
public class ConfigLoader {

  FileConfiguration config;

  /**
   * <p>
   * コンストラクタ.
   * </p>
   *
   * @param plugin         {@link Plugin} プラグイン本体.
   */
  public ConfigLoader(Plugin plugin) {
    plugin.saveDefaultConfig();
    this.config = plugin.getConfig();
  }

  /**
   * <p>
   * Configから無効化ワールド一覧の取得.
   * </p>
   *
   * @return {@link List} configに設定されたPetBall無効化ワールド一覧.
   */
  public List<String> getUnsableWorldNames() {
    return this.config.getStringList("UnusableWorldNames");
  }

  /**
   * <p>
   * Configからログレベルを取得.
   * </p>
   *
   * @return {@link String} configに設定されたログレベル.
   */
  public String getLogLevel() {
    return this.config.getString("log-level");
  }
}
