package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class SquidBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.SQUID;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.SQUID_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.SQUID;
  }
}
