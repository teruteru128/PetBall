package com.github.albatross256.petball;

import com.github.albatross256.petball.eventlistner.EventListener;
import com.github.teruteru128.logger.Logger;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * <p>
 * プラグインの初期実行クラス.
 * </p>
 */
public class Main extends JavaPlugin {

  /**
   * <p>
   * PetBallに関するデータを管理するManager. <br>
   * {@link com.github.albatross256.petball.BallManager}
   * </p>
   */
  public BallManager ballManager;

  /**
   * <p>
   * 読み込まれたサーバーのワールドに関するデータを管理するManager.<br>
   * {@link com.github.albatross256.petball.WorldManager}
   * </p>
   */
  public WorldManager worldManager;

  /**
   * <p>
   * 読み込まれたボールのレシピに関するデータを管理するManager.<br>
   * {@link com.github.albatross256.petball.WorldManager}
   * </p>
   */
  private BallRecipeManager ballRecipeManager;

  /**
   * <p>
   * 読み込まれたボールのレシピに関するデータを管理するManager.<br>
   * {@link com.github.albatross256.petball.ConfigLoader}
   * </p>
   */
  private ConfigLoader configLoader;

  /**
   * <p>
   * クラス内のログを出力するロガー.<br>
   * {@link com.github.teruteru128.logger.Logger}
   * </p>
   */
  private Logger logger;

  /**
   * プラグイン有効化時の初期化処理.
   */
  @Override
  public void onEnable() {
    // ロガーの設定
    configLoader = new ConfigLoader(this);
    String logLevel = configLoader.getLogLevel();
    if (logLevel == null) {
      logLevel = "INFO";
      logger.warn("log-levelに値がセットされていないため、INFOに設定されました");
    }
    Logger.register(this, logLevel);
    logger = Logger.getInstance(this);

    logger.debug("Main.onEnable:Start");
    this.ballManager = new BallManager(logger);
    this.worldManager = new WorldManager(this, logger);
    this.getServer().getPluginManager()
        .registerEvents(new EventListener(ballManager, worldManager, this), this);

    ballRecipeManager = new BallRecipeManager(this, logger);
    ballRecipeManager.init();
    logger.debug("Main.onEnable:End");
  }

  /**
   * プラグイン無効化処理.
   */
  @Override
  public void onDisable() {
    super.onDisable();
    ballRecipeManager.disable();
  }
}
