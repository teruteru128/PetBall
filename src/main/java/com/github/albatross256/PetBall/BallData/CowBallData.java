package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class CowBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.COW;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.COW_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.COW;
  }
}
