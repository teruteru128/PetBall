package com.github.albatross256.petball.balldata;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

/**
 * <p>
 * ボールデータの基底クラス.
 * </p>
 * */
public abstract class BallData {

  /**
   * <p>
   * ボールにコンテンツ内容を埋め込む際のキー
   * {@value}.
   * </p>
   * */
  public static final String ENTITYBALL_CONTENT_KEY = "EntityBall";

  /**
   * <p>
   * ボールにコンテンツ内容を空と設定する際のキー
   * {@value}.
   * </p>
   * */
  public static final String ENTITYBALL_CONTENT_EMPTY = "Empty";

  /**
   * <p>
   * ボールに付随するNBTデータを取得するためのキー(~1.20.4)
   * {@value}.
   * </p>
   * */
  public static final String ENTITYBALL_NBT_KEY = "EntityBall_NBT";

  /**
   * <p>
   * ボールに付随するISCデータを取得するためのキー(1.20.5~)
   * {@value}.
   * </p>
   * */
  public static final String ENTITYBALL_ISC_KEY = "EntityBall_ISC";

  /**
   * <p>
   * 二重起動防止のために埋め込むタイムスタンプを取得するためのキー
   * {@value}.
   * </p>
   * */
  public static final String ENTITYBALL_TIMESTAMP_KEY = "EntityBallTimeStump";

  /**
   * <p>
   * アイテム名等に設定するボールの名称
   * {@value}.
   * </p>
   * */
  public static final String BALL_NAME = "Pet_Ball";

  /**
   * <p>
   * このボールで召喚可能なエンティティタイプ.
   * </p>
   *
   * @return {@link org.bukkit.entity.EntityType}
   * */
  public abstract EntityType getEntityType();

  /**
   * <p>
   * 空のボールとして扱うときに表示するマテリアルタイプ.
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
   * <p>
   * 空のボールとして利用しているエンティティタイプ.
   * </p>
   *
   * @return {@link org.bukkit.entity.EntityType}
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
   * <p>
   * ボールに表示する表示名を取得.
   * </p>
   *
   * @return {@value #BALL_NAME} .
   * */
  public String getDisplayName() {
    return BALL_NAME;
  }
}