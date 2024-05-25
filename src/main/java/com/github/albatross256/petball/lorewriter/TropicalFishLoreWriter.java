package com.github.albatross256.petball.lorewriter;

import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TropicalFish;

import net.md_5.bungee.api.ChatColor;

public class TropicalFishLoreWriter extends LoreWriter {

  /**
   * Loreに表示するMobの日本語名
   */
  private static String loreMobName = "熱帯魚";

  /**
   * コンストラクタ
   */
  public TropicalFishLoreWriter() {
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

    lore.add("肌の色 : " + translateColorName(((TropicalFish) entity).getBodyColor()));
    lore.add("模様の色 : " + translateColorName(((TropicalFish) entity).getPatternColor()));
    lore.add("形・模様の種類 : " + translatePatternName(((TropicalFish) entity).getPattern()));
    return lore;
  }

  /*
   * ※ 命名は雰囲気。わかりやすさを重視した。
   */
  private String translatePatternName(TropicalFish.Pattern patternNameEnglish) {
    String name = "";
    switch (patternNameEnglish) {
      case KOB:
        name += "コーブ";
        break;
      case SUNSTREAK:
        name += "サンストリーク";
        break;
      case SNOOPER:
        name += "スヌーパー";
        break;
      case DASHER:
        name += "ダッシャー";
        break;
      case BRINELY:
        name += "ブラインリー";
        break;
      case SPOTTY:
        name += "スポッティー";
        break;
      case FLOPPER:
        name += "フロッパー";
        break;
      case STRIPEY:
        name += "ストライピー";
        break;
      case GLITTER:
        name += "グリッター";
        break;
      case BLOCKFISH:
        name += "ブロックフィッシュ";
        break;
      case BETTY:
        name += "ベティー";
        break;
      case CLAYFISH:
        name += "クレイフィッシュ";
        break;
    }
    name += "型";
    return name;
  }

  private String translateColorName(DyeColor colorNameEnglish) {
    String name = "";
    switch (colorNameEnglish) {
      case WHITE:
        name += ChatColor.WHITE + "白";
        break;
      case ORANGE:
        name += ChatColor.RED + "橙";
        break;
      case MAGENTA:
        name += ChatColor.RED + "赤紫";
        break;
      case LIGHT_BLUE:
        name += ChatColor.AQUA + "空";
        break;
      case YELLOW:
        name += ChatColor.YELLOW + "黄";
        break;
      case LIME:
        name += ChatColor.GREEN + "黄緑";
        break;
      case PINK:
        name += ChatColor.LIGHT_PURPLE + "桃";
        break;
      case GRAY:
        name += ChatColor.DARK_GRAY + "灰";
        break;
      case LIGHT_GRAY:
        name += ChatColor.GRAY + "薄灰";
        break;
      case CYAN:
        name += ChatColor.BLUE + "青緑";
        break;
      case PURPLE:
        name += ChatColor.DARK_PURPLE + "紫";
        break;
      case BLUE:
        name += ChatColor.DARK_BLUE + "青";
        break;
      case BROWN:
        name += ChatColor.GRAY + "茶";
        break;
      case GREEN:
        name += ChatColor.GREEN + "緑";
        break;
      case RED:
        name += ChatColor.DARK_RED + "赤";
        break;
      case BLACK:
        name += ChatColor.GRAY + "黒";
        break;
    }
    name += "色";
    return name;
  }
}
