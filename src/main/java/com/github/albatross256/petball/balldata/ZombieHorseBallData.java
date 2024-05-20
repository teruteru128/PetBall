package com.github.albatross256.petball.balldata;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

/**
 * <p>
 * PetBall の {@link com.github.albatross256.petball.balldata.BallData} .<br>
 * {@link org.bukkit.entity.ZombieHorse} を捕獲するボールを構築する実装クラス.
 * </p>
 * */
public class ZombieHorseBallData extends BallData {

  /**
   * <p>
   * このボールで召喚可能なエンティティタイプ.
   * </p>
   *
   * @return {@link org.bukkit.entity.EntityType}
   * */
  @Override
  public EntityType getEntityType() {
    return EntityType.ZOMBIE_HORSE;
  }

  /**
   * <p>
   * ボールデータに利用されるスポーンエッグ表示.
   * </p>
   *
   * @return {@link org.bukkit.Material}
   * */
  @Override
  public Material getFilledBallMaterial() {
    return Material.ZOMBIE_HORSE_SPAWN_EGG;
  }

  /**
   * </p>
   * このボールに格納可能なエンティティタイプ.
   * </p>
   *
   * @return {@link org.bukkit.entity.EntityType}
   * */
  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.ZOMBIE_HORSE;
  }
}
