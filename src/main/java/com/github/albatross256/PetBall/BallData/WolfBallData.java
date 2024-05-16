package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class WolfBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.WOLF;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.WOLF_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.WOLF;
  }
}
