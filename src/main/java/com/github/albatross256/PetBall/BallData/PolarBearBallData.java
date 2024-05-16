package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class PolarBearBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.POLAR_BEAR;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.POLAR_BEAR_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.WOLF;
  }
}
