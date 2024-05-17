package com.github.albatross256.petball;

import com.github.albatross256.petball.balldata.AllayBallData;
import com.github.albatross256.petball.balldata.ArmadilloBallData;
import com.github.albatross256.petball.balldata.AxolotlBallData;
import com.github.albatross256.petball.balldata.BallData;
import com.github.albatross256.petball.balldata.BatBallData;
import com.github.albatross256.petball.balldata.BeeBallData;
import com.github.albatross256.petball.balldata.CamelBallData;
import com.github.albatross256.petball.balldata.CatBallData;
import com.github.albatross256.petball.balldata.ChickenBallData;
import com.github.albatross256.petball.balldata.CodBallData;
import com.github.albatross256.petball.balldata.CowBallData;
import com.github.albatross256.petball.balldata.DolphinBallData;
import com.github.albatross256.petball.balldata.DonkeyBallData;
import com.github.albatross256.petball.balldata.FoxBallData;
import com.github.albatross256.petball.balldata.FrogBallData;
import com.github.albatross256.petball.balldata.GlowSquidBallData;
import com.github.albatross256.petball.balldata.GoatBallData;
import com.github.albatross256.petball.balldata.HorseBallData;
import com.github.albatross256.petball.balldata.IronGolemBallData;
import com.github.albatross256.petball.balldata.LlamaBallData;
import com.github.albatross256.petball.balldata.MuleBallData;
import com.github.albatross256.petball.balldata.MushroomBallData;
import com.github.albatross256.petball.balldata.OcelotBallData;
import com.github.albatross256.petball.balldata.PandaBallData;
import com.github.albatross256.petball.balldata.ParrotBallData;
import com.github.albatross256.petball.balldata.PigBallData;
import com.github.albatross256.petball.balldata.PiglinBallData;
import com.github.albatross256.petball.balldata.PolarBearBallData;
import com.github.albatross256.petball.balldata.PufferFishBallData;
import com.github.albatross256.petball.balldata.RabbitBallData;
import com.github.albatross256.petball.balldata.SalmonBallData;
import com.github.albatross256.petball.balldata.SheepBallData;
import com.github.albatross256.petball.balldata.SkeletonHorseBallData;
import com.github.albatross256.petball.balldata.SnifferBallData;
import com.github.albatross256.petball.balldata.SnowGolemBallData;
import com.github.albatross256.petball.balldata.SquidBallData;
import com.github.albatross256.petball.balldata.StriderBallData;
import com.github.albatross256.petball.balldata.TadpoleBallData;
import com.github.albatross256.petball.balldata.TraderLlamaBallData;
import com.github.albatross256.petball.balldata.TropicalFishData;
import com.github.albatross256.petball.balldata.TurtleBallData;
import com.github.albatross256.petball.balldata.VillagerBallData;
import com.github.albatross256.petball.balldata.WanderingTraderBallData;
import com.github.albatross256.petball.balldata.WolfBallData;
import com.github.albatross256.petball.balldata.ZombieHorseBallData;
import com.github.teruteru128.logger.Logger;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import org.bukkit.entity.EntityType;

/**
 * ボールマネージャー
 */
public class BallManager {

  protected Map<EntityType, BallData> ballData;
  private final Logger logger;

  /**
   * コンストラクタ
   *
   * @param logger ロガー
   */
  public BallManager(Logger logger) {
    logger.debug("BallManager:Start");
    this.logger = logger;
    init();
    logger.debug("BallManager:End");
  }

  /**
   * 初期化。PetBallのボール本体の一覧を作成。
   */
  private void init() {
    logger.debug("BallManager.init:Start");

    var map = new EnumMap<EntityType, BallData>(EntityType.class);
    BallData[] ballDataArray = {
        new VillagerBallData(),
        new CatBallData(),
        new OcelotBallData(),
        new ParrotBallData(),
        new WolfBallData(),
        new CowBallData(),
        new MushroomBallData(),
        new PigBallData(),
        new SheepBallData(),
        new ChickenBallData(),
        new RabbitBallData(),
        new PolarBearBallData(),
        new TurtleBallData(),
        new DolphinBallData(),
        new SquidBallData(),
        new IronGolemBallData(),
        new SnowGolemBallData(),
        new BatBallData(),
        new WanderingTraderBallData(),
        new PandaBallData(),
        new FoxBallData(),
        new LlamaBallData(),
        new MuleBallData(),
        new DonkeyBallData(),
        new CodBallData(),
        new SalmonBallData(),
        new TropicalFishData(),
        new PufferFishBallData(),
        new BeeBallData(),
        new HorseBallData(),
        new ZombieHorseBallData(),
        new SkeletonHorseBallData(),
        new TraderLlamaBallData(),
        new PiglinBallData(),
        new StriderBallData(),
        new AxolotlBallData(),
        new GoatBallData(),
        new GlowSquidBallData(),
        new AllayBallData(),
        new FrogBallData(),
        new TadpoleBallData(),
        new CamelBallData(),
        new SnifferBallData(),
        new ArmadilloBallData(),
    };

    for (BallData ballData : ballDataArray) {
      logger.trace("ballData:" + ballData);
      registerBall(map, ballData);
    }
    this.ballData = Collections.unmodifiableMap(map);
    logger.debug("BallManager.init:End");
  }

  /**
   * PetBallデータの登録
   *
   * @param map      登録先のMAP
   * @param ballData 登録したいボールデータ
   */
  private static void registerBall(EnumMap<EntityType, BallData> map, BallData ballData) {
    map.put(ballData.getEntityType(), ballData);
  }

  /**
   * すべてのPetBallデータの取得
   *
   * @return 全ボールデータ一覧
   */
  public Map<EntityType, BallData> getAllBallDatas() {
    logger.debug("BallManager.getAllBallDatas");
    return this.ballData;
  }

  /**
   * PetBallのボールデータ取得
   *
   * @param entityType 取得したいエンティティタイプ
   * @return 該当するボールデータ
   */
  public BallData getBallData(EntityType entityType) {
    logger.debug("BallManager.getBallData");
    return this.ballData.get(entityType);
  }


}
