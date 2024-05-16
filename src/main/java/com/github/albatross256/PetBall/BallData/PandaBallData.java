package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class PandaBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.PANDA;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.PANDA_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.PANDA;
  }
}
