package com.github.albatross256.petball.balldata;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class RabbitBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.RABBIT;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.RABBIT_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.RABBIT;
  }
}
