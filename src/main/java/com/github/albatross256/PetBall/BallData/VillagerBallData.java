package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class VillagerBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.VILLAGER;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.VILLAGER_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.VILLAGER;
  }
}
