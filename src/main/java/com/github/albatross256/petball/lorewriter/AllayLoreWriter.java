package com.github.albatross256.petball.lorewriter;

import java.util.List;
import org.bukkit.entity.Entity;

public class AllayLoreWriter extends LoreWriter {

  private static String loreMobName = "アレイ";

  /**
   * コンストラクタ.
   */
  public AllayLoreWriter() {
    super(loreMobName);
  }

  /**
   * Lore情報の作成.
   *
   * @param entity Loreを作成するエンティティ情報
   */
  @Override
  public List<String> generateLore(Entity entity) {
    List<String> lore = generateCommonLore(entity);
    return lore;
  }
}
