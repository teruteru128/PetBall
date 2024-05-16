

package com.github.albatross256.PetBall;

import com.github.albatross256.PetBall.EventListener.EventListener;

import com.github.albatross256.PetBall.io.ConfigLoader;
import com.github.teruteru128.logger.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

  public BallManager ballManager;
  public WorldManager worldManager;
  private BallRecipeManager ballRecipeManager;
  private ConfigLoader configLoader;
  private Logger logger;

  /**
   * プラグイン有効化時の初期化処理
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

    ballRecipeManager = new BallRecipeManager(this, ballManager.getAllBallDatas(), logger);
    ballRecipeManager.init();
    logger.debug("Main.onEnable:End");
  }

  /**
   * プラグイン無効化処理
   */
  @Override
  public void onDisable() {
    super.onDisable();
    ballRecipeManager.disable();
  }
}
