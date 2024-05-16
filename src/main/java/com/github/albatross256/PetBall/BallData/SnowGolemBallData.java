package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class SnowGolemBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.SNOW_GOLEM;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.SNOW_GOLEM_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.SNOW_GOLEM;
  }
}
