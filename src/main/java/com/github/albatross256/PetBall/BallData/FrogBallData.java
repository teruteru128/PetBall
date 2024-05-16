package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class FrogBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.FROG;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.FROG_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.FROG;
  }
}
