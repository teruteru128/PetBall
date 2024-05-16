package com.github.albatross256.PetBall.LoreWriter;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Goat;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class GoatLoreWriter extends LoreWriter {

  /**
   * Loreに表示するMobの日本語名
   */
  private static String loreMobName = "ヤギ";

  /**
   * コンストラクタ
   */
  public GoatLoreWriter() {
    super(loreMobName);
  }

  /**
   * Lore情報の作成
   *
   * @param entity Loreを作成するエンティティ情報
   */
  @Override
  public List<String> generateLore(Entity entity) {
    List<String> lore = generateCommonLore(entity);
    return lore;
  }
}
