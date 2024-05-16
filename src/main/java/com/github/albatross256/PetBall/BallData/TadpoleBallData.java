package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class TadpoleBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.TADPOLE;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.TADPOLE_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.TADPOLE;
  }
}
