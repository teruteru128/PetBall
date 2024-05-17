package com.github.albatross256.petball.balldata;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

/**
 * ボールデータの基底クラス.
 * */
public abstract class BallData {

  /**
   * ボールにコンテンツ内容を埋め込む際のキー.
   * */
  public static final String ENTITYBALL_CONTENT_KEY = "EntityBall";

  /**
   * ボールにコンテンツ内容を空と設定する際のキー.
   * */
  public static final String ENTITYBALL_CONTENT_EMPTY = "Empty";

  /**
   * ボールに付随するNBTデータを取得するためのキー(~1.20.4).
   * */
  public static final String ENTITYBALL_NBT_KEY = "EntityBall_NBT";

  /**
   * ボールに付随するISCデータを取得するためのキー(1.20.5~).
   * */
  public static final String ENTITYBALL_ISC_KEY = "EntityBall_ISC";

  /**
   * 二重起動防止のために埋め込むタイムスタンプを取得するためのキー.
   * */
  public static final String ENTITYBALL_TIMESTAMP_KEY = "EntityBallTimeStump";

  /**
   * アイテム名等に設定するボールの名称.
   * */
  public static final String BALL_NAME = "Pet_Ball";

  /**
   * ボールデータに格納可能なエンティティタイプ.
   *
   * @return EntityType 対象のエンティティタイプ.
   * */
  public abstract EntityType getEntityType();

  /**
   * <p>
   * このボールで召喚可能なエンティティタイプ.
   * </p>
   *
   * @return {@link org.bukkit.entity.EntityType}
   * */
  public Material getEmptyBallMaterial() {
    return Material.ENDERMAN_SPAWN_EGG;
  }

  /**
   * <p>
   * ボールデータに利用されるスポーンエッグ表示.
   * </p>
   *
   * @return {@link org.bukkit.Material}
   * */
  public abstract Material getFilledBallMaterial();

  /**
   * 空のボールとして利用しているエンティティタイプ.
   *
   * @return EntityType 対象のエンティティ.
   * */
  public EntityType getEmptyBallEntityType() {
    return EntityType.ENDERMAN;
  }

  /**
   * </p>
   * このボールに格納可能なエンティティタイプ.
   * </p>
   *
   * @return {@link org.bukkit.entity.EntityType}
   * */
  public abstract EntityType getFilledBallEntityType();

  /**
   * ボールに表示する表示名を取得.
   *
   * @return String 表示名.
   * */
  public String getDisplayName() {
    return BALL_NAME;
  }
}