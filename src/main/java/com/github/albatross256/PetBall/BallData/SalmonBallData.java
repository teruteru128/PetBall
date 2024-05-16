package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class SalmonBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.SALMON;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.SALMON_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.SALMON;
  }
}
