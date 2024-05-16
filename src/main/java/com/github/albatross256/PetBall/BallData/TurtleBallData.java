package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class TurtleBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.TURTLE;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.TURTLE_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.TURTLE;
  }
}
