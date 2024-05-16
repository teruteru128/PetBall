package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class ChickenBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.CHICKEN;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.CHICKEN_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.CHICKEN;
  }
}
