package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class StriderBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.STRIDER;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.STRIDER_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.STRIDER;
  }
}
