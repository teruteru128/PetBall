package com.github.albatross256.petball.balldata;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

/**
 * ALLAY　を格納するボールデータ.
 * */
public class AllayBallData extends BallData {

  /**
   * ボールデータに格納可能なエンティティタイプ.
   *
   * @return EntityType 対象のエンティティタイプ.
   * */
  @Override
  public EntityType getEntityType() {
    return EntityType.ALLAY;
  }

  /**
   * ボールデータに利用されるスポーンエッグ表示.
   *
   * @return Material 対象のスポーンエッグ表示.
   * */
  @Override
  public Material getFilledBallMaterial() {
    return Material.ALLAY_SPAWN_EGG;
  }

  /**
   * ボールに格納可能なエンティティ.
   *
   * @return EntityType 対象のエンティティ.
   * */
  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.ALLAY;
  }
}
