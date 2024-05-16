package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class DolphinBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.DOLPHIN;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.DOLPHIN_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.DOLPHIN;
  }
}
