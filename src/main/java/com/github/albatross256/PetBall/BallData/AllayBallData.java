package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class AllayBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.ALLAY;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.ALLAY_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.ALLAY;
  }
}
