package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class BatBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.BAT;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.BAT_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.BAT;
  }
}
