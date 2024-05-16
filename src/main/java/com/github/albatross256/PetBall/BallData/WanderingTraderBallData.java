package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class WanderingTraderBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.WANDERING_TRADER;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.WANDERING_TRADER_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.WANDERING_TRADER;
  }
}
