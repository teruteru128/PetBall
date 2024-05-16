package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class PigBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.PIG;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.PIG_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.PIG;
  }
}
