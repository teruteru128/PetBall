package com.github.albatross256.PetBall.LoreWriter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Squid;

public class SquidLoreWriter extends LoreWriter {
	/** Loreに表示するMobの日本語名 */
	private static String loreMobName = "イカ";

	/**
	 * コンストラクタ
	 * */
	public SquidLoreWriter(){
		super(loreMobName);
	}

	/**
	 * Lore情報の作成
	 * @param entity Loreを作成するエンティティ情報
	 * */
	@Override
	public List<String> generateLore(Entity entity) {
		List<String> lore = generateCommonLore(entity);
		return lore;
	}
}
