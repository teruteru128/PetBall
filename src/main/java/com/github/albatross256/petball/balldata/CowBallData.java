package com.github.albatross256.petball.balldata;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

/**
 * COW　を格納するボールデータ.
 * */
public class CowBallData extends BallData {

  /**
   * ボールデータに格納可能なエンティティタイプ.
   *
   * @return EntityType 対象のエンティティタイプ.
   * */
  @Override
  public EntityType getEntityType() {
    return EntityType.COW;
  }

  /**
   * ボールデータに利用されるスポーンエッグ表示.
   *
   * @return Material 対象のスポーンエッグ表示.
   * */
  @Override
  public Material getFilledBallMaterial() {
    return Material.COW_SPAWN_EGG;
  }

  /**
   * ボールに格納可能なエンティティ.
   *
   * @return EntityType 対象のエンティティ.
   * */
  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.COW;
  }
}
