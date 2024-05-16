package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class GoatBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.GOAT;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.GOAT_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.GOAT;
  }
}
