package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class MuleBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.MULE;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.MULE_SPAWN_EGG;
  }


  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.MULE;
  }
}
