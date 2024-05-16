package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class PiglinBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.PIGLIN;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.PIGLIN_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.PIGLIN;
  }
}
